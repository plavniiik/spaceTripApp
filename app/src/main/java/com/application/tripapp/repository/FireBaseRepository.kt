package com.application.tripapp.repository

import android.util.Log
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.network.PictureOfTheDayResponse
import com.application.tripapp.utils.toEntity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FireBaseRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,

    ) {

    val pictures = MutableStateFlow<List<PictureEntity>>(emptyList())
    fun signIn(
        email: String, password: String, onSuccess: () -> Unit,
        onError: (error: String) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onError(it.localizedMessage)
            }
    }

    fun signUp(
        email: String, password: String, onSuccess: () -> Unit,
        onError: (error: String) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {
                onError(it.localizedMessage)
            }

    }

    fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    fun savePictureOfTheDay(
        picture: PictureEntity?,
        onSuccess: () -> Unit,
        onError: (error: String) -> Unit
    ) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val key = Firebase.database.getReference("picturesOfTheDay").child(userId).push().key
            if (key != null) {
                picture?.id = key
                Firebase.database.getReference("picturesOfTheDay")
                    .child(userId)
                    .child(key)
                    .setValue(picture)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onError(it.localizedMessage) }
            } else {
                onError("Error generating unique key")
            }
        } else {
            onError("User is not logged in")
        }
    }


    fun deletePictureOfTheDay(
        pictureId: String,
        onSuccess: () -> Unit,
        onError: (error: String) -> Unit
    ) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            Firebase.database.getReference("picturesOfTheDay")
                .child(userId)
                .child(pictureId)
                .removeValue()
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onError(it.localizedMessage) }
        } else {
            onError("User is not logged in")
        }
    }


    fun getPicturesOfTheDay(coroutineScope: CoroutineScope) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            Firebase.database.getReference("picturesOfTheDay")
                .child(userId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        coroutineScope.launch {
                            val listPictures =
                                snapshot.children.mapNotNull { it.getValue(PictureEntity::class.java) }
                            pictures.emit(listPictures)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        } else {

        }
    }


    suspend fun getAddedPicture(picture: PictureEntity): PictureEntity? =
        suspendCoroutine { continuation ->
            val pictureListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var addedPicture: PictureEntity? = null
                    for (ds in dataSnapshot.children) {
                        if (ds.key == firebaseAuth.currentUser?.uid) {
                            for (child in ds.children) {
                                val pic = child.getValue(PictureEntity::class.java)
                                if (pic?.url == picture.url) {
                                    addedPicture = pic
                                    break
                                }
                            }
                        }
                        if (addedPicture != null) break
                    }
                    continuation.resume(addedPicture)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    continuation.resumeWithException(databaseError.toException())
                }
            }
            Firebase.database.getReference("picturesOfTheDay")
                .addListenerForSingleValueEvent(pictureListener)
        }

    suspend fun isPictureAdded(picture: PictureEntity): Flow<Boolean> = flow {
        emit(getAddedPicture(picture) != null)
    }

    suspend fun getPictureId(picture: PictureOfTheDayResponse): String {
        val entity = picture.toEntity()
        val addedPicture = getAddedPicture(entity)
        return if (addedPicture != null) {
            addedPicture.id
        } else {
            Firebase.database.getReference("picturesOfTheDay").push().key ?: ""
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}