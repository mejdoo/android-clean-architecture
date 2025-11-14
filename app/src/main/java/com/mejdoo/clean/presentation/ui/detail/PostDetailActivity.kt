package com.mejdoo.clean.presentation.ui.detail

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mejdoo.clean.presentation.model.PostDetail
import com.mejdoo.clean.presentation.model.UiState
import com.mejdoo.clean.presentation.ui.misc.BaseActivity
import com.mejdoo.clean.presentation.viewmodel.PostDetailViewModel
import com.mejdoo.clean.util.AVATARS_URL
import com.mejdoo.clean.util.POST_ID_EXTRA_KEY
import com.mejdoo.clean.util.USER_ID_EXTRA_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostDetailActivity : BaseActivity() {

    private val postDetailViewModel: PostDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val postId = intent.getIntExtra(POST_ID_EXTRA_KEY, 0)
        val userId = intent.getIntExtra(USER_ID_EXTRA_KEY, 0)

        postDetailViewModel.loadPostDetail(postId, userId)
    }

    @Composable
    override fun ScreenContent() {
        val uiState by postDetailViewModel.postDetailState.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "") },
                    navigationIcon = {
                        IconButton(onClick = { finish() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(), color = MaterialTheme.colors.background
            ) {

                when (uiState) {
                    is UiState.Loading -> {
                        // Could show progress indicator
                    }

                    is UiState.Success<*> -> {
                        UpdateUi((uiState as UiState.Success<com.mejdoo.clean.presentation.model.PostDetail>).data)
                    }

                    is UiState.Error -> {
                        val message = (uiState as UiState.Error).message
                        // In Compose we typically use Snackbar/Toast from activity; keep simple Text here
                        Text(text = message)
                    }
                }
            }
        }
    }
}

@Composable
fun UpdateUi(postDetail: PostDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        val painter = rememberAsyncImagePainter("${AVATARS_URL}${postDetail.userId}")
        Image(
            painter = painter, contentDescription = null, modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = postDetail.title ?: "",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = postDetail.body ?: "",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = postDetail.userName ?: "",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(
            text = "Comments: ${postDetail.commentCount}",
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
