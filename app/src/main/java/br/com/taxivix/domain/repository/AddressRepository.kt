package br.com.taxivix.domain.repository

import br.com.taxivix.data.dto.CitiesResponse

interface AddressRepository {
    suspend fun getListCities(uf: String): List<CitiesResponse>
}