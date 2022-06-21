package br.com.taxivix.ui.listtaxistands

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import br.com.taxivix.domain.model.TaxiStand
import br.com.taxivix.ui.confirmaddress.ConfirmAddressActivity
import br.com.taxivix.ui.detailtaxistand.DetailTaxiStandActivity
import br.com.taxivix.ui.listtaxistands.presentation.ListTaxiStandsEvent
import br.com.taxivix.ui.listtaxistands.presentation.ListTaxiStandsViewModel
import br.com.taxivix.ui.theme.TaxiVIXTheme
import br.com.taxivix.util.NetworkImage
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListTaxiStandsActivity : ComponentActivity() {
    private val viewModel: ListTaxiStandsViewModel by viewModel()
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getListTaxiStands()
        viewModel.onEvent(ListTaxiStandsEvent.GetCurrentCity)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            TaxiVIXTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    ContainerListTaxiStands(viewModel, ::getListTaxiStands)
                }
            }
        }
    }

    private fun getListTaxiStands() {
        if (viewModel.locationState.value.isUsingLocation) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getLocation()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        } else {
            viewModel.onEvent(ListTaxiStandsEvent.GetListTaxiStands())
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        /*val location = Location(LocationManager.GPS_PROVIDER)
        location.latitude = -20.346991
        location.longitude = -40.384819
        viewModel.onEvent(ListTaxiStandsEvent.GetListTaxiStands(location))
        return*/
        fusedLocationProviderClient?.lastLocation?.addOnCompleteListener {
            viewModel.onEvent(ListTaxiStandsEvent.GetListTaxiStands(it.result))
        }
    }
}

@Composable
private fun ContainerListTaxiStands(viewModel: ListTaxiStandsViewModel, getListTaxiStands: () -> Unit) {
    val context = LocalContext.current as Activity
    val uiState by viewModel.uiState.collectAsState()
    val locationState by viewModel.locationState.collectAsState()
    var items by remember { mutableStateOf(emptyList<TaxiStand>()) }
    var isLoading by remember { mutableStateOf(false) }
    var currentCityName by remember { mutableStateOf("") }
    var checkIsUsingLocation by remember { mutableStateOf(false) }

    LaunchedEffect(locationState) {
        checkIsUsingLocation = locationState.isUsingLocation
        if (locationState.valueChanged) {
            getListTaxiStands()
        }
    }

    LaunchedEffect(uiState) {
        isLoading = viewModel.uiState.value.isLoading
        currentCityName = viewModel.uiState.value.currentCityName
        if (viewModel.uiState.value.error == null) {
            items = viewModel.uiState.value.items
        }
    }

    Column(
        modifier = Modifier.background(Color(0xffEEEEEE))
    ) {
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
        Text(
            text = "Pontos de Taxi",
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Cidade: $currentCityName",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, bottom = 6.dp)
        )
        Row {
            Checkbox(
                modifier = Modifier
                    .padding(start = 6.dp, bottom = 6.dp),
                checked = checkIsUsingLocation, onCheckedChange = {
                    viewModel.onEvent(ListTaxiStandsEvent.ChangePrefUserLocation(it))
                })
            Text(
                "Usar minha localização",
                modifier = Modifier
                    .padding(start = 6.dp, bottom = 6.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(items) { item ->
                ItemPoint(item)
            }
        }
        Button(
            modifier = Modifier
                .padding(horizontal = 6.dp, vertical = 12.dp)
                .fillMaxWidth()
                .height(40.dp),
            onClick = {
                context.startActivity(Intent(context, ConfirmAddressActivity::class.java))
            }
        ) {
            Text("Alterar minha cidade")
        }
    }
}

@Composable
private fun ItemPoint(item: TaxiStand) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.clickable {
            val intent = Intent(context, DetailTaxiStandActivity::class.java)
            intent.putExtra("id", item.id)
            context.startActivity(intent)
        }
            .padding(6.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            Modifier
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .width(100.dp)
            ) {
                NetworkImage(
                    modifier = Modifier
                        .aspectRatio(1f),
                    url = item.pointPhoto,
                )
            }
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                Text(text = item.pointName, fontWeight = FontWeight.Bold)
                Text(text = item.fullNameOfAddress)
                Text(text = item.pointPhone)
            }
        }
    }
}