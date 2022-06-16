package br.com.taxivix.ui.confirmaddress

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.taxivix.ui.confirmaddress.presentation.ConfirmAddressEvent
import br.com.taxivix.ui.confirmaddress.presentation.ConfirmAddressViewModel
import br.com.taxivix.ui.listtaxistands.ListTaxiStandsActivity
import br.com.taxivix.ui.theme.TaxiVIXTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfirmAddressActivity : ComponentActivity() {
    private val viewModel: ConfirmAddressViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaxiVIXTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    ContainerConfirmAddress(::onClickConfirmAddress, viewModel)
                }
            }
        }
    }

    private fun onClickConfirmAddress() {
        viewModel.onEvent(ConfirmAddressEvent.ConfirmAddress)
    }
}

@Composable
private fun ContainerConfirmAddress(onClickConfirm: () -> Unit, viewModel: ConfirmAddressViewModel) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(uiState) {
        if (viewModel.uiState.value.isSuccessful) {
            context.startActivity(Intent(context, ListTaxiStandsActivity::class.java))
        }
    }

    Row {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Local de embarque", style = TextStyle(fontSize = 20.sp))
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                value = "",
                onValueChange = {
                    // not implement
                },
                label = { Text("Estado") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                value = "",
                onValueChange = {
                    // not implement
                },
                label = { Text("Cidade") }
            )

            Button(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                onClick = onClickConfirm
            ) {
                Text("Confirmar")
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun ContainerConfirmAddressDefaultPreview() {
    TaxiVIXTheme {
        //ContainerConfirmAddress {}
    }
}*/
