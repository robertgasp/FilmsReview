package com.example.filmsreview.repository

import android.util.Log
import com.example.filmsreview.model.ClickToSaveComments
import com.example.filmsreview.model.FilmsRepositoryInterface
import com.example.filmsreview.model.database.comments.CommentsEntity
import com.example.filmsreview.model.database.comments.DataBaseOfComments
import com.example.filmsreview.model.database.history.DataBase
import com.example.filmsreview.model.database.history.HistoryEntity
import com.example.filmsreview.repository.rest.rest_entities.FactDataObj
import com.example.filmsreview.repository.rest.rest_entities.FactDataObjForDB
import com.example.filmsreview.repository.rest.rest_entities.FilmRepo

class FilmsRepository : FilmsRepositoryInterface, ClickToSaveComments {

    override fun getFilmFromInternet(): List<FactDataObj> = FactDataObj.getFilmsListFromInternet()

    override fun getFilm(id: Int?): FactDataObj {
        val lang = "ru-RU"
        val dataObj = FilmRepo.api.getOneFilmFromInternet(id, lang).execute().body()

        return FactDataObj(
            id = dataObj?.id,
            posterPath = dataObj?.posterPath,
            title = dataObj?.title,
            releaseDate = dataObj?.releaseDate,
            mediaType = dataObj?.mediaType,
            voteAverage = dataObj?.voteAverage,
            overview = dataObj?.overview,
            adult = dataObj?.adult
        )
    }


    override fun getComment(id: Int?): FactDataObjForDB =
        convertEntityToComment(id)

    private fun convertEntityToComment(id: Int?): FactDataObjForDB {
        lateinit var factDataObjForDB: FactDataObjForDB
        id?.let {
            val dataFromDB = DataBaseOfComments.db.commentsDao().getDataById(id.toLong())
            Log.i("dataFromDB", "=" + dataFromDB.toString())  //почему-то не читает

            factDataObjForDB = if (dataFromDB != null) {
                FactDataObjForDB(
                    FactDataObj(dataFromDB.id?.toInt()),
                    dataFromDB.dateOfWatching,
                    dataFromDB.userComments
                )
            } else
                FactDataObjForDB(FactDataObj(0), "", "")
        }
        return factDataObjForDB
    }


    override fun saveComments(factDataObjForDB: FactDataObjForDB) {
        DataBaseOfComments.db.commentsDao()
            .insert(
                CommentsEntity(
                    factDataObjForDB.factDataObj.id?.toLong(),
                    factDataObjForDB.dateOfWatchig,
                    factDataObjForDB.userComments
                )
            )
        Log.i("userComment", "=" + factDataObjForDB.userComments)
    }


    // дальше код для реализации отображения на отдельном фрагменте истории запросов
    override fun getAllHistory(): List<FactDataObjForDB> =
        convertHistoryEntityToFactDataObj(DataBase.db.historyDao().all())


    override fun saveEntity(factDataObjForDB: FactDataObjForDB) {
        // DataBase.db.historyDao().insert(convertFactDataObjToEntities(factDataObjForDB))
    }


    private fun convertHistoryEntityToFactDataObj(entityList: List<HistoryEntity>): List<FactDataObjForDB> =
        entityList.map {
            FactDataObjForDB(FactDataObj(it.id?.toInt()), it.dateOfWatching, it.userComments)
        }

}