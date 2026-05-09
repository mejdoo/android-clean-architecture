package com.mejdoo.clean.presentation.ui.feed

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import coil.compose.rememberAsyncImagePainter
import com.mejdoo.clean.domain.usecase.CombinedPostUserComments
import com.mejdoo.clean.presentation.model.PostDetail
import com.mejdoo.clean.presentation.model.UiState
import com.mejdoo.clean.presentation.viewmodel.FeedViewModel
import com.mejdoo.clean.util.AVATARS_URL
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.listSaver

// Colors & constants
private val PrimaryStart = Color(0xFF0B2545)
private val PrimaryEnd = Color(0xFF3B82F6)
private val Accent = Color(0xFFFF6B6B)
private val MutedText = Color(0xFF475569)

private val CARD_CORNER = 14.dp
private val CARD_ELEVATION = 8.dp
private val AVATAR_SIZE = 48.dp
private val THUMB_SIZE = 72.dp
private val COMMENT_AVATAR_SIZE = 38.dp
private const val COMMENT_AVATAR_ALPHA = 0.85f
private val COMMENT_ITEM_HEIGHT = 56.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedScreen(viewModel: FeedViewModel) {
    val uiState by viewModel.state.collectAsState()
    val selectedState by viewModel.selectedDetails.collectAsState()
    val searchState = rememberSaveable { mutableStateOf("") }
    val isRefreshing = uiState is UiState.Loading

    val pullRefreshState = rememberPullRefreshState(isRefreshing, { viewModel.reload() })

    androidx.compose.material.Scaffold(
        topBar = {
            androidx.compose.material.TopAppBar(
                title = {
                    Text(
                        text = "Your Feed",
                        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraBold)
                    )
                },
                backgroundColor = androidx.compose.material.MaterialTheme.colors.surface,
                actions = {
                    IconButton(onClick = { viewModel.reload() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HeaderSection(search = searchState.value, onSearchChange = { searchState.value = it })

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
            ) {
                when (val state = uiState) {
                    is UiState.Loading -> LoadingState()
                    is UiState.Error -> ErrorState(state.message)
                    is UiState.Success -> {
                        FeedBody(
                            data = state.data,
                            search = searchState.value,
                            loadPostDetails = { postId, userId -> viewModel.loadPostDetails(postId, userId) },
                            selectedState = selectedState
                        )
                    }
                }
                PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
            }
        }
    }
}

@Composable
private fun HeaderSection(search: String, onSearchChange: (String) -> Unit) {
    val headerBrush = Brush.horizontalGradient(listOf(PrimaryStart, PrimaryEnd))

    Surface(modifier = Modifier.fillMaxWidth(), elevation = 6.dp) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerBrush)
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Good afternoon",
                        style = MaterialTheme.typography.subtitle2,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Mejdoo",
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold),
                        color = Color.White
                    )
                }

                val avatar = "${AVATARS_URL}0"
                Image(
                    painter = rememberAsyncImagePainter(avatar),
                    contentDescription = "profile",
                    modifier = Modifier
                        .size(AVATAR_SIZE)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            SearchBar(value = search, onValueChange = onSearchChange)
        }
    }
}

@Composable
private fun SearchBar(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        placeholder = { Text("Search posts, users, content...") },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White.copy(alpha = 0.06f)),
        singleLine = true,
        textStyle = MaterialTheme.typography.body1.copy(color = Color.White)
    )
}

@Composable
private fun LoadingState() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        repeat(4) { ShimmerRow() }
    }
}

@Composable
private fun ShimmerRow() {
    val transition = rememberInfiniteTransition()
    val alpha by transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(animation = tween<Float>(durationMillis = 700), repeatMode = androidx.compose.animation.core.RepeatMode.Reverse)
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 8.dp),
        shape = RoundedCornerShape(CARD_CORNER),
        elevation = CARD_ELEVATION
    ) {
        Box(modifier = Modifier.height(COMMENT_ITEM_HEIGHT).background(Color.LightGray.copy(alpha = alpha)))
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message)
    }
}

@Composable
private fun FeedBody(
    data: List<PostDetail>,
    search: String,
    loadPostDetails: (Int, Int) -> Unit,
    selectedState: UiState<CombinedPostUserComments>?
) {
    val filtered = remember(data, search) {
        val q = search.trim().lowercase()
        if (q.isEmpty()) data else data.filter { p ->
            val titleMatches = p.title.lowercase().contains(q)
            val userMatches = p.userName.lowercase().contains(q)
            val bodyMatches = p.body.lowercase().contains(q)
            titleMatches || userMatches || bodyMatches
        }
    }

    val selectedCombined = (selectedState as? UiState.Success<CombinedPostUserComments>)?.data
    val selectedPostId = remember { androidx.compose.runtime.mutableStateOf<Int?>(null) }

    LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(filtered) { post ->
            val expanded = selectedPostId.value == post.postId
            PostCard(
                post = post,
                expanded = expanded,
                onSelect = {
                    if (selectedPostId.value == post.postId) selectedPostId.value = null
                    else {
                        selectedPostId.value = post.postId
                        loadPostDetails(post.postId, post.userId)
                    }
                },
                selectedState = selectedState
            )
        }
    }
}

