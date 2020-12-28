package com.anningtex.roomsql.act;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.anningtex.roomsql.R;
import com.anningtex.roomsql.dao.OrderSpecDao;
import com.anningtex.roomsql.db.MyDataBase;
import com.anningtex.roomsql.entriy.OrderSpecBean;
import com.syp.library.BaseRecycleAdapter;

import java.util.List;

/**
 * @author Administrator
 */
public class OrderSpecActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mInsertOrderEdit, mInsertMetersPerBaleEdit, mInsertUnitEnEdit;
    private RecyclerView mRvItem;
    private BaseRecycleAdapter adapter;
    private OrderSpecDao orderSpecDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_spec);
        initView();
    }

    private void initView() {
        //Initialization
        mInsertOrderEdit = findViewById(R.id.insert_order_edit);
        mInsertMetersPerBaleEdit = findViewById(R.id.insert_metersPerBale_edit);
        mInsertUnitEnEdit = findViewById(R.id.insert_unitEn_edit);
        findViewById(R.id.insert_button).setOnClickListener(this);
        findViewById(R.id.query_button).setOnClickListener(this);
        mRvItem = findViewById(R.id.rv_item);
        //Dao
        orderSpecDao = MyDataBase.getInstance(getApplicationContext()).getOrderSpecDao();
        queryAllData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert_button:
                String order = mInsertOrderEdit.getText().toString().trim();
                String metersPerBale = mInsertMetersPerBaleEdit.getText().toString().trim();
                String unit = mInsertUnitEnEdit.getText().toString().trim();
                OrderSpecBean orderSpecBean = new OrderSpecBean(order, metersPerBale, unit);
                orderSpecDao.insertOrderSpecEntity(orderSpecBean);
                queryAllData();
                break;
            case R.id.query_button:
                queryAllData();
                break;
            default:
                break;
        }
    }

    private void queryAllData() {
        List<OrderSpecBean> orderSpecEntities = orderSpecDao.queryAll();
        if (orderSpecEntities != null && orderSpecEntities.size() > 0) {
            adapter = new BaseRecycleAdapter(R.layout.item_student, orderSpecEntities);
            mRvItem.setAdapter(adapter);
            adapter.setOnDataToViewListener((helper, item, position) -> {
                OrderSpecBean orderSpecBean = (OrderSpecBean) item;
                helper.setText(R.id.tvId, orderSpecBean.getOrder());
                helper.setText(R.id.tvName, orderSpecBean.getMetersPerBale());
                helper.setText(R.id.tvAge, orderSpecBean.getUnitEn());
            });
            adapter.setOnItemLongClickListener((adapter, view, position) -> {
                List<OrderSpecBean> data = adapter.getData();
                OrderSpecBean orderSpecBean = data.get(position);
                orderSpecDao.deleteOrderSpecEntity(orderSpecBean);
                orderSpecEntities.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            });
        }
    }
}