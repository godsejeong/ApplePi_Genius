package godsejeong.com.genius.util

import godsejeong.com.genius.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Services{
    //2TSka0qETEQs9ZlouQcVX3G2F5tfmlkZ
    @GET("/game/data/{token}")
    fun Token(@Path("token") token: String): Call<User>

}