package godsejeong.com.genius.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.R.id.message
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import godsejeong.com.genius.R
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import android.widget.TextView
import android.widget.FrameLayout
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import godsejeong.com.genius.data.GameData
import godsejeong.com.genius.data.SaveUserData
import godsejeong.com.genius.data.UserData
import io.realm.Realm
import io.realm.RealmObject.deleteFromRealm
import io.realm.RealmResults
import io.realm.RealmObject.deleteFromRealm


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        showTwoButtonSnackbar()

        Realm.getDefaultInstance().use { realm ->

            realm.where(UserData::class.java).findAll().forEach {
                profileName.text = it.user_name
            }

            realm.where(GameData::class.java).findAll().forEach {
                Glide.with(this).load("http://aws.soylatte.kr:3000" + it.profile).into(profilePhoto)
            }
        }
    }


    private fun showTwoButtonSnackbar() {

        // Create the Snackbar
        val objLayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        var snackbar = Snackbar.make(this.findViewById<View>(android.R.id.content), "", Snackbar.LENGTH_INDEFINITE)

        // Get the Snackbar layout view
        val layout = snackbar.view as Snackbar.SnackbarLayout

        // Set snackbar layout params
//        val navbarHeight = getNavBarHeight(this) as Int
        val parentParams = layout.layoutParams as FrameLayout.LayoutParams
        parentParams.setMargins(0, 0, 0, 0)
        layout.layoutParams = parentParams
        layout.setPadding(0, 0, 0, 0)
        layout.layoutParams = parentParams

        // Inflate our custom view
        val snackView = layoutInflater.inflate(R.layout.customsnackbar, null)

        // Configure our custom vie

        val textViewOne = snackView.findViewById(R.id.cheakView) as TextView
        textViewOne.setOnClickListener {
            startActivity<MainActivity>()
            finish()
            snackbar.dismiss()
        }

        val textViewTwo = snackView.findViewById(R.id.beforeView) as TextView
        textViewTwo.setOnClickListener {
            startActivity<TokenRegistrationActivity>()

            var realm = Realm.getDefaultInstance()

            val User = realm.where(UserData::class.java).findAll()
            val Game = realm.where(GameData::class.java).findAll()
            realm.beginTransaction()

            // Delete all matches
            User.deleteAllFromRealm()
            Game.deleteAllFromRealm()

            realm.commitTransaction()

            finish()

            snackbar.dismiss()
        }

        // Add our custom view to the Snackbar's layout
        layout.addView(snackView, objLayoutParams)

        // Show the Snackbar
        snackbar.show()
    }

}