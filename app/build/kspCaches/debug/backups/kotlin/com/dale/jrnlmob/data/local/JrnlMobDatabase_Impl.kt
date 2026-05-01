package com.dale.jrnlmob.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.dale.jrnlmob.`data`.local.dao.EntryDao
import com.dale.jrnlmob.`data`.local.dao.EntryDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class JrnlMobDatabase_Impl : JrnlMobDatabase() {
  private val _entryDao: Lazy<EntryDao> = lazy {
    EntryDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1,
        "d78ea537389ecd00d649fdad3ec49f04", "a7ee358cf6edc88dd0b1cc8b56d0bb29") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `entries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date_time` TEXT NOT NULL, `title` TEXT NOT NULL, `body` TEXT NOT NULL, `mood` TEXT, `location` TEXT, `weather` TEXT, `tags` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd78ea537389ecd00d649fdad3ec49f04')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `entries`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsEntries: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsEntries.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsEntries.put("date_time", TableInfo.Column("date_time", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsEntries.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsEntries.put("body", TableInfo.Column("body", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsEntries.put("mood", TableInfo.Column("mood", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsEntries.put("location", TableInfo.Column("location", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsEntries.put("weather", TableInfo.Column("weather", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsEntries.put("tags", TableInfo.Column("tags", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsEntries.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsEntries.put("updated_at", TableInfo.Column("updated_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysEntries: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesEntries: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoEntries: TableInfo = TableInfo("entries", _columnsEntries, _foreignKeysEntries,
            _indicesEntries)
        val _existingEntries: TableInfo = read(connection, "entries")
        if (!_infoEntries.equals(_existingEntries)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |entries(com.dale.jrnlmob.data.local.entity.EntryEntity).
              | Expected:
              |""".trimMargin() + _infoEntries + """
              |
              | Found:
              |""".trimMargin() + _existingEntries)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "entries")
  }

  public override fun clearAllTables() {
    super.performClear(false, "entries")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(EntryDao::class, EntryDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun entryDao(): EntryDao = _entryDao.value
}
