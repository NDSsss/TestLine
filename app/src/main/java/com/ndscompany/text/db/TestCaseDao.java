package com.ndscompany.text.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ndscompany.text.classes.TestCase;
import com.ndscompany.text.classes.Version;

import java.util.List;

@Dao
public interface TestCaseDao {

    @Query("SELECT * FROM TestCase")
    List<TestCase> getTestCases();

    @Query("SELECT * FROM TestCase WHERE versionId = :versionId")
    List<TestCase> getTestCasesByVersionId(long versionId);

    @Insert
    void insert(TestCase testCase);

    @Update
    void update(TestCase testCase);

    @Delete
    void delete(TestCase testCase);
}
