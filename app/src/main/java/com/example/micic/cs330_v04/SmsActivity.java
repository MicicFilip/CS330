package com.example.micic.cs330_v04;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Filip on 01-Jun-17.
 */

public class SmsActivity extends AppCompatActivity {

    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    IntentFilter intentFilter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);
        deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);
//-filter prijema SMS poruka-
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
//---registrovanje primaoca---
        registerReceiver(intentReceiver, intentFilter);
    }

    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//-prikazuje primljeni SMS u TextView pogledu -
            TextView SMSes = (TextView) findViewById(R.id.textView1);
            SMSes.setText(intent.getExtras().getString("sms"));
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case AppCompatActivity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS prosleđen",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generička greška",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "Nema usluge",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio isključen",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case AppCompatActivity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS dostavljen",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case AppCompatActivity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS nije dostavljen",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(intentReceiver);
        unregisterReceiver(smsSentReceiver);
        unregisterReceiver(smsDeliveredReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(intentReceiver);
    }

    public void onClickSMS(View v) {
        sendSMS("5556", "Pozdravni SMS - primer!");
    }

    public void onClickEmail(View v){
        String[] to = {"micic.filip@gmail.com"};
        String[] cc = {"filip.micic.2388@metropolitan.ac.rs"};
        sendEmail(to, cc, "Pozdrav!!!", "Pozdrav društvo!!!");

    }

    private void sendEmail(String[] emailAddresses, String[] carbonCopies,String subject, String message){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        String[] to = emailAddresses;
        String[] cc = carbonCopies;
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email"));
    }

    private void sendSMS(String phoneNumber,
                         String message){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message,
                sentPI, deliveredPI);
    }

    public void onSMSIntentClick (View v) {
        Intent i = new
                Intent(android.content.Intent.ACTION_VIEW);
        i.putExtra("address", "5556; 5558; 5560");
        i.putExtra("sms_body", "Pozdravni SMS - primer!");
        i.setType("vnd.android-dir/mms-sms");
        startActivity(i);
    }

}
