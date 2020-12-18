package com.anningtex.roomsql.entriy;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @Author Song
 * @Desc: Model
 * 说明：
 * @Entity标签，通过该标签将该类与Room中表关联起来。tableName属性可以为该表设置名字，如果不设置，则表名与类名相同。
 * @PrimaryKey标签用于指定该字段作为表的主键。
 * @ColumnInfo标签可用于设置该字段存储在数据库表中的名字并指定字段的类型。
 * @Ignore标签用来告诉系统忽略该字段或者方法。
 */

@Entity(tableName = "Student")
public class StudentBean {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    public String name;

    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.TEXT)
    public String age;

    /**
     * 新增键值
     */
    @ColumnInfo(name = "OLID", typeAffinity = ColumnInfo.INTEGER)
    private int olid;

    @ColumnInfo(name = "TEST_NAME", typeAffinity = ColumnInfo.TEXT)
    private String testName;

    @ColumnInfo(name = "BHIIID", typeAffinity = ColumnInfo.INTEGER)
    private int BHIIID;

    public StudentBean(int id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    /**
     * 由于Room只能识别和使用一个构造器，如果希望定义多个构造器，可以使用Ignore标签，让Room忽略这个构造器
     * 同样，@Ignore标签还可用于字段，使用@Ignore标签标记过的字段，Room不会持久化该字段的数据
     */
    @Ignore
    public StudentBean(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public int getOlid() {
        return olid;
    }

    public void setOlid(int olid) {
        this.olid = olid;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getBHIIID() {
        return BHIIID;
    }

    public void setBHIIID(int BHIIID) {
        this.BHIIID = BHIIID;
    }
}
