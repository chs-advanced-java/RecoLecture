package ajlyfe.lectureapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeacherMainScreen1with3Buttons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main_screen1with3_buttons);
    }

    Button btn = (Button) findViewById(R.id.buttonClasses);
    /*
    Button btn = (Button) findViewById(R.id.);

    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(TeacherMainScreen1with3Buttons.this, TeacherClassMainScreen.class));
        }
    });
    */
}
