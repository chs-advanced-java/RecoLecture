package ajlyfe.lectureapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import ajlyfe.lectureapp.NavigationDrawer;
import ajlyfe.lectureapp.R;

public class TeacherMainActivity extends AppCompatActivity {

    public NavigationDrawer navigationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createDrawer(this, toolbar, findViewById(R.id.main_layout));

        CardView myClassesCard = (CardView) findViewById(R.id.buttonClasses);
        myClassesCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherMainActivity.this, TeacherClassMainScreen.class);
                startActivity(intent);
            }
        });

        CardView uploadCard = (CardView) findViewById(R.id.buttonUpload);
        uploadCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherMainActivity.this, UploadActivity.class));
            }
        });
    }

    public void createDrawer(final AppCompatActivity activity, Toolbar tb, final View view) {
        navigationDrawer = new NavigationDrawer(activity, tb);
        navigationDrawer.initialize()
                .addFooter("Settings", GoogleMaterial.Icon.gmd_settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: startActivity(new Intent(getApplicationContext(), Settings.class));
                    }
                }, 0)
                .addFooter("Send Feedback", GoogleMaterial.Icon.gmd_feedback, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"kdixson538@gmail.com"});
                        i.putExtra(Intent.EXTRA_SUBJECT, "Feedback for RecoLecture");
                        i.putExtra(Intent.EXTRA_TEXT   , "Love the app!");
                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Snackbar.make(view, "There are no email clients installed", Snackbar.LENGTH_SHORT);
                        }
                    }
                }, 1);
    }

    public NavigationDrawer getDrawer(AppCompatActivity activity, Toolbar tb, View view) {
        createDrawer(activity, tb, view);
        return navigationDrawer;
    }
}