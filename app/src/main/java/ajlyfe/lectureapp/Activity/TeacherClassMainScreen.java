package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ajlyfe.lectureapp.*;

public class TeacherClassMainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_class_main_screen);

        Button classMainButtonOne = (Button) findViewById(R.id.buttonClassesTestOne);
        classMainButtonOne.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TeacherClassMainScreen.this, TeacherClassViewScreen.class);
                startActivity(intent);
            }
        });

        Button classMainButtonTwo = (Button) findViewById(R.id.buttonClassesTestTwo);
        classMainButtonTwo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TeacherClassMainScreen.this, TeacherClassViewScreen.class);
                startActivity(intent);
            }
        });
    }
}
