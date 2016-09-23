package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ajlyfe.lectureapp.R;

public class TeacherMainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main_screen);

        Button buttonClasses = (Button) findViewById(R.id.buttonClasses);
        buttonClasses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainScreen.this, TeacherClassMainScreen.class);
                startActivity(intent);
            }
        });

        Button buttonUpload = (Button) findViewById(R.id.buttonUpload);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherMainScreen.this, UploadActivity.class));
            }
        });
    }
}