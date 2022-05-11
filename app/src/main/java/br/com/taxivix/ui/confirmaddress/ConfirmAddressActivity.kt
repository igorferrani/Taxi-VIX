package br.com.taxivix.ui.confirmaddress

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    ContainerConfirmAddress(::onClickConfirmAddress)
                }
            }
        }
    }

    private fun onClickConfirmAddress() {
        viewModel.confirmUserAddress()
        startActivity(Intent(this, ListTaxiStandsActivity::class.java))
    }
}

@Composable
fun ContainerConfirmAddress(onClickConfirm: () -> Unit) {
    val context = LocalContext.current
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

@Preview(showBackground = true)
@Composable
fun ContainerConfirmAddressDefaultPreview() {
    TaxiVIXTheme {
        ContainerConfirmAddress {}
    }
}