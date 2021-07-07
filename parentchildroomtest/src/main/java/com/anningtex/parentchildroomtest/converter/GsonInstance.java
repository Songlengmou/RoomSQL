package com.anningtex.parentchildroomtest.converter;

import com.google.gson.Gson;

/**
 * @Author Song
 * @Desc:将gson抽离出单例模式
 * @Date：2021-07-06
 */
public class GsonInstance {
    private static GsonInstance INSTANCE;
    private static Gson gson;

    public static GsonInstance getInstance() {
        if (INSTANCE == null) {
            synchronized (GsonInstance.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GsonInstance();
                }
            }
        }
        return INSTANCE;
    }

    public Gson getGson() {
        if (gson == null) {
            synchronized (GsonInstance.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }
}