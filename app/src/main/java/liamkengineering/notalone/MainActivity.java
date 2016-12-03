package liamkengineering.notalone;
/*
NOTE: DON'T FORGET TO ENABLE STORAGE PERMISSINOS OR THE APP WILL FAIL
 */
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
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

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Settings settings = new Settings();
    List<Uri> photoList;
    List<String> niceThings;
    Button settingsButton;
    Button textMessageButton;
    Button hotlineButton;
    String hotline = "8002738255";

    Button personalButton;
    TextView mTest;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    String test;
    int curMessage = 0;
    // for updating UI
    private final Handler handler = new Handler();
    private final Runnable tickUi = new Runnable() {
        @Override
        public void run() {
            updateMessages();
            handler.postDelayed(tickUi, 5000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoList = settings.readFromInternalStorage(this.getApplicationContext());
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

        hotlineButton = (Button) findViewById(R.id.hotline);
        hotlineButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hotline));
                startActivity(i);
            }
        });

        personalButton = (Button) findViewById(R.id.personal);
        personalButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(MainActivity.this, Personal.class);
                startActivity(i);
            }
        });
        mDatabase = database.getReference(settings.getUsername()); // get username here
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                niceThings = dataSnapshot.getValue(List.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mTest.setText("error");
            }
        });
        handler.post(tickUi);
    }

    public void updateMessage() {
        mTest.setText(test);
    }
        public void updateMessages() {
            if(niceThings != null) {
                if(niceThings.size() > 0) {
                    mTest.setText(niceThings.get(curMessage));
                    curMessage = (curMessage+1)%niceThings.size();
                }
            }
        }

}
