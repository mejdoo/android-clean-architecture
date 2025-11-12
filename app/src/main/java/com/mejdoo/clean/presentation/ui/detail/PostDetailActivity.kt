package com.mejdoo.clean.presentation.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mejdoo.clean.R
import com.mejdoo.clean.databinding.ActivityPostDetailBinding
import com.mejdoo.clean.presentation.model.PostDetail
import com.mejdoo.clean.presentation.model.UiState
import com.mejdoo.clean.presentation.ui.misc.BaseActivity
import com.mejdoo.clean.presentation.viewmodel.PostDetailViewModel
import com.mejdoo.clean.util.AVATARS_URL
import com.mejdoo.clean.util.POST_ID_EXTRA_KEY
import com.mejdoo.clean.util.USER_ID_EXTRA_KEY
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

        // Get IDs from intent
        val postId = intent.getIntExtra(POST_ID_EXTRA_KEY, 0)
        val userId = intent.getIntExtra(USER_ID_EXTRA_KEY, 0)

        // Collect the post detail state
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                postDetailViewModel.postDetailState.collectLatest { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            // Show loading UI if needed
                            // binding.progressBar.visibility = View.VISIBLE
                        }

                        is UiState.Success -> {
                            // binding.progressBar.visibility = View.GONE
                            updateUi(uiState.data)
                        }

                        is UiState.Error -> {
                            // binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@PostDetailActivity,
                                uiState.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        // Load post detail
        postDetailViewModel.loadPostDetail(postId, userId)
    }

    private fun updateUi(postDetail: PostDetail) {
        binding.postTitle.text = postDetail.title
        binding.postBody.text = postDetail.body
        binding.postAuthor.text = postDetail.userName
        binding.postComments.text =
            getString(R.string.comments, postDetail.commentCount)
        Picasso.get().load("$AVATARS_URL${postDetail.userId}").into(binding.toolbarImage)
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
