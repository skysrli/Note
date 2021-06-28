package com.example.note;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.note.util.StringUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (StringUtil.getResources() == null)
            StringUtil.setResources(getResources());
    }
}