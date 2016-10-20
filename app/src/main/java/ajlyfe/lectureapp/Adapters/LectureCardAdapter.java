package ajlyfe.lectureapp.Adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ajlyfe.lectureapp.R;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views from the inflated layout
public class LectureCardAdapter extends RecyclerView.Adapter<LectureCardAdapter.ViewHolder> {

    // Store objects to store data
    private List<LectureCard> lectureList;

    // Store parent activity
    Activity parentActivity;

    // Pass in the predefined Array into the constructor
    public LectureCardAdapter(@NonNull List<LectureCard> lectures, Activity parentActivity) {
        lectureList = lectures;
        this.parentActivity = parentActivity;
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
    public void onBindViewHolder(final LectureCardAdapter.ViewHolder viewHolder, int position) {
        // Get the object and it's data based on position
        final LectureCard lecture = lectureList.get(position);

        // Define the views from ViewHolder up here to play with them and do things! yasss
        TextView title = viewHolder.lectureTitle;
        title.setText(lecture.getLectureName());

        TextView teacher = viewHolder.lectureTeacher;
        teacher.setText(lecture.getTeacherName());

        ImageView download = viewHolder.lectureDownload;
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: RETRIEVE FROM THE SERVER
                Snackbar comeGetYourSnacks = Snackbar.make(parentActivity.findViewById(R.id.recyclerViewLecturesHolder),
                        "Lecture '" + lecture.getLectureName() + "' downloaded!",
                        Snackbar.LENGTH_LONG);

                comeGetYourSnacks.setActionTextColor(parentActivity.getResources().getColor(R.color.colorAccent));

                comeGetYourSnacks.setAction("OPEN", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playLecture(lecture.getLectureName());
                    }
                });

                comeGetYourSnacks.show();
            }
        });

        ImageView delete = viewHolder.lectureDelete;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(viewHolder.getAdapterPosition());
                //TODO: REMOVE LECTURE FROM DEVICE
            }
        });
    }

    public void removeAt(int position) {
        lectureList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, lectureList.size());
    }

    public void playLecture(String fileName) {
        //parentActivity.startActivity(new Intent(parentActivity, PlayLecture.class));
        View playLectureView = View.inflate(parentActivity, R.layout.play_lecture, null);

        final MediaPlayer mPlayer = MediaPlayer.create(parentActivity, R.raw.never);
        final FloatingActionButton playPause = (FloatingActionButton) playLectureView.findViewById(R.id.playPause);

        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer.isPlaying()) {
                    playPause.setImageResource(R.drawable.ic_play);
                    mPlayer.pause();
                } else {
                    playPause.setImageResource(R.drawable.ic_pause);
                    mPlayer.start();
                }
            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        builder.setTitle(fileName);
        builder.setView(playLectureView)
                .setCancelable(false)
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                })
                .show();
    }

    // Returns the total count of items in the list probably won't use this but we gotta @Override
    @Override
    public int getItemCount() { return lectureList.size(); }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder extends RecyclerView.ViewHolder {
        // These will be referenced every time up in the BindViewHolder
        TextView lectureTitle;
        TextView lectureTeacher;
        ImageView lectureDownload;
        ImageView lectureDelete;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            this.lectureTitle = (TextView) itemView.findViewById(R.id.lectureName);
            this.lectureTeacher = (TextView) itemView.findViewById(R.id.lectureTeacher);
            this.lectureDownload = (ImageView) itemView.findViewById(R.id.downloadLecture);
            this.lectureDelete = (ImageView) itemView.findViewById(R.id.deleteLecture);
        }
    }
}