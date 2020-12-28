package com.anningtex.roomsql.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.anningtex.roomsql.dao.OrderSpecDao;
import com.anningtex.roomsql.dao.PhoneDao;
import com.anningtex.roomsql.dao.StudentDao;
import com.anningtex.roomsql.entriy.OrderSpecBean;
import com.anningtex.roomsql.entriy.PhoneBean;
import com.anningtex.roomsql.entriy.StudentBean;

/**
 * @Author Song
 */
@Database(entities = {StudentBean.class, PhoneBean.class, OrderSpecBean.class}, version = 4, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "Student.db";
    private static MyDataBase databaseInstance;

    public static synchronized MyDataBase getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room
                    .databaseBuilder(context.getApplicationContext(), MyDataBase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build();
        }
        return databaseInstance;
    }

    public abstract StudentDao studentDao();

    public abstract PhoneDao phoneDao();

    /**
     * 1.升级数据库
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
    /**
     * 2.原表中增加一个键值，并且再次创建新表Phone
     */
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

    /**
     * 3.不需要写上方的这些Migration，直接创建一个新表,升级数据库版本进行调用
     */
    public abstract OrderSpecDao getOrderSpecDao();
}
