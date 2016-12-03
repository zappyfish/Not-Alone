package liamkengineering.notalone;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Personal extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    EditText sendMessage;
    Button sendButton;
    List<String> niceThings; // need to get current vals in nice things, then append and add to nice things


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        niceThings = getNiceThings();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sendMessage = (EditText) findViewById(R.id.send);
        sendButton = (Button) findViewById(R.id.sendbutton);
        sendButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Settings set = new Settings();
                mDatabase = database.getReference(set.getUsername()); // get username
                if(sendMessage.getText()!=null) {
                    niceThings.add(sendMessage.getText().toString()); // add to list
                    mDatabase.setValue(niceThings); // save list
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public List<String> getNiceThings() {
        Settings set = new Settings();
        String key = set.getUsername();
        Set<String> s = set.sharedPrefs.getStringSet(key, null);
        List<String> l = new ArrayList<String>();
        l.addAll(s);
        return l;
    }
}
