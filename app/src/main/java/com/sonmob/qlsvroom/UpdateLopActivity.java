package com.sonmob.qlsvroom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.sonmob.qlsvroom.database.LopDB;
import com.sonmob.qlsvroom.databinding.ActivityMainBinding;
import com.sonmob.qlsvroom.databinding.ActivityUpdateLopBinding;
import com.sonmob.qlsvroom.model.Lop;

public class UpdateLopActivity extends AppCompatActivity {

    private ActivityUpdateLopBinding binding;
    private Lop mLop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateLopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mLop = (Lop) getIntent().getExtras().get("object_lop");

        if (mLop != null){
            binding.edTenlop.setText(mLop.getTenlop());
            binding.edMalop.setText(mLop.getMalop());
        }
        binding.btnUpdateLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLop();
            }
        });
    }

    private void updateLop() {
        String strTenlop = binding.edTenlop.getText().toString().trim();
        String strMalop = binding.edMalop.getText().toString().trim();

        //check (ten, ma) nhap vao trong thi return
        if (TextUtils.isEmpty(strTenlop) || TextUtils.isEmpty(strMalop)) {
            return;
        }

        //update lop in database
        mLop.setTenlop(strTenlop);
        mLop.setMalop(strMalop);

        LopDB.getInstance(this).lopDAO().updateLop(mLop);

        //sau khi update thanh cong
        Toast.makeText(this, "update successfully", Toast.LENGTH_SHORT).show();

        //quay lai main chinh va load laij data
        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK, intentResult);
        //destroy activity nay di
        finish();

    }
}