package com.anningtex.roomsql.act;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.anningtex.roomsql.R;
import com.anningtex.roomsql.adapter.StudentAdapter;
import com.anningtex.roomsql.db.MyDataBase;
import com.anningtex.roomsql.entriy.StudentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * desc:Room的基本使用
 * source:https://zhuanlan.zhihu.com/p/77036077
 * 数据表中的注解说明：
 * 1. Entity：这是一个Model类，对应于数据库中的一张表。Entity类是Sqlite表结构在Java类的映射。
 * 2. Dao：（Data Access Objects）数据访问对象，顾名思义，我们可以通过它来访问数据。
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MyDataBase myDatabase;
    private List<StudentBean> studentBeanList;
    private StudentAdapter studentAdapter;

    private Button mBtnInsertStudent, mBtnQueryStudentAll, mBtnQueryStudentId, mBtnNext;
    private EditText mEtInputId;
    private ListView mLvStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtnInsertStudent = findViewById(R.id.btnInsertStudent);
        mBtnInsertStudent.setOnClickListener(this);
        mBtnQueryStudentAll = findViewById(R.id.btnQueryStudentAll);
        mBtnQueryStudentAll.setOnClickListener(this);
        mBtnQueryStudentId = findViewById(R.id.btnQueryStudentId);
        mBtnQueryStudentId.setOnClickListener(this);
        mEtInputId = findViewById(R.id.etInputId);
        mLvStudent = findViewById(R.id.lvStudent);
        mBtnNext = findViewById(R.id.btn_next);
        mBtnNext.setOnClickListener(this);

        studentBeanList = new ArrayList<>();
        studentAdapter = new StudentAdapter(MainActivity.this, studentBeanList);
        mLvStudent.setAdapter(studentAdapter);
        mLvStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                updateOrDeleteDialog(studentBeanList.get(position));
                return false;
            }
        });

        myDatabase = MyDataBase.getInstance(this);
        // 不能直接在UI线程中执行这些操作，需要放在工作线程中进行，所以使用AsyncTask来进行查询操作。
        new QueryStudentTask().execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInsertStudent:
                openAddStudentDialog();
                break;
            case R.id.btnQueryStudentAll:
                new QueryStudentTask().execute();
                break;
            case R.id.btnQueryStudentId:
                String studentID = mEtInputId.getText().toString().trim();
                if (TextUtils.isEmpty(studentID)) {
                    Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    new QueryStudentIdTask(Integer.valueOf(studentID)).execute();
                    mEtInputId.setText("");
                }
                break;
            case R.id.btn_next:
                startActivity(new Intent(MainActivity.this, PhoneActivity.class));
                break;
            default:
                break;
        }
    }

    private void openAddStudentDialog() {
        View customView = this.getLayoutInflater().inflate(R.layout.dialog_layout_student, null);
        final EditText etName = customView.findViewById(R.id.etName);
        final EditText etAge = customView.findViewById(R.id.etAge);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Add Student");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etAge.getText().toString())) {
                    Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    new InsertStudentTask(etName.getText().toString(), etAge.getText().toString()).execute();
                }
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setView(customView);
        dialog.show();
    }

    private void updateOrDeleteDialog(final StudentBean studentBean) {
        final String[] options = new String[]{"修改", "删除"};
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Choose")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            openUpdateStudentDialog(studentBean);
                        } else if (which == 1) {
                            new DeleteStudentTask(studentBean).execute();
                        }
                    }
                }).show();
    }

    private void openUpdateStudentDialog(final StudentBean studentBean) {
        if (studentBean == null) {
            return;
        }

        View customView = this.getLayoutInflater().inflate(R.layout.dialog_layout_student, null);
        final EditText etName = customView.findViewById(R.id.etName);
        final EditText etAge = customView.findViewById(R.id.etAge);
        etName.setText(studentBean.name);
        etAge.setText(studentBean.age);

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Update Student");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etAge.getText().toString())) {
                    Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    new UpdateStudentTask(studentBean.id, etName.getText().toString(), etAge.getText().toString()).execute();
                }
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setView(customView);
        dialog.show();
    }

    /**
     * 增刪改查
     */
    private class InsertStudentTask extends AsyncTask<Void, Void, Void> {
        String name, age;

        public InsertStudentTask(final String name, final String age) {
            this.name = name;
            this.age = age;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            myDatabase.studentDao().insertStudent(new StudentBean(name, age));
            studentBeanList.clear();
            studentBeanList.addAll(myDatabase.studentDao().getStudentList());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            studentAdapter.notifyDataSetChanged();
        }
    }

    private class DeleteStudentTask extends AsyncTask<Void, Void, Void> {
        StudentBean studentBean;

        public DeleteStudentTask(StudentBean studentBean) {
            this.studentBean = studentBean;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            myDatabase.studentDao().deleteStudent(studentBean);
            studentBeanList.clear();
            studentBeanList.addAll(myDatabase.studentDao().getStudentList());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            studentAdapter.notifyDataSetChanged();
        }
    }

    private class UpdateStudentTask extends AsyncTask<Void, Void, Void> {
        int id;
        String name, age;

        public UpdateStudentTask(final int id, final String name, final String age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            myDatabase.studentDao().updateStudent(new StudentBean(id, name, age));
            studentBeanList.clear();
            studentBeanList.addAll(myDatabase.studentDao().getStudentList());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            studentAdapter.notifyDataSetChanged();
        }
    }

    private class QueryStudentTask extends AsyncTask<Void, Void, Void> {
        public QueryStudentTask() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentBeanList.clear();
            studentBeanList.addAll(myDatabase.studentDao().getStudentList());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            studentAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 根据学生id查询
     */
    private class QueryStudentIdTask extends AsyncTask<Void, Void, Void> {
        int id;

        public QueryStudentIdTask(int id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            studentBeanList.clear();
            studentBeanList.add(myDatabase.studentDao().getStudentById(id));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            studentAdapter.notifyDataSetChanged();
        }
    }
}
