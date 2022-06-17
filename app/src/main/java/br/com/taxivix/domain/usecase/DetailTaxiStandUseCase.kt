package br.com.taxivix.domain.usecase

import br.com.taxivix.domain.model.TaxiStand
import br.com.taxivix.domain.repository.TaxiRemoteRepository

class DetailTaxiStandUseCase(
    private val remoteRepository: TaxiRemoteRepository
) {
    suspend operator fun invoke(id: String): TaxiStand {
        return remoteRepository.getDetailStand(id)
    }
}
