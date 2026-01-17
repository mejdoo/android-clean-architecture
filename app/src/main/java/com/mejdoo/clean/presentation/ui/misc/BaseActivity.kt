package com.mejdoo.clean.presentation.ui.misc

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.mejdoo.clean.R
import com.mejdoo.clean.util.connectivityAsFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "BaseActivity"
private const val SNACKBAR_BG_HEX = 0xFF323232
private val SNACKBAR_BG_COLOR = Color(SNACKBAR_BG_HEX)
private val SNACKBAR_PADDING = 16.dp

open class BaseActivity : AppCompatActivity() {
    // Use Compose's SnackbarHostState instead of Android Snackbar anchored to legacy views
    private val snackbarHostState = SnackbarHostState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set a single Compose root that holds the Scaffold + SnackbarHost so it can't be
        // accidentally replaced by subclasses calling their own setContent.
        setContent {
            AppHost(snackbarHostState) {
                // call the subclass-provided content slot
                ScreenContent()
            }
        }

        // Observe connectivity and show/dismiss a Compose Snackbar via the SnackbarHostState.
        lifecycleScope.launch {
            connectivityAsFlow(applicationContext).collectLatest { isConnected ->
                Log.d(TAG, "connectivity changed: $isConnected")
                if (!isConnected) {
                    val isSnackbarShown = snackbarHostState.currentSnackbarData != null
                    Log.d(TAG, "No connection - showing snackbar/toast. currentSnackbarData=$isSnackbarShown")

                    // show a persistent snackbar until connection is back
                    // launch in lifecycleScope (not the collecting coroutine) and ensure we don't
                    // create duplicates if a snackbar is already shown.
                    if (!isSnackbarShown) {
                        lifecycleScope.launch {
                            snackbarHostState.showSnackbar(
                                message = getString(R.string.no_connection),
                                duration = SnackbarDuration.Indefinite
                            )
                        }
                    }
                } else {
                    Log.d(TAG, "Connected - dismissing snackbar if present")
                    // dismiss current snackbar if any
                    snackbarHostState.currentSnackbarData?.dismiss()
                }
            }
        }
    }

    /**
     * Subclasses should override this to provide their Compose UI. Keep it empty by default.
     * This ensures the BaseActivity's Scaffold and SnackbarHost remain the single root composition.
     */
    @Suppress("FunctionName")
    @Composable
    protected open fun ScreenContent() {
        // default empty content
    }
}

@Suppress("FunctionName")
@Composable
private fun AppHost(snackbarHostState: SnackbarHostState, content: @Composable () -> Unit) {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            // Activity content (fills the available space)
            Surface(modifier = Modifier.fillMaxSize()) {
                content()
            }

            // Snackbar overlay placed on top of content, bottom-centered with padding
            Box(
                modifier =
                Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        modifier = Modifier.padding(SNACKBAR_PADDING),
                        backgroundColor = SNACKBAR_BG_COLOR,
                        contentColor = Color.White
                    )
                }
            }
        }
    }
}
