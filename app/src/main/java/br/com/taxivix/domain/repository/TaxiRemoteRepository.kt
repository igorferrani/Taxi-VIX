package br.com.taxivix.domain.repository

import android.location.Location
import br.com.taxivix.data.dto.TaxiStandResponse

interface TaxiRemoteRepository {
    suspend fun getListStands(cityId: String, location: Location?): List<TaxiStandResponse>

    suspend fun getDetailStand(id: String): TaxiStandResponse
}