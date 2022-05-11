package br.com.taxivix.domain.usecase

import br.com.taxivix.domain.repository.TaxiRemoteRepository

class ConfirmUserAddressUseCase(
    private val remoteRepository: TaxiRemoteRepository
) {
    suspend operator fun invoke() {
        return remoteRepository.confirmUserAddress()
    }
}
