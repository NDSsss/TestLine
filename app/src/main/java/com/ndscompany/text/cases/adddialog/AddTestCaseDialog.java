package com.ndscompany.text.cases.adddialog;

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
import com.ndscompany.text.classes.TestCase;

public class AddTestCaseDialog extends DialogFragment {

    public static final String TAG = "AddTestCaseDialog.TAG";

    private AddTestCaseDialogInterface addTestCaseDialogInterface;
    private EditText etName;
    private EditText etDescription;
    private Button btnAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_test_case, container);
        etName = view.findViewById(R.id.et_dialog_add_test_case_name);
        etDescription = view.findViewById(R.id.et_dialog_add_test_case_description);
        btnAdd = view.findViewById(R.id.btn_dialog_add_test_case_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addTestCaseDialogInterface != null){
                    TestCase testCase = new TestCase();
                    testCase.setName(etName.getText().toString());
                    testCase.setDescription(etDescription.getText().toString());
                    testCase.setState(TestCase.NOT);
                    testCase.setText("");
                    addTestCaseDialogInterface.saveName(testCase);
                }
                dismiss();
            }
        });
        return view;
    }

    public void setAddTestCaseDialogInterface(AddTestCaseDialogInterface addTestCaseDialogInterface){
        this.addTestCaseDialogInterface = addTestCaseDialogInterface;
    }

    public interface AddTestCaseDialogInterface {
        void saveName(TestCase testCase);
    }
}
