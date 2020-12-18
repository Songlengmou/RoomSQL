package com.anningtex.roomsql.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.anningtex.roomsql.entriy.StudentBean;

import java.util.List;

/**
 * @Author Song
 * @Desc: 数据库表的增删改查
 */
@Dao
public interface StudentDao {
    @Insert
    void insertStudent(StudentBean studentBean);

    @Delete
    void deleteStudent(StudentBean studentBean);

    @Update
    void updateStudent(StudentBean studentBean);

    @Query("SELECT * FROM Student")
    List<StudentBean> getStudentList();

    @Query("SELECT * FROM Student WHERE id = :id")
    StudentBean getStudentById(int id);
}
