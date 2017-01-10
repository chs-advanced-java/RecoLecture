/*
 * Copyright (c) 2017 RecoLecture.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ajlyfe.lectureapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

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