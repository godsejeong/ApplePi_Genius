package godsejeong.com.genius.util

import android.content.Context
import godsejeong.com.genius.data.GameData
import godsejeong.com.genius.data.UserData
import io.realm.Realm
import ninja.sakib.pultusorm.core.PultusORM

class RealmUtils() {
    var realm = Realm.getDefaultInstance()

    fun name() : String{
        var name = ""
        realm.where(UserData::class.java).findAll().forEach {
            name =  it.user_name
        }
        return name
    }

    fun token() : String{
        var token = ""
        realm.where(UserData::class.java).findAll().forEach {
            token =  it.user_token
        }
        return token
    }

    fun profile() : String {
        var img = ""
        realm.where(GameData::class.java).findAll().forEach {
            img = Utils.url + it.profile
        }
        return img
    }
}