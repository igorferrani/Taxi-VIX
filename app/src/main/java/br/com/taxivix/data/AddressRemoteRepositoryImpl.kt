package br.com.taxivix.data

import br.com.taxivix.data.dto.CitiesResponse
import br.com.taxivix.data.dto.StateResponse
import br.com.taxivix.domain.repository.AddressRepository

class AddressRemoteRepositoryImpl : AddressRepository {
    override suspend fun getListStates(): List<StateResponse> {
        return arrayListOf(
            StateResponse(
                1,
                "ES",
                "Espírito Santo"
            )
        )
    }

    override suspend fun getListCities(uf: String): List<CitiesResponse> {
        return arrayListOf(
            CitiesResponse(
                1,
                "Cariacica"
            )
        )
    }

}