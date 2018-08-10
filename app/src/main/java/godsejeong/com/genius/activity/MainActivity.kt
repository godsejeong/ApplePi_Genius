package godsejeong.com.genius.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import godsejeong.com.genius.R
import godsejeong.com.genius.adapter.ProfileRecyclerAdapter
import godsejeong.com.genius.data.GameData
import godsejeong.com.genius.data.ProfileData
import godsejeong.com.genius.data.UserData
import godsejeong.com.genius.util.Utils
import io.realm.Realm
import kotlinx.android.synthetic.main.profile_recyclerview.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {
    lateinit var adapter: ProfileRecyclerAdapter
    var item: ArrayList<ProfileData> = ArrayList()
    var name = ""
    var img = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(applicationContext)

        var realm = Realm.getDefaultInstance()

        realm.where(UserData::class.java).findAll().forEach {
            name = it.user_name
        }
        realm.where(GameData::class.java).findAll().forEach {
            img = Utils.url + it.profile
        }

        var layoutManager = GridLayoutManager(this,5)
        profileRecyclerView.layoutManager = layoutManager

        item.add(ProfileData(name,img))

        for(i in 0..8){
            item.add(ProfileData("이름",Utils.url + "/img/profile.png"))
        }
        adapter = ProfileRecyclerAdapter(item,this)
        profileRecyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.logOut ->{
                var realm = io.realm.Realm.getDefaultInstance()

                val User = realm.where(UserData::class.java).findAll()
                val Game = realm.where(GameData::class.java).findAll()

                realm.beginTransaction()

                // Delete all matches
                User.deleteAllFromRealm()
                Game.deleteAllFromRealm()

                realm.commitTransaction()
                startActivity<TokenRegistrationActivity>()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
