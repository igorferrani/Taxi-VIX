package br.com.taxivix.domain.usecase

import br.com.taxivix.domain.model.TaxiStand
import br.com.taxivix.domain.repository.TaxiRemoteRepository

class ListTaxiStandsUseCase(
    private val remoteRepository: TaxiRemoteRepository
) {
    suspend operator fun invoke(cityId: String): List<TaxiStand> {
        return remoteRepository.getListStands(cityId)
    }
}
