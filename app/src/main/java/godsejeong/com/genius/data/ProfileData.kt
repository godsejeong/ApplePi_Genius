package godsejeong.com.genius.data

class ProfileData(name : String,img : String,token : String){
    var img = ""
    var name = ""
    var token = ""
    init {
        this.token = token
        this.img = img
        this.name = name
    }
}