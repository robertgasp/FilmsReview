package com.example.filmsreview.model.fireBaseDB

import com.example.filmsreview.room.entity.FavoriteFilmEntity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseDbManager {
    val db = Firebase.database.getReference("main")
    val auth = Firebase.auth

    fun postFilmToDB(filmLitFromLocalDB: FavoriteFilmEntity) {
        if (auth.uid != null) {
            db.child(auth.uid.toString()).child(filmLitFromLocalDB.id.toString())
                .child("FavoriteFilmEntity")
                .setValue((filmLitFromLocalDB))
        }
    }

    fun deleteFilmFromDB(filmLitFromLocalDB: FavoriteFilmEntity) {
        if (auth.uid != null) {
            db.child(auth.uid.toString()).child(filmLitFromLocalDB.id.toString()).removeValue()
        }
    }

    fun getFromDB(fireBaseCallback: FireBaseCallback) {
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listFilms: MutableList<FavouriteFilmFirebase> = mutableListOf()
                val result = snapshot.child(auth.uid.toString())
                for (item in result.children) {
                    val film =
                        item.child("FavoriteFilmEntity").getValue(FavouriteFilmFirebase::class.java)

                    if (film != null) {
                        listFilms.add(film)
                    }
                }
                fireBaseCallback.getData(listFilms)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun interface FireBaseCallback {
        fun getData(list: MutableList<FavouriteFilmFirebase>)
    }
}