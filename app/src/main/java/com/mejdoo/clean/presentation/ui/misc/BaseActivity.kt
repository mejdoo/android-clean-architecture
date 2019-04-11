package com.mejdoo.clean.presentation.ui.misc


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mejdoo.clean.R
import com.mejdoo.clean.util.ConnectivityLiveData
import kotlinx.android.synthetic.main.activity_base.*


open class BaseActivity : AppCompatActivity() {


    private lateinit var connectivityLiveData: ConnectivityLiveData
    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_base)

        connectivityLiveData = ConnectivityLiveData(applicationContext)

        snackbar = Snackbar.make(
            frameContainer,
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
