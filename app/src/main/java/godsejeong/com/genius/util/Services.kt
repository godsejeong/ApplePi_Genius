package godsejeong.com.genius.util

import godsejeong.com.genius.data.BasicData
import godsejeong.com.genius.data.MembarData
import godsejeong.com.genius.data.User
import godsejeong.com.genius.data.UserList
import retrofit2.Call
import retrofit2.http.*

interface Services {

    @GET("/game/data/{token}")
    fun Token(@Path("token") token: String): Call<User>

    @FormUrlEncoded
    @POST("/game/move")
    fun Move(@Field("user_token") user_token : String,
             @Field("move_department") move_department : String) : Call<BasicData>

    @FormUrlEncoded
    @POST("/game/fire")
    fun Fire(@Field("user_token") user_token : String,
             @Field("oppenent_token ") oppenent_token : String) : Call<BasicData>

    @GET("/game/user/list/{token}")
    fun UserList(@Path("token") token : String) : Call<UserList>

    @GET("/game/check/start")
    fun RoundCheck() : Call<UserList>

    @GET("/game/room/member/")
    fun Member(@Path("token") token: String) : Call<MembarData>

}