package com.example.filmsreview.room.service

import androidx.room.Transaction
import com.example.filmsreview.room.dao.HistoryDao
import com.example.filmsreview.room.entity.HistoryEntity

class HistoryServiceImpl(private val historyDao: HistoryDao) : HistoryService {

    override fun getAllCache(): List<HistoryEntity> {
        return historyDao.all()
    }

    override fun historyInsert(entity: HistoryEntity) {
        historyDao.insert(entity)
    }
}