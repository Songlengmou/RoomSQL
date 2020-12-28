package com.anningtex.roomsql.act;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.anningtex.roomsql.R;
import com.anningtex.roomsql.adapter.PhoneAdapter;
import com.anningtex.roomsql.db.MyDataBase;
import com.anningtex.roomsql.entriy.PhoneBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class PhoneActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mNameEdit, mPhoneEdit, etNameQuery, etNumberQuery;
    private ListView listView;
    private PhoneAdapter phoneAdapter;
    private List<PhoneBean> phoneBeanList;

    private MyDataBase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initView();
    }

    private void initView() {
        mNameEdit = findViewById(R.id.insert_name_edit);
        mPhoneEdit = findViewById(R.id.insert_phone_edit);
        etNameQuery = findViewById(R.id.et_name_query);
        etNumberQuery = findViewById(R.id.et_number_query);
        listView = findViewById(R.id.data_list_view);
        findViewById(R.id.query_button).setOnClickListener(this);
        findViewById(R.id.insert_button).setOnClickListener(this);
        findViewById(R.id.btn_data_query).setOnClickListener(this);

        phoneBeanList = new ArrayList<>();
        phoneAdapter = new PhoneAdapter(PhoneActivity.this, phoneBeanList);
        listView.setAdapter(phoneAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                updateOrDeleteDialog(phoneBeanList.get(i));
                return false;
            }
        });

        myDatabase = MyDataBase.getInstance(this);
        new QueryPhoneTask().execute();
    }

    private void updateOrDeleteDialog(final PhoneBean phoneBean) {
        final String[] options = new String[]{"修改", "删除"};
        new AlertDialog.Builder(PhoneActivity.this)
                .setTitle("Choose")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        openUpdateStudentDialog(phoneBean);
                    } else if (which == 1) {
                        new DeleteStudentTask(phoneBean).execute();
                    }
                }).show();
    }

    private void openUpdateStudentDialog(final PhoneBean phoneBean) {
        if (phoneBean == null) {
            return;
        }
        View customView = getLayoutInflater().inflate(R.layout.dialog_layout_student, null);
        final EditText etName = customView.findViewById(R.id.etName);
        final EditText etNumber = customView.findViewById(R.id.etAge);
        etName.setText(phoneBean.name);
        etNumber.setText(phoneBean.number);
        final AlertDialog.Builder builder = new AlertDialog.Builder(PhoneActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Update Phone");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog1, which) -> {
            if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etNumber.getText().toString())) {
                Toast.makeText(PhoneActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
            } else {
                new UpdatePhoneTask(phoneBean.longId, etName.getText().toString().trim(), etNumber.getText().toString().trim()).execute();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", (dialog12, which) -> dialog12.dismiss());
        dialog.setView(customView);
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        String mName = mNameEdit.getText().toString();
        String mPhone = mPhoneEdit.getText().toString();
        String mNameQuery = etNameQuery.getText().toString();
        String mNumberQuery = etNumberQuery.getText().toString();
        switch (view.getId()) {
            case R.id.query_button:
                new QueryPhoneTask().execute();
                break;
            case R.id.insert_button:
                if (mName.isEmpty()) {
                    Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else if (mPhone.isEmpty()) {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    new InsertPhoneTask(mNameEdit.getText().toString().trim(), mPhoneEdit.getText().toString().trim()).execute();
                }
                break;
            case R.id.btn_data_query:
                if (mNameQuery.isEmpty()) {
                    Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else if (mNumberQuery.isEmpty()) {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    new QueryPhoneIdTask(etNameQuery.getText().toString().trim(), etNumberQuery.getText().toString().trim()).execute();
                }
                break;
            default:
                break;
        }
    }

    public class InsertPhoneTask extends AsyncTask<Void, Void, Void> {
        String name, number;

        public InsertPhoneTask(String name, String number) {
            this.name = name;
            this.number = number;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            myDatabase.phoneDao().addPhoneData(new PhoneBean(name, number));
            phoneBeanList.clear();
            phoneBeanList.addAll(myDatabase.phoneDao().getPhoneList());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            phoneAdapter.notifyDataSetChanged();
        }
    }

    public class QueryPhoneTask extends AsyncTask<Void, Void, Void> {
        public QueryPhoneTask() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            phoneBeanList.clear();
            phoneBeanList.addAll(myDatabase.phoneDao().getPhoneList());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            phoneAdapter.notifyDataSetChanged();
        }
    }

    public class QueryPhoneIdTask extends AsyncTask<Void, Void, Void> {
        String name, number;

        public QueryPhoneIdTask(String name, String number) {
            this.name = name;
            this.number = number;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            phoneBeanList.clear();
            phoneBeanList.add(myDatabase.phoneDao().getPhoneById(name, number));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            phoneAdapter.notifyDataSetChanged();
        }
    }

    private class UpdatePhoneTask extends AsyncTask<Void, Void, Void> {
        int longId;
        String name, number;

        public UpdatePhoneTask(int longId, String name, String number) {
            this.longId = longId;
            this.name = name;
            this.number = number;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            myDatabase.phoneDao().updatePhoneData(new PhoneBean(longId, name, number));
            phoneBeanList.clear();
            phoneBeanList.addAll(myDatabase.phoneDao().getPhoneList());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            phoneAdapter.notifyDataSetChanged();
        }
    }

    private class DeleteStudentTask extends AsyncTask<Void, Void, Void> {
        PhoneBean phoneBean;

        public DeleteStudentTask(PhoneBean phoneBean) {
            this.phoneBean = phoneBean;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            myDatabase.phoneDao().deletePhoneData(phoneBean);
            phoneBeanList.clear();
            phoneBeanList.addAll(myDatabase.phoneDao().getPhoneList());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            phoneAdapter.notifyDataSetChanged();
        }
    }
}