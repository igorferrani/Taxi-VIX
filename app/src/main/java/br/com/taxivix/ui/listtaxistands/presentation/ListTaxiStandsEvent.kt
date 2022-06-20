package br.com.taxivix.ui.listtaxistands.presentation

import android.location.Location

sealed class ListTaxiStandsEvent {
    data class GetListTaxiStands(val location: Location? = null) : ListTaxiStandsEvent()
    object GetCurrentCity : ListTaxiStandsEvent()
    data class ChangePrefUserLocation(val isUsingLocation: Boolean) : ListTaxiStandsEvent()
}
