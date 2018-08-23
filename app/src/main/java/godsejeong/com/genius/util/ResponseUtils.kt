package godsejeong.com.genius.util

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.gson.JsonObject
import godsejeong.com.genius.activity.DepartmentActivity
import godsejeong.com.genius.activity.popup.FiremessgePopupActivity
import godsejeong.com.genius.data.BasicData
import godsejeong.com.genius.data.UserList
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResponseUtils{
    fun move(context: Context,token : String, move_department : String,activity: Activity){
        var res = RetrofitUtils.postService.Move(token,move_department)
        res.enqueue(object : Callback<BasicData>{
            override fun onFailure(call: Call<BasicData>?, t: Throwable?) {
                context.toast("Server Error")
                Log.e("moveError",t!!.message)
            }

            override fun onResponse(call: Call<BasicData>?, response: Response<BasicData>) {
                var status = response.body()!!.status
                var message = response.body()!!.message
                when(status){
                    200 -> {
                        if(message == "이동에 성공하셨습니다"){
                            context.startActivity<DepartmentActivity>("department" to move_department)
                            activity.finish()
                        }else if(message == "이동이 불가능합니다"){
                            context.toast("$message\n자신의 부서로 이동해 주세요")
                        }
                    }

                    else->{
                        context.toast(message)
                    }
                }
            }
        })
    }

    fun fire(context: Context,token : String,oppenent_token : String,name :String){
        var res =  RetrofitUtils.postService.Fire(token,oppenent_token)
        res.enqueue(object : Callback<BasicData>{
            override fun onFailure(call: Call<BasicData>?, t: Throwable?) {
                Log.e("firedata",t!!.message)
                context.toast("Server Error")
            }

            override fun onResponse(call: Call<BasicData>?, response: Response<BasicData>) {
                var message = response.body()!!.message
                var status = response.body()!!.status
                when(status){
                    200 ->{
                        if(message == "상대를 성공적으로 해고 시켰습니다!"){
                            context.toast(message)
                            RetrofitUtils.friecheak = true
                        }else if(message == "상대방을 해고 할 수 없습니다"){
                            context.toast(message)
                        }else if(message == "당신이 해고 되었습니다"){
                            context.toast(message)
                        }
                    }

                    else->{
                        context.toast(message)
                    }

                }
            }
        })
    }
}