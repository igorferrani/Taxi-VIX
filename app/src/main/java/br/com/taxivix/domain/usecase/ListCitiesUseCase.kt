package br.com.taxivix.domain.usecase

import br.com.taxivix.data.dto.CitiesResponse
import br.com.taxivix.data.dto.StateResponse
import br.com.taxivix.domain.repository.AddressRepository

class ListCitiesUseCase(
    private val remoteRepository: AddressRepository
) {
    suspend operator fun invoke(uf: String): List<CitiesResponse> {
        return remoteRepository.getListCities(uf)
    }
}
