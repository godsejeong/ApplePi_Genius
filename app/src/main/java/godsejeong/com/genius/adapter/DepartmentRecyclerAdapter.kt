package godsejeong.com.genius.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import godsejeong.com.genius.R
import godsejeong.com.genius.activity.popup.CardPopupActivity
import godsejeong.com.genius.activity.popup.FirePopupActivity
import godsejeong.com.genius.activity.popup.MemoPopupActivity
import godsejeong.com.genius.data.ProfileData
import godsejeong.com.genius.util.RealmUtils
import godsejeong.com.genius.util.RetrofitUtils
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onLongClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class DepartmentRecyclerAdapter(items: ArrayList<ProfileData>, context: Context) : RecyclerView.Adapter<DepartmentRecyclerAdapter.ViewHolder>() {
    var items: ArrayList<ProfileData> = ArrayList()
    var adaptercontext: Context? = null

    init {
        this.items = items
        this.adaptercontext = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.department_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = items[position]
        var intent = Intent(adaptercontext, FirePopupActivity::class.java)
        holder.name.text = data.name
        Glide.with(adaptercontext!!).load(data.img).into(holder.img)
        holder.itemView.onClick {

            if (RetrofitUtils.roundcheck) {
                if (RealmUtils().token() != data.token) {
                    intent.putExtra("name", data.name + "님을 해고하시겠습니까?")
                    intent.putExtra("oppenent_token", data.token)
                    adaptercontext!!.startActivity(intent)
                }
            }else if(RealmUtils().token() == data.token){
                adaptercontext!!.startActivity<CardPopupActivity>("img" to RealmUtils().profileCard())
            }else if(data.name == "이름"){
                adaptercontext!!.toast("존재하지 않는 사용자 입니다.")
            }else{
                adaptercontext!!.toast("라운드가 시작하지 않았습니다.")
            }
        }

        holder.itemView.onLongClick {
            if(RealmUtils().token() == data.token){
                adaptercontext!!.startActivity<CardPopupActivity>("img" to RealmUtils().profileCard())
            }else {
                adaptercontext!!.startActivity<MemoPopupActivity>("token" to data.token,
                        "name" to data.name)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.departmentitemText)
        var img: ImageView = itemView.findViewById(R.id.departmentitemImg)
    }
}
