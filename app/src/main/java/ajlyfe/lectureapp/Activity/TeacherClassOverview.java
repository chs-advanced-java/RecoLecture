package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;

import ajlyfe.lectureapp.*;
import io.codetail.widget.RevealFrameLayout;
import io.codetail.widget.RevealLinearLayout;

public class TeacherClassOverview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*final RevealFrameLayout mRevealLayout = (RevealFrameLayout) findViewById(R.id.reveal_layout);
        final View mRevealView = findViewById(R.id.reveal_view);
        final View mViewToReveal = findViewById(R.id.reveal_view);*/

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {/*
                fab.setClickable(false); // Avoid clicking FAB again and again...
                int[] location = new int[2];
                fab.getLocationOnScreen(location);
                location[0] += fab.getWidth() / 2;
                location[1] += fab.getHeight() / 2;

                final Intent intent = new Intent(TeacherClassOverview.this, TeacherCreateClass.class);

                mRevealView.setVisibility(View.VISIBLE);
                mRevealLayout.setVisibility(View.VISIBLE);

                mRevealLayout.show(location[0], location[1]); // Expand from center of FAB. Actually, it just plays reveal animation.
                fab.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        /**
                         * Without using R.anim.hold, the screen will flash because of transition
                         * of Activities.
                         *//*
                        overridePendingTransition(0, R.anim.hold);
                    }
                }, 600); // 600 is default duration of reveal animation in RevealLayout
                fab.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fab.setClickable(true);
                        mRevealLayout.setVisibility(View.INVISIBLE);
                        mViewToReveal.setVisibility(View.INVISIBLE);
                    }
                }, 960); // Or some numbers larger than 600.*/
            }
        });

        CardView classOneCard = (CardView) findViewById(R.id.classOne);
        CardView classTwoCard = (CardView) findViewById(R.id.classTwo);

        RelativeLayout classTwoLayout = (RelativeLayout) classTwoCard.getChildAt(0);
        TextView classTwoTitle = (TextView) classTwoLayout.getChildAt(0);

        classTwoTitle.setText("Class Two");

        classOneCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherClassOverview.this, TeacherClassView.class);
                intent.putExtra("CLASS_CLICKED", 1);
                startActivity(intent);

            }
        });

        classTwoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherClassOverview.this, TeacherClassView.class);
                intent.putExtra("CLASS_CLICKED", 2);
                startActivity(intent);
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(TeacherClassOverview.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
