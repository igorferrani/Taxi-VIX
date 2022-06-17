package br.com.taxivix.domain.usecase

import br.com.taxivix.data.dto.StateResponse
import br.com.taxivix.domain.repository.AddressRepository

class ListStatesUseCase(
    private val remoteRepository: AddressRepository
) {
    suspend operator fun invoke(): List<StateResponse> {
        return remoteRepository.getListStates()
    }
}
