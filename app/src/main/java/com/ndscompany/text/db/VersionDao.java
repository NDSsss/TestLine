package com.ndscompany.text.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ndscompany.text.classes.Project;
import com.ndscompany.text.classes.Version;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface VersionDao {

    @Query("SELECT * FROM Version")
    List<Version> getVersions();

    @Query("SELECT * FROM Version WHERE id = :projectId")
    List<Version> getVersionsByProjectId(long projectId);

    @Insert
    void insert(Version version);

    @Update
    void update(Version version);

    @Delete
    void delete(Version version);
}
