package com.application.tripapp.repository

import androidx.lifecycle.MutableLiveData
import com.application.tripapp.R
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.model.PictureOfTheDay
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FireBaseRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,

    ) {
    val picturesResult = MutableLiveData<List<PictureEntity>>()
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
        picture: PictureEntity,
        onSuccess: () -> Unit,
        onError: (error: String) -> Unit
    ) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val key = Firebase.database.getReference("picturesOfTheDay").child(userId).push().key
            if (key != null) {
                picture.id = key
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

    fun getPicturesOfTheDay(onSuccess: (results: List<PictureEntity>) -> Unit,
                            onError: (error: String) -> Unit
    ) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            Firebase.database.getReference("picturesOfTheDay")
                .child(userId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val results =
                            dataSnapshot.children.mapNotNull { it.getValue(PictureEntity::class.java) }
                        onSuccess(results)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        onError(error.message)
                    }
                })
        } else {
            onError("User is not logged in")
        }
    }

    suspend fun isPictureAdded(picture: PictureEntity): Boolean =
        suspendCoroutine { continuation ->
            val pictureListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var isAdded = false
                    for (ds in dataSnapshot.children) {
                        if (ds.key == firebaseAuth.currentUser?.uid) {
                            for (child in ds.children) {
                                val pic = child.getValue(PictureEntity::class.java)
                                if (pic?.url == picture.url) {
                                    isAdded = true
                                    break
                                }
                            }
                        }
                        if (isAdded) break
                    }
                    continuation.resume(isAdded)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    continuation.resumeWithException(databaseError.toException())
                }
            }
            Firebase.database.getReference("picturesOfTheDay")
                .addListenerForSingleValueEvent(pictureListener)
        }

    fun signOut() {
        firebaseAuth.signOut()
    }
}