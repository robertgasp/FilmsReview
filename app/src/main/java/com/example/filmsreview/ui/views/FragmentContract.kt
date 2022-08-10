package com.example.filmsreview.ui.views

import com.google.firebase.auth.FirebaseUser

interface FragmentContract {

    fun update(user: FirebaseUser?)
}