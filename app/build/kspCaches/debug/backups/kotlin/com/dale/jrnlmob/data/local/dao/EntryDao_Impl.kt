package com.dale.jrnlmob.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.dale.jrnlmob.`data`.local.entity.EntryEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class EntryDao_Impl(
  __db: RoomDatabase,
) : EntryDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfEntryEntity: EntityInsertAdapter<EntryEntity>

  private val __deleteAdapterOfEntryEntity: EntityDeleteOrUpdateAdapter<EntryEntity>

  private val __updateAdapterOfEntryEntity: EntityDeleteOrUpdateAdapter<EntryEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfEntryEntity = object : EntityInsertAdapter<EntryEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `entries` (`id`,`date_time`,`title`,`body`,`mood`,`location`,`weather`,`tags`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: EntryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.dateTime)
        statement.bindText(3, entity.title)
        statement.bindText(4, entity.body)
        val _tmpMood: String? = entity.mood
        if (_tmpMood == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpMood)
        }
        val _tmpLocation: String? = entity.location
        if (_tmpLocation == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpLocation)
        }
        val _tmpWeather: String? = entity.weather
        if (_tmpWeather == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpWeather)
        }
        val _tmpTags: String? = entity.tags
        if (_tmpTags == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpTags)
        }
        statement.bindLong(9, entity.createdAt)
        statement.bindLong(10, entity.updatedAt)
      }
    }
    this.__deleteAdapterOfEntryEntity = object : EntityDeleteOrUpdateAdapter<EntryEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `entries` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: EntryEntity) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfEntryEntity = object : EntityDeleteOrUpdateAdapter<EntryEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `entries` SET `id` = ?,`date_time` = ?,`title` = ?,`body` = ?,`mood` = ?,`location` = ?,`weather` = ?,`tags` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: EntryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.dateTime)
        statement.bindText(3, entity.title)
        statement.bindText(4, entity.body)
        val _tmpMood: String? = entity.mood
        if (_tmpMood == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpMood)
        }
        val _tmpLocation: String? = entity.location
        if (_tmpLocation == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpLocation)
        }
        val _tmpWeather: String? = entity.weather
        if (_tmpWeather == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpWeather)
        }
        val _tmpTags: String? = entity.tags
        if (_tmpTags == null) {
          statement.bindNull(8)
        } else {
          statement.bindText(8, _tmpTags)
        }
        statement.bindLong(9, entity.createdAt)
        statement.bindLong(10, entity.updatedAt)
        statement.bindLong(11, entity.id)
      }
    }
  }

  public override suspend fun insertEntry(entry: EntryEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfEntryEntity.insertAndReturnId(_connection, entry)
    _result
  }

  public override suspend fun deleteEntry(entry: EntryEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __deleteAdapterOfEntryEntity.handle(_connection, entry)
  }

  public override suspend fun updateEntry(entry: EntryEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfEntryEntity.handle(_connection, entry)
  }

  public override fun getAllEntries(): Flow<List<EntryEntity>> {
    val _sql: String = "SELECT * FROM entries ORDER BY date_time DESC"
    return createFlow(__db, false, arrayOf("entries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDateTime: Int = getColumnIndexOrThrow(_stmt, "date_time")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfBody: Int = getColumnIndexOrThrow(_stmt, "body")
        val _columnIndexOfMood: Int = getColumnIndexOrThrow(_stmt, "mood")
        val _columnIndexOfLocation: Int = getColumnIndexOrThrow(_stmt, "location")
        val _columnIndexOfWeather: Int = getColumnIndexOrThrow(_stmt, "weather")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<EntryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: EntryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpDateTime: String
          _tmpDateTime = _stmt.getText(_columnIndexOfDateTime)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpBody: String
          _tmpBody = _stmt.getText(_columnIndexOfBody)
          val _tmpMood: String?
          if (_stmt.isNull(_columnIndexOfMood)) {
            _tmpMood = null
          } else {
            _tmpMood = _stmt.getText(_columnIndexOfMood)
          }
          val _tmpLocation: String?
          if (_stmt.isNull(_columnIndexOfLocation)) {
            _tmpLocation = null
          } else {
            _tmpLocation = _stmt.getText(_columnIndexOfLocation)
          }
          val _tmpWeather: String?
          if (_stmt.isNull(_columnIndexOfWeather)) {
            _tmpWeather = null
          } else {
            _tmpWeather = _stmt.getText(_columnIndexOfWeather)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_columnIndexOfTags)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              EntryEntity(_tmpId,_tmpDateTime,_tmpTitle,_tmpBody,_tmpMood,_tmpLocation,_tmpWeather,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getEntryById(id: Long): Flow<EntryEntity?> {
    val _sql: String = "SELECT * FROM entries WHERE id = ?"
    return createFlow(__db, false, arrayOf("entries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDateTime: Int = getColumnIndexOrThrow(_stmt, "date_time")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfBody: Int = getColumnIndexOrThrow(_stmt, "body")
        val _columnIndexOfMood: Int = getColumnIndexOrThrow(_stmt, "mood")
        val _columnIndexOfLocation: Int = getColumnIndexOrThrow(_stmt, "location")
        val _columnIndexOfWeather: Int = getColumnIndexOrThrow(_stmt, "weather")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: EntryEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpDateTime: String
          _tmpDateTime = _stmt.getText(_columnIndexOfDateTime)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpBody: String
          _tmpBody = _stmt.getText(_columnIndexOfBody)
          val _tmpMood: String?
          if (_stmt.isNull(_columnIndexOfMood)) {
            _tmpMood = null
          } else {
            _tmpMood = _stmt.getText(_columnIndexOfMood)
          }
          val _tmpLocation: String?
          if (_stmt.isNull(_columnIndexOfLocation)) {
            _tmpLocation = null
          } else {
            _tmpLocation = _stmt.getText(_columnIndexOfLocation)
          }
          val _tmpWeather: String?
          if (_stmt.isNull(_columnIndexOfWeather)) {
            _tmpWeather = null
          } else {
            _tmpWeather = _stmt.getText(_columnIndexOfWeather)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_columnIndexOfTags)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              EntryEntity(_tmpId,_tmpDateTime,_tmpTitle,_tmpBody,_tmpMood,_tmpLocation,_tmpWeather,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchEntries(query: String): Flow<List<EntryEntity>> {
    val _sql: String = """
        |
        |        SELECT * FROM entries 
        |        WHERE title LIKE '%' || ? || '%' 
        |        OR body LIKE '%' || ? || '%'
        |        OR location LIKE '%' || ? || '%'
        |        OR tags LIKE '%' || ? || '%'
        |        ORDER BY date_time DESC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("entries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        _argIndex = 3
        _stmt.bindText(_argIndex, query)
        _argIndex = 4
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfDateTime: Int = getColumnIndexOrThrow(_stmt, "date_time")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfBody: Int = getColumnIndexOrThrow(_stmt, "body")
        val _columnIndexOfMood: Int = getColumnIndexOrThrow(_stmt, "mood")
        val _columnIndexOfLocation: Int = getColumnIndexOrThrow(_stmt, "location")
        val _columnIndexOfWeather: Int = getColumnIndexOrThrow(_stmt, "weather")
        val _columnIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<EntryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: EntryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpDateTime: String
          _tmpDateTime = _stmt.getText(_columnIndexOfDateTime)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpBody: String
          _tmpBody = _stmt.getText(_columnIndexOfBody)
          val _tmpMood: String?
          if (_stmt.isNull(_columnIndexOfMood)) {
            _tmpMood = null
          } else {
            _tmpMood = _stmt.getText(_columnIndexOfMood)
          }
          val _tmpLocation: String?
          if (_stmt.isNull(_columnIndexOfLocation)) {
            _tmpLocation = null
          } else {
            _tmpLocation = _stmt.getText(_columnIndexOfLocation)
          }
          val _tmpWeather: String?
          if (_stmt.isNull(_columnIndexOfWeather)) {
            _tmpWeather = null
          } else {
            _tmpWeather = _stmt.getText(_columnIndexOfWeather)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_columnIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_columnIndexOfTags)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              EntryEntity(_tmpId,_tmpDateTime,_tmpTitle,_tmpBody,_tmpMood,_tmpLocation,_tmpWeather,_tmpTags,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getEntryCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM entries"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAll() {
    val _sql: String = "DELETE FROM entries"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
