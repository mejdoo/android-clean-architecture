package com.mejdoo.clean.presentation.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.mejdoo.clean.R
import com.mejdoo.clean.presentation.model.PostDetail
import com.mejdoo.clean.presentation.model.Resource
import com.mejdoo.clean.presentation.ui.misc.BaseActivity
import com.mejdoo.clean.presentation.viewmodel.PostDetailViewModel
import com.mejdoo.clean.util.AVATARS_URL
import com.mejdoo.clean.util.POST_ID_EXTRA_KEY
import com.mejdoo.clean.util.USER_ID_EXTRA_KEY
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_detail.*
import org.koin.android.viewmodel.ext.android.viewModel


class PostDetailActivity : BaseActivity() {


    private val postDetailViewModel: PostDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.fadein, R.anim.fadeout)

        setContentView(R.layout.activity_post_detail)

        setSupportActionBar(detailToolbar)

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
            postTitle.text = postDetail.title
            postBody.text = postDetail.body
            postAuthor.text = postDetail.username
            postComments.text = String.format(resources.getString(R.string.comments), postDetail.nbComments)
            Picasso.get().load(AVATARS_URL + postDetail.userId).into(toolbarImage)
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
