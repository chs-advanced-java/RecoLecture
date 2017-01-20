/*
 * Copyright (c) 2017 RecoLecture.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ajlyfe.lectureapp.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;

import ajlyfe.lectureapp.Activity.LoginActivity;
import ajlyfe.lectureapp.Activity.StudentActivityMain;
import ajlyfe.lectureapp.Activity.StudentClassPage;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import ajlyfe.lectureapp.WriteToDatabase;

public class ClassCardAdapter extends RecyclerView.Adapter<ClassCardAdapter.ViewHolder> {
    private ArrayList<ClassCard> classList;
    private Context context;
    private Activity parentActivity;

    private static final int HEADER = 2048;
    private static final int NORMAL_ITEM = 4096;
    private static final String DELETE_STUDENT_URL = "http://www.chs.mcvsd.org/sandbox/delete-students.php?classCode=";

    public ClassCardAdapter(@NonNull ArrayList<ClassCard> classes, Context ctx, Activity parentActivity) {
        context = ctx;
        classList = classes;
        this.parentActivity = parentActivity;

        if (classList.size() == 1) {
            parentActivity.findViewById(R.id.noClasses).setVisibility(View.VISIBLE);
        }
        else {
            parentActivity.findViewById(R.id.noClasses).setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return NORMAL_ITEM;
        }
    }

    @Override
    public ClassCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == HEADER) {
            view = inflater.inflate(R.layout.student_main_header, parent, false);
        } else {
            view = inflater.inflate(R.layout.class_card, parent, false);
        }

        return new ClassCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ClassCardAdapter.ViewHolder viewHolder, int position) {
        if (getItemViewType(viewHolder.getAdapterPosition()) != HEADER) {
            position = viewHolder.getAdapterPosition();

            final ClassCard thisClassCard = classList.get(position);

            TextView title = viewHolder.classTitle;
            title.setText(thisClassCard.getClassName());

            TextView description = viewHolder.classDescription;
            description.setText(thisClassCard.getClassDescription());

            ImageView classColor = viewHolder.classCardColor;
            classColor.setBackgroundColor(Utils.generateColor());

            CardView classCard = viewHolder.card;
            classCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StudentClassPage.class);
                    intent.putExtra("CLASS_CLICKED", thisClassCard.getClassName());
                    context.startActivity(intent);
                }
            });

            Toolbar mToolbar = viewHolder.toolbar;
            mToolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StudentClassPage.class);
                    intent.putExtra("CLASS_CLICKED", thisClassCard.getClassName());
                    context.startActivity(intent);
                }
            });

            // Catch reinflation of menu items
            try {
                mToolbar.getMenu().getItem(0);
            } catch (IndexOutOfBoundsException e) {
                Log.e("TOOLBAR", "inflating menu for class " + title.getText());
                mToolbar.inflateMenu(R.menu.student_class_card_menu);
                mToolbar.setOverflowIcon(getOverflowIcon());
                final int finalPosition = position;
                mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setTitle("Warning!")
                                .setMessage("You are about to unenroll from a class. Are you sure you " +
                                        "want to do this? This action cannot be undone.")
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int i) {
                                        dialoginterface.cancel();
                                    }
                                })
                                .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int idk) {
                                        final ProgressDialog loading = ProgressDialog.show(parentActivity,"Please wait...","Fetching...",false,false);

                                        String url = DELETE_STUDENT_URL + thisClassCard.getClassCode() + "&email=" + Utils.getPrefs(Utils.SHARED_PREFERENCES, context).getString(Utils.PREF_EMAIL, null).toString();

                                        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                loading.dismiss();
                                                Toast.makeText(parentActivity, "Unenrolled from class. \nSign out to complete request.", Toast.LENGTH_LONG).show();
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(parentActivity,error.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        });

                                        RequestQueue requestQueue = Volley.newRequestQueue(parentActivity);
                                        requestQueue.add(stringRequest);
                                        removeAt(finalPosition);
                                        //removeClass();
                                    }
                                }).show();
                        return true;
                    }
                });
            }
        }
    }

    private Drawable getOverflowIcon() {
        boolean darkTheme = context.getSharedPreferences(Utils.SHARED_PREFERENCES, Context.MODE_PRIVATE)
                .getBoolean(Utils.PREF_DARK_THEME, false);

        if (darkTheme) {
            return context.getResources().getDrawable(R.drawable.overflow_light);
        } else {
            return context.getResources().getDrawable(R.drawable.overflow_dark);
        }
    }

    private void removeAt(int position) {
        classList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, classList.size());

        if (classList.size() == 1) {
            parentActivity.findViewById(R.id.noClasses).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView classTitle;
        TextView classDescription;
        ImageView classCardColor;
        CardView card;
        Toolbar toolbar;

        public ViewHolder(View itemView) {
            super(itemView);

            this.toolbar = (Toolbar) itemView.findViewById(R.id.classCardToolbar);
            this.card = (CardView) itemView.findViewById(R.id.classCard);
            this.classTitle = (TextView) itemView.findViewById(R.id.className);
            this.classDescription = (TextView) itemView.findViewById(R.id.classCardStudentDescription);
            this.classCardColor = (ImageView) itemView.findViewById(R.id.classCardColor);
        }
    }
/**
    public void removeClass(String classCode, String email) {
        class RemoveClass extends AsyncTask<String, Void, String> {
            private ProgressDialog loading;
            private WriteToDatabase ruc = new WriteToDatabase();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(parentActivity, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(parentActivity, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("classCode", params[0]);
                data.put("email", params[1]);

                return ruc.sendPostRequest(LEAVE_CLASS_URL, data);
            }
        }
        RemoveClass rc = new RemoveClass();
        rc.execute(classCode, email);
    }
    **/
}
