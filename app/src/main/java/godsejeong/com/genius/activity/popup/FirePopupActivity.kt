package godsejeong.com.genius.activity.popup

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.LinearLayout
import android.widget.PopupWindow
import godsejeong.com.genius.R
import godsejeong.com.genius.util.RealmUtils
import godsejeong.com.genius.util.ResponseUtils
import kotlinx.android.synthetic.main.activity_fire_popup.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class FirePopupActivity : Activity() {
    var oppenent_token = ""
    var name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_fire_popup)

        val popupView = layoutInflater.inflate(R.layout.activity_fire_popup, null)
        var mPopupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mPopupWindow.isFocusable = true

        var intent = intent
        name = intent.getStringExtra("name")

        oppenent_token = intent.getStringExtra("oppenent_token")

        fireContent.text = name + "님을 해고하시겠습니까?"
        Log.e("namename",name)

        fireClear.onClick {
            finish()
        }

        fireCheck.onClick {
            if (oppenent_token == "") {
                toast("존재하지 않는 사용자 입니다.")
                finish()
            } else if (oppenent_token != null) {
//                toast("정상적으로 해고되었습니다.")
                ResponseUtils().fire(this@FirePopupActivity, RealmUtils().token(), oppenent_token,name)
                finish()
            }
        }
    }
}
