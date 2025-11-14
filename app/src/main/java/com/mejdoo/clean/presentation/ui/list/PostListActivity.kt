package com.mejdoo.clean.presentation.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mejdoo.clean.presentation.model.PostItem
import com.mejdoo.clean.presentation.model.UiState
import com.mejdoo.clean.presentation.ui.detail.PostDetailActivity
import com.mejdoo.clean.presentation.ui.misc.BaseActivity
import com.mejdoo.clean.presentation.viewmodel.PostListViewModel
import com.mejdoo.clean.util.POST_ID_EXTRA_KEY
import com.mejdoo.clean.util.USER_ID_EXTRA_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostListActivity : BaseActivity() {

    private val postListViewModel: PostListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Keep any non-UI initialization here. UI is provided via ScreenContent().
    }

    @Composable
    override fun ScreenContent() {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            PostListScreen(postListViewModel) { postId, userId ->
                val intent = Intent(this@PostListActivity, PostDetailActivity::class.java).apply {
                    putExtra(POST_ID_EXTRA_KEY, postId)
                    putExtra(USER_ID_EXTRA_KEY, userId)
                }
                startActivity(intent)
            }
        }
    }
}

@Composable
fun PostListScreen(viewModel: PostListViewModel, onItemClick: (Int, Int) -> Unit) {
    val uiState by viewModel.postItemsState.collectAsState()

    when (uiState) {
        is UiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 48.dp))
            }
        }

        is UiState.Success<*> -> {
            val list = (uiState as UiState.Success<List<PostItem>>).data
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(list) { item ->
                    PostListItem(item.title, onClick = { onItemClick(item.postId, item.userId) })
                }
            }
        }

        is UiState.Error -> {
            val message = (uiState as UiState.Error).message
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = message)
            }
        }
    }
}

@Composable
fun PostListItem(title: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(16.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1
        )
    }
}
