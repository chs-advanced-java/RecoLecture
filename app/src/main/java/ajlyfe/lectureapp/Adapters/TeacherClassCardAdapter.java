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

import ajlyfe.lectureapp.Activity.TeacherClassView;
import ajlyfe.lectureapp.R;

public class TeacherClassCardAdapter extends RecyclerView.Adapter<TeacherClassCardAdapter.ViewHolder> {
    private List<TeacherClassCard> classList;
    private Context context;

    public TeacherClassCardAdapter(@NonNull List<TeacherClassCard> classes, Context ctx) {
        context = ctx;
        classList = classes;
    }

    @Override
    public TeacherClassCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View card = inflater.inflate(R.layout.class_card, parent, false);
        return new TeacherClassCardAdapter.ViewHolder(card);
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

    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView classTitle;
        TextView classTeacher;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);

            this.card = (CardView) itemView.findViewById(R.id.classCard);
            this.classTitle = (TextView) itemView.findViewById(R.id.className);
            this.classTeacher = (TextView) itemView.findViewById(R.id.classTeacher);
        }
    }
}
