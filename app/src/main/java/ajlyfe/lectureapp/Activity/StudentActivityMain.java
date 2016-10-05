package ajlyfe.lectureapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ajlyfe.lectureapp.R;

public class StudentActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabStudent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentActivityMain.this, StudentEnterClassCode.class));
            }
        });

        /** HARDCODE! **/
        CardView classFourCard = (CardView) findViewById(R.id.classFour);
        CardView classThreeCard = (CardView) findViewById(R.id.classThree);

        RelativeLayout classThreeLayout = (RelativeLayout) classThreeCard.getChildAt(0);
        TextView classThreeTitle = (TextView) classThreeLayout.getChildAt(0);

        classThreeTitle.setText("Class Two");

        classFourCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivityMain.this, StudentClassPage.class);
                intent.putExtra("CLASS_CLICKED", 2);
                startActivity(intent);

            }
        });

        classThreeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivityMain.this, StudentClassPage.class);
                intent.putExtra("CLASS_CLICKED", 1);
                startActivity(intent);
            }
        });

        /** END HARDCODE! **/
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
                startActivity(new Intent(StudentActivityMain.this, SettingsActivity.class));
                return true;

            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
