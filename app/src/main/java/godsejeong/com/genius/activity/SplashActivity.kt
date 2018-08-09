package godsejeong.com.genius.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import godsejeong.com.genius.R
import godsejeong.com.genius.data.User
import godsejeong.com.genius.data.UserData
import godsejeong.com.genius.util.ORMUtil
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import android.view.WindowManager



class SplashActivity : AppCompatActivity() {

    lateinit var anim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var token: String?
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        var hd1 = Handler()
        var hd2 = Handler()

        try {
            var list: List<Any> = ORMUtil(this).userORM.find(UserData())
            var user = list[list.size - 1] as UserData
            token = user.user_token
            Log.e("token",token)
        }catch (e : ArrayIndexOutOfBoundsException){
            token = null
        }

        startAnimations()
        hd1.postDelayed({
            endAnimation()
            hd2.postDelayed({
                splashLayout.visibility = View.INVISIBLE

                if(token!=null) {
                    startActivity<MainActivity>()
                    finish()
                }else {
                    startActivity<TokenRegistrationActivity>()
                    finish()
                }
            },2500)
        },2000)
    }

    private fun startAnimations(){
        anim = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fadein)
        anim.reset()
        splashLayout.clearAnimation()
        splashLayout.startAnimation(anim)
    }

    private fun endAnimation(){
        anim = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fadeout)
        anim.reset()
        splashLayout.clearAnimation()
        splashLayout.startAnimation(anim)
    }

    override fun onBackPressed(){}
}
