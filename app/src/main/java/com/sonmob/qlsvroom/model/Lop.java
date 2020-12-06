package com.sonmob.qlsvroom.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Lop")
public class Lop implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String malop;
    private String tenlop;

    public Lop(String tenlop, String malop) {
        this.tenlop = tenlop;
        this.malop = malop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenlop() {
        return tenlop;
    }

    public void setTenlop(String tenlop) {
        this.tenlop = tenlop;
    }

    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }
}
