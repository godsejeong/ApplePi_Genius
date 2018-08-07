package godsejeong.com.genius.util

import android.content.Context
import ninja.sakib.pultusorm.core.PultusORM

class ORMUtil(context: Context) {
    val appPath: String = context.filesDir.absolutePath
    var userORM = PultusORM("user.db" , appPath)
}