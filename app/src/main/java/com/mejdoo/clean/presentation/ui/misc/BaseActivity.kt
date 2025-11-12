package com.mejdoo.clean.presentation.ui.misc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.mejdoo.clean.R
import com.mejdoo.clean.databinding.ActivityBaseBinding
import com.mejdoo.clean.util.connectivityAsFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

open class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding
    private lateinit var snackBar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        snackBar =
            Snackbar.make(
                binding.frameContainer,
                resources.getString(R.string.no_connection),
                Snackbar.LENGTH_INDEFINITE,
            )

        lifecycleScope.launch {
            connectivityAsFlow(applicationContext).collectLatest { updateSnackBar(it) }
        }
    }

    private fun updateSnackBar(networkStatus: Boolean) {
        when (networkStatus) {
            false -> snackBar.show()
            true -> snackBar.dismiss()
        }
    }
}
