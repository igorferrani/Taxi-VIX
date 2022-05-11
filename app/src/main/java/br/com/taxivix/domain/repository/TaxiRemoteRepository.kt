package br.com.taxivix.domain.repository

import br.com.taxivix.domain.repository.dto.TaxiStandResponse

interface TaxiRemoteRepository {
    suspend fun getListStands(): List<TaxiStandResponse>

    suspend fun confirmUserAddress()
}