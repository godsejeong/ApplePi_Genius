package godsejeong.com.genius.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import godsejeong.com.genius.R
import godsejeong.com.genius.activity.TokenRegistrationActivity
import godsejeong.com.genius.data.GameData
import godsejeong.com.genius.data.UserData
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_start.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import org.jetbrains.anko.startActivity

class StartFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate( R.layout.fragment_start, container, false)

        view.startBtn.onClick {
            var transation = activity!!.supportFragmentManager.beginTransaction()
            transation.replace(R.id.mainLayout, DepartmentFragment())
            transation.commit()
        }

        view.startBtn.onLongClick {
            var realm = io.realm.Realm.getDefaultInstance()

            val User = realm.where(UserData::class.java).findAll()
            val Game = realm.where(GameData::class.java).findAll()

            realm.beginTransaction()

            // Delete all matches
            User.deleteAllFromRealm()
            Game.deleteAllFromRealm()

            realm.commitTransaction()
            activity!!.startActivity<TokenRegistrationActivity>()
            activity!!.finish()
        }
        return view
    }
}
