package br.com.taxivix.domain.repository

import br.com.taxivix.data.dto.CitiesResponse
import br.com.taxivix.data.dto.StateResponse

interface AddressRepository {
    suspend fun getListStates(): List<StateResponse>

    suspend fun getListCities(uf: String): List<CitiesResponse>
}