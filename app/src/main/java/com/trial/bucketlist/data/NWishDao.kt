package com.trial.bucketlist.data

import android.content.Context
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class NWishDao {
    fun addWishN(
        userData : NWish
    ) = CoroutineScope(Dispatchers.IO).launch{

        val firestoreRef = Firebase.firestore
            .collection("user")
            .document(userData.id)

        try{
            firestoreRef.set(userData)
//                .addOnSuccessListener {
//                Toast.makeText(context, "Data saved successfully", Toast.LENGTH_SHORT).show()
//            }

        } catch (e: Exception){
//            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }

    }

    fun getWishesN(): Flow<List<NWish>> = flow {
        val firestore = Firebase.firestore
                                .collection("user")

        try {
            val snapshot = firestore.get().await()
            val wishesList = snapshot.documents.mapNotNull { document ->
                document.toObject(NWish::class.java)
            }

            emit(wishesList)
        } catch (e: Exception) {
            // Handle any errors here
            emit(emptyList()) // Emit an empty list or handle error state
        }
    }

    fun getWishById(id: String): Flow<NWish?> = flow {
        val firestore = Firebase.firestore
                                .collection("user").document(id)

        try {
            val snapshot = firestore.get().await()
            if (snapshot.exists()) {
                val wish = snapshot.toObject<NWish>()
                emit(wish)
            } else {
//                Toast.makeText(context, "No User Data Found", Toast.LENGTH_SHORT).show()
                emit(null)
            }
        } catch (e: Exception) {
//            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            emit(null)
        }
    }

    suspend fun updateWishByIdN(wish: NWish) {
        val firestore = Firebase.firestore
                                .collection("user").document(wish.id)

        try {
            // Perform the update operation in a suspended context
            withContext(Dispatchers.IO) {
                firestore.update("title", wish.title).await()
                firestore.update("description", wish.description).await()
            }
//            Toast.makeText(context, "Wish updated successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
//            Toast.makeText(context, "Error updating wish: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
    }

    fun deleteWishN(
        wish:NWish
    ) = CoroutineScope(Dispatchers.IO).launch{

        val firestoreRef = Firebase.firestore
            .collection("user")
            .document(wish.id)

        try{
            firestoreRef.get().addOnSuccessListener {
                if(it.exists()){
                    firestoreRef
                        .delete()
                        .addOnSuccessListener {
//                            Toast.makeText(
//                                context,
//                                "Data Successfully Deleted",
//                                Toast.LENGTH_SHORT).show()
                        }
                }
                else{
//                    Toast.makeText(context, "No User Data Found", Toast.LENGTH_SHORT).show()
                }
            }

        } catch (e: Exception){
//            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}