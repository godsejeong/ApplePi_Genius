package godsejeong.com.genius.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import godsejeong.com.genius.data.UserList
import org.json.JSONObject
import org.json.simple.parser.JSONParser
import retrofit2.Call

class UserListUtils(context: Context, token: String) : Thread() {
    var context: Context = context
    var token: String = token
    lateinit var data : org.json.simple.JSONArray
    val res: Call<UserList> = RetrofitUtils.postService.UserList(token)

    override fun run() {
        var status = res.execute().body()!!.status

        when (status) {
            200 -> {
                Log.e("test","Test")
                data = JSONParser().parse(Gson().toJson(res.clone().execute().body()!!.data)) as org.json.simple.JSONArray
            }
        }
    }

    fun userlist(): org.json.simple.JSONArray {
        return data
    }

}