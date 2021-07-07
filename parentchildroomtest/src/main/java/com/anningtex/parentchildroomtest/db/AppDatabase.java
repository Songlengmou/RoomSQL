package com.anningtex.parentchildroomtest.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.anningtex.parentchildroomtest.dao.ParentChildDao;
import com.anningtex.parentchildroomtest.entity.ParentChildEntity;

/**
 * @Author Song
 * @Date：2021-07-07
 */
@Database(entities = {ParentChildEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ParentChildDao parentChildDao();
}