package godsejeong.com.genius.activity.popup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import godsejeong.com.genius.R
import android.R.attr.src
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import android.graphics.BitmapFactory.decodeResource
import android.graphics.Matrix
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_card_popup.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class CardPopupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_popup)
        val popupView = layoutInflater.inflate(R.layout.activity_fire_popup, null)
        var mPopupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        mPopupWindow.isFocusable = true

        var img = intent.getStringExtra("img")
        Glide.with(this).load(img).into(cardImg)
        cardClear.setImageBitmap(rotateImage(decodeResource(resources,R.drawable.ic_add_circle_outline_black_24dp),90F))

        cardClear.onClick {
            finish()
        }
    }

    // 이미지 회전 함수
   fun rotateImage(src : Bitmap,degree : Float) : Bitmap{
        // Matrix 객체 생성
        var matrix = Matrix()
        // 회전 각도 셋팅
        matrix.postRotate(degree)
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.width,src.height, matrix, true)
    }

}
