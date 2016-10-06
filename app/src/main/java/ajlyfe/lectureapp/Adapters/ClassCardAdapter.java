package ajlyfe.lectureapp.Adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ajlyfe.lectureapp.R;

public class ClassCardAdapter extends RecyclerView.Adapter<ClassCardAdapter.ViewHolder> {
    // Store a member variable for the contacts
    private List<ClassCard> classList;
    // Store the context for easy access

    // Pass in the contact array into the constructor
    public ClassCardAdapter(@Nullable List<ClassCard> classes) {
        if (classes != null) {
            classList = classes;
        } else {
            for (int i = 1; i <= 24; i++) {
                classList.add(new ClassCard("Spanish " + i, "Plumezzzzz"));
            }
        }
    }

    @Override
    public ClassCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View card = inflater.inflate(R.layout.class_card, parent, false);

        // Return a new holder instance
        return new ClassCardAdapter.ViewHolder(card);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ClassCardAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        ClassCard clss = classList.get(position);

        // Set item views based on your views and data model
        TextView title = viewHolder.classTitle;
        title.setText(clss.getClassName());

        TextView teacher = viewHolder.classTeacher;
        teacher.setText(clss.getTeacherName());
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

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);


            this.classTitle = (TextView) itemView.findViewById(R.id.className);
            this.classTeacher = (TextView) itemView.findViewById(R.id.classTeacher);
        }
    }
}
