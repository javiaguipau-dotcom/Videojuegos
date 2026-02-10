package com.example.videojuegos.data.source

import com.example.videojuegos.domain.models.Videojuego
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseDataSource(private val firestore: FirebaseFirestore) {

    companion object {
        private const val COLLECTION_NAME = "videojuegos"
    }

    suspend fun getAll(): List<Videojuego> {
        return try {
            val snapshot = firestore.collection(COLLECTION_NAME).get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Videojuego::class.java)?.copy(id = doc.id.toIntOrNull() ?: 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getById(id: Int): Videojuego? {
        return try {
            val snapshot = firestore.collection(COLLECTION_NAME).document(id.toString()).get().await()
            snapshot.toObject(Videojuego::class.java)?.copy(id = id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun save(videojuego: Videojuego): Boolean {
        return try {
            firestore.collection(COLLECTION_NAME).document(videojuego.id.toString()).set(videojuego).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun update(videojuego: Videojuego): Boolean {
        return try {
            firestore.collection(COLLECTION_NAME).document(videojuego.id.toString()).set(videojuego).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun delete(id: Int): Boolean {
        return try {
            firestore.collection(COLLECTION_NAME).document(id.toString()).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
