package br.com.taxivix.ui.splashscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.taxivix.ui.confirmaddress.ConfirmAddressActivity
import br.com.taxivix.ui.listtaxistands.ListTaxiStandsActivity
import br.com.taxivix.ui.splashscreen.presentation.Pages
import br.com.taxivix.ui.splashscreen.presentation.SplashScreenViewModel
import br.com.taxivix.ui.theme.TaxiVIXTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : ComponentActivity() {
    private val viewModel: SplashScreenViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.checkAddressConfirmed()
        }, 3000)

        setContent {
            TaxiVIXTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    ContainerSplash(viewModel)
                }
            }
        }
    }
}

@Composable
private fun ContainerSplash(viewModel: SplashScreenViewModel) {
    val activity = (LocalContext.current as? Activity)
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(uiState) {
        when (viewModel.uiState.value.redirectTo) {
            Pages.CONFIRM_ADDRESS -> {
                activity?.let{
                    it.startActivity(Intent(activity, ConfirmAddressActivity::class.java))
                    it.finish()
                }
            }
            Pages.LIST_TAXI_STANDS -> {
                activity?.let{
                    it.startActivity(Intent(activity, ListTaxiStandsActivity::class.java))
                    it.finish()
                }

            }
            Pages.SPLASH -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Taxi VIX",
            modifier = Modifier.padding(16.dp),
            fontSize = 28.sp
        )
    }
}