package godsejeong.com.genius.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import godsejeong.com.genius.R
import org.jetbrains.anko.toast

class FrieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frie)


    }

    override fun onBackPressed() {
        toast("넌 해고됐다")
    }

}
