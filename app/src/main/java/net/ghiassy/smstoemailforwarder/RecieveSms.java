package net.ghiassy.smstoemailforwarder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static net.ghiassy.smstoemailforwarder.SettingsActivity.forwardSMS;

public class RecieveSms extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle=intent.getExtras();
        Object[] messages=(Object[])bundle.get("pdus");
        SmsMessage[] sms=new SmsMessage[messages.length];
        ArrayList<String> body = new ArrayList<>();
        for(int n=0;n<messages.length;n++){
            sms[n]=SmsMessage.createFromPdu((byte[]) messages[n]);
        }

        for(SmsMessage msg:sms){
       body.add(msg.getMessageBody());
        }
        forwardSMS(context,body);




    }

}


