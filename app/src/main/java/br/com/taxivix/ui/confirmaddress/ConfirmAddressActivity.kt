package br.com.taxivix.ui.confirmaddress

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
import androidx.core.content.edit
import br.com.taxivix.ui.confirmaddress.presentation.ConfirmAddressEvent
import br.com.taxivix.ui.confirmaddress.presentation.ConfirmAddressViewModel
import br.com.taxivix.ui.confirmaddress.presentation.ListCitiesState
import br.com.taxivix.ui.confirmaddress.presentation.ListStatesState
import br.com.taxivix.ui.listtaxistands.ListTaxiStandsActivity
import br.com.taxivix.ui.theme.TaxiVIXTheme
import br.com.taxivix.util.SharedPreferencesManager
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

    private fun onClickConfirmAddress(mUF: String, mCity: String) {
        viewModel.onEvent(ConfirmAddressEvent.ConfirmAddress(mUF, mCity))
    }
}

@Composable
private fun ContainerConfirmAddress(onClickConfirm: (mUF: String, mCity: String) -> Unit, viewModel: ConfirmAddressViewModel) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val uiListStatesState by viewModel.uiListStatesState.collectAsState()
    val uiListCitiesState by viewModel.uiListCitiesState.collectAsState()

    var mUF by remember { mutableStateOf("") }
    var mCity by remember { mutableStateOf("") }

    var expandedMenuStates by remember { mutableStateOf(false) }
    var expandedMenuCities by remember { mutableStateOf(false) }

    var mListMenuStates by remember { mutableStateOf(emptyList<String>()) }
    var mListMenuCities by remember { mutableStateOf(emptyList<String>()) }

    LaunchedEffect(uiState) {
        if (viewModel.uiState.value.isSuccessful) {
            context.startActivity(Intent(context, ListTaxiStandsActivity::class.java))
        }
    }

    LaunchedEffect(uiListStatesState) {
        viewModel.uiListStatesState.value.let { listStatesState ->
            when (listStatesState) {
                is ListStatesState.Success -> {
                    if (!expandedMenuStates && listStatesState.list.isNotEmpty()) {
                        mListMenuStates = listStatesState.list.map {
                            it.sigla
                        }
                        expandedMenuStates = true
                    }
                }
                is ListStatesState.Error -> {

                }
            }
        }
    }

    LaunchedEffect(uiListCitiesState) {
        viewModel.uiListCitiesState.value.let { listCitiesState ->
            when (listCitiesState) {
                is ListCitiesState.Success -> {
                    if (!expandedMenuCities && listCitiesState.list.isNotEmpty()) {
                        mListMenuCities = listCitiesState.list.map {
                            it.nome
                        }
                        expandedMenuCities = true
                    }
                }
                is ListCitiesState.Error -> {

                }
            }
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
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(ConfirmAddressEvent.GetListStates)
                    },
                value = mUF,
                enabled = false,
                onValueChange = {},
                label = { Text("Estado") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(ConfirmAddressEvent.GetListCities(mUF))
                    },
                value = mCity,
                onValueChange = {},
                enabled = false,
                label = { Text("Cidade") }
            )

            Button(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                onClick = {
                    onClickConfirm(mUF, mCity)
                }
            ) {
                Text("Confirmar")
            }

            DropdownMenu(
                expanded = expandedMenuStates,
                onDismissRequest = {
                    expandedMenuStates = false
                }
            ) {
                mListMenuStates.forEach { label ->
                    DropdownMenuItem(onClick = {
                        mUF = label
                        expandedMenuStates = false
                    }) {
                        Text(text = label)
                    }
                }
            }

            DropdownMenu(
                expanded = expandedMenuCities,
                onDismissRequest = {
                    expandedMenuCities = false
                }
            ) {
                mListMenuCities.forEach { label ->
                    DropdownMenuItem(onClick = {
                        mCity = label
                        expandedMenuCities = false
                    }) {
                        Text(text = label)
                    }
                }
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
