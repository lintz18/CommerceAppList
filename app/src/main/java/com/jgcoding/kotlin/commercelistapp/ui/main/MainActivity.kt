package com.jgcoding.kotlin.commercelistapp.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import com.jgcoding.kotlin.commercelistapp.R
import com.jgcoding.kotlin.commercelistapp.core.Extensions.setStatusBar
import com.jgcoding.kotlin.commercelistapp.core.Network
import com.jgcoding.kotlin.commercelistapp.core.systemdesign.CommerceListTheme
import com.jgcoding.kotlin.commercelistapp.databinding.ActivityMainBinding
import com.jgcoding.kotlin.commercelistapp.domain.model.Commerce
import com.jgcoding.kotlin.commercelistapp.ui.Navigation
import com.jgcoding.kotlin.commercelistapp.ui.common.Result
import com.jgcoding.kotlin.commercelistapp.ui.compose.components.Toolbar
import com.jgcoding.kotlin.commercelistapp.ui.detail.DetailActivity
import com.jgcoding.kotlin.commercelistapp.ui.main.adapter.CommerceAdapter
import com.jgcoding.kotlin.commercelistapp.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        const val ID = "ID"
    }

    private var coordinates: Location = Location("MyLocation")
    private val locationPermissionCode = 2

    private var stationList = emptyList<Commerce>()
    private var foodList = emptyList<Commerce>()
    private var leisureList = emptyList<Commerce>()

    @Preview(showBackground = true)
    @Composable
    fun CustomToolbarPreview() {
        CommerceListTheme {
            val navController = rememberNavController()
            Toolbar(navController = navController, title = "Commerce Lists", isBack = false)
        }
    }

    //region LC
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }

        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = true
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val action: String? = intent?.action
        val data: Uri? = intent?.data
        Log.i(TAG, "onCreate: $data")
        Log.i(TAG, "onCreate: $action")
    }
    //endregion

    //region PERMISSION
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getMyCoordinates()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    locationPermissionCode
                )
            }
        }
    }
    //endregion

    //region METHODS
    private fun getMyCoordinates() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    Log.i(TAG, "Lat: ${location?.latitude}, Lon: ${location?.longitude}")
                    coordinates = location!!
                }
        }
    }

    private fun prepareFilteredLists(list: List<Commerce>) {
        stationList = list.filter {
            it.category.uppercase().contains("GAS_STATION")
        }

        foodList = list.filter {
            it.category.uppercase().contains("FOOD")
        }

        leisureList = list.filter {
            it.category.uppercase().contains("LEISURE")
        }
    }
    //endregion
}