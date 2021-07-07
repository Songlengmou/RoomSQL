package com.anningtex.parentchildroomtest.converter;

import androidx.room.TypeConverter;

import com.anningtex.parentchildroomtest.entity.ParentChildEntity;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @Author Song
 * @Desc:转换类型
 * @Date：2021-07-07
 */
public class InfoConverter {
    @TypeConverter
    public String objectToString(List<ParentChildEntity.InfoEntity> list) {
        return GsonInstance.getInstance().getGson().toJson(list);
    }

    @TypeConverter
    public List<ParentChildEntity.InfoEntity> stringToObject(String json) {
        Type listType = new TypeToken<List<ParentChildEntity.InfoEntity>>() {
        }.getType();
        return GsonInstance.getInstance().getGson().fromJson(json, listType);
    }
}