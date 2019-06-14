package com.ndscompany.text.projects;

import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ndscompany.text.R;
import com.ndscompany.text.app.App;
import com.ndscompany.text.classes.Project;
import com.ndscompany.text.projects.createdialog.AddProjectDialog;

public class MainActivity extends AppCompatActivity {

    private DialogFragment addProjectDialog;
    private ProjectsAdapter adapter;
    private ViewPager vpProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity", "onCreate: ");
        setContentView(R.layout.activity_projects);
        adapter = new ProjectsAdapter(getSupportFragmentManager(), App.getInstanse().getDatabase().projectDao().getProjects());
        vpProjects = findViewById(R.id.vp_activity_projects);
        vpProjects.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_create_project:
                addProjectDialog = new AddProjectDialog();
                ((AddProjectDialog) addProjectDialog).setAddProjectDialogInterface(new AddProjectDialog.AddProjectDialogInterface() {
                    @Override
                    public void saveName(String name) {
                        Project project = new Project();
                        project.setProjectName(name);
                        App.getInstanse().getDatabase().projectDao().insert(project);
                        adapter.setProjects(App.getInstanse().getDatabase().projectDao().getProjects());
                    }
                });
                addProjectDialog.show(getSupportFragmentManager(), AddProjectDialog.TAG);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
