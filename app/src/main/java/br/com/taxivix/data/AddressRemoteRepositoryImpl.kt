package br.com.taxivix.data

import br.com.taxivix.data.dto.CitiesResponse
import br.com.taxivix.domain.repository.AddressRepository
import br.com.taxivix.util.await
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressRemoteRepositoryImpl : AddressRepository {
    override suspend fun getListCities(uf: String): List<CitiesResponse> {
        val tempList = mutableListOf<CitiesResponse>()
        val db = Firebase.firestore
        val ref = db.collection("cities")
        val cities = ref
            .whereIn("id", arrayListOf("3205309", "3205002"))
            .get()
            .await()
        cities.documents.forEach {
            tempList.add(
                CitiesResponse(
                    id = it.data?.get("id") as String,
                    nome = it.data?.get("nome") as String
                )
            )
        }
        return tempList
    }

}