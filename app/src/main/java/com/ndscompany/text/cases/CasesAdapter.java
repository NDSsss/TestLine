package com.ndscompany.text.cases;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ndscompany.text.R;
import com.ndscompany.text.classes.TestCase;

import java.util.ArrayList;
import java.util.List;

public class CasesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<TestCase> testCases;
    private TestCaseListener listener;

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
        notifyDataSetChanged();
    }

    public void setListener(TestCaseListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case TYPE_ITEM:
                return new ViewHOlderItem(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_test_case, viewGroup, false));
            case TYPE_FOOTER:
            default:
                return new ViewHolderFooter(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_test_case_add, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof ViewHOlderItem){
            ViewHOlderItem viewHolderItem = (ViewHOlderItem) viewHolder;
            viewHolderItem.tvName.setText(testCases.get(i).getName());
            viewHolderItem.tvDescription.setText(testCases.get(i).getDescription());
            viewHolderItem.tvComment.setText(testCases.get(i).getText());
            switch (testCases.get(i).getState()){
                case TestCase.WORK:
                    viewHolderItem.rbWork.setChecked(true);
                    break;
                case TestCase.BLOCKED:
                    viewHolderItem.rbBlocked.setChecked(true);
                    break;
                case TestCase.NOT:
                    default:
                    viewHolderItem.rbNot.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return testCases == null? 1: testCases.size()+1;
    }


    @Override
    public int getItemViewType(int position) {
        if (testCases == null || testCases.size() == 0) {
            return TYPE_FOOTER;
        } else {
            if (position < testCases.size()) {
                return TYPE_ITEM;
            } else {
                return TYPE_FOOTER;
            }
        }
    }

    public class ViewHOlderItem extends RecyclerView.ViewHolder{

        public TextView tvName;
        public TextView tvDescription;
        public TextView tvComment;
        public RadioButton rbWork;
        public RadioButton rbNot;
        public RadioButton rbBlocked;
        public Button btnSave;

        public ViewHOlderItem(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_test_case_name);
            tvDescription = itemView.findViewById(R.id.tv_item_test_case_description);
            tvComment = itemView.findViewById(R.id.tv_item_test_case_comment);
            rbWork = itemView.findViewById(R.id.rb_test_case_work);
            rbNot = itemView.findViewById(R.id.rb_test_case_not);
            rbBlocked = itemView.findViewById(R.id.rb_test_case_blocked);
            btnSave = itemView.findViewById(R.id.btn_test_case_save);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        TestCase testCase = testCases.get(getAdapterPosition());
                        int state;
                        if(rbWork.isChecked()){
                            state = TestCase.WORK;
                        } else if(rbBlocked.isChecked()){
                            state=TestCase.BLOCKED;
                        } else {
                            state = TestCase.NOT;
                        }
                        testCase.setState(state);
                        testCase.setText(tvComment.getText().toString());
                        listener.save(testCase);
                    }
                }
            });
        }
    }

    public class ViewHolderFooter extends RecyclerView.ViewHolder{

        public TextView tvCreate;

        public ViewHolderFooter(@NonNull View itemView) {
            super(itemView);
            tvCreate = itemView.findViewById(R.id.tv_item_test_case_add);
            tvCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.create();
                    }
                }
            });
        }
    }

    public interface TestCaseListener{
        void save(TestCase testCase);
        void create();
    }
}
