package godsejeong.com.genius.util

import android.content.Context
import android.util.Log
import godsejeong.com.genius.activity.DepartmentActivity
import godsejeong.com.genius.data.MoveDepartment
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoveUtils{
    fun move(context: Context,token : String, move_department : String){
        var res = Utils.postService.Move(token,move_department)
        res.enqueue(object : Callback<MoveDepartment>{
            override fun onFailure(call: Call<MoveDepartment>?, t: Throwable?) {
                context.toast("Server Error")
                Log.e("moveError",t!!.message)
            }

            override fun onResponse(call: Call<MoveDepartment>?, response: Response<MoveDepartment>) {
                var status = response.body()!!.status
                var message = response.body()!!.message
                when(status){
                    200 -> {
                        if(message == "이동에 성공하셨습니다"){
                            context.startActivity<DepartmentActivity>("department" to move_department)
                        }else if(message == "이동이 불가능합니다"){
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