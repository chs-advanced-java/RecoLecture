package ajlyfe.lectureapp.Activity;

import android.graphics.Color;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import ajlyfe.lectureapp.*;

public class TeacherClassMainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_class_main_screen);

        CardView classOneCard = (CardView) findViewById(R.id.classOne);
        CardView classTwoCard = (CardView) findViewById(R.id.classTwo);

        RelativeLayout classTwoLayout = (RelativeLayout) classTwoCard.getChildAt(0);
        TextView classTwoTitle = (TextView) classTwoLayout.getChildAt(0);

        classTwoTitle.setText("Class Two");

        classOneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherClassMainScreen.this, TeacherClassViewScreen.class));
            }
        });

        classTwoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherClassMainScreen.this, TeacherClassViewScreen.class));
            }
        });
    }
}
