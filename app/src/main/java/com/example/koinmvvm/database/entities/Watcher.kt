package com.example.koinmvvm.database.entities

import androidx.room.ColumnInfo
import com.example.koinmvvm.database.constants.FIELD_SB_CREATED_AT
import com.example.koinmvvm.database.constants.FIELD_SB_DELETED_AT
import com.example.koinmvvm.database.constants.FIELD_SB_STATUS
import com.example.koinmvvm.database.constants.FIELD_SB_UPDATED_AT
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class Watcher(
    @SerializedName(FIELD_SB_STATUS) @ColumnInfo(name = FIELD_SB_STATUS) var status: Int = 0,
    @SerializedName(FIELD_SB_CREATED_AT) @ColumnInfo(name = FIELD_SB_CREATED_AT) var createdAt: Date? = null,
    @SerializedName(FIELD_SB_UPDATED_AT) @ColumnInfo(name = FIELD_SB_UPDATED_AT) var updatedAt: Date? = Date(),
    @SerializedName(FIELD_SB_DELETED_AT) @ColumnInfo(name = FIELD_SB_DELETED_AT) var deletedAt: Date? = null
) : Serializable