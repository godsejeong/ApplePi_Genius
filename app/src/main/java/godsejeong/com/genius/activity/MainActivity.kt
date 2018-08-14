package godsejeong.com.genius.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.eclipsesource.json.JsonObject
import com.github.nkzawa.emitter.Emitter
import com.google.gson.Gson
import godsejeong.com.genius.R
import godsejeong.com.genius.adapter.ProfileRecyclerAdapter
import godsejeong.com.genius.data.ProfileData
import godsejeong.com.genius.fragment.DepartmentFragment
import godsejeong.com.genius.util.RealmUtils
import godsejeong.com.genius.util.RetrofitUtils
import godsejeong.com.genius.util.UserListUtils
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import com.google.gson.reflect.TypeToken
import godsejeong.com.genius.activity.popup.CardPopupActivity
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {
    lateinit var adapter: ProfileRecyclerAdapter
    var item: ArrayList<ProfileData> = ArrayList()
    var name = ""
    var img = ""
    var savei: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(applicationContext)
        var pref = getSharedPreferences("pref", Context.MODE_PRIVATE)

        var transation = supportFragmentManager.beginTransaction()
        transation.add(R.id.mainLayout, DepartmentFragment())
        transation.commit()

        name = RealmUtils().name()
        img = RealmUtils().profile()

        var layoutManager = object : GridLayoutManager(this, 5) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        mainRecycler.layoutManager = layoutManager

//        if(pref.getBoolean("game",false)){
//            load()
//        }else {
            item.add(ProfileData(name, img,RealmUtils().token()))

            for (i in 0..8) {
                item.add(ProfileData("이름", RetrofitUtils.url + "/img/profile.png", ""))
            }
//        }
        adapter = ProfileRecyclerAdapter(item, this)
        mainRecycler.adapter = adapter

        RetrofitUtils.socket.on("game_start_check", GaemStart)
        RetrofitUtils.socket.connect()
    }

    var GaemStart = Emitter.Listener {
        var pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        this.runOnUiThread {
            var editer = pref.edit()
            editer.putBoolean("game",it[0] as Boolean)
            editer.commit()

            if (pref.getBoolean("game",false)) {
                var userlist = UserListUtils(this, RealmUtils().token())
                userlist.start()
                userlist.join()

                var array = userlist.userlist() as org.json.simple.JSONArray?
                for (i in 0 .. array!!.size-2) {
                    var tmp = array!![i] as org.json.simple.JSONObject?

                    if (RealmUtils().token() == tmp!!.get("user_token") as String) {
                        savei = i
                    }

                    item[i] = (ProfileData(
                            tmp!!.get("user_name") as String,
                            RetrofitUtils.url + tmp!!.get("user_profile") as String,
                            tmp!!.get("user_token") as String))
                }
            }
            var firstimg = item[0].img
            var firstname = item[0].name
            var firsttoken = item[0].token

            var tempimg = item[savei].img
            var tempname = item[savei].name
            var temptoken = item[savei].token
            Log.e("tempimg",tempimg)

            item[0] = (ProfileData(
                    tempname,
                    tempimg,
                    temptoken))

            item[savei] = (ProfileData(
                    firstname,
                    firstimg,
                    firsttoken))
//            save()
            startActivity<CardPopupActivity>("img" to RealmUtils().profileCard())
            adapter.notifyDataSetChanged()
        }
    }

    fun save(){
        var pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = pref.edit()
        val json = Gson().toJson(item)
        editor.putString("save", json)
        editor.commit()
    }

    fun load(){
        var pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val json = pref.getString("save", "")
        var items: ArrayList<ProfileData>? = ArrayList()
        items = Gson().fromJson(json, object : TypeToken<ArrayList<ProfileData>>() {}.type)
        if (items != null) item.addAll(items)
    }
}
