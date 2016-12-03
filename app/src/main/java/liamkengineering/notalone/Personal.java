package liamkengineering.notalone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Personal extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    EditText sendMessage;
    Button sendButton;
    Button getName;
    EditText sendUserName;
    List<String> niceThings; // need to get current vals in nice things, then append and add to nice things
    boolean canWrite = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        final SharedPreferences sharedPrefs = getSharedPreferences("App_settings", Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendUserName = (EditText) findViewById(R.id.senduser);
        sendMessage = (EditText) findViewById(R.id.send);
        getName = (Button) findViewById(R.id.get);
        sendButton = (Button) findViewById(R.id.sendbutton);
        sendButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Settings set = new Settings();
                if(!canWrite) { // make sure the list is already loaded up
                    Toast toast = Toast.makeText(Personal.this, "you haven't gotten name yet", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    String usrname = sendUserName.getText().toString(); // get the username of the person you want to send to
                    if (usrname != null) {
                        mDatabase = database.getReference(usrname); // get database from username
                        if (sendMessage.getText() != null) {

                            niceThings.add(sendMessage.getText().toString()); // add to list
                            //niceThings.add("test");
                            mDatabase.setValue(niceThings); // save list
                        }
                    } else {
                        Toast toast = Toast.makeText(Personal.this, "no username set", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });
        getName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usrname = sendUserName.getText().toString();
                niceThings = new ArrayList<String>();
                canWrite = true;
                if (usrname != null) {
                    mDatabase = database.getReference(usrname);
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            niceThings = (ArrayList<String>)dataSnapshot.getValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    /*
    Get the already saved stuff so we don't overwrite it
     */
    public List<String> getNiceThings(SharedPreferences sharedPrefs) {
        Settings set = new Settings();
        String key = set.getUsername(sharedPrefs);
        Set<String> s = sharedPrefs.getStringSet(key, null);
        List<String> l = new ArrayList<String>();
        if(s!=null)l.addAll(s);
        return l;
    }
}
