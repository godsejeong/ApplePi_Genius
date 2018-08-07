package godsejeong.com.genius.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import godsejeong.com.genius.R
import godsejeong.com.genius.util.Utils
import godsejeong.com.genius.data.User
import godsejeong.com.genius.util.ORMUtil
import kotlinx.android.synthetic.main.activity_token_registration.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokenRegistrationActivity : AppCompatActivity() {
    var token: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_token_registration)

        tokenNext.onClick {
            token = tokenText.text.toString()

            if (token.isEmpty()) {
                tokenText.error = "토큰을 입력해주세요"
                tokenText.requestFocus()
            }

            if (token.isNotEmpty()) {
                var res = Utils.postService.Token(token)
                res.enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>?, t: Throwable?) {
                        Log.e("tokenError", t!!.message)
                        toast("Server Error")
                    }

                    override fun onResponse(call: Call<User>?, response: Response<User>) {
                        var status = response.body()!!.status

                        when(status) {
                            200 -> {
                                Log.e("test",Gson().toJson(response.body()))
                                ORMUtil(this@TokenRegistrationActivity).userORM.save(response.body()!!)
                                startActivity<ProfileActivity>()
                                finish()
                            }

                            401 -> {
                                Log.e("token401",response.message())
                                tokenText.text = null
                                tokenText.error = "존재하지 않는 토큰입니다."
                            }

                            else -> {

                            }
                        }
                    }
                })
            }
        }
    }
}
