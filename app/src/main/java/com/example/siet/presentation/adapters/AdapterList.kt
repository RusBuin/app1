//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.siet.R
//import com.example.siet.domain.models.UserOfList
//import kotlinx.android.synthetic.main.item_user.view.*
//
//class AdapterList(
//    private val users: List<UserOfList>
//) : RecyclerView.Adapter<AdapterList.UserViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
//        return UserViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
//        val user = users[position]
//        holder.bind(user)
//    }
//
//    override fun getItemCount() = users.size
//
//    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bind(user: UserOfList) {
//            itemView.userName.text = "${user.firstName} ${user.lastName}"
//            itemView.userEmail.text = user.email
//            Glide.with(itemView.context).load(user.avatar).into(itemView.userAvatar)
//        }
//    }
//}
