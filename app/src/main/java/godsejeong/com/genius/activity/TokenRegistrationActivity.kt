package godsejeong.com.genius.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import godsejeong.com.genius.R
import godsejeong.com.genius.data.GameData
import godsejeong.com.genius.util.RetrofitUtils
import godsejeong.com.genius.data.User
import godsejeong.com.genius.data.UserData
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_token_registration.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TokenRegistrationActivity : AppCompatActivity() {

    var token: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_token_registration)
        Realm.init(applicationContext)

        tokenNext.onClick {
            token = tokenText.text.toString()

            if (token.isEmpty()) {
                tokenText.error = "토큰을 입력해주세요"
                tokenText.requestFocus()
            }

            if (token.isNotEmpty()) {
                var res = RetrofitUtils.postService.Token(token)
                res.enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>?, t: Throwable?) {
                        Log.e("tokenError", t!!.message)
                        toast("Server Error")
                    }

                    override fun onResponse(call: Call<User>?, response: Response<User>) {
                        var status = response.body()!!.status

                        when(status) {
                            200 -> {
                                var mRealm = Realm.getDefaultInstance()

                                mRealm.beginTransaction()
                                var userdata : UserData = mRealm.createObject(UserData::class.java,UUID.randomUUID().toString())
                                var user = mRealm.copyFromRealm(userdata)
                                user.apply {
                                    this._id = response.body()!!.data!!._id
                                    this.user_token = response.body()!!.data!!.user_token
                                    this.die = response.body()!!.data!!.die
                                    this.user_name = response.body()!!.data!!.user_name
                                    this.setting = response.body()!!.data!!.setting
                                    this.now_room = response.body()!!.data!!.now_room
                                }
                                mRealm.copyToRealm(user)
                                var gamedata : GameData = mRealm.createObject(GameData::class.java,UUID.randomUUID().toString())
                                var game = mRealm.copyFromRealm(gamedata)
                                game.apply {
                                    this.card = response.body()!!.data!!.game_data!!.card
                                    this.department = response.body()!!.data!!.game_data!!.department
                                    this.lose_condition = response.body()!!.data!!.game_data!!.lose_condition
                                    this.name = response.body()!!.data!!.game_data!!.name
                                    this.pay = response.body()!!.data!!.game_data!!.pay
                                    this.profile = response.body()!!.data!!.game_data!!.profile
                                    this.pay_number = response.body()!!.data!!.game_data!!.pay_number
                                    this.rank = response.body()!!.data!!.game_data!!.rank
                                    this.uniqueness = response.body()!!.data!!.game_data!!.uniqueness
                                    this.win_condition = response.body()!!.data!!.game_data!!.win_condition
                                }
                                mRealm.copyToRealm(game)
                                mRealm.commitTransaction()

                                Log.e("test",Gson().toJson(response.body()!!.data))

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
