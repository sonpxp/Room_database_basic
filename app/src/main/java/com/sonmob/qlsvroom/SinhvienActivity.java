package com.sonmob.qlsvroom;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.sonmob.qlsvroom.databinding.ActivitySinhvienBinding;

public class SinhvienActivity extends AppCompatActivity {

    private ActivitySinhvienBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySinhvienBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}