package com.example.data.data_soruce.auth

import android.util.Log
import com.example.domain.repositories.auth.AuthRemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val mFirebaseAuth: FirebaseAuth
) : AuthRemoteDataSource {
    override fun register(
        email : String,
        password : String,
        onSuccess : () -> Unit,
        onFailure : (Throwable) -> Unit
    ){
        mFirebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                Log.e("FIB Auth" , "User Registered Successfully")
                onSuccess()
            }.addOnFailureListener {
                onFailure(it)
                Log.e("FIB Auth","Error : ${it.message}")
            }
    }

    override fun login(
        email: String,
        password: String,
        onSuccess: (String?) -> Unit,
        onFailure: (Throwable) -> Unit,
    ) {
        mFirebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                Log.e("FIB Auth" , "User logged Successfully")
                onSuccess(it.user?.uid)
            }.addOnFailureListener {
                onFailure(it)
                Log.e("FIB Auth","Error : ${it.message}")
            }
    }
}