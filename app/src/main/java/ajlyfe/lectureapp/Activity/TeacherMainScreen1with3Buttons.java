package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ajlyfe.lectureapp.R;

public class TeacherMainScreen1with3Buttons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main_screen1with3_buttons);

        Button buttonClasses = (Button) findViewById(R.id.buttonClasses);
        buttonClasses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainScreen1with3Buttons.this, TeacherClassMainScreen.class);
                startActivity(intent);
            }
        });

        Button buttonUpload = (Button) findViewById(R.id.button2);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherMainScreen1with3Buttons.this, UploadActivity.class));
            }
        });
    }
}