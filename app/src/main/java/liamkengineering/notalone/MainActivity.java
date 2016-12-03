package liamkengineering.notalone;
/*
NOTE: DON'T FORGET TO ENABLE STORAGE PERMISSINOS OR THE APP WILL FAIL
 */
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Settings settings = new Settings();
    Button settingsButton;
    Button textMessageButton;
<<<<<<< HEAD
    Button hotlineButton;
    String hotline = "8002738255";
=======
    Button personalButton;
    TextView mTest;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    String test;
>>>>>>> 1d796f88807c5babea24c517b379a7ab14b91340

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTest = (TextView) findViewById(R.id.test);
        settingsButton = (Button) findViewById(R.id.settings);
        settingsButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, Settings.class);
                startActivity(i);

            }
        });

        textMessageButton = (Button) findViewById(R.id.textMessage);
        textMessageButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, TextMessage.class);
                startActivity(i);

            }
        });

<<<<<<< HEAD
        hotlineButton = (Button) findViewById(R.id.hotline);
        hotlineButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hotline));
                startActivity(i);
            }
        });
=======
        personalButton = (Button) findViewById(R.id.personal);
        personalButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, Personal.class);
                startActivity(i);
            }
        });
        mDatabase = database.getReference("message");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                test = dataSnapshot.getValue(String.class);
                updateMessage();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void updateMessage() {
        mTest.setText(test);
>>>>>>> 1d796f88807c5babea24c517b379a7ab14b91340
    }
}
