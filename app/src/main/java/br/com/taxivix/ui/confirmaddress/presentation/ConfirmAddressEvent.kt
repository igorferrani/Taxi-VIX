package br.com.taxivix.ui.confirmaddress.presentation

sealed class ConfirmAddressEvent {
    data class ConfirmAddress(val uf: String, val city: String) : ConfirmAddressEvent()
    object GetListStates : ConfirmAddressEvent()
    data class GetListCities(val uf: String) : ConfirmAddressEvent()
}
