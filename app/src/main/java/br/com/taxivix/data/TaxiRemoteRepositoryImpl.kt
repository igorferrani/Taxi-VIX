package br.com.taxivix.data

import br.com.taxivix.data.dto.TaxiStandResponse
import br.com.taxivix.domain.repository.TaxiRemoteRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TaxiRemoteRepositoryImpl : TaxiRemoteRepository {
    private suspend fun <T> Task<T>.await(): T = suspendCoroutine { continuation ->
        addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(task.result)
            } else {
                continuation.resumeWithException(task.exception ?: RuntimeException("Unknown task exception"))
            }
        }
    }

    override suspend fun getListStands(): List<TaxiStandResponse> {
        val tempList = mutableListOf<TaxiStandResponse>()
        val db = Firebase.firestore
        val states = db.collection("taxiStands").get().await()
        states.documents.forEach {
            tempList.add(
                TaxiStandResponse(
                    id = it.id,
                    fullNameOfAddress = it.data?.get("fullNameOfAddress") as String,
                    horizontalPoint = it.data?.get("horizontalPoint") as Double,
                    verticalPoint = it.data?.get("verticalPoint") as Double,
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
            fullNameOfAddress = result.data?.get("fullNameOfAddress") as String,
            horizontalPoint = result.data?.get("horizontalPoint") as Double,
            verticalPoint = result.data?.get("verticalPoint") as Double,
            pointName = result.data?.get("pointName") as String,
            pointPhoto = result.data?.get("pointPhoto") as String,
            pointPhone = result.data?.get("pointPhone") as String,
        )
    }
}