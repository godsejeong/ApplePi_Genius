package godsejeong.com.genius.activity.popup

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.LinearLayout
import android.widget.PopupWindow
import godsejeong.com.genius.R
import godsejeong.com.genius.activity.FrieActivity
import godsejeong.com.genius.data.UserData
import godsejeong.com.genius.util.RealmUtils
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_firemessge_popup.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.util.*

class FiremessgePopupActivity : Activity() {
    var inputname = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_firemessge_popup)

        Realm.init(applicationContext)

        val popupView = layoutInflater.inflate(R.layout.activity_firemessge_popup, null)
        var mPopupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mPopupWindow.isFocusable = true

        var name = intent.getSerializableExtra("name") as ArrayList<String>
        var mRealm = Realm.getDefaultInstance()

        for (i in 0 until name.size) {
            inputname = inputname + name[i] + ", "
            if (RealmUtils().name() == name[i]) {

                mRealm.beginTransaction()
                var userdata: UserData = mRealm.createObject(UserData::class.java, UUID.randomUUID().toString())
                userdata.apply {
                    this.die = true
                }

                mRealm.commitTransaction()
                startActivity<FrieActivity>()
            }
        }

        if (name.size != 0) {
            firemessgeContent.text = inputname + "님이 해고 당하였습니다."
        }


        firemessgeCheck.onClick {
            finish()
        }
    }

}
