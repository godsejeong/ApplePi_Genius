package godsejeong.com.genius.activity.popup

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.Window
import android.widget.LinearLayout
import android.widget.PopupWindow
import godsejeong.com.genius.R
import kotlinx.android.synthetic.main.activity_memo_popup.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import android.R.id.edit
import android.content.SharedPreferences



class MemoPopupActivity : Activity() {
    var token = ""
    var name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_memo_popup)

        val popupView = layoutInflater.inflate(R.layout.activity_memo_popup, null)
        var mPopupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mPopupWindow.isFocusable = true

        token = intent.getStringExtra("token")
        name = intent.getStringExtra("name")

        memoContent.setText(load(token))
        memoName.text = name

        memoClear.onClick {
            remove(token)
            finish()
        }

        memoCheck.onClick {
            finish()
        }

        memoSave.onClick {
            var content = memoContent.text.toString()
            save(token,content)
            finish()
        }
    }

    fun save(token: String,content : String) {
        var pref = getSharedPreferences("prfe", Context.MODE_PRIVATE)
        var edit = pref.edit()
        edit.putString(token,content)
        edit.commit()
    }

    fun remove(token : String){
        val pref = getSharedPreferences("prfe", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.remove(token)
        editor.commit()
    }

    fun load(token : String) : String {
        var pref = getSharedPreferences("prfe", Context.MODE_PRIVATE)
        return pref.getString(token,"")
    }
}
