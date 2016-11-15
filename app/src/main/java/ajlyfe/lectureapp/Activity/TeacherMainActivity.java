package ajlyfe.lectureapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class TeacherMainActivity extends AppCompatActivity {

    // Storage Permissions variables
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    public static void verifyStoragePermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Granted
                } else {
                    //Denied
                }
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fadeCircle();

        verifyStoragePermissions(this);

        File lectureDirectory = new File("/sdcard/RecoLecture/");
        lectureDirectory.mkdirs();

        CardView myClassesCard = (CardView) findViewById(R.id.buttonClasses);
        myClassesCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this, TeacherClassOverview.class);
                startActivity(intent);
            }
        });

        CardView uploadCard = (CardView) findViewById(R.id.buttonUpload);
        uploadCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherMainActivity.this, UploadActivity.class));
            }
        });

        CardView recordCard = (CardView) findViewById(R.id.buttonRecord);
        recordCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherMainActivity.this, RecordActivity.class));
            }
        });
    }

    private void fadeCircle() {
        final ImageView colorBlock = (ImageView) findViewById(R.id.colorBlock);
        colorBlock.setVisibility(View.VISIBLE);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                colorBlock.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        colorBlock.startAnimation(fadeOut);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(TeacherMainActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}