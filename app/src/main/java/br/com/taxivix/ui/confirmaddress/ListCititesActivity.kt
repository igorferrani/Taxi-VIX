package br.com.taxivix.ui.confirmaddress

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.com.taxivix.data.dto.CitiesResponse
import br.com.taxivix.ui.confirmaddress.presentation.ConfirmAddressEvent
import br.com.taxivix.ui.confirmaddress.presentation.ConfirmAddressViewModel
import br.com.taxivix.ui.confirmaddress.presentation.ListCitiesState
import br.com.taxivix.ui.theme.TaxiVIXTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListCitiesActivity : ComponentActivity() {
    private val viewModel: ConfirmAddressViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onEvent(ConfirmAddressEvent.GetListCities("ES"))

        setContent {
            TaxiVIXTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    ContainerListTaxiStands(viewModel)
                }
            }
        }
    }
}

@Composable
private fun ContainerListTaxiStands(viewModel: ConfirmAddressViewModel) {
    val uiListCitiesState by viewModel.uiListCitiesState.collectAsState()
    var items by remember { mutableStateOf(emptyList<CitiesResponse>()) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(uiListCitiesState) {
        viewModel.uiListCitiesState.value.let { listCitiesState ->
            isLoading = listCitiesState.isLoading
            if (!listCitiesState.isLoading) {
                if (listCitiesState.error == null) {
                    items = listCitiesState.list
                } else {
                    // Show error
                }
            }
        }
    }

    Column {
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
        LazyColumn {
            items(items) { item ->
                ItemPoint(item)
            }
        }
    }
}

@Composable
private fun ItemPoint(item: CitiesResponse) {
    val context = LocalContext.current as Activity
    Row(Modifier.clickable {
        val intent = Intent()
        intent.putExtra("cityId", item.id)
        intent.putExtra("cityName", item.nome)
        context.setResult(Activity.RESULT_OK, intent)
        context.finish()
    }) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .weight(1f)
        ) {
            Text(text = item.nome)
        }
    }
}