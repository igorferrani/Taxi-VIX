package br.com.taxivix.ui.detailtaxistand

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.taxivix.domain.model.TaxiStand
import br.com.taxivix.ui.detailtaxistand.presentation.DetailTaxiStandViewModel
import br.com.taxivix.ui.theme.TaxiVIXTheme
import br.com.taxivix.util.NetworkImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailTaxiStandActivity : ComponentActivity() {
    private val viewModel: DetailTaxiStandViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getListTaxiStands()

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
private fun ContainerListTaxiStands(viewModel: DetailTaxiStandViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var taxiStand by remember { mutableStateOf<TaxiStand?>(null) }

    LaunchedEffect(uiState) {
        if (viewModel.uiState.value.isSuccessful) {
            taxiStand = viewModel.uiState.value.taxiStand
        }
    }

    taxiStand?.let {
        Header(it)
    }
}

@Composable
private fun Header(item: TaxiStand) {
    Column {
        NetworkImage(
            modifier = Modifier
                .aspectRatio(0.8f),
            url = item.pointPhoto,
        )
        Text(text = item.pointName)
        Text(text = item.fullNameOfAddress, style = TextStyle(fontSize = 12.sp))
        Text(text = item.pointPhone)
    }
}