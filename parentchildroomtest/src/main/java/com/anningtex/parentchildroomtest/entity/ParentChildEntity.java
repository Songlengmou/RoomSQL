package com.anningtex.parentchildroomtest.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.anningtex.parentchildroomtest.converter.InfoConverter;

import java.util.List;

/**
 * @Author Song
 * @Date：2021-07-07
 */
@Entity
@TypeConverters(InfoConverter.class)
public class ParentChildEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private List<InfoEntity> infoEntities;

    public ParentChildEntity() {

    }

    @Ignore
    public ParentChildEntity(String name, List<InfoEntity> infoEntities) {
        this.name = name;
        this.infoEntities = infoEntities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InfoEntity> getInfoEntities() {
        return infoEntities;
    }

    public void setInfoEntities(List<InfoEntity> infoEntities) {
        this.infoEntities = infoEntities;
    }

    @Override
    public String toString() {
        return "ParentChildEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", infoEntities=" + infoEntities.toString() +
                '}';
    }

    @Entity
    public static class InfoEntity {
        @PrimaryKey(autoGenerate = true)
        private long id;
        private int age;
        private String address;

        public InfoEntity() {

        }

        @Ignore
        public InfoEntity(int age, String address) {
            this.age = age;
            this.address = address;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "InfoEntity{" +
                    "id=" + id +
                    ", age=" + age +
                    ", address='" + address + '\'' +
                    '}';
        }
    }
}
