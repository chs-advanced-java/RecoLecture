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

public class ClassSelectCardAdapter extends RecyclerView.Adapter<ClassSelectCardAdapter.ViewHolder> {
    private ArrayList<ClassSelectCard> classSelectList;

    private static final int NORMAL_ITEM = 4096;

    public ClassSelectCardAdapter(@NonNull ArrayList<ClassSelectCard> classes, Context ctx) {
        classSelectList = classes;
    }

    @Override
    public int getItemViewType(int position) {
        
        return NORMAL_ITEM;

    }

    @Override
    public ClassSelectCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
            view = inflater.inflate(R.layout.class_select_card, parent, false);


        return new ClassSelectCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ClassSelectCardAdapter.ViewHolder viewHolder, int position) {

        position = viewHolder.getAdapterPosition();

        final ClassSelectCard clss = classSelectList.get(position);

        TextView title = viewHolder.classSelectTitle;
        title.setText(clss.getClassName());

        final CheckBox box = viewHolder.check;

        CardView classCard = viewHolder.card;
        classCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clss.getChecked()){
                    box.setChecked(true);
                    clss.setChecked(true);
                }
                else{
                    box.setChecked(false);
                    clss.setChecked(false);
                }
            }
        });

        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clss.getChecked()){
                    box.setChecked(true);
                    clss.setChecked(true);
                }
                else{
                    box.setChecked(false);
                    clss.setChecked(false);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return classSelectList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView classSelectTitle;
        CardView card;
        CheckBox check;

        public ViewHolder(View itemView) {
            super(itemView);
            this.card = (CardView) itemView.findViewById(R.id.classSelectCard);
            this.classSelectTitle = (TextView) itemView.findViewById(R.id.className);
            this.check = (CheckBox) itemView.findViewById(R.id.classCheckBox);
        }
    }

    public ArrayList<ClassSelectCard> getArrayList(){
        return classSelectList;
    }

}