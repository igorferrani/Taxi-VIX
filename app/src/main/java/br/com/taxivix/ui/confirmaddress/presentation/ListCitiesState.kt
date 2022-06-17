package br.com.taxivix.ui.confirmaddress.presentation

import br.com.taxivix.data.dto.CitiesResponse

sealed class ListCitiesState {
    data class Success(val list: List<CitiesResponse>) : ListCitiesState()
    data class Error(val exception: Exception) : ListCitiesState()
}
