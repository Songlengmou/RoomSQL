package com.anningtex.roomsql.entriy;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @Author Song
 */
@Entity(tableName = "Phone")
public class PhoneBean {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "longId", typeAffinity = ColumnInfo.INTEGER)
    public int longId;

    @ColumnInfo(name = "NAME", typeAffinity = ColumnInfo.TEXT)
    public String name;

    @ColumnInfo(name = "NUMBER", typeAffinity = ColumnInfo.TEXT)
    public String number;

    public PhoneBean(int longId, String name, String number) {
        this.longId = longId;
        this.name = name;
        this.number = number;
    }

    @Ignore
    public PhoneBean(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public int getLongId() {
        return longId;
    }

    public void setLongId(int longId) {
        this.longId = longId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
