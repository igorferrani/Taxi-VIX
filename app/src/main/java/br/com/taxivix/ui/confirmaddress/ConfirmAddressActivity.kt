package br.com.taxivix.ui.confirmaddress

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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

        viewModel.onEvent(ConfirmAddressEvent.GetCurrentCity)

        setContent {
            TaxiVIXTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    ContainerConfirmAddress(::onClickConfirmAddress, viewModel)
                }
            }
        }
    }

    private fun onClickConfirmAddress(cityId: String, cityName: String) {
        viewModel.onEvent(ConfirmAddressEvent.ConfirmAddress(cityId, cityName))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            val cityId = data?.getStringExtra("cityId")
            val cityName = data?.getStringExtra("cityName")

            if (cityId != null && cityName != null){
                viewModel.onEvent(ConfirmAddressEvent.ChangeCity(cityId, cityName))
            }
        }
    }
}

@Composable
private fun ContainerConfirmAddress(onClickConfirm: (cityId: String, cityName: String) -> Unit, viewModel: ConfirmAddressViewModel) {
    val context = LocalContext.current as Activity
    val uiState by viewModel.uiState.collectAsState()
    val uiCityState by viewModel.uiCityState.collectAsState()

    var mCityName by remember { mutableStateOf("") }
    var mCityId by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (viewModel.uiState.value.isSuccessful) {
            val intent = Intent(context, ListTaxiStandsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            context.finish()
        }
    }

    LaunchedEffect(uiCityState) {
        viewModel.uiCityState.value.let { cityState ->
            if (cityState.citySelected) {
                mCityName = cityState.cityName
                mCityId = cityState.cityId
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selecione sua cidade", style = TextStyle(fontSize = 26.sp))

        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .clickable {
                    val intent = Intent(context, ListCitiesActivity::class.java)
                    context.startActivityForResult(intent, 1)
                },
            value = mCityName,
            onValueChange = {},
            enabled = false,
            label = { Text("Cidade") }
        )

        Button(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 12.dp)
                .fillMaxWidth()
                .height(40.dp),
            onClick = {
                onClickConfirm(mCityId, mCityName)
            }
        ) {
            Text("Confirmar")
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
