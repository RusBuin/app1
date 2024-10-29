import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.example.siet.R
import com.example.siet.domain.models.UserOfList
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class AdapterList(
    private val onFollowClick: (UserOfList) -> Unit,
    private val onUnfollowClick: (UserOfList) -> Unit
) : PagingDataAdapter<UserOfList, AdapterList.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.bind(it, onFollowClick, onUnfollowClick) }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.userName)
        private val userEmail: TextView = itemView.findViewById(R.id.userEmail)
        private val userAvatar: ImageView = itemView.findViewById(R.id.userAvatar)
        private val buttonFollow: Button = itemView.findViewById(R.id.buttonFollow)

        fun bind(
            user: UserOfList,
            onFollowClick: (UserOfList) -> Unit,
            onUnfollowClick: (UserOfList) -> Unit
        ) {
            userName.text = "${user.firstName} ${user.lastName}"
            userEmail.text = user.email
            Glide.with(itemView.context).load(user.avatar).into(userAvatar)

            // Установка обработчика для кнопки
            buttonFollow.setOnClickListener {
                onFollowClick(user) // Здесь просто вызываем onFollowClick для следования
            }
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<UserOfList>() {
        override fun areItemsTheSame(oldItem: UserOfList, newItem: UserOfList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserOfList, newItem: UserOfList): Boolean {
            return oldItem == newItem
        }
    }
}
