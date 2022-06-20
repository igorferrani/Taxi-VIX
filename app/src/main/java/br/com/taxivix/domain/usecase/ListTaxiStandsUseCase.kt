package br.com.taxivix.domain.usecase

import android.location.Location
import br.com.taxivix.domain.model.TaxiStand
import br.com.taxivix.domain.repository.TaxiRemoteRepository

class ListTaxiStandsUseCase(
    private val remoteRepository: TaxiRemoteRepository
) {
    suspend operator fun invoke(cityId: String, location: Location?): List<TaxiStand> {
        return remoteRepository.getListStands(cityId, location)
    }
}
