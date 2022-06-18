package br.com.taxivix.ui.confirmaddress.presentation

sealed class ConfirmAddressEvent {
    data class ConfirmAddress(val cityId: String, val cityName: String) : ConfirmAddressEvent()
    data class GetListCities(val uf: String) : ConfirmAddressEvent()
    data class ChangeCity(val cityId: String, val cityName: String) : ConfirmAddressEvent()
    object GetCurrentCity : ConfirmAddressEvent()
}
