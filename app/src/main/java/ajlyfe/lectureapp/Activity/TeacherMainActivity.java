package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ajlyfe.lectureapp.R;

public class TeacherMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int[] clickEgg = {0};

        TextView teacherHomeTitle = (TextView) findViewById(R.id.teacherHomeTitle);
        teacherHomeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEgg[0]++;
                if (clickEgg[0] == 5) {
                    startActivity(new Intent(TeacherMainActivity.this, StudentActivityMain.class));
                    clickEgg[0] = 0;
                }
            }
        });

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
            public void onClick(View V) {
                clickEgg[0]++;
                if (clickEgg[0] == 10) {
                    startActivity(new Intent(TeacherMainActivity.this, StudentActivityMain.class));
                    clickEgg[0] = 0;
                }

                //Insert Code to Record Activity Later

            }
        });
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