package com.anningtex.roomsql.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.anningtex.roomsql.entriy.OrderSpecBean;

import java.util.List;

/**
 * @author Administrator
 */
@Dao
public interface OrderSpecDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrderSpecEntity(OrderSpecBean orderSpecBean);

    @Query("SELECT * FROM OrderSpecBean")
    List<OrderSpecBean> queryAll();

    @Delete()
    void deleteOrderSpecEntity(OrderSpecBean orderSpecBean);
}
