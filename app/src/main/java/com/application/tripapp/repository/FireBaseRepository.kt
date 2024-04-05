package com.application.tripapp.repository

import android.util.Log
import com.application.tripapp.db.PictureEntity
import com.application.tripapp.network.PictureOfTheDayResponse
import com.application.tripapp.utils.toEntity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FireBaseRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,

    ) {

    val picturesSubject = BehaviorSubject.create<List<PictureEntity>>()
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


    fun getPicturesOfTheDay() {
        val userId = firebaseAuth.currentUser?.uid
        Log.d("FireBaseRepository", "Successfully got data from Firebase")
        if (userId != null) {
            Firebase.database.getReference("picturesOfTheDay")
                .child(userId)
                .get()
                .addOnSuccessListener { dataSnapshot ->
                    Log.d("FireBaseRepository", "Successfully got data from Firebase")
                    val pictures =
                        dataSnapshot.children.mapNotNull { it.getValue(PictureEntity::class.java) }
                    picturesSubject.onNext(pictures)
                }
                .addOnFailureListener {
                    Log.e("FireBaseRepository", "Failed to get data from Firebase", it)
                    picturesSubject.onError(it)
                }
        } else {
            Log.e("FireBaseRepository", "User is not logged in")
            picturesSubject.onError(Exception("User is not logged in"))
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

    suspend fun isPictureAdded(picture: PictureEntity): Boolean {
        return getAddedPicture(picture) != null
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