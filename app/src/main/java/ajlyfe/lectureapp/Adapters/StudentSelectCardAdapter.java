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
import android.widget.TextView;

import java.util.List;

import ajlyfe.lectureapp.Activity.StudentClassPage;
import ajlyfe.lectureapp.R;

public class StudentSelectCardAdapter extends RecyclerView.Adapter<StudentSelectCardAdapter.ViewHolder> {
    private List<StudentSelectCard> studentSelectList;
    private Context context;
    private boolean checked;

    private static final int HEADER = 2048;
    private static final int NORMAL_ITEM = 4096;

    public StudentSelectCardAdapter(@NonNull List<StudentSelectCard> students, Context ctx) {
        context = ctx;
        studentSelectList = students;
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
    public StudentSelectCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == HEADER) {
            view = inflater.inflate(R.layout.student_main_header, parent, false);
        } else {
            view = inflater.inflate(R.layout.student_select_card, parent, false);
        }

        return new StudentSelectCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentSelectCardAdapter.ViewHolder viewHolder, int position) {
        if (getItemViewType(viewHolder.getAdapterPosition()) != HEADER) {
            position = viewHolder.getAdapterPosition();

            final StudentSelectCard clss = studentSelectList.get(position);

            TextView title = viewHolder.studentSelectTitle;
            title.setText(clss.getClassName());

            final CheckBox box = viewHolder.check;

            CardView classCard = viewHolder.card;
            classCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!checked){
                        box.setChecked(true);
                        checked = true;
                    }
                    else{
                        box.setChecked(false);
                        checked = false;
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return studentSelectList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentSelectTitle;
        CardView card;
        CheckBox check;

        public ViewHolder(View itemView) {
            super(itemView);
            this.card = (CardView) itemView.findViewById(R.id.studentSelectCard);
            this.studentSelectTitle = (TextView) itemView.findViewById(R.id.studentName);
            this.check = (CheckBox) itemView.findViewById(R.id.studentCheckBox);

        }
    }

    public void toggleAllChecked() {
        boolean allChecked = true;
        for(int x = 0; x < studentSelectList.size(); x++){
            if (studentSelectList.get(x).getChecked()){
                allChecked = false;
            }
        }

        if (allChecked){
            for (int y = 0; y < studentSelectList.size(); y++){
                studentSelectList.get(y).setChecked(false);
            }
        }
        else{
            for (int y = 0; y < studentSelectList.size(); y++){
                studentSelectList.get(y).setChecked(true);
            }
        }
        notifyDataSetChanged();
    }
}