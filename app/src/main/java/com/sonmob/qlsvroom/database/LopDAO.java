package com.sonmob.qlsvroom.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sonmob.qlsvroom.model.Lop;

import java.util.List;

@Dao
public interface LopDAO {

    @Query("SELECT * FROM Lop")
    List<Lop> getAllLop();

    @Query("SELECT * FROM Lop WHERE  malop= :malop")
    List<Lop> checkMalop(String malop);

    @Query("SELECT * FROM Lop WHERE tenlop LIKE '%' || :tenlop || '%'")
    List<Lop> searchLop(String tenlop);

    @Insert
    void insertLop(Lop lop);

    @Update
    void updateLop(Lop lop);

    @Delete
    void deleteLop(Lop lop);
}
