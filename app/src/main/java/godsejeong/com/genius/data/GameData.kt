package godsejeong.com.genius.data
import io.realm.RealmList
import io.realm.RealmObject
import ninja.sakib.pultusorm.annotations.AutoIncrement
import ninja.sakib.pultusorm.annotations.PrimaryKey

open class GameData : RealmObject(){
    @io.realm.annotations.PrimaryKey
    var name : String = ""  //유저 역할 이름을 의미한다
    var pay : String = "" //유저 연봉을 의미한다
    var pay_number : Int? = null //유저 연봉 숫자표기
    var department : String = "" //유저 부서를 의미한다
    var win_condition : RealmList<String>? = null //유저 승리조건을 의미한다
    var lose_condition : String  = "" //유저 패배조건을 의미한다
    var uniqueness : RealmList<String>?  = null //유저 특이사항을 의미한다
    var rank : Int? = null //유저 권력순위를 의미한다
    var profile : String = "" //유저 프로필 이미지 URL을 의미한다
    var card : String = "" //유저 카드 이미지 URL을 의미한다
}