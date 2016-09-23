package ajlyfe.lectureapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import ajlyfe.lectureapp.R;

public class TeacherClassViewScreen extends AppCompatActivity {

    private int classNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_class_view_screen);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            classNumber = extras.getInt("CLASS_CLICKED");
        }

        TextView classTitle = (TextView) findViewById(R.id.classTitle);
        switch (classNumber) {
            case 1:
                classTitle.setText("Class One");
                break;

            case 2:
                classTitle.setText("Class Two");
                break;
        }
    }
}
