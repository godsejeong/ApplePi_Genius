package godsejeong.com.genius.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import godsejeong.com.genius.R
import godsejeong.com.genius.data.GameData
import godsejeong.com.genius.data.UserData
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import io.realm.RealmObject.deleteFromRealm



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLinear.onClick {

            var realm = io.realm.Realm.getDefaultInstance()

            val User = realm.where(UserData::class.java).findAll()
            val Game = realm.where(GameData::class.java).findAll()

            realm.beginTransaction()

            // Delete all matches
            User.deleteAllFromRealm()
            Game.deleteAllFromRealm()

            realm.commitTransaction()
        }
    }
}
