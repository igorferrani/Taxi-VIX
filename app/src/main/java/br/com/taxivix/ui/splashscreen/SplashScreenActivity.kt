package br.com.taxivix.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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

        viewModel.checkAddressConfirmed()

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
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(uiState) {
        when (viewModel.uiState.value.redirectTo) {
            Pages.CONFIRM_ADDRESS -> {
                context.startActivity(Intent(context, ConfirmAddressActivity::class.java))
            }
            Pages.LIST_TAXI_STANDS -> {
                context.startActivity(Intent(context, ListTaxiStandsActivity::class.java))
            }
            Pages.SPLASH -> {}
        }
    }
}