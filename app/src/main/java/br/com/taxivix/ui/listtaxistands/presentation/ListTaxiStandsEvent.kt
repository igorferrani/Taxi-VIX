package br.com.taxivix.ui.listtaxistands.presentation

sealed class ListTaxiStandsEvent {
    object GetListTaxiStands : ListTaxiStandsEvent()
    object GetCurrentCity : ListTaxiStandsEvent()
}
