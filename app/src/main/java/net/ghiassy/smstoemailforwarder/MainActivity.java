package net.ghiassy.smstoemailforwarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferences = this.getSharedPreferences("net.ghiassy.smstoemailforwarder", Context.MODE_PRIVATE);
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                     intent.putExtra("LoadSettings", true);
                     startActivity(intent);
                     finish();
    }
}
