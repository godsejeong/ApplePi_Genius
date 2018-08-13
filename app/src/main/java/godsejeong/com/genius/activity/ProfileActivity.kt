package godsejeong.com.genius.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import godsejeong.com.genius.R
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.startActivity
import android.widget.TextView
import android.widget.FrameLayout
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import godsejeong.com.genius.data.GameData
import godsejeong.com.genius.data.UserData
import godsejeong.com.genius.util.RealmUtils
import io.realm.Realm


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Realm.init(applicationContext)
        showTwoButtonSnackbar()

        profileName.text = RealmUtils().name()
        Glide.with(this).load(RealmUtils().profile()).into(profilePhoto)

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
