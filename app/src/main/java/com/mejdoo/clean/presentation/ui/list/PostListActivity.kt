package com.mejdoo.clean.presentation.ui.list


import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.mejdoo.clean.R
import com.mejdoo.clean.databinding.ActivityPostListBinding

import com.mejdoo.clean.presentation.ui.misc.BaseActivity
import com.mejdoo.clean.presentation.viewmodel.PostListViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class PostListActivity : BaseActivity() {

    private lateinit var binding: ActivityPostListBinding

    private val postListViewModel: PostListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_list)


        binding.lifecycleOwner = this

        binding.viewModel = postListViewModel


        postListViewModel.getPostList()

    }
}
