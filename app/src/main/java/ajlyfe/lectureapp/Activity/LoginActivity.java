package ajlyfe.lectureapp.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import ajlyfe.lectureapp.Adapters.ClassCard;
import ajlyfe.lectureapp.Adapters.TeacherClassCard;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import static ajlyfe.lectureapp.Activity.TeacherClassOverview.AUTO_DESCRIPTION;
import static ajlyfe.lectureapp.Activity.TeacherClassOverview.NULL_CLASS;

public class LoginActivity extends AppCompatActivity {

    public final String DATA_URL = "http://www.chs.mcvsd.org/sandbox/get-accountLoginData.php?username=";
    private final String JSON_ARRAY = "result";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Display splash screen
        setContentView(R.layout.splashscreen);

        final int[] counter = new int[1];
        RelativeLayout splashScreenHolder = (RelativeLayout) findViewById(R.id.splashScreenHolder);
        splashScreenHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter[0]++;
                if (counter[0] >= 10) {
                    Toast.makeText(LoginActivity.this, "You win a cookie", Toast.LENGTH_SHORT).show();
                }
            }
        });

        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        final ImageView circle = (ImageView) findViewById(R.id.splashCircle);
        final Animation grow = AnimationUtils.loadAnimation(this, R.anim.grow);

        pulsator.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                circle.setVisibility(View.VISIBLE);
                circle.startAnimation(grow);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkLoginIn();
                    }
                }, 1000);
            }
        }, new Random().nextInt(2500) + 500);

        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
    }

    public void checkLoginIn() {
        preferences = Utils.getPrefs(Utils.SHARED_PREFERENCES, this);
        editor = preferences.edit();

        if (preferences.getBoolean(Utils.PREF_LOGGED_IN, false)) {
            if (preferences.getBoolean(Utils.PREF_IS_TEACHER, false)) {
                startActivity(new Intent(LoginActivity.this, TeacherMainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 5000);
            } else {
                startActivity(new Intent(LoginActivity.this, StudentActivityMain.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 5000);
            }
        } else {
            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {

                // PERMISSIONS GRANTED, CONTINUE
                File recoLectureDir = new File(Utils.getLecturePath());
                File dummy1 = new File(Utils.getLecturePath() + "/Spanish I");
                File dummy2 = new File(Utils.getLecturePath() + "/Spanish II");
                File dummy3 = new File(Utils.getLecturePath() + "/Spanish III");
                File dummy4 = new File(Utils.getLecturePath() + "/Spanish IV");
                File dummy5 = new File(Utils.getLecturePath() + "/Spanish V");

                recoLectureDir.mkdir();
                dummy1.mkdir();
                dummy2.mkdir();
                dummy3.mkdir();
                dummy4.mkdir();
                dummy5.mkdir();

            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                    Toast.makeText(LoginActivity.this, "Permission REQUIRED to work properly", Toast.LENGTH_LONG).show();
                }

                //REQUEST PERMISSION
                Utils.verifyStoragePermissions(this);
            }

            startLogin();
        }
    }

    public void startLogin() {
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ImageView colorBlock = (ImageView) findViewById(R.id.colorBlock);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                colorBlock.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        colorBlock.startAnimation(fadeOut);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equalsIgnoreCase("")) {
                    TextInputLayout passwordTIL = (TextInputLayout) findViewById(R.id.passwordHolder);
                    passwordTIL.setErrorEnabled(true);
                    passwordTIL.setError("This field cannot be empty");
                } else if (username.getText().toString().equalsIgnoreCase("")){
                    TextInputLayout usernameTIL = (TextInputLayout) findViewById(R.id.usernameHolder);
                    usernameTIL.setErrorEnabled(true);
                    usernameTIL.setError("This field cannot be empty");
                }
                getData(username.getText().toString(), password.getText().toString());
            }
        });

        final TextView signup = (TextView) findViewById(R.id.signupPrompt);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationScreen.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            //Granted
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
            View dialogView = View.inflate(getApplicationContext(), R.layout.permissions_denied_dialog, null);
            dialog.setTitle("Attention!")
                    .setView(dialogView)
                    .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int idk) {
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utils.verifyStoragePermissions(LoginActivity.this);
                        }
                    })
                    .show();
        }
    }

    private void getData(final String user, final String password) {
        if(user.equals(""))
        {
            Toast.makeText(this, "Please enter a username.", Toast.LENGTH_LONG).show();
            return;
        }
        if(password.equals(""))
        {
            Toast.makeText(this, "Please enter a password.", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        String url = DATA_URL+user;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response, user, password);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response, String user, String password)
    {
        String userData;
        String passwordData;
        int isTeacherData;

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject data = result.getJSONObject(0);

            userData = data.getString("username");
            passwordData = data.getString("password");
            isTeacherData = data.getInt("teacher");

            if(userData.equals(user) && passwordData.equals(password))
            {
                if(isTeacherData == 1)
                {
                    editor.putBoolean(Utils.PREF_LOGGED_IN, true);
                    editor.putBoolean(Utils.PREF_IS_TEACHER, true);
                    editor.putBoolean(Utils.PREF_DUMMY_CLASSES, true);
                    editor.apply();

                    ArrayList<TeacherClassCard> mClasses = new ArrayList<>();
                    mClasses.add(0, new TeacherClassCard(NULL_CLASS, "Header (NULL)"));
                    mClasses.add(1, new TeacherClassCard("Spanish I", AUTO_DESCRIPTION));
                    mClasses.add(2, new TeacherClassCard("Spanish II", AUTO_DESCRIPTION));
                    mClasses.add(3, new TeacherClassCard("Spanish III", AUTO_DESCRIPTION));
                    mClasses.add(4, new TeacherClassCard("Spanish IV", AUTO_DESCRIPTION));
                    mClasses.add(5, new TeacherClassCard("Spanish V", AUTO_DESCRIPTION));

                    Utils.setTeacherClassList(mClasses, LoginActivity.this);

                    startActivity(new Intent(LoginActivity.this, TeacherMainActivity.class));
                    finish();
                }
                else
                {
                    editor.putBoolean(Utils.PREF_LOGGED_IN, true);
                    editor.putBoolean(Utils.PREF_IS_TEACHER, false);
                    editor.putBoolean(Utils.PREF_DUMMY_CLASSES, true);
                    editor.apply();

                    ArrayList<ClassCard> mClasses = new ArrayList<>();
                    mClasses.add(0, new ClassCard(NULL_CLASS, "Header (NULL)"));
                    mClasses.add(1, new ClassCard("Spanish I", AUTO_DESCRIPTION));
                    mClasses.add(2, new ClassCard("Spanish II", AUTO_DESCRIPTION));
                    mClasses.add(3, new ClassCard("Spanish III", AUTO_DESCRIPTION));
                    mClasses.add(4, new ClassCard("Spanish IV", AUTO_DESCRIPTION));
                    mClasses.add(5, new ClassCard("Spanish V", AUTO_DESCRIPTION));

                    Utils.setStudentClassList(mClasses, LoginActivity.this);

                    startActivity(new Intent(LoginActivity.this, StudentActivityMain.class));
                    finish();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}