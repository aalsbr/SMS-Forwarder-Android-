package net.ghiassy.smstoemailforwarder;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.telephony.SmsManager;

import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.ArrayList;


public class SettingsActivity extends AppCompatActivity {

    public static final String TAG = "SettingsActivity";
    private static UserInfo userInfo;
    private static TextView txtViewReceiverEmail;
    private static final int PERMISSION_REQUEST_CODE = 1;
    Button  btnHide;
    SharedPreferences sharedPreferences;



//=========================================================================//

    public void btnSaveSettingsClick(View view)
    {
        closeKeyboard();

        if(hasError())
        {return;}


        //setting new variables
        userInfo = new UserInfo(txtViewReceiverEmail.getText().toString());
        sharedPreferences.edit().putString("ReceiverEmail" , userInfo.getReceiverEmail()).apply();
        LoadUserInfo();
        Toast.makeText(this, "Information Saved.", Toast.LENGTH_SHORT).show();

    }

    //load variables from the Shared preferences
    private void LoadSettings()
    {

        txtViewReceiverEmail.setText(sharedPreferences.getString("ReceiverEmail", ""));
        LoadUserInfo();
    }
    public void LoadUserInfo()
    {
        userInfo = new UserInfo(txtViewReceiverEmail.getText().toString());
    }

    //basic error check
    private boolean hasError()
    {

        if(txtViewReceiverEmail.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Email Cannot be empty!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
    //Closing keyboard
    private void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    //=========================================================================//
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "NO Permission!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferences = this.getSharedPreferences("net.ghiassy.smstoemailforwarder", Context.MODE_PRIVATE);
        txtViewReceiverEmail = findViewById(R.id.txtReceiverEmail);
        txtViewReceiverEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {closeKeyboard();}
            }
        });


        if(getIntent().getBooleanExtra("LoadSettings", false))
        {
            Toast.makeText(this, "Load Settings", Toast.LENGTH_SHORT).show();
            LoadSettings();
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M  && checkSelfPermission(Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1000);
        }


    }


    public static void forwardSMS(Context context,  ArrayList msgBody)
    {
        // get and verify forwarding number
        userInfo = new UserInfo(txtViewReceiverEmail.getText().toString());
        String fwdToNumber = userInfo.ReceiverEmail.toString();
        if (fwdToNumber.length() == 0){
            String errMsg = "SMS Forwarder error: forwarding number is not specified.";
            Toast.makeText(context, errMsg, Toast.LENGTH_LONG).show();
            return;
        }
        SmsManager.getDefault().sendMultipartTextMessage(fwdToNumber,
                null,
                 msgBody,
                null,
                null);



    }

}
