package com.ndscompany.text.projects;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ndscompany.text.classes.Project;
import com.ndscompany.text.versions.VersionsFragment;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends FragmentPagerAdapter {

    private List<Project> projects;

    public ProjectsAdapter(FragmentManager fm, List<Project> projects) {
        super(fm);
        this.projects = projects;
    }

    public void setProjects(List<Project> projects){
        this.projects = projects;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return VersionsFragment.newInstance(projects.get(i).getId());
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return projects.get(position).getProjectName();
    }

    @Override
    public int getCount() {
        return projects == null? 0 : projects.size();
    }
}
