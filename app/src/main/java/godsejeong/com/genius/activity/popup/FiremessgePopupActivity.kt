package godsejeong.com.genius.activity.popup

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.LinearLayout
import android.widget.PopupWindow
import godsejeong.com.genius.R
import kotlinx.android.synthetic.main.activity_firemessge_popup.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FiremessgePopupActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_firemessge_popup)

        val popupView = layoutInflater.inflate(R.layout.activity_firemessge_popup, null)
        var mPopupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mPopupWindow.isFocusable = true

        var name = intent.getStringExtra("name")

        firemessgeContent.text = name + "님이 해고 당하였습니다."

        firemessgeCheck.onClick {
            finish()
        }
    }
}
