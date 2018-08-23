package godsejeong.com.genius.activity

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.WindowManager
import godsejeong.com.genius.R
import kotlinx.android.synthetic.main.activity_department.*
import org.jetbrains.anko.backgroundResource
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.google.gson.Gson
import godsejeong.com.genius.activity.popup.FiremessgePopupActivity
import godsejeong.com.genius.adapter.DepartmentRecyclerAdapter
import godsejeong.com.genius.data.MembarData
import godsejeong.com.genius.data.ProfileData
import godsejeong.com.genius.util.FireMessgeUtils
import godsejeong.com.genius.util.RealmUtils
import godsejeong.com.genius.util.RetrofitUtils
import godsejeong.com.genius.util.RoundCheckUtils
import io.realm.Realm
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.simple.JSONArray
import org.json.simple.parser.JSONParser
import retrofit2.Call
import java.util.*
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


class DepartmentActivity : AppCompatActivity() {
    var department = ""
    var item: ArrayList<ProfileData> = ArrayList()
    var name = ""
    var img = ""
    var savei = 0
    lateinit var adapter: DepartmentRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department)
        Realm.init(applicationContext)

        var roundcheak = RoundCheckUtils(this)
        roundcheak.start()
        roundcheak.join()

        var round = roundcheak.Cheak()

        if (round == "round_one") {
            departmentroundOne.setBackgroundResource(R.drawable.departmentround_select_background)
        } else if (round == "round_two") {
            departmentroundOne.setBackgroundResource(R.drawable.departmentround_background)
            departmentroundTwo.setBackgroundResource(R.drawable.departmentround_select_background)
        } else if (round == "round_three") {
            departmentroundTwo.setBackgroundResource(R.drawable.departmentround_background)
            departmentroundThree.setBackgroundResource(R.drawable.departmentround_select_background)
        } else if (round == "round_four") {
            departmentroundThree.setBackgroundResource(R.drawable.departmentround_background)
            departmentroundFour.setBackgroundResource(R.drawable.departmentround_select_background)
        }


        var layoutManager = object : GridLayoutManager(this, 3) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        departmentRecycler.layoutManager = layoutManager

        name = RealmUtils().name()
        img = RealmUtils().profile()
        item.add(ProfileData(name, RetrofitUtils.url + "/img/profile.png", RealmUtils().token()))

        for (i in 0..9) {
            item.add(ProfileData("이름", RetrofitUtils.url + "/img/profile.png", ""))
        }

        adapter = DepartmentRecyclerAdapter(item, this)
        departmentRecycler.adapter = adapter

        department = intent.getStringExtra("department")
        when (department) {
            "인사" -> {
                setStatusBarColor(R.color.insa)
                departmentLayout.backgroundResource = R.color.insa
            }
            "영업" -> {
                setStatusBarColor(R.color.production)
                departmentLayout.backgroundResource = R.color.production
            }
            "생산" -> {
                setStatusBarColor(R.color.sales)
                departmentLayout.backgroundResource = R.color.sales
            }
        }
        Log.e("department", department)

//      RetrofitUtils.socket.on("department", Department)
        RetrofitUtils.socket.on("round_start_check", Round)
        RetrofitUtils.socket.connect()
    }

    fun Start() {
        var res = RetrofitUtils.postService.Member(RealmUtils().token())
        res.enqueue(object : Callback<MembarData> {
            override fun onFailure(call: Call<MembarData>?, t: Throwable?) {
                toast("ServerError")
                Log.e("memobarerror", t!!.message)
            }

            override fun onResponse(call: Call<MembarData>?, response: Response<MembarData>) {
                var status = response.body()!!.status
                when (status) {
                    200 -> {
                        var nowRoom = response.body()!!.data.room_name

                        if (nowRoom == department) {
                            var array = JSONParser().parse(Gson().toJson(response.body()!!.data.user_list)) as JSONArray
                            for (i in 0 until array.size) {
                                var tmp = array[i] as? org.json.simple.JSONObject

                                try {
                                    if (RealmUtils().token() == tmp!!.get("user_token") as String) {
                                        savei = i
                                    }

                                    item[i] = (ProfileData(
                                            tmp!!.get("user_name") as String,
                                            RetrofitUtils.url + "/img/profile.png",
                                            tmp!!.get("user_token") as String))
                                } catch (e: KotlinNullPointerException) {}
                            }

                            var firstimg = item[0].img
                            var firstname = item[0].name
                            var firsttoken = item[0].token

                            var tempimg = item[savei].img
                            var tempname = item[savei].name
                            var temptoken = item[savei].token

                            item[0] = (ProfileData(
                                    tempname,
                                    tempimg,
                                    temptoken))

                            item[savei] = (ProfileData(
                                    firstname,
                                    firstimg,
                                    firsttoken))

                            adapter.notifyDataSetChanged()
                        }
                    }
                    else -> {
                        toast(response.body()!!.message)
                    }
                }
            }
        })
    }

    var h = 10
    var s = 0
    var tt = timmer()
    var Round = Emitter.Listener {
        this.runOnUiThread {
            RetrofitUtils.roundcheck = it[0] as Boolean

            Log.e("Roundcheak", RetrofitUtils.roundcheck.toString())

            if (RetrofitUtils.roundcheck) {
                tt = timmer()
                Timer().schedule(tt, 0, 1000)
                toast("라운드가 시작되었습니다.")
                Start()
            } else if (!RetrofitUtils.roundcheck) {
                toast("라운드가 종료되었습니다.")
                startActivity<MainActivity>()
                finish()
                try {
                    var firelist = firelist()
                    startActivity<FiremessgePopupActivity>("name" to firelist)
                } catch (e: NullPointerException) {
                    Log.e("Nullerror", e.toString())
                }
                RetrofitUtils.friecheak = false
                RetrofitUtils.roundcheck = false
                tt!!.cancel()
            }
        }
    }

    fun timmer(): TimerTask {
        var tt = object : TimerTask() {

            val finish = object : Handler() {
                override fun handleMessage(msg: Message) {
                    departmenttime.text = h.toString() + ":" + s
                    toast("라운드가 종료되었습니다.")
                    startActivity<MainActivity>()
                    finish()
                    try {
                        var firelist = firelist()
                        startActivity<FiremessgePopupActivity>("name" to firelist)
                    } catch (e: NullPointerException) {
                        Log.e("Nullerror", e.toString())
                    }

                    RetrofitUtils.roundcheck = false
                }
            }

            val handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    departmenttime.text = h.toString() + ":" + s
                }
            }

            override fun run() {

                if (s <= 1 && h <= 0) {
                    h = 0
                    s = 0

                    val msg = finish.obtainMessage()
                    finish.sendMessage(msg)
                    this.cancel()
                } else {

                    if (s <= 1) {
                        h -= 1
                        s = 61
                    }

                    Log.e(h.toString(), s.toString())

                    s -= 1

                    val msg = handler.obtainMessage()
                    handler.sendMessage(msg)
                }
            }
        }
        return tt
    }

    //back버튼 막기
    override fun onBackPressed() {
        toast("라운드가 끌나기 전까지 이동이 불가합니다.")
    }

    fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            window.statusBarColor = ContextCompat.getColor(this, color)
        }
    }

    fun firelist(): ArrayList<String> {
        var firelist = FireMessgeUtils(this)
        firelist.start()
        firelist.join()
        return firelist.getname()
    }

}
