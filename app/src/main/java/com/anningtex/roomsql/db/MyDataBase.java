package com.anningtex.roomsql.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.anningtex.roomsql.dao.PhoneDao;
import com.anningtex.roomsql.dao.StudentDao;
import com.anningtex.roomsql.entriy.PhoneBean;
import com.anningtex.roomsql.entriy.StudentBean;

/**
 * @Author Song
 */
@Database(entities = {StudentBean.class, PhoneBean.class}, version = 3, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "Student.db";

    private static MyDataBase databaseInstance;

    public static synchronized MyDataBase getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room
                    .databaseBuilder(context.getApplicationContext(), MyDataBase.class, DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build();
        }
        return databaseInstance;
    }

    public abstract StudentDao studentDao();

    public abstract PhoneDao phoneDao();

    /**
     * 升级数据库
     * 增加两个键值
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Student"
                    + " ADD COLUMN OLID INTEGER NOT NULL DEFAULT 0");
            database.execSQL("ALTER TABLE Student"
                    + " ADD COLUMN TEST_NAME TEXT");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Student"
                    + " ADD COLUMN BHIIID INTEGER NOT NULL DEFAULT 0");

            database.execSQL("CREATE TABLE IF NOT EXISTS Phone " +
                    "(longId INTEGER PRIMARY KEY NOT NULL DEFAULT 0, " +
                    "NAME TEXT, NUMBER TEXT)");
        }
    };
}
