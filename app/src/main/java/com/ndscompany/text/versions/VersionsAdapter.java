package com.ndscompany.text.versions;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ndscompany.text.R;
import com.ndscompany.text.classes.Version;

import java.util.ArrayList;
import java.util.List;

public class VersionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<Version> versions;
    private VersionsAdapterinterface versionsAdapterinterface;

    public void setVersions(List<Version> versions){
        this.versions = versions;
        notifyDataSetChanged();
    }

    public void setVersionsAdapterinterface(VersionsAdapterinterface versionsAdapterinterface) {
        this.versionsAdapterinterface = versionsAdapterinterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case TYPE_ITEM:
                return new ViewHolderItem(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_version, viewGroup, false));
            case TYPE_FOOTER:
                return new ViewHolderFooter(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_versions_add, viewGroup, false));
                default:
                    return new ViewHolderFooter(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_versions_add, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof ViewHolderItem){
            ViewHolderItem viewHolderItem = (ViewHolderItem) viewHolder;
            viewHolderItem.tvName.setText(String.valueOf(versions.get(i).getProjectId())+" версия " + versions.get(i).getName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (versions == null || versions.size() == 0) {
            return TYPE_FOOTER;
        } else {
            if (position < versions.size()) {
                return TYPE_ITEM;
            } else {
                return TYPE_ITEM;
            }
        }
    }

    @Override
    public int getItemCount() {
        return versions == null ? 1 : versions.size() + 1;
    }

    private class ViewHolderItem extends RecyclerView.ViewHolder {
        public TextView tvName;

        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_version_name);
        }
    }

    public class ViewHolderFooter extends RecyclerView.ViewHolder {
        public TextView tvAddVersion;

        public ViewHolderFooter(@NonNull View itemView) {
            super(itemView);
            tvAddVersion = itemView.findViewById(R.id.tv_item_version_add);
            tvAddVersion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(versionsAdapterinterface!= null){
                        versionsAdapterinterface.addVersion();
                    }
                }
            });
        }
    }

    public interface VersionsAdapterinterface{
        void addVersion();
    }
}
