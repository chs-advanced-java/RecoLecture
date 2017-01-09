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

import java.util.ArrayList;

import ajlyfe.lectureapp.R;

public class LectureSelectCardAdapter extends RecyclerView.Adapter<LectureSelectCardAdapter.ViewHolder> {
    private ArrayList<LectureSelectCard> lectureSelectList;

    private static final int HEADER = 2048;
    private static final int NORMAL_ITEM = 4096;

    public LectureSelectCardAdapter(@NonNull ArrayList<LectureSelectCard> lectures, Context ctx) {
        lectureSelectList = lectures;
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
    public LectureSelectCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == HEADER) {
            view = inflater.inflate(R.layout.lecture_select_main_header, parent, false);
        } else {
            view = inflater.inflate(R.layout.lecture_select_card, parent, false);
        }

        return new LectureSelectCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LectureSelectCardAdapter.ViewHolder viewHolder, int position) {
        if (getItemViewType(viewHolder.getAdapterPosition()) != HEADER) {
            position = viewHolder.getAdapterPosition();

            final LectureSelectCard lecture = lectureSelectList.get(position);

            TextView title = viewHolder.lectureSelectTitle;
            title.setText(lecture.getFileName());

            final CheckBox box = viewHolder.check;

            CardView classCard = viewHolder.card;
            classCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!lecture.getChecked()){
                        box.setChecked(true);
                        lecture.setChecked(true);
                    }
                    else{
                        box.setChecked(false);
                        lecture.setChecked(false);
                    }
                }
            });

            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!lecture.getChecked()){
                        box.setChecked(true);
                        lecture.setChecked(true);
                    }
                    else{
                        box.setChecked(false);
                        lecture.setChecked(false);
                    }
                }
            });
        }

    }
    @Override
    public int getItemCount() {
        return lectureSelectList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView lectureSelectTitle;
        CardView card;
        CheckBox check;

        public ViewHolder(View itemView) {
            super(itemView);
            this.card = (CardView) itemView.findViewById(R.id.lectureSelectCard);
            this.lectureSelectTitle = (TextView) itemView.findViewById(R.id.fileName);
            this.check = (CheckBox) itemView.findViewById(R.id.fileCheckBox);

        }
    }

    public ArrayList<LectureSelectCard> getArrayList(){
        return lectureSelectList;
    }
}
