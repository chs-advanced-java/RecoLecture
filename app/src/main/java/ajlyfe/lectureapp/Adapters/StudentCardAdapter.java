package ajlyfe.lectureapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class StudentCardAdapter extends RecyclerView.Adapter<StudentCardAdapter.ViewHolder> {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private ArrayList<? extends StudentCard> studentList;

    private Activity parentActivity;

    public StudentCardAdapter(@NonNull ArrayList<? extends StudentCard> studentList, Activity parentActivity) {
        this.studentList = studentList;
        this.parentActivity = parentActivity;
        preferences = Utils.getPrefs(Utils.SHARED_PREFERENCES, parentActivity);
        editor = preferences.edit();
    }

    @Override
    public StudentCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View card = inflater.inflate(R.layout.student_card, parent, false);

        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(final StudentCardAdapter.ViewHolder viewHolder, int position) {
        final StudentCard student = studentList.get(position);

        TextView name = viewHolder.studentName;
        name.setText(student.getName());

        TextView date = viewHolder.dateJoined;
        date.setText("Joined class: " + student.getDateJoined());

        ImageView stalk = viewHolder.stalkStudent;
        stalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parentActivity, "email: " + student.getEmail(), Toast.LENGTH_SHORT).show();
            }
        });

        final ImageView kill = viewHolder.killStudent;
        kill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar comeGetYourSnacks = Snackbar.make(parentActivity.findViewById(R.id.recyclerViewLecturesHolder),
                        "Successfully removed '" + student.getName() + "' from class!",
                        Snackbar.LENGTH_LONG);

                comeGetYourSnacks.show();
            }
        });
    }

    @Override
    public int getItemCount() { return studentList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        TextView dateJoined;
        ImageView stalkStudent;
        ImageView killStudent;

        ViewHolder(View itemView) {
            super(itemView);
            this.studentName = (TextView) itemView.findViewById(R.id.studentName);
            this.dateJoined = (TextView) itemView.findViewById(R.id.dateJoined);
            this.stalkStudent = (ImageView) itemView.findViewById(R.id.stalkStudent);
            this.killStudent = (ImageView) itemView.findViewById(R.id.killStudent);
        }
    }
}