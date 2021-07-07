package com.anningtex.parentchildroomtest.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.anningtex.parentchildroomtest.entity.ParentChildEntity;

import java.util.List;

/**
 * @Author Song
 * @Date：2021-07-07
 */
@Dao
public interface ParentChildDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertParentChildEntity(ParentChildEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ParentChildEntity> entities);

    @Query("SELECT * FROM ParentChildEntity")
    List<ParentChildEntity> getAllData();
}