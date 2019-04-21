package com.ndscompany.text.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ndscompany.text.classes.Project;
import com.ndscompany.text.classes.TestCase;
import com.ndscompany.text.classes.Version;

@Database(entities = {Project.class, Version.class, TestCase.class}, version = 1)
public abstract class ProjectsDb extends RoomDatabase {
    public abstract ProjectDao projectDao();
    public abstract VersionDao versionDao();
    public abstract TestCaseDao testCaseDao();
}
