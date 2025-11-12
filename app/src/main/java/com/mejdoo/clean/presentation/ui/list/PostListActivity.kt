package com.mejdoo.clean.presentation.ui.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mejdoo.clean.databinding.ActivityPostListBinding
import com.mejdoo.clean.presentation.model.UiState
import com.mejdoo.clean.presentation.ui.misc.BaseActivity
import com.mejdoo.clean.presentation.viewmodel.PostListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostListActivity : BaseActivity() {

    private lateinit var binding: ActivityPostListBinding
    private val postListViewModel: PostListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup adapter
        val adapter = PostListAdapter(mutableListOf())
        binding.recyclerView.adapter = adapter

        // Collect StateFlow from ViewModel and update UI
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                postListViewModel.postItemsState.collectLatest { uiState ->
                    when (uiState) {
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            adapter.submitList(uiState.data.toMutableList())
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@PostListActivity,
                                uiState.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}

