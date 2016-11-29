package ajlyfe.lectureapp.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ajlyfe.lectureapp.R;

public class LectureCheckboxAdapter extends RecyclerView.Adapter<LectureCheckboxAdapter.ViewHolder> {
    private List<LectureCheckbox> lecturesChecked;

    public LectureCheckboxAdapter(@NonNull List<LectureCheckbox> mLectures) {
        lecturesChecked = mLectures;
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
