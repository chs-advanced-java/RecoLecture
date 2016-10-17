package ajlyfe.lectureapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ajlyfe.lectureapp.Activity.TeacherClassOverview;
import ajlyfe.lectureapp.Activity.TeacherClassView;
import ajlyfe.lectureapp.R;

public class TeacherClassCardAdapter extends RecyclerView.Adapter<TeacherClassCardAdapter.ViewHolder> {
    private List<TeacherClassCard> classList;
    private Context context;
    private Activity parentActivity;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;


    public TeacherClassCardAdapter(@NonNull List<TeacherClassCard> classes, Context ctx, Activity parentActivity) {
        context = ctx;
        classList = classes;
        this.parentActivity = parentActivity;
    }

    @Override
    public TeacherClassCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View card = inflater.inflate(R.layout.class_card, parent, false);
        return new TeacherClassCardAdapter.ViewHolder(card);
    }

    public void setClassList(List<TeacherClassCard> l){
        classList = l;
    }

    @Override
    public void onBindViewHolder(final TeacherClassCardAdapter.ViewHolder viewHolder, int position) {
        final TeacherClassCard clss = classList.get(position);

        TextView title = viewHolder.classTitle;
        title.setText(clss.getClassName());

        TextView teacher = viewHolder.classTeacher;
        teacher.setText(null);

        CardView classCard = viewHolder.card;
        classCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TeacherClassView.class);
                intent.putExtra("CLASS_CLICKED", clss.getClassName());
                context.startActivity(intent);
            }
        });

        Toolbar mToolbar = viewHolder.toolbar;
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TeacherClassView.class);
                intent.putExtra("CLASS_CLICKED", clss.getClassName());
                context.startActivity(intent);
            }
        });

        // Add overflow menu
        mToolbar.inflateMenu(R.menu.teacher_class_card_menu);
        mToolbar.setOverflowIcon(context.getResources().getDrawable(R.drawable.overflow));
        final int finalPosition = position;
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Warning!")
                        .setMessage("You are about to permanently delete a class. Are you sure you " +
                                "want to do this? This action can NOT be undone.")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                dialoginterface.cancel();
                            }
                        })
                        .setPositiveButton("OK, M8!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int idk) {
                                removeAt(finalPosition);

                                preferenceSettings = context.getSharedPreferences("Classes", PREFERENCE_MODE_PRIVATE);
                                preferenceEditor = preferenceSettings.edit();
                                classList.remove(finalPosition);
                                Set<String> set = new HashSet<>();
                                for(int x = 0; x < classList.size(); x++) {
                                    set.add(classList.get(x).getClassName());
                                }
                                preferenceEditor.putStringSet("Key", set);
                                preferenceEditor.commit();
                                context.startActivity(new Intent(parentActivity, TeacherClassOverview.class));

                                Snackbar.make(parentActivity.findViewById(R.id.classOverviewLayout),
                                        "Deleted '" + clss.getClassName() + "' successfully",
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

            this.card = (CardView) itemView.findViewById(R.id.classCard);
            this.classTitle = (TextView) itemView.findViewById(R.id.className);
            this.classTeacher = (TextView) itemView.findViewById(R.id.classTeacher);
            this.toolbar = (Toolbar) itemView.findViewById(R.id.classCardToolbar);
        }
    }
}
