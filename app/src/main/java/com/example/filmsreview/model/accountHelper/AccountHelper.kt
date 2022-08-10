package com.example.filmsreview.model.accountHelper

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.filmsreview.R
import com.example.filmsreview.ui.views.FragmentContract
import com.google.firebase.auth.FirebaseUser

class AccountHelper(
    private var fragment: Fragment
) {

    private var user: FirebaseUser? = null
    private val mAuth = FirebaseAuthentication.mAuth

    fun singUpWithEmail(email: String, password: String, dialogDismiss: () -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result.user?.let { sendEmailVerification(it) }
                    user = task.result.user
                    val fragmentUpdater = fragment as FragmentContract
                    fragmentUpdater.update(user)
                } else {
                    Toast.makeText(
                        fragment.requireContext(),
                        fragment.resources.getString(R.string.sign_up_error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }


    fun signInWithEmail(email: String, password: String, dialogDismiss: () -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user = task.result.user
                    val fragmentUpdater = fragment as FragmentContract
                    fragmentUpdater.update(user)
                    dialogDismiss()
                } else {

                    Toast.makeText(
                        fragment.requireContext(),
                        fragment.resources.getString(R.string.sign_in_error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    fragment.requireContext(),
                    fragment.resources.getString(R.string.send_verification_email_successful),
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                Toast.makeText(
                    fragment.requireContext(),
                    fragment.resources.getString(R.string.send_verification_email_error),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }


}