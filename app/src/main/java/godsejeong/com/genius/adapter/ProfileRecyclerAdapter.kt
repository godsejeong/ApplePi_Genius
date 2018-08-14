package godsejeong.com.genius.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import godsejeong.com.genius.R
import godsejeong.com.genius.activity.popup.CardPopupActivity
import godsejeong.com.genius.activity.popup.MemoPopupActivity
import godsejeong.com.genius.data.ProfileData
import godsejeong.com.genius.util.RealmUtils
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import org.jetbrains.anko.startActivity

class ProfileRecyclerAdapter(items: ArrayList<ProfileData>, context: Context) : RecyclerView.Adapter<ProfileRecyclerAdapter.ViewHolder>() {
    var items: ArrayList<ProfileData> = ArrayList()
    var adaptercontext: Context? = null

    init {
        this.items = items
        this.adaptercontext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.profile_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = items[position]
        holder.name.text = data.name
        Glide.with(adaptercontext!!).load(data.img).into(holder.img)

        holder.itemView.onClick {
            if(RealmUtils().token() == data.token){
                adaptercontext!!.startActivity<CardPopupActivity>("img" to RealmUtils().profileCard())
            }
        }

        holder.itemView.onLongClick {
            if(RealmUtils().token() == data.token){
                adaptercontext!!.startActivity<CardPopupActivity>("img" to RealmUtils().profileCard())
            }else{
                adaptercontext!!.startActivity<MemoPopupActivity>("token" to data.token,
                        "name" to data.name)
            }

        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.profileitemName)
        var img: ImageView = itemView.findViewById(R.id.prfileitmeImg)
    }

}