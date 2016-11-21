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

public class ClassSelectCardAdapter extends RecyclerView.Adapter<ClassSelectCardAdapter.ViewHolder> {
    private List<ClassSelectCard> classSelectList;
    private Context context;
    private Activity parentActivity;
    private boolean checked;

    private static final int HEADER = 2048;
    private static final int NORMAL_ITEM = 4096;

    public ClassSelectCardAdapter(@NonNull List<ClassSelectCard> classes, Context ctx, Activity parentActivity) {
        context = ctx;
        classSelectList = classes;
        this.parentActivity = parentActivity;
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
    public ClassSelectCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
            view = inflater.inflate(R.layout.class_select_card, parent, false);


        return new ClassSelectCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ClassSelectCardAdapter.ViewHolder viewHolder, int position) {
        if (getItemViewType(viewHolder.getAdapterPosition()) != HEADER) {
            position = viewHolder.getAdapterPosition();

            final ClassSelectCard clss = classSelectList.get(position);

            TextView title = viewHolder.classSelectTitle;
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
        return classSelectList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView classSelectTitle;
        CardView card;
        CheckBox check;

        public ViewHolder(View itemView) {
            super(itemView);
            this.card = (CardView) itemView.findViewById(R.id.classSelectCard);
            this.classSelectTitle = (TextView) itemView.findViewById(R.id.className);
            this.check = (CheckBox) itemView.findViewById(R.id.classCheckBox);

        }
    }
}