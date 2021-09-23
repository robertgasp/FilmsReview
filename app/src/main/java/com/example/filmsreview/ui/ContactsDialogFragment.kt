package com.example.filmsreview.ui

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.filmsreview.R
import com.example.filmsreview.databinding.FragmentContactsDialogBinding


class ContactsDialogFragment : DialogFragment() {

    private var _binding: FragmentContactsDialogBinding? = null
    private val binding get() = _binding!!

    private fun permissionResult() =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                getContacts()
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.need_permission_to_read_contacts),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun getTheme(): Int  = R.style.ContactsDialogFragment


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactsDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkContactsPermission()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    private fun checkContactsPermission() {
        context?.let { notNullContext ->
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    notNullContext,
                    Manifest.permission.READ_CONTACTS
                ) -> {
                    getContacts()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        permissionResult().launch(Manifest.permission.READ_CONTACTS)
    }

    private fun getContacts() {
        context?.let {
            //отправляем запрос на получение контактов и получае отет в виде курсора
            val cursorWithContacts: Cursor? = it.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )

            cursorWithContacts?.let {
                cursor->
                for (i in 0..cursor.count){
                    //переходим на позицию в Cursor'e
                    if (cursor.moveToPosition(i)){
                        //берем из Cursor'a столбец с именем
                        val name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        addView(name)
                    }
                }
            }
            cursorWithContacts?.close()
        }
    }

    private fun addView(textToShow: String?) = with(binding) {
        containerForContacts.addView(AppCompatTextView(requireContext()).apply {
            text=textToShow
            textSize=resources.getDimension(R.dimen.main_container_text_size)
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContactsDialogFragment
    }


}