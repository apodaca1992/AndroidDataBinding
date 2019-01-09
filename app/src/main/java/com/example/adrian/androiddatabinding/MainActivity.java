package com.example.adrian.androiddatabinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.adrian.androiddatabinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private Person person = new Person("Test", "Person");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setPerson(person);
    }
}
