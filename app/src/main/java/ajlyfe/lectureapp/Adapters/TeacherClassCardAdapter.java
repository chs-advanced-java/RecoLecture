package ajlyfe.lectureapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
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

import java.util.ArrayList;

import ajlyfe.lectureapp.Activity.TeacherClassOverview;
import ajlyfe.lectureapp.Activity.TeacherClassView;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class TeacherClassCardAdapter extends RecyclerView.Adapter<TeacherClassCardAdapter.ViewHolder> {
    private ArrayList<TeacherClassCard> classList;
    private Context context;
    private Activity parentActivity;

    private static final int HEADER = 2048;
    private static final int NORMAL_ITEM = 4096;

    public TeacherClassCardAdapter(ArrayList<TeacherClassCard> classes, Context ctx, Activity parentActivity) {
        context = ctx;
        classList = classes;
        this.parentActivity = parentActivity;
    }

    /** Since the header is at position 0 in the RecyclerView, we must accommodate for it.
     *  onBindViewHolder loops through based on the size of our ArrayList.
     *  If we don't make our size, one more bigger than it needs to be, we will lose a class.
     */

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return NORMAL_ITEM;
        }
    }

    @Override
    public TeacherClassCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == HEADER) {
            view = inflater.inflate(R.layout.overview_header, parent, false);
        } else {
            view = inflater.inflate(R.layout.class_card, parent, false);
        }
        return new TeacherClassCardAdapter.ViewHolder(view);
    }

    public void setClassList(ArrayList<TeacherClassCard> l){
        classList = l;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final TeacherClassCardAdapter.ViewHolder viewHolder, int position) {
        position = viewHolder.getAdapterPosition();

        if (getItemViewType(viewHolder.getAdapterPosition()) != HEADER) {
            final TeacherClassCard mClass = classList.get(position);

            TextView title = viewHolder.classTitle;
            title.setText(mClass.getName());

            TextView classCount = viewHolder.classDescription;
            classCount.setText(mClass.getDescription());

            ImageView classColor = viewHolder.classCardColor;
            classColor.setBackgroundColor(Utils.generateColor());

            final CardView classCard = viewHolder.card;
            classCard.setOnClickListener(getOnCardClick(viewHolder, mClass));

            Toolbar mToolbar = viewHolder.toolbar;
            mToolbar.setOnClickListener(getOnCardClick(viewHolder, mClass));

            if (!viewHolder.classTitle.getText().equals(parentActivity.getString(R.string.no_classes_title))) {
                // Catch reinflation of menu items
                try {
                    mToolbar.getMenu().getItem(0);
                } catch (IndexOutOfBoundsException e) {
                    Log.e("TOOLBAR", "inflating menu for class " + title.getText());
                    mToolbar.inflateMenu(R.menu.teacher_class_card_menu);
                    mToolbar.setOverflowIcon(getOverflowIcon());
                    final int finalPosition = position;
                    mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.action_delete) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                dialog.setTitle("Warning!")
                                        .setMessage("You are about to permanently delete a class. Are you sure you " +
                                                "want to do this? This action can NOT be undone.")
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialoginterface, int i) {
                                                dialoginterface.cancel();
                                            }
                                        })
                                        .setPositiveButton("OK!", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialoginterface, int idk) {
                                                removeAt(finalPosition);

                                                Utils.setTeacherClassList(classList, parentActivity);

                                                Snackbar.make(parentActivity.findViewById(R.id.classOverviewLayout),
                                                        "Deleted '" + mClass.getName() + "' successfully",
                                                        Snackbar.LENGTH_LONG)
                                                        .setAction("Dandy!", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) { /*ihateusers*/ }
                                                        })
                                                        .setActionTextColor(Color.parseColor("#FFFFC107"))
                                                        .show();
                                            }
                                        }).show();
                            } else if (item.getItemId() == R.id.action_invite) {
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("message/rfc822");
                                i.putExtra(Intent.EXTRA_SUBJECT, "Join my new class on RecoLecture!");
                                i.putExtra(Intent.EXTRA_TEXT, "Use the code " + mClass.getCode() + " to join my class \""
                                        + mClass.getName() + ".\"");
                                try {
                                    parentActivity.startActivity(Intent.createChooser(i, "Send mail..."));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(parentActivity, "There are no email clients installed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            return true;
                        }
                    });
                }
            }
        }
    }

    private View.OnClickListener getOnCardClick(final TeacherClassCardAdapter.ViewHolder viewHolder, final TeacherClassCard mClass) {
        if (viewHolder.classTitle.getText().equals(parentActivity.getString(R.string.no_classes_title))) {
            boolean[] boolArr = { false };

            return TeacherClassOverview.onFABClick(parentActivity, boolArr, (FloatingActionButton) parentActivity.findViewById(R.id.fabTeacher));
        } else {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TeacherClassView.class);
                    intent.putExtra("CLASS_CLICKED", mClass.getName());
                    intent.putExtra("CLASS_CODE", mClass.getCode());
                    context.startActivity(intent);
                }
            };
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
}
