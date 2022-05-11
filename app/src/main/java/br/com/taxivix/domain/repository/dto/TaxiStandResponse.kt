package br.com.taxivix.domain.repository.dto

import br.com.taxivix.domain.model.TaxiStand

data class TaxiStandResponse(
    override val fullNameOfAddress: String,
    override val horizontalPoint: Double,
    override val verticalPoint: Double,
    override val pointName: String,
    override val pointPhotos: List<String>,
    override val pointPhone: String
) : TaxiStand {

}