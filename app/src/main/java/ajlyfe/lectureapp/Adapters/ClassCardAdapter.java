package ajlyfe.lectureapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ajlyfe.lectureapp.Activity.StudentActivityMain;
import ajlyfe.lectureapp.Activity.StudentClassPage;
import ajlyfe.lectureapp.R;

public class ClassCardAdapter extends RecyclerView.Adapter<ClassCardAdapter.ViewHolder> {
    private List<ClassCard> classList;
    private Context context;
    private Activity parentActivity;

    public ClassCardAdapter(@NonNull List<ClassCard> classes, Context ctx, Activity parentActivity) {
        context = ctx;
        classList = classes;
        this.parentActivity = parentActivity;
    }

    @Override
    public ClassCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View card = inflater.inflate(R.layout.class_card, parent, false);

        return new ClassCardAdapter.ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(final ClassCardAdapter.ViewHolder viewHolder, int position) {
        position = viewHolder.getAdapterPosition();

        final ClassCard clss = classList.get(position);

        TextView title = viewHolder.classTitle;
        title.setText(clss.getClassName());

        TextView teacher = viewHolder.classTeacher;
        teacher.setText(clss.getTeacherName());

        CardView classCard = viewHolder.card;
        classCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudentClassPage.class);
                intent.putExtra("CLASS_CLICKED", clss.getClassName());
                context.startActivity(intent);
            }
        });

        Toolbar mToolbar = viewHolder.toolbar;
        mToolbar.inflateMenu(R.menu.class_card_menu);
        final int finalPosition = position;
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Warning!")
                        .setMessage("You are about to delete a class. Are you sure you" +
                                "want to do this? This action cannot be undone.")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }
                        })
                        .setPositiveButton("OK, M8!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int idk) {
                                removeAt(finalPosition);
                                Snackbar.make(parentActivity.findViewById(R.id.classOverviewLayout),
                                            "Class '" + clss.getClassName() + "' deleted",
                                            Snackbar.LENGTH_LONG)
                                        .setAction("Dandy!", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) { /*ihateusers*/ }
                                        })
                                        .setActionTextColor(Color.parseColor("#FFFFC107"))
                                        .show();
                            }
                        }).show();
                return true;
            }
        });
    }

    public void removeAt(int position) {
        classList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, classList.size());
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView classTitle;
        TextView classTeacher;
        CardView card;
        Toolbar toolbar;

        public ViewHolder(View itemView) {
            super(itemView);

            this.toolbar = (Toolbar) itemView.findViewById(R.id.classCardToolbar);
            this.card = (CardView) itemView.findViewById(R.id.classCard);
            this.classTitle = (TextView) itemView.findViewById(R.id.className);
            this.classTeacher = (TextView) itemView.findViewById(R.id.classTeacher);
        }
    }
}
