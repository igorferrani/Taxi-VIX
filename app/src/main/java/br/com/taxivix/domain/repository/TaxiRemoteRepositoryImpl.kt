package br.com.taxivix.domain.repository

import br.com.taxivix.domain.repository.dto.TaxiStandResponse

class TaxiRemoteRepositoryImpl : TaxiRemoteRepository {
    override suspend fun getListStands(): List<TaxiStandResponse> {
        return arrayListOf(
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                arrayListOf("https://google.com"),
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                arrayListOf("https://google.com"),
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                arrayListOf("https://google.com"),
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                arrayListOf("https://google.com"),
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                arrayListOf("https://google.com"),
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                arrayListOf("https://google.com"),
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                arrayListOf("https://google.com"),
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                arrayListOf("https://google.com"),
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                arrayListOf("https://google.com"),
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                arrayListOf("https://google.com"),
                "(27) 4002-8922"
            )
        )
    }

    override suspend fun confirmUserAddress() {

    }
}