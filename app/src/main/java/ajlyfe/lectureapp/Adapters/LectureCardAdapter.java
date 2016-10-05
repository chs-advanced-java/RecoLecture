package ajlyfe.lectureapp.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ajlyfe.lectureapp.R;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class LectureCardAdapter extends RecyclerView.Adapter<LectureCardAdapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<LectureCard> lectureList;
    // Store the context for easy access

    // Pass in the contact array into the constructor
    public LectureCardAdapter(@Nullable List<LectureCard> lectures) {
        if (lectures != null) {
            lectureList = lectures;
        } else {
            for (int i = 1; i <= 24; i++) {
                lectureList.add(new LectureCard("Spanish " + i, "Plumezzzzz"));
            }
        }
    }

    @Override
    public LectureCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Inflate the custom layout
        View card = inflater.inflate(R.layout.lecture_card, parent, false);

        // Return a new holder instance
        return new ViewHolder(card);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(LectureCardAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        LectureCard lecture = lectureList.get(position);

        // Set item views based on your views and data model
        TextView title = viewHolder.lectureTitle;
        //title.setText(lecture.getLectureName());

        TextView teacher = viewHolder.lectureTeacher;
        //teacher.setText(lecture.getTeacherName());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return lectureList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView lectureTitle;
        TextView lectureTeacher;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);


            this.lectureTitle = (TextView) itemView.findViewById(R.id.lectureTitle);
            this.lectureTeacher = (TextView) itemView.findViewById(R.id.lectureTeacher);
        }
    }
}