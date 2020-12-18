package com.anningtex.roomsql.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.anningtex.roomsql.entriy.PhoneBean;

import java.util.List;

/**
 * @Author Song
 */
@Dao
public interface PhoneDao {
    @Insert
    void addPhoneData(PhoneBean phoneBean);

    @Query("SELECT * FROM Phone")
    List<PhoneBean> getPhoneList();

    @Query("SELECT * FROM Phone WHERE NAME = :name AND NUMBER=:number")
    PhoneBean getPhoneById(String name, String number);

    @Delete
    void deletePhoneData(PhoneBean phoneBean);

    @Update
    void updatePhoneData(PhoneBean phoneBean);
}
