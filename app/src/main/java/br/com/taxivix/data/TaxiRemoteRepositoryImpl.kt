package br.com.taxivix.data

import android.location.Location
import br.com.taxivix.data.dto.TaxiStandResponse
import br.com.taxivix.domain.repository.TaxiRemoteRepository
import br.com.taxivix.util.AddressCalc
import br.com.taxivix.util.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TaxiRemoteRepositoryImpl : TaxiRemoteRepository {
    override suspend fun getListStands(cityId: String, location: Location?): List<TaxiStandResponse> {
        val originalList = mutableListOf<TaxiStandResponse>()
        val db = Firebase.firestore
        val states = db.collection("taxiStands")
            .whereEqualTo("cityId", cityId)
            .get().await()

        val mapDistances = mutableMapOf<Double, TaxiStandResponse>()

        states.documents.forEach { documentSnapshot ->
            val taxiStandResponse = TaxiStandResponse(
                id = documentSnapshot.id,
                cityId = documentSnapshot.data?.get("cityId") as String,
                fullNameOfAddress = documentSnapshot.data?.get("fullNameOfAddress") as String,
                pointName = documentSnapshot.data?.get("pointName") as String,
                pointPhoto = documentSnapshot.data?.get("pointPhoto") as String,
                pointPhone = documentSnapshot.data?.get("pointPhone") as String,
                latitude = documentSnapshot.data?.get("latitude") as Double,
                longitude = documentSnapshot.data?.get("longitude") as Double
            )

            location?.let {
                val distance = AddressCalc.getDistanceFromLatLonInKm(
                    it.latitude,
                    it.longitude,
                    taxiStandResponse.latitude,
                    taxiStandResponse.longitude
                )
                mapDistances[distance] = taxiStandResponse
            }

            originalList.add(
                taxiStandResponse
            )
        }
        return if (location != null) {
            mapDistances.toSortedMap().values.toList()
        } else {
            originalList
        }
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