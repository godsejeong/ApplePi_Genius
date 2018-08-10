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
import godsejeong.com.genius.data.ProfileData

class ProfileRecyclerAdapter(items: ArrayList<ProfileData>, context: Context) : RecyclerView.Adapter<ProfileRecyclerAdapter.ViewHolder>() {
    var items: ArrayList<ProfileData> = ArrayList()
    var context: Context? = null

    init {
        this.items = items
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.profile_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = items[position]
        holder.name.text = data.name
        Glide.with(context!!).load(data.img).into(holder.img)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.profileitemName)
        var img: ImageView = itemView.findViewById(R.id.prfileitmeImg)
    }

}