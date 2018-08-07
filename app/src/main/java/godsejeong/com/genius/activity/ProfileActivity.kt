package godsejeong.com.genius.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import godsejeong.com.genius.R
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profilePhoto.onClick {

        }

        profileCheak.onClick {

        }
    }

    fun Profile(){

    }

}
