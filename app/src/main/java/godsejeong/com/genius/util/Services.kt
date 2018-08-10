package godsejeong.com.genius.util

import godsejeong.com.genius.data.MoveDepartment
import godsejeong.com.genius.data.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Services {

    @GET("/game/data/{token}")
    fun Token(@Path("token") token: String): Call<User>

    @POST("/game/move")
    fun Move(@Field("user_token") user_token : String,
             @Field("move_department") move_department : String) : Call<MoveDepartment>
}