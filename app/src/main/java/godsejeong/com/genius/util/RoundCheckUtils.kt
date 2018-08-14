package godsejeong.com.genius.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import godsejeong.com.genius.data.UserList
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import retrofit2.Call

class RoundCheckUtils(context: Context) : Thread() {
    var context: Context = context
    lateinit var data: org.json.simple.JSONArray
    val res: Call<UserList> = RetrofitUtils.postService.RoundCheck()
    var ruturndata = ""
    override fun run() {
        var status = res.execute().body()!!.status

        when (status) {
            200 -> {
                Log.e("test", "Test")
                data = JSONParser().parse(Gson().toJson(res.clone().execute().body()!!.data)) as org.json.simple.JSONArray

                var tmp = data[0] as JSONObject
                ruturndata = tmp!!.get("now_round") as String
            }
        }
    }

    fun Cheak(): String {
        return ruturndata
    }
}