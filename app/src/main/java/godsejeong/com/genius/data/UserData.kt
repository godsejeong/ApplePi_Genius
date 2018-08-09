package godsejeong.com.genius.data

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey


open class UserData : RealmObject() {
    @io.realm.annotations.PrimaryKey
    open  var _id: String = ""
    open lateinit var user_token: String //유저 토큰을 의미한다
    open lateinit var user_name: String //유저 이름을 의미한다
    open var setting: Boolean = false //유저 역할이 정해졌는지 여부를 의미한다
    open var die: Boolean = false//유저가 해고됬는지 의미한다
    open lateinit var now_room: String //유저 위치를 의미한다
    open var game_data: GameData? = null //유저 역할에 대한 정보를 가지고있다
    open var __v: Int = 0
}