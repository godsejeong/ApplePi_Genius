package godsejeong.com.genius.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Utils {
    var url = "http://aws.soylatte.kr:3000"
    var retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val postService = retrofit!!.create(Services::class.java)
}

