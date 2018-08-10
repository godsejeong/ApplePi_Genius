package godsejeong.com.genius.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import godsejeong.com.genius.R
import godsejeong.com.genius.activity.DepartmentActivity
import godsejeong.com.genius.data.GameData
import godsejeong.com.genius.data.UserData
import godsejeong.com.genius.util.MoveUtils
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_department.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class DepartmentFragment : Fragment(){
    var token = ""
    var department = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Realm.init(context)
        var view = inflater!!.inflate( R.layout.fragment_department, container, false)
        var intent = Intent(activity,DepartmentActivity::class.java)

        var realm = Realm.getDefaultInstance()

        realm.where(UserData::class.java).findAll().forEach {
            token = it.user_token
        }
        realm.where(GameData::class.java).findAll().forEach {
            department = it.department
        }

        view.insaBtn.onClick {
            intent.putExtra("department","인사")
            startActivity(intent)

            MoveUtils().move(getContext()!!,token,department,"인사")
        }

        view.ProductionBtn.onClick {
            intent.putExtra("department","영업")
            startActivity(intent)
        }

        view.salesBtn.onClick {
            intent.putExtra("department","생산")
            startActivity(intent)
        }

        return view
    }
}
