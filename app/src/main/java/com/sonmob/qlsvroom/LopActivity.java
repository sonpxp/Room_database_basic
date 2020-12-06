package com.sonmob.qlsvroom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.sonmob.qlsvroom.adapter.LopAdapter;
import com.sonmob.qlsvroom.database.LopDB;
import com.sonmob.qlsvroom.databinding.ActivityLopBinding;
import com.sonmob.qlsvroom.model.Lop;

import java.util.ArrayList;
import java.util.List;
//    _Database: Truy van csdl, tra ve ket qua va show cho nguoi dung
//    _Entity: Dai dien bang trong database(quy dinh bang do bao gom nhung truong gi)
//    _DAO: Chua phuong thuc truy van csdl: insert, update, delete, query, select ...

public class LopActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 10;
    private ActivityLopBinding binding;
    private LopAdapter lopAdapter;
    private List<Lop> mListLop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLopBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        lopAdapter = new LopAdapter(new LopAdapter.IClickItemLop() {
            @Override
            public void updateLop(Lop lop) {
                clickUpdateLop(lop);
            }

            @Override
            public void deleteLop(Lop lop) {
                clickDeleteLop(lop);
            }
        });
        mListLop = new ArrayList<>();
        lopAdapter.setDataLop(mListLop);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvLop.setLayoutManager(linearLayoutManager);

        binding.rcvLop.setAdapter(lopAdapter);

        //add lop
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLop();
            }
        });

        binding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    handelSearchLop();
                }
                return false;
            }
        });
        loadLop();
    }

    private void handelSearchLop() {
        String strKeyword = binding.edtSearch.getText().toString().trim();
        //clear data
        mListLop = new ArrayList<>();
        //tra ve 1 list lop
        mListLop = LopDB.getInstance(this).lopDAO().searchLop(strKeyword);
        lopAdapter.setDataLop(mListLop);
        hideKeyboard();
    }

    private void addLop() {
        String strTenlop = binding.edTenlop.getText().toString().trim();
        String strMalop = binding.edMalop.getText().toString().trim();

        //check (ten, ma) nhap vao trong thi return
        if (TextUtils.isEmpty(strTenlop) || TextUtils.isEmpty(strMalop)) {
            return;
        }

        Lop lop = new Lop(strTenlop, strMalop);

        if (isLopExist(lop)){
            Toast.makeText(this, "Ma lop da ton tai", Toast.LENGTH_SHORT).show();
            return;
        }
        LopDB.getInstance(this).lopDAO().insertLop(lop);
        Toast.makeText(this, "add lop successfully", Toast.LENGTH_SHORT).show();

        //add xong thuc hien reset edittext
        binding.edTenlop.setText("");
        binding.edMalop.setText("");

        //an ban phim
        hideKeyboard();
        //hien thi data add len list cua rcv
        loadLop();
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLop(){
        mListLop = LopDB.getInstance(this).lopDAO().getAllLop();
        lopAdapter.setDataLop(mListLop);
    }

    private boolean isLopExist(Lop lop){
        List<Lop> list = LopDB.getInstance(this).lopDAO().checkMalop(lop.getMalop());
        //return chung to list lop da ton tai
        return list != null && !list.isEmpty();
    }

    private void clickUpdateLop(Lop lop) {
        Intent intent = new Intent(LopActivity.this, UpdateLopActivity.class);
        Bundle bundle = new Bundle();
        //model implement Serializable
        bundle.putSerializable("object_lop", lop);
        intent.putExtras(bundle);
        startActivityForResult(intent, MY_REQUEST_CODE);
    }
    private void clickDeleteLop(Lop lop) {
        new AlertDialog.Builder(this)
                .setTitle("Xoa Lop")
                .setMessage("ban co chac chan muon xoa khong \n du lieu se khong duoc khoi phuc")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //thuc hien delete lop
                        LopDB.getInstance(LopActivity.this).lopDAO().deleteLop(lop);
                        Toast.makeText(LopActivity.this, "Delete lop successfully", Toast.LENGTH_SHORT).show();

                        //load lai data
                        loadLop();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            loadLop();
        }
    }
}