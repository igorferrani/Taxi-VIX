package br.com.taxivix.ui.listtaxistands

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.taxivix.domain.model.TaxiStand
import br.com.taxivix.ui.listtaxistands.presentation.ListTaxiStandsState
import br.com.taxivix.ui.listtaxistands.presentation.ListTaxiStandsViewModel
import br.com.taxivix.ui.theme.TaxiVIXTheme
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
                    ContainerListTaxiStands((viewModel.state as ListTaxiStandsState.ShowList).items)
                }
            }
        }
    }
}

@Composable
fun ContainerListTaxiStands(items: List<TaxiStand>) {
    LazyColumn {
        items(items) { item ->
            ItemPoint(item)
        }
    }
}

@Composable
fun ItemPoint(item: TaxiStand) {
    Row {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .width(70.dp)
        ) {

        }
        Column(
            modifier = Modifier
                .padding(12.dp)
                .weight(1f)
        ) {
            Text(text = item.pointName)
            Text(text = item.fullNameOfAddress, style = TextStyle(fontSize = 12.sp))
            Text(text = item.pointPhone)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContainerListTaxiStandsDefaultPreview() {
    TaxiVIXTheme {
        ContainerListTaxiStands(arrayListOf())
    }
}