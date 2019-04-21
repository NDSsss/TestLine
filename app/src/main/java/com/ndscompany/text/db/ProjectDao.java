package com.ndscompany.text.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ndscompany.text.classes.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM Project")
    List<Project> getProjects();

    @Insert
    void insert(Project project);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);


}
