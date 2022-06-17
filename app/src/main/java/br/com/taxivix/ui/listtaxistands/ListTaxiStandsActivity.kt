package br.com.taxivix.ui.listtaxistands

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.taxivix.domain.model.TaxiStand
import br.com.taxivix.ui.detailtaxistand.DetailTaxiStandActivity
import br.com.taxivix.ui.listtaxistands.presentation.ListTaxiStandsViewModel
import br.com.taxivix.ui.theme.TaxiVIXTheme
import br.com.taxivix.util.NetworkImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListTaxiStandsActivity : ComponentActivity() {
    private val viewModel: ListTaxiStandsViewModel by viewModel()

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
private fun ContainerListTaxiStands(viewModel: ListTaxiStandsViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    var items by remember { mutableStateOf(emptyList<TaxiStand>()) }

    LaunchedEffect(uiState) {
        if (viewModel.uiState.value.isSuccessful) {
            items = viewModel.uiState.value.items
        }
    }

    LazyColumn {
        items(items) { item ->
            ItemPoint(item)
        }
    }
}

@Composable
private fun ItemPoint(item: TaxiStand) {
    val context = LocalContext.current
    Row(Modifier.clickable {
        val intent = Intent(context, DetailTaxiStandActivity::class.java)
        intent.putExtra("id", item.id)
        context.startActivity(intent)
    }) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .width(70.dp)
        ) {
            NetworkImage(
                modifier = Modifier
                    .aspectRatio(0.8f),
                url = item.pointPhoto,
            )
        }
        Column(
            modifier = Modifier
                .padding(12.dp)
                .weight(1f)
        ) {
            Text(text = item.pointName)
            Text(text = item.fullNameOfAddress)
            Text(text = item.pointPhone)
        }
    }
}