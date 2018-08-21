package godsejeong.com.genius.util

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import godsejeong.com.genius.data.FireListData
import godsejeong.com.genius.data.User
import godsejeong.com.genius.data.UserList
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import retrofit2.Call

class FireMessgeUtils(context: Context) : Thread() {
    var context: Context = context
    val res: Call<FireListData> = RetrofitUtils.postService.FileList()
    var returndata : ArrayList<String> = ArrayList()

    override fun run() {
        var status = res.execute().body()!!.status

        when (status) {
            200 -> {

                Log.e("test", "Test")
                var array =JSONParser().parse(Gson().toJson(res.clone().execute().body()!!.data)) as org.json.simple.JSONArray
                for(i in 0 until array.size){
                    var tmp = array[i] as JSONObject
                    var name = tmp.get("user_name") as String
                    Log.e("name",name)
                    returndata!!.add(name)
                }
            }

            404->{
                Log.e("userlist","쨋든에러")
            }
        }
    }

    fun getname() : ArrayList<String>{
        return returndata!!
    }
}