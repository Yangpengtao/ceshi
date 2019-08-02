package com.oomall.myapplication.aaaa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oomall.myapplication.R;
import com.oomall.myapplication.aaaa.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}
