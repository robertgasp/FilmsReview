package com.example.filmsreview.model.database.comments

import android.provider.ContactsContract
import android.text.Editable
import android.widget.EditText
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import java.util.*

@Entity
data class CommentsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val dateOfWatching: String,
    val userComments: String

    //@Embedded val factDataObj: FactDataObj
)
