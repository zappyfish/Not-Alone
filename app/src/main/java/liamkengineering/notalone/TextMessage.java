package liamkengineering.notalone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.net.Uri;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by KWU on 12/3/16.
 */

public class TextMessage extends AppCompatActivity {
    //private Checkbox c1;
    List<Long> sendTo;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        sendTo = new LinkedList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.idk);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (long curr : sendTo) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + curr));
                    intent.putExtra("sms_body", "Hello");
                    startActivity(intent);
                }
                //sendSMSMessage(9739539755, "Hello");

            }
        });
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkbox_c1:
                if (checked) {
                    sendTo.add(9739539755L);
                }

        }
    }

    protected void sendSMSMessage(int phoneNo, String message) {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

}
