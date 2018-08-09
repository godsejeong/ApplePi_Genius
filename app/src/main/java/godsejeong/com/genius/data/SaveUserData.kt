package godsejeong.com.genius.data

//SQLiteLog: (1) unrecognized token: "{" 이에러가 떠서 data를 분리시켜주기위해 만든 클래쓰임
class SaveUserData{
    var _id : String = ""
    var user_token : String = "" //유저 토큰을 의미한다
    var user_name : String = "" //유저 이름을 의미한다
    var setting : Boolean = false //유저 역할이 정해졌는지 여부를 의미한다
    var die : Boolean = false //유저가 해고됬는지 의미한다
    var now_room : String = "" //유저 위치를 의미한다
    var __v : Int? = null
}
