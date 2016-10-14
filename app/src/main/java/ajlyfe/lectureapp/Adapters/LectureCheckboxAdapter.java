package ajlyfe.lectureapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ajlyfe.lectureapp.Activity.StudentClassPage;
import ajlyfe.lectureapp.R;

public class LectureCheckboxAdapter extends RecyclerView.Adapter<LectureCheckboxAdapter.ViewHolder> {
    private List<LectureCheckbox> lecturesChecked;
    private Context context;

    public LectureCheckboxAdapter(@NonNull List<LectureCheckbox> mLectures, Context ctx) {
        lecturesChecked = mLectures;
        context = ctx;
    }

    @Override
    public LectureCheckboxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View card = inflater.inflate(R.layout.lecture_checkbox, parent, false);
        return new LectureCheckboxAdapter.ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(final LectureCheckboxAdapter.ViewHolder viewHolder, int position) {
        position = viewHolder.getAdapterPosition();
        RelativeLayout lectureCheckboxHolder = viewHolder.lectureCheckboxHolder;
        final TextView lectureName = viewHolder.lectureName;
        final CheckBox lectureCheckbox = viewHolder.lectureCheckbox;

        lectureCheckboxHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lectureCheckbox.setChecked(!lectureCheckbox.isChecked());
            }
        });

        lectureName.setText(lecturesChecked.get(position).getLectureTitle());
    }
    @Override
    public int getItemCount() {
        return lecturesChecked.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout lectureCheckboxHolder;
        TextView lectureName;
        CheckBox lectureCheckbox;

        public ViewHolder(View itemView) {
            super(itemView);

            this.lectureCheckboxHolder = (RelativeLayout) itemView.findViewById(R.id.lectureCheckboxHolder);
            this.lectureName = (TextView) itemView.findViewById(R.id.lectureCheckboxTitle);
            this.lectureCheckbox = (CheckBox) itemView.findViewById(R.id.lectureCheckboxCheckbox);
        }
    }
}
