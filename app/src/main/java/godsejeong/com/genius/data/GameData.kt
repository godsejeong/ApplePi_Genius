package godsejeong.com.genius.data

class GameData{
    var name : String = "" //유저 역할 이름을 의미한다 1
    var pay : String = "" //유저 연봉을 의미한다 1
    var pay_number : Number = 0 //유저 연봉 숫자표기 1
    var department : String = "" //유저 부서를 의미한다 1
    var win_condition : Array<String>? = null //유저 승리조건을 의미한다 1
    var lose_condition : String  = "" //유저 패배조건을 의미한다 1
    var uniqueness : Array<String>?  = null //유저 특이사항을 의미한다 1
    var rank : Number = 0 //유저 권력순위를 의미한다 1
    var profile : String = "" //유저 프로필 이미지 URL을 의미한다 1
    var card : String = "" //유저 카드 이미지 URL을 의미한다 1
}