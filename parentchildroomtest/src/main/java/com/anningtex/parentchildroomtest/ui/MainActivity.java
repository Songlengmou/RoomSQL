package com.anningtex.parentchildroomtest.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.anningtex.parentchildroomtest.R;
import com.anningtex.parentchildroomtest.application.AppContext;
import com.anningtex.parentchildroomtest.dao.ParentChildDao;
import com.anningtex.parentchildroomtest.entity.ParentChildEntity;
import com.syp.library.BaseRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * desc:带有数组的一种使用
 */
public class MainActivity extends AppCompatActivity {
    private EditText mInsertNameEdit, mInsertAddressEdit;
    private Button mBtnInsert;
    private RecyclerView mRvData;
    private ParentChildDao parentChildDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentChildDao = AppContext.getInstance().getAppDB().parentChildDao();
        initView();
        queryData();
    }

    private void initView() {
        mInsertNameEdit = findViewById(R.id.insert_name_edit);
        mInsertAddressEdit = findViewById(R.id.insert_address_edit);
        mBtnInsert = findViewById(R.id.btn_insert);
        mRvData = findViewById(R.id.rv_data);

//1.
//        mBtnInsert.setOnClickListener(view -> {
//            ParentChildEntity parentChildEntity = new ParentChildEntity();
//            parentChildEntity.setName(strName);
//            List<ParentChildEntity.InfoEntity> infoEntities = new ArrayList<>();
//            ParentChildEntity.InfoEntity entity = new ParentChildEntity.InfoEntity();
//            for (int j = 0; j < 3; j++) {
//                entity.setAge(521);
//                entity.setAddress(strAddress);
//                infoEntities.add(entity);
//            }
//            parentChildEntity.setInfoEntities(infoEntities);
//            parentChildDao.insertParentChildEntity(parentChildEntity);
//            Log.e("TAG", infoEntities.toString() + " infoEntitiesSize: " + infoEntities.size());
//            queryData();
//        });

        //2.
        mBtnInsert.setOnClickListener(view -> {
            String strName = mInsertNameEdit.getText().toString();
            String strAddress = mInsertAddressEdit.getText().toString();
            List<ParentChildEntity> childEntities = new ArrayList<>();
            List<ParentChildEntity.InfoEntity> infoEntities = new ArrayList<>();
            infoEntities.add(new ParentChildEntity.InfoEntity(521, strAddress));
            childEntities.add(new ParentChildEntity(strName, infoEntities));
            parentChildDao.insertAll(childEntities);
            queryData();
        });
    }

    private void queryData() {
        if (parentChildDao != null) {
            List<ParentChildEntity> allDataEntities = parentChildDao.getAllData();
            BaseRecycleAdapter<ParentChildEntity> adapter = new BaseRecycleAdapter<>(R.layout.item_main, allDataEntities);
            adapter.setOnDataToViewListener((helper, item, position) -> {
                ParentChildEntity infoEntity = (ParentChildEntity) item;
                helper.setText(R.id.tv_name, infoEntity.getName());
                RecyclerView rvChild = helper.getView(R.id.rv_child);
                List<ParentChildEntity.InfoEntity> infoEntities = infoEntity.getInfoEntities();
                BaseRecycleAdapter<ParentChildEntity.InfoEntity> childAdapter = new BaseRecycleAdapter<>(R.layout.item_main_child, infoEntities);
                childAdapter.setOnDataToViewListener((helperChild, itemChild, positionChild) -> {
                    ParentChildEntity.InfoEntity entity = (ParentChildEntity.InfoEntity) itemChild;
                    helperChild.setText(R.id.tv_age, entity.getAge() + "");
                    helperChild.setText(R.id.tv_address, entity.getAddress());
                });
                rvChild.setAdapter(childAdapter);
            });
            mRvData.setAdapter(adapter);
        }
    }
}