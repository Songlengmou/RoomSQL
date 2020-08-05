package com.anningtex.roomsql.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @Author Song
 * @Desc:
 */
@Database(entities = {Student.class},version = 2)
public abstract class MyDatabaseUpData extends RoomDatabase {
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //此处对于数据库中的所有更新都需要写下面的代码
            database.execSQL("ALTER TABLE student "
                    + " ADD COLUMN last_update INTEGER");
        }
    };
}
