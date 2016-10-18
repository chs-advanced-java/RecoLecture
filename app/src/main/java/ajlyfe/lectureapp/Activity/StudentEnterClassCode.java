package ajlyfe.lectureapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ajlyfe.lectureapp.Adapters.TeacherClassCard;
import ajlyfe.lectureapp.R;

import static android.R.id.edit;

public class StudentEnterClassCode extends AppCompatActivity {
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enter_class_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button joinClassButton = (Button) findViewById(R.id.JoinClassButton);
        joinClassButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final EditText enterClassCode =  (EditText) findViewById(R.id.editText3);
                String classCodeStudent = (String) enterClassCode.getText().toString();
                if (classCodeStudent.equals("3zb8c27n")) {
                    preferenceSettings = getSharedPreferences("Classes", PREFERENCE_MODE_PRIVATE);
                    preferenceEditor = preferenceSettings.edit();
                    Set<String> tempClassList;
                    tempClassList = preferenceSettings.getStringSet("KeyStudent", null);
                    /**Reminder to change class name later!!!**/
                    tempClassList.add("Spanish 4");
                    preferenceEditor.putStringSet("KeyStudent", tempClassList);
                    preferenceEditor.commit();

                    startActivity(new Intent(StudentEnterClassCode.this, StudentActivityMain.class));
                    startActivity(new Intent(StudentEnterClassCode.this, StudentClassPage.class));
                    finish();

                }
                else {
                    Toast.makeText(StudentEnterClassCode.this, "Your code does not appear to be valid. Please check it and try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(StudentEnterClassCode.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
