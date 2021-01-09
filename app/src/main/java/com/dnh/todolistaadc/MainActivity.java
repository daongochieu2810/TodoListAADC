package com.dnh.todolistaadc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        snackBarPractice();
    }
    private void snackBarPractice() {
        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordContainer);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Hello from the other side", BaseTransientBottomBar.LENGTH_LONG);
        snackbar.show();
        Button button = findViewById(R.id.showSnackBar);
        button.setOnClickListener(v -> {
            snackbar.show();
        });
    }
}