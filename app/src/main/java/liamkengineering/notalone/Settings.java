package liamkengineering.notalone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Settings extends AppCompatActivity {
    List<Uri> photoList;
    int photoNum = 0;
    TextView textTargetUri;
    ImageView targetImage;
    public static final String PREFS_NAME = "NKDROID_APP";
    public static final String PHOTOS = "Photo";
    SharedPreferences sharedPrefs;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPrefs = getSharedPreferences("App_settings", Context.MODE_PRIVATE);
        Button buttonLoadImage = (Button) findViewById(R.id.loadimage);
        Button nextButton = (Button) findViewById(R.id.next);
        Button saveButton = (Button) findViewById(R.id.save);
        textTargetUri = (TextView) findViewById(R.id.targeturi);
        targetImage = (ImageView) findViewById(R.id.targetimage);
        ctx = this;
        buttonLoadImage.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //saveToInternalStorage();
                savePhotos(ctx);
            }});
        nextButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (photoList == null) {
                    // do nothing
                }
                else if(photoList.size() == 0) {
                    // still do nothing
                }
                else {
                    Bitmap bitmap;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(photoList.get(photoNum)));
                        targetImage.setImageBitmap(bitmap);
                        photoNum = (photoNum + 1) % photoList.size(); // loop back to beginning

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        photoList = readFromInternalStorage(ctx);
        if(photoList == null) {
            photoList = new ArrayList<>();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            if(photoList!=null) photoList.add(targetUri);
            textTargetUri.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                targetImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    public void savePhotos(Context ctx) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Set<String> s = new HashSet<String>();
        for(int i =0; i<photoList.size();i++) {
            s.add(photoList.get(i).toString());
        }
        editor.putStringSet("PHOTOS", s);
        editor.apply();

    }

    public List<Uri> readFromInternalStorage(Context ctx) {
        List<Uri> l = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        Set<String> s = sharedPrefs.getStringSet("PHOTOS", null);

        if(s!= null) {
            temp.addAll(s);
            for (int i = 0; i < temp.size(); i++) {
                l.add(Uri.parse(temp.get(i)));
            }
            return l;
        }
        else {
            return null;
        }
    }

}
