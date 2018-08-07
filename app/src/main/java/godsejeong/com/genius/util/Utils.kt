package godsejeong.com.genius.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Utils {
//    http://aws.soylatte.kr:5000
    var retrofit = Retrofit.Builder()
            .baseUrl("http://aws.soylatte.kr:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val postService = retrofit!!.create(Services::class.java)
}

