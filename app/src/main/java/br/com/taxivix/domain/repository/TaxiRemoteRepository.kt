package br.com.taxivix.domain.repository

import br.com.taxivix.data.dto.TaxiStandResponse

interface TaxiRemoteRepository {
    suspend fun getListStands(cityId: String): List<TaxiStandResponse>

    suspend fun getDetailStand(id: String): TaxiStandResponse
}