package godsejeong.com.genius.util

import godsejeong.com.genius.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Services{

    @GET("/game/data/{token}")
    fun Token(@Path("token") token: String): Call<User>

    }