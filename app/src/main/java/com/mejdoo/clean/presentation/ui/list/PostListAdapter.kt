package com.mejdoo.clean.presentation.ui.list


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mejdoo.clean.R
import com.mejdoo.clean.databinding.ItemPostListBinding
import com.mejdoo.clean.presentation.model.PostItem
import com.mejdoo.clean.presentation.ui.detail.PostDetailActivity
import com.mejdoo.clean.util.POST_ID_EXTRA_KEY
import com.mejdoo.clean.util.USER_ID_EXTRA_KEY


class PostListAdapter(private var postItems: MutableList<PostItem>) :
        RecyclerView.Adapter<PostViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->

            val item = postItems[v.tag as Int]

            val intent = Intent(v.context, PostDetailActivity::class.java).apply {
                putExtra(POST_ID_EXTRA_KEY, item.postId)
                putExtra(USER_ID_EXTRA_KEY, item.userId)
            }
            v.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val binding =
                DataBindingUtil.inflate<ItemPostListBinding>(
                        layoutInflater,
                        R.layout.item_post_list,
                        parent,
                        false
                )

        return PostViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.binding.postItem = postItems[position]
        holder.binding.executePendingBindings()

        with(holder.itemView) {
            tag = position
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount(): Int {
        return postItems.size
    }


}