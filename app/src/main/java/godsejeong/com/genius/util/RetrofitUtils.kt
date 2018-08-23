package godsejeong.com.genius.util

import com.github.nkzawa.socketio.client.IO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtils {
    var url = "http://aws.soylatte.kr:3000"

    var socket = IO.socket(url)

    var friecheak = false

    var roundcheck = false

    var retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val postService = retrofit!!.create(Services::class.java)
}

