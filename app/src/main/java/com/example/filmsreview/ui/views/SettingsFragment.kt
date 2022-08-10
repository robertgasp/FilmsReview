package com.example.filmsreview.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.filmsreview.R
import com.example.filmsreview.databinding.FragmentSettingsBinding
import com.example.filmsreview.model.accountHelper.FirebaseAuthentication
import com.example.filmsreview.ui.dialogs.Dialog
import com.example.filmsreview.ui.dialogs.DialogConst
import com.google.firebase.auth.FirebaseUser

class SettingsFragment : Fragment(), FragmentContract {

    private lateinit var textViewAccount: TextView
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var dialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        textViewAccount = binding.accountName

        dialog = Dialog(this) {
            //листенер на закрытие окна диалога, может пригодится
        }

        binding.settingsSignIn.setOnClickListener {
            dialog?.createSignDialog(DialogConst.SIGN_IN_STATE)
        }

        binding.settingsSignUp.setOnClickListener {
            dialog?.createSignDialog(DialogConst.SIGN_UP_STATE)

        }

        binding.signOut.setOnClickListener {
            update(null)
            FirebaseAuthentication.signOut()

        }
    }

    override fun update(user: FirebaseUser?) {
        if (user == null) {
            binding.accountName.text = getString(R.string.not_registred)
            binding.signOut.visibility = View.GONE
            binding.settingsSignUp.visibility = View.VISIBLE
            binding.settingsSignIn.visibility = View.VISIBLE

        } else {
            binding.settingsSignUp.visibility = View.GONE
            binding.settingsSignIn.visibility = View.GONE
            binding.signOut.visibility = View.VISIBLE
            binding.accountName.text = user.email
        }
    }

    override fun onStart() {
        super.onStart()
        update(FirebaseAuthentication.mAuth.currentUser)
    }
}