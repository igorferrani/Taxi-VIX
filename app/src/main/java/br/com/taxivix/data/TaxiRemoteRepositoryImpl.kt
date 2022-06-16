package br.com.taxivix.data

import br.com.taxivix.domain.repository.TaxiRemoteRepository
import br.com.taxivix.data.dto.TaxiStandResponse

class TaxiRemoteRepositoryImpl : TaxiRemoteRepository {
    override suspend fun getListStands(): List<TaxiStandResponse> {
        return arrayListOf(
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                "https://spguia.melhoresdestinos.com.br/system/fotos_local/fotos/526/show/praca-dos-namorados.jpg",
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                "https://spguia.melhoresdestinos.com.br/system/fotos_local/fotos/526/show/praca-dos-namorados.jpg",
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                "https://spguia.melhoresdestinos.com.br/system/fotos_local/fotos/526/show/praca-dos-namorados.jpg",
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                "https://spguia.melhoresdestinos.com.br/system/fotos_local/fotos/526/show/praca-dos-namorados.jpg",
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                "https://spguia.melhoresdestinos.com.br/system/fotos_local/fotos/526/show/praca-dos-namorados.jpg",
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                "https://spguia.melhoresdestinos.com.br/system/fotos_local/fotos/526/show/praca-dos-namorados.jpg",
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                "https://spguia.melhoresdestinos.com.br/system/fotos_local/fotos/526/show/praca-dos-namorados.jpg",
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                "https://spguia.melhoresdestinos.com.br/system/fotos_local/fotos/526/show/praca-dos-namorados.jpg",
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                "https://spguia.melhoresdestinos.com.br/system/fotos_local/fotos/526/show/praca-dos-namorados.jpg",
                "(27) 4002-8922"
            ),
            TaxiStandResponse(
                "Rua dos guararapes",
                10.0,
                20.0,
                "Praça dos desejos",
                "https://spguia.melhoresdestinos.com.br/system/fotos_local/fotos/526/show/praca-dos-namorados.jpg",
                "(27) 4002-8922"
            )
        )
    }

    override suspend fun getDetailStand(): TaxiStandResponse {
        return TaxiStandResponse(
            "Rua dos guararapes",
            10.0,
            20.0,
            "Praça dos desejos",
            "https://spguia.melhoresdestinos.com.br/system/fotos_local/fotos/526/show/praca-dos-namorados.jpg",
            "(27) 4002-8922"
        )
    }

    override suspend fun confirmUserAddress() {

    }
}