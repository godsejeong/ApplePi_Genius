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
import godsejeong.com.genius.util.ResponseUtils
import godsejeong.com.genius.util.RetrofitUtils
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_department.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.toast

class DepartmentFragment : Fragment() {
    var token = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Realm.init(context)
        var view = inflater!!.inflate(R.layout.fragment_department, container, false)

        var realm = Realm.getDefaultInstance()

        realm.where(UserData::class.java).findAll().forEach {
            token = it.user_token
        }

        view.departmentText.onLongClick {
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

        view.insaBtn.onClick {
            if (RetrofitUtils.gameStart)
                ResponseUtils().move(getContext()!!, token, "인사")
            else
                toast("게임이 시작 후 이동이 가능합니다.")
        }
        view.ProductionBtn.onClick {
            if (RetrofitUtils.gameStart)
                ResponseUtils().move(getContext()!!, token, "영업")
            else
                toast("게임이 시작 후 이동이 가능합니다.")
        }

        view.salesBtn.onClick {
            if (RetrofitUtils.gameStart)
                ResponseUtils().move(getContext()!!, token, "생산")
            else
                toast("게임이 시작 후 이동이 가능합니다.")
        }

        return view
    }
}
