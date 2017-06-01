package util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.micic.cs330_v04.SmsActivity;

/**
 * Created by Filip on 01-Jun-17.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "SMS iz ";
        if (bundle != null) {
//---učitavanje primljene SMS poruke---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                if (i == 0) {
//---preuzimanje podataka o pošiljaocu---
                    str += msgs[i].getOriginatingAddress();
                    str += ": ";
                }
                str += msgs[i].getMessageBody().toString();
            }

            //---prikazivanje nove SMS poruke---
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            Log.d("SMSReceiver", str);
/* //---zaustavljanje slanja/primanja---
this.abortBroadcast();*/
//---pokretanje SMSActivity---
            Intent mainActivityIntent = new Intent(context, SmsActivity.class);
            mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainActivityIntent);
//---Slanje namere BroadcastIntent za ažuriranje SMS iz aktivnosti---
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms", str);
            context.sendBroadcast(broadcastIntent);
        }


    }
}
