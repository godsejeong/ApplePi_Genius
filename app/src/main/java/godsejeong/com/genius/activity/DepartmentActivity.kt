package godsejeong.com.genius.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.WindowManager
import godsejeong.com.genius.R
import kotlinx.android.synthetic.main.activity_department.*
import org.jetbrains.anko.backgroundResource
import android.app.Activity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import godsejeong.com.genius.adapter.DepartmentRecyclerAdapter
import godsejeong.com.genius.data.ProfileData
import godsejeong.com.genius.util.RealmUtils
import godsejeong.com.genius.util.Utils
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*


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

        var layoutManager = GridLayoutManager(this,3)
        departmentRecycler.layoutManager = layoutManager

        name = RealmUtils().name()
        img = RealmUtils().profile()
        item.add(ProfileData(name,img))

        for(i in 1..8){
            item.add(ProfileData("이름", Utils.url + "/img/profile.png"))
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
