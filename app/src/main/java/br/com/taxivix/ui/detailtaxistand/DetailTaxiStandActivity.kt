package br.com.taxivix.ui.detailtaxistand

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.taxivix.domain.model.TaxiStand
import br.com.taxivix.ui.detailtaxistand.presentation.DetailTaxiStandViewModel
import br.com.taxivix.ui.theme.TaxiVIXTheme
import br.com.taxivix.util.NetworkImage
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailTaxiStandActivity : ComponentActivity() {
    private val viewModel: DetailTaxiStandViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.extras?.getString("id") ?: ""

        viewModel.getTaxiStand(id)

        setContent {
            TaxiVIXTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    ContainerListTaxiStands(viewModel, ::callPhoneNumber)
                }
            }
        }
    }

    private fun callPhoneNumber(number: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
            viewModel.phoneNumber = number
        } else {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    viewModel.phoneNumber?.let {
                        callPhoneNumber(it)
                    }
                }
                return
            }
        }
    }
}

@Composable
private fun ContainerListTaxiStands(viewModel: DetailTaxiStandViewModel, callPhoneNumber: (number: String) -> Unit) {
    val activity = (LocalContext.current as? Activity)

    val uiState by viewModel.uiState.collectAsState()
    var taxiStand by remember { mutableStateOf<TaxiStand?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        isLoading = viewModel.uiState.value.isLoading
        if (viewModel.uiState.value.error == null) {
            taxiStand = viewModel.uiState.value.taxiStand
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    taxiStand?.let {
                        Text(text = it.pointName)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        activity?.finish()
                    }) {
                        Icon(Icons.Filled.ArrowBack,"")
                    }
                },
                backgroundColor = Color.Blue,
                contentColor = Color.White,
                elevation = 12.dp
            )
        }, content = {
            Column {
                if (isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                ContentDetailTaxiStand(taxiStand, callPhoneNumber)
            }
        }
    )
}

@Composable
private fun ContentDetailTaxiStand(item: TaxiStand?, callPhoneNumber: (number: String) -> Unit) {
    item?.let {
        Column {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
            ) {
                item {
                    NetworkImage(
                        modifier = Modifier.height(200.dp),
                        url = item.pointPhoto,
                    )
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = item.pointName,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = item.fullNameOfAddress)
                        Text(text = item.pointPhone)
                    }
                }
            }

            Button(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                onClick = {
                    callPhoneNumber(item.pointPhone)
                }
            ) {
                Text(text = "Ligar para telefone")
            }
        }
    }
}