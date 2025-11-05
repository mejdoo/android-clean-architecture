package com.mejdoo.clean.presentation.ui.misc


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mejdoo.clean.R
import com.mejdoo.clean.util.ConnectivityLiveData
import com.mejdoo.clean.databinding.ActivityBaseBinding


open class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding
    private lateinit var connectivityLiveData: ConnectivityLiveData
    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connectivityLiveData = ConnectivityLiveData(applicationContext)

        snackbar = Snackbar.make(
                binding.frameContainer,
                resources.getString(R.string.no_connection),
                Snackbar.LENGTH_INDEFINITE
        )

        connectivityLiveData.observe(
                this,
                Observer<Boolean> { updateSnackbar(it) })

    }

    private fun updateSnackbar(networkStatus: Boolean) {

        when (networkStatus) {
            false -> snackbar.show()
            true -> snackbar.dismiss()
        }

    }


}
