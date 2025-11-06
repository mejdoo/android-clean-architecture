package com.mejdoo.clean.presentation.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.mejdoo.clean.R
import com.mejdoo.clean.databinding.ActivityPostDetailBinding
import com.mejdoo.clean.presentation.model.PostDetail
import com.mejdoo.clean.presentation.model.Resource
import com.mejdoo.clean.presentation.ui.misc.BaseActivity
import com.mejdoo.clean.presentation.viewmodel.PostDetailViewModel
import com.mejdoo.clean.util.AVATARS_URL
import com.mejdoo.clean.util.POST_ID_EXTRA_KEY
import com.mejdoo.clean.util.USER_ID_EXTRA_KEY
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityPostDetailBinding
    private val postDetailViewModel: PostDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.fadein, R.anim.fadeout)

        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val postId = intent.getIntExtra(POST_ID_EXTRA_KEY, 0)
        val userId = intent.getIntExtra(USER_ID_EXTRA_KEY, 0)

        postDetailViewModel.getPostDetail(postId, userId)

        postDetailViewModel.postDetailLiveData.observe(
            this,
            Observer<Resource<PostDetail>> { updateUi(it.data as PostDetail?) })

    }

    private fun updateUi(postDetail: PostDetail?) {


        if (postDetail != null) {
            binding.postTitle.text = postDetail.title
            binding.postBody.text = postDetail.body
            binding.postAuthor.text = postDetail.username
            binding.postComments.text =
                String.format(resources.getString(R.string.comments), postDetail.nbComments)
            Picasso.get().load(AVATARS_URL + postDetail.userId).into(binding.toolbarImage)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {

                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }


}
