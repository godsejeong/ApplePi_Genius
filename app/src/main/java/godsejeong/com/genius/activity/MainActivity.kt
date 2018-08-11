package godsejeong.com.genius.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import godsejeong.com.genius.R
import godsejeong.com.genius.adapter.ProfileRecyclerAdapter
import godsejeong.com.genius.data.GameData
import godsejeong.com.genius.data.ProfileData
import godsejeong.com.genius.data.UserData
import godsejeong.com.genius.fragment.DepartmentFragment
import godsejeong.com.genius.fragment.StartFragment
import godsejeong.com.genius.util.RealmUtils
import godsejeong.com.genius.util.Utils
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){
    lateinit var adapter: ProfileRecyclerAdapter
    var item: ArrayList<ProfileData> = ArrayList()
    var name = ""
    var img = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(applicationContext)

        var transation = supportFragmentManager.beginTransaction()
        transation.add(R.id.mainLayout,DepartmentFragment())
        transation.commit()

//        var realm = Realm.getDefaultInstance()
//
//        realm.where(UserData::class.java).findAll().forEach {
//            name = it.user_name
//        }
//        realm.where(GameData::class.java).findAll().forEach {
//            img = Utils.url + it.profile
//        }

        name = RealmUtils().name()
        img = Utils.url + RealmUtils().profile()

        var layoutManager = GridLayoutManager(this,5)
        mainRecycler.layoutManager = layoutManager

        item.add(ProfileData(name,img))

        for(i in 0..8){
            item.add(ProfileData("이름",Utils.url + "/img/profile.png"))
        }
        adapter = ProfileRecyclerAdapter(item,this)
        mainRecycler.adapter = adapter

    }
}
