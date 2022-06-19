package br.com.taxivix.data

import br.com.taxivix.data.dto.TaxiStandResponse
import br.com.taxivix.domain.repository.TaxiRemoteRepository
import br.com.taxivix.util.AddressCalc
import br.com.taxivix.util.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TaxiRemoteRepositoryImpl : TaxiRemoteRepository {
    override suspend fun getListStands(cityId: String): List<TaxiStandResponse> {
        val tempList = mutableListOf<TaxiStandResponse>()
        val db = Firebase.firestore
        val states = db.collection("taxiStands")
            .whereEqualTo("cityId", cityId)
            .get().await()
        val lat = -20.346938
        val lon = -40.384841

        val mapDistances = mutableMapOf<Double, TaxiStandResponse>()

        states.documents.forEach {
            val taxiStandResponse = TaxiStandResponse(
                id = it.id,
                cityId = it.data?.get("cityId") as String,
                fullNameOfAddress = it.data?.get("fullNameOfAddress") as String,
                pointName = it.data?.get("pointName") as String,
                pointPhoto = it.data?.get("pointPhoto") as String,
                pointPhone = it.data?.get("pointPhone") as String,
                latitude = it.data?.get("latitude") as Double,
                longitude = it.data?.get("longitude") as Double
            )

            val distance = AddressCalc.getDistanceFromLatLonInKm(lat, lon, it.data?.get("latitude") as Double, it.data?.get("longitude") as Double)
            mapDistances[distance] = taxiStandResponse

            tempList.add(
                taxiStandResponse
            )
        }
        return mapDistances.toSortedMap().values.toList()
    }

    override suspend fun getDetailStand(id: String): TaxiStandResponse {
        val db = Firebase.firestore
        val result = db.collection("taxiStands").document(id).get().await()
        return TaxiStandResponse(
            id = result.id,
            cityId = result.data?.get("cityId") as String,
            fullNameOfAddress = result.data?.get("fullNameOfAddress") as String,
            pointName = result.data?.get("pointName") as String,
            pointPhoto = result.data?.get("pointPhoto") as String,
            pointPhone = result.data?.get("pointPhone") as String,
            latitude = result.data?.get("latitude") as Double,
            longitude = result.data?.get("longitude") as Double
        )
    }
}