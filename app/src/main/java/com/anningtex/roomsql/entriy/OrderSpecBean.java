package com.anningtex.roomsql.entriy;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * @author Administrator
 */
@Entity(primaryKeys = {"order", "metersPerBale", "unitEn"})
public class OrderSpecBean {
    @NonNull
    private String order;
    @NonNull
    private String metersPerBale;
    @NonNull
    private String unitEn;

    public OrderSpecBean(String order, String metersPerBale, String unitEn) {
        this.order = order;
        this.metersPerBale = metersPerBale;
        this.unitEn = unitEn;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getMetersPerBale() {
        return metersPerBale;
    }

    public void setMetersPerBale(String metersPerBale) {
        this.metersPerBale = metersPerBale;
    }

    public String getUnitEn() {
        return unitEn;
    }

    public void setUnitEn(String unitEn) {
        this.unitEn = unitEn;
    }
}
