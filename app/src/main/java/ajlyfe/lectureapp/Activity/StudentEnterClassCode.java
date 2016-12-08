package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class StudentEnterClassCode extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_enter_class_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = Utils.getPrefs(Utils.SHARED_PREFERENCES, this);
        editor = preferences.edit();

        Button joinClassButton = (Button) findViewById(R.id.JoinClassButton);
        joinClassButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final EditText enterClassCode =  (EditText) findViewById(R.id.editText3);
                String classCodeStudent = enterClassCode.getText().toString();
                if (classCodeStudent.equals("3zb8c27n")) {
                    Set<String> tempClassList;
                    tempClassList = preferences.getStringSet("KeyStudent", null);
                    /**Reminder to change class name later!!!**/
                    tempClassList.add("Spanish 4");
                    editor.putStringSet("KeyStudent", tempClassList);
                    editor.apply();

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
