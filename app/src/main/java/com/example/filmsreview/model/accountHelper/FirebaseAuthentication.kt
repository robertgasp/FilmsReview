package com.example.filmsreview.model.accountHelper

import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object FirebaseAuthentication {
    val mAuth = FirebaseAuth.getInstance()
    fun signOut() {
        mAuth.signOut()
    }
}