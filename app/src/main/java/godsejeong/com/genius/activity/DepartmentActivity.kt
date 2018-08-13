package godsejeong.com.genius.activity

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import godsejeong.com.genius.R
import kotlinx.android.synthetic.main.activity_department.*
import org.jetbrains.anko.backgroundResource
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.google.gson.Gson
import godsejeong.com.genius.adapter.DepartmentRecyclerAdapter
import godsejeong.com.genius.data.ProfileData
import godsejeong.com.genius.util.RealmUtils
import godsejeong.com.genius.util.RetrofitUtils
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class DepartmentActivity : AppCompatActivity() {
    var department = ""
    var item: ArrayList<ProfileData> = ArrayList()
    var name = ""
    var img = ""
    lateinit var adapter : DepartmentRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department)
        Realm.init(applicationContext)

        var layoutManager =object : GridLayoutManager(this,3){
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        departmentRecycler.layoutManager = layoutManager

        name = RealmUtils().name()
        img = RealmUtils().profile()
        item.add(ProfileData(name,img,""))

        for(i in 1..8){
            item.add(ProfileData("이름", RetrofitUtils.url + "/img/profile.png",""))
        }

        adapter = DepartmentRecyclerAdapter(item,this)
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
        Log.e("department",department)
        RetrofitUtils.socket.on("department",Department)
        RetrofitUtils.socket.connect()

    }

    var Department = Emitter.Listener {
        val receivedData = it[0] as JSONObject
        Log.e("receivedData", receivedData.toString())
    }

    fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            window.statusBarColor = ContextCompat.getColor(this,color)
        }
    }
}
