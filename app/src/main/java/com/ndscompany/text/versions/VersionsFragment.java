package com.ndscompany.text.versions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndscompany.text.R;
import com.ndscompany.text.app.App;
import com.ndscompany.text.classes.Version;
import com.ndscompany.text.versions.adddialog.AddVersionDialog;

import java.util.ArrayList;
import java.util.List;

public class VersionsFragment extends Fragment implements VersionsAdapter.VersionsAdapterinterface {

    private static final String PROJECT_ID = "VersionsFragment.PROJECT_ID";

    private RecyclerView rvVersions;
    private VersionsAdapter adapter;
    private List<Version> versions;
    private long projectId;

    public static VersionsFragment newInstance(long projectId){
        VersionsFragment fragment = new VersionsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(PROJECT_ID, projectId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgs();
        getVersions();
    }

    private void getArgs(){
        if(getArguments()!=null){
            projectId = getArguments().getLong(PROJECT_ID);
        }
    }

    private void getVersions(){
        versions = App.getInstanse().getDatabase().versionDao().getVersionsByProjectId(projectId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_versions, container,false);
        rvVersions = view.findViewById(R.id.rv_fragment_versions);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new VersionsAdapter();
        rvVersions.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setVersions(versions);
        rvVersions.setAdapter(adapter);
        adapter.setVersionsAdapterinterface(this);
    }

    @Override
    public void addVersion() {
        AddVersionDialog  dialog = new AddVersionDialog();
        dialog.setAddProjectDialogInterface(new AddVersionDialog.AddProjectDialogInterface() {
            @Override
            public void saveName(String name) {
                Version version = new Version();
                version.setName(name);
                version.setProjectId(projectId);
                App.getInstanse().getDatabase().versionDao().insert(version);
                adapter.setVersions(App.getInstanse().getDatabase().versionDao().getVersionsByProjectId(projectId));
            }
        });
        dialog.show(getChildFragmentManager(),AddVersionDialog.TAG);
    }
}
