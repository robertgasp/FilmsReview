package com.example.filmsreview.room.service

import com.example.filmsreview.room.entity.HistoryEntity

interface HistoryService {
    fun getAllCache(): List<HistoryEntity>

    fun historyInsert(entity: HistoryEntity)
}