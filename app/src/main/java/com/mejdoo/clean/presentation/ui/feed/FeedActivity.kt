package com.mejdoo.clean.presentation.ui.feed

import android.os.Bundle
import androidx.activity.compose.setContent
import com.mejdoo.clean.presentation.ui.misc.BaseActivity
import com.mejdoo.clean.presentation.ui.theme.CleanTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.mejdoo.clean.presentation.viewmodel.FeedViewModel

class FeedActivity : BaseActivity() {
    private val viewModel: FeedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContent {
            CleanTheme {
                FeedScreen(viewModel = viewModel)
            }
        }
    }
}
