package ajlyfe.lectureapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.HashMap;

import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import ajlyfe.lectureapp.WriteToDatabase;

public class RegistrationScreen extends AppCompatActivity {

    private static final String REGISTER_URL = "http://www.chs.mcvsd.org/sandbox/insert-accountdb.php";

    private EditText emailET;
    private EditText firstNameET;
    private EditText lastNameET;
    private EditText usernameET;
    private EditText passwordET;
    private EditText confirmPasswordET;

    private CheckBox isTeacherCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setCustomTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isTeacherCB = (CheckBox) findViewById(R.id.checkBoxIAmATeacher);
        final ScrollView scrollView = (ScrollView) findViewById(R.id.registerScrollView);
        final EditText[] editTexts = {
                emailET = (EditText) findViewById(R.id.emailET),
                firstNameET = (EditText) findViewById(R.id.firstNameET),
                lastNameET = (EditText) findViewById(R.id.lastNameET),
                usernameET = (EditText) findViewById(R.id.usernameET),
                passwordET = (EditText) findViewById(R.id.passwordET),
                confirmPasswordET = (EditText) findViewById(R.id.confirmPasswordET)
        };

        final int constant = 200;

        for (final EditText editText : editTexts) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().length() <= 1) {
                        if (editText.getId() != R.id.emailET) {
                            scrollView.smoothScrollTo(0, scrollView.getScrollY() + constant);
                        }
                    }
                }
            });
        }

        Button register = (Button) findViewById(R.id.buttonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(findViewById(R.id.content_registration_screen).getWindowToken(), 0);

                // Escape characters commonly used in SQL injection attacks
                boolean continuable = true;

                for (final EditText editText : editTexts) {
                    for (final char badChar : Utils.ESCAPE_CHARACTERS) {
                        if (editText.getText().toString().contains(String.valueOf(badChar))) {
                            continuable = false;
                            TextInputLayout til = (TextInputLayout) editText.getParent().getParent();
                            til.setErrorEnabled(true);
                            til.setError("Illegal input detected");
                        }
                    }
                }

                if (continuable)
                {
                    registerUser();
                    //startActivity(new Intent(RegistrationScreen.this, LoginActivity.class));
                }
            }
        });
    }

    private void registerUser()
    {
        String email = emailET.getText().toString().trim();
        String fname = firstNameET.getText().toString().trim();
        String lname = lastNameET.getText().toString().trim();
        String username = usernameET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String confPassword = confirmPasswordET.getText().toString().trim();

        boolean isTeacher = isTeacherCB.isChecked();

        String isTeacherString = "0";

        if(isTeacher)
        {
            isTeacherString = "1";
        }

        register(email, fname, lname, username, password, confPassword, isTeacherString);
    }

    private void register(String email, String fname, String lname, String username, String password, String confPassword, String isTeacher)
    {
        class RegisterUser extends AsyncTask<String, Void, String>
        {
            private ProgressDialog loading;
            private WriteToDatabase ruc = new WriteToDatabase();

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(RegistrationScreen.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                if(s.equals("Registration successful!"))
                {
                    startActivity(new Intent(RegistrationScreen.this, LoginActivity.class));
                }
            }

            @Override
            protected String doInBackground(String... params)
            {
                HashMap<String, String> data = new HashMap<>();
                data.put("email",params[0]);
                data.put("fname",params[1]);
                data.put("lname",params[2]);
                data.put("username",params[3]);
                data.put("password",params[4]);
                data.put("confPassword",params[5]);
                data.put("isTeacher",params[6]);

                return ruc.sendPostRequest(REGISTER_URL, data);
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(email, fname, lname, username, password, confPassword, isTeacher);
    }
}