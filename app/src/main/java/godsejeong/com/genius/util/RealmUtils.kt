package godsejeong.com.genius.util

import android.content.Context
import godsejeong.com.genius.data.GameData
import godsejeong.com.genius.data.UserData
import io.realm.Realm

class RealmUtils() {
    var realm = Realm.getDefaultInstance()
    var userdata = realm.where(UserData::class.java).findAll()
    var gamedata =  realm.where(GameData::class.java).findAll()
    fun name() : String{
        var name = ""
        userdata.forEach {
            name =  it.user_name
        }
        return name
    }

    fun token() : String{
        var token = ""
        userdata.forEach {
            token =  it.user_token
        }
        return token
    }

    fun profile() : String {
        var img = ""
       gamedata.forEach {
            img = RetrofitUtils.url + it.profile
        }
        return img
    }

    fun profileCard() : String {
        var img = ""
        gamedata.forEach {
            img = RetrofitUtils.url + it.card
        }
        return img
    }
}