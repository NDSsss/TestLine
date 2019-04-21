package com.ndscompany.text.app;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.ndscompany.text.db.ProjectsDb;

public class App extends Application {
    private static App instanse;
    private ProjectsDb database;

    @Override
    public void onCreate() {
        super.onCreate();
        instanse = this;
        database = Room.databaseBuilder(this,ProjectsDb.class, "ProjectsDb").allowMainThreadQueries().build();
    }

    public static App getInstanse() {
        return instanse;
    }

    public void setInstanse(App instanse) {
        this.instanse = instanse;
    }

    public ProjectsDb getDatabase() {
        if(database == null){
            database = Room.databaseBuilder(this,ProjectsDb.class, "ProjectsDb").allowMainThreadQueries().build();
        }
        return database;
    }

    public void setDatabase(ProjectsDb database) {
        this.database = database;
    }
}
