package br.com.taxivix.ui.confirmaddress.presentation

import br.com.taxivix.data.dto.CitiesResponse

data class ListCitiesState (
    val isLoading: Boolean = true,
    val list: List<CitiesResponse> = emptyList(),
    val error: Exception? = null
)

data class CityState (
    val citySelected: Boolean,
    val cityId: String = "",
    val cityName: String = ""
)
