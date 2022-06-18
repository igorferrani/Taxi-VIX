package br.com.taxivix.data.dto

import br.com.taxivix.domain.model.TaxiStand

data class TaxiStandResponse(
    override val id: String,
    override val cityId: String,
    override val fullNameOfAddress: String,
    override val pointName: String,
    override val pointPhoto: String,
    override val pointPhone: String
) : TaxiStand