@Composable
private fun PostCard(
    post: PostDetail,
    expanded: Boolean,
    onSelect: () -> Unit,
    selectedState: UiState<CombinedPostUserComments>?
) {
    val palettes = listOf(
        listOf(Accent, PrimaryEnd),
        listOf(Color(0xFFF97316), Color(0xFFFFE082)),
        listOf(Color(0xFF0891B2), Accent),
        listOf(Color(0xFF10B981), PrimaryStart)
    )
    val colors = palettes[post.postId % palettes.size]

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 6.dp)
            .border(BorderStroke(1.dp, colors[0].copy(alpha = 0.12f)), shape = RoundedCornerShape(CARD_CORNER))
            .animateContentSize()
            .clickable(onClick = onSelect)
            .pointerInput(Unit) { detectTapGestures(onLongPress = { /* future action */ }) },
        elevation = CARD_ELEVATION,
        shape = RoundedCornerShape(CARD_CORNER)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CompactAvatar(postId = post.postId, userName = post.userName)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = post.userName,
                            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "${post.commentCount} comments",
                            style = MaterialTheme.typography.caption,
                            color = MutedText
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = post.title,
                        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Medium),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = post.body,
                        style = MaterialTheme.typography.body2.copy(color = Color(0xFF475569)),
                        maxLines = if (expanded) Int.MAX_VALUE else 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            PostFooter(expanded = expanded, onToggle = onSelect)

            if (expanded) {
                Divider(modifier = Modifier.padding(vertical = 10.dp))
                CommentsSection(selectedState = selectedState, postId = post.postId)
            }
        }
    }
}

@Composable
private fun CompactAvatar(postId: Int, userName: String) {
    val initial = userName.trim().firstOrNull()?.uppercaseChar() ?: '?'
    val painter = rememberAsyncImagePainter("${AVATARS_URL}$postId")

    Box(
        modifier = Modifier
            .size(THUMB_SIZE)
            .clip(RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.matchParentSize().background(Brush.verticalGradient(listOf(PrimaryStart, PrimaryEnd))))
        Box(
            modifier = Modifier
                .size(AVATAR_SIZE)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.06f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = initial.toString(), style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.SemiBold), color = Color.White)
        }
    }
}

@Composable
private fun PostFooter(expanded: Boolean, onToggle: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        val rotation by animateFloatAsState(
            targetValue = if (expanded) 180f else 0f,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
        )

        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = if (expanded) "Collapse" else "Expand",
            modifier = Modifier
                .size(36.dp)
                .rotate(rotation)
                .clickable(onClick = onToggle),
            tint = MutedText.copy(alpha = 0.95f)
        )
    }
}

@Composable
private fun CommentsSection(selectedState: UiState<CombinedPostUserComments>?, postId: Int) {
    when (selectedState) {
        is UiState.Loading -> LoadingSkeleton()
        is UiState.Error -> Text(text = selectedState.message, style = MaterialTheme.typography.caption, color = Color.Gray)
        is UiState.Success -> {
            val combined = selectedState.data
            if (combined.post.id != postId) {
                Text(text = "Loading comments...", style = MaterialTheme.typography.caption, color = Color.Gray)
            } else {
                val comments = combined.comments
                if (comments.isEmpty()) {
                    Text(text = "No comments", style = MaterialTheme.typography.caption, color = Color.Gray)
                } else {
                    Column {
                        comments.forEach { c ->
                            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                                val initial = c.name.trim().firstOrNull()?.uppercaseChar() ?: '?'
                                val avatarColors = listOf(Color(0xFF7C3AED), Color(0xFF06B6D4), Color(0xFFEF4444), Color(0xFF10B981))
                                val pick = avatarColors[(c.name.hashCode() and Int.MAX_VALUE) % avatarColors.size]

                                Box(
                                    modifier = Modifier
                                        .size(COMMENT_AVATAR_SIZE)
                                        .clip(CircleShape)
                                        .background(Brush.verticalGradient(listOf(pick, pick.copy(alpha = COMMENT_AVATAR_ALPHA)))) ,
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = initial.toString(), style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.SemiBold), color = Color.White)
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column {
                                    Text(text = c.name, style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Medium))
                                    Text(text = c.body, style = MaterialTheme.typography.body2.copy(color = Color(0xFF475569)), maxLines = 4, overflow = TextOverflow.Ellipsis)
                                }
                            }
                        }
                    }
                }
            }
        }
        else -> Text(text = "Loading comments...", style = MaterialTheme.typography.caption, color = Color.Gray)
    }
}

@Composable
private fun LoadingSkeleton() {
    Column { repeat(3) { ShimmerRow() } }
}

private fun selectedCombinedToUiState(selectedCombined: CombinedPostUserComments?): UiState<CombinedPostUserComments> =
    if (selectedCombined == null) UiState.Loading else UiState.Success(selectedCombined)
