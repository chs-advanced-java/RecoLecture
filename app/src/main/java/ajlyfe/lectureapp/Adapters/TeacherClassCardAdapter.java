package ajlyfe.lectureapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ajlyfe.lectureapp.Activity.StudentClassPage;
import ajlyfe.lectureapp.R;

public class TeacherClassCardAdapter extends RecyclerView.Adapter<TeacherClassCardAdapter.ViewHolder> {
    // Store a member variable for the contacts
    private List<ClassCard> classList;

    //Store context to switch activities and yeah
    private Context context;

    // Pass in the contact array into the constructor
    public TeacherClassCardAdapter(@NonNull List<ClassCard> classes, Context ctx) {
        context = ctx;
        classList = classes;
    }

    @Override
    public TeacherClassCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View card = inflater.inflate(R.layout.class_card, parent, false);

        // Return a new holder instance
        return new TeacherClassCardAdapter.ViewHolder(card);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(final TeacherClassCardAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final ClassCard clss = classList.get(position);

        // Set item views based on your views and data model
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

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return classList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView classTitle;
        TextView classTeacher;
        CardView card;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.card = (CardView) itemView.findViewById(R.id.classCard);
            this.classTitle = (TextView) itemView.findViewById(R.id.className);
            this.classTeacher = (TextView) itemView.findViewById(R.id.classTeacher);
        }
    }
}
