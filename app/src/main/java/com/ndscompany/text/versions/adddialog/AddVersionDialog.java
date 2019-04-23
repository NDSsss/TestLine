package com.ndscompany.text.versions.adddialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ndscompany.text.R;

public class AddVersionDialog extends DialogFragment {

    public static final String TAG = "AddTestCaseDialog.TAG";

    private AddProjectDialogInterface addProjectDialogInterface;
    private EditText editText;
    private Button btnAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_project, container);
        editText = view.findViewById(R.id.et_dialog_add_project_name);
        btnAdd = view.findViewById(R.id.btn_dialog_add_project_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addProjectDialogInterface != null){
                    addProjectDialogInterface.saveName(editText.getText().toString());
                }
                dismiss();
            }
        });
        return view;
    }

    public void setAddProjectDialogInterface(AddProjectDialogInterface addProjectDialogInterface){
        this.addProjectDialogInterface = addProjectDialogInterface;
    }

    public interface AddProjectDialogInterface{
        void saveName(String name);
    }
}
