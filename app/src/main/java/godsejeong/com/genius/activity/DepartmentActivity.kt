package godsejeong.com.genius.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.WindowManager
import godsejeong.com.genius.R
import kotlinx.android.synthetic.main.activity_department.*
import org.jetbrains.anko.backgroundResource
import android.app.Activity
import android.support.v4.content.ContextCompat


class DepartmentActivity : AppCompatActivity() {
    var department = ""
    var statusbarcolor = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department)

        department = intent.getStringExtra("department")

        when (department) {
            "인사" -> {
                setStatusBarColor(R.color.insa)
                departmentLayout.backgroundResource = R.color.insa
            }
            "영업" -> {
                setStatusBarColor(R.color.production)
                departmentLayout.backgroundResource = R.color.production
            }
            "생산" -> {
                setStatusBarColor(R.color.sales)
                departmentLayout.backgroundResource = R.color.sales
            }
        }



    }

    fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            window.statusBarColor = ContextCompat.getColor(this,color)
        }
    }
}
