package br.com.taxivix.data

import br.com.taxivix.data.dto.TaxiStandResponse
import br.com.taxivix.domain.repository.TaxiRemoteRepository
import br.com.taxivix.util.await
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TaxiRemoteRepositoryImpl : TaxiRemoteRepository {
    override suspend fun getListStands(cityId: String): List<TaxiStandResponse> {
        val tempList = mutableListOf<TaxiStandResponse>()
        val db = Firebase.firestore
        val states = db.collection("taxiStands")
            .whereEqualTo("cityId", cityId)
            .get().await()
        states.documents.forEach {
            tempList.add(
                TaxiStandResponse(
                    id = it.id,
                    cityId = it.data?.get("cityId") as String,
                    fullNameOfAddress = it.data?.get("fullNameOfAddress") as String,
                    pointName = it.data?.get("pointName") as String,
                    pointPhoto = it.data?.get("pointPhoto") as String,
                    pointPhone = it.data?.get("pointPhone") as String,
                )
            )
        }
        return tempList
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
        )
    }
}