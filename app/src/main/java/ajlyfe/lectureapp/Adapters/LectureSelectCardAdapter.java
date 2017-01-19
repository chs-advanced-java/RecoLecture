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

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.util.ArrayList;

import ajlyfe.lectureapp.R;

public class LectureSelectCardAdapter extends RecyclerView.Adapter<LectureSelectCardAdapter.ViewHolder> {
    private ArrayList<LectureSelectCard> lectureSelectList;
    private Activity parentActivity;
    private static OnLectureChecked mListener;

    private static final int HEADER = 2048;
    private static final int NORMAL_ITEM = 4096;

    public LectureSelectCardAdapter(@NonNull ArrayList<LectureSelectCard> lectures, Activity parentActivity) {
        lectureSelectList = lectures;
        this.parentActivity = parentActivity;
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

            CardView lectureCard = viewHolder.card;
            lectureCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null){
                        mListener.onChecked(lecture);
                    }

                    if (!lecture.getChecked()) {
                        box.setChecked(true);
                        lecture.setChecked(true);
                    } else {
                        box.setChecked(false);
                        lecture.setChecked(false);
                    }
                }
            });
            final long[] then = {0};
            lectureCard.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        then[0] = System.currentTimeMillis();
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        if ((System.currentTimeMillis() - then[0]) > 10000) {
                            new MaterialDialog.Builder(parentActivity)
                                    .title("Attention!")
                                    .content("You are about to delete all broken lectures.")
                                    .positiveText("Ok")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            boolean deleted = false;
                                            for (int i = 0; i < lectureSelectList.size(); i++) {
                                                if (lectureSelectList.get(i).isBroken()) {
                                                    new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                                                            "/RecoLecture/" + lectureSelectList.get(i).getID() + ".mp3").delete();
                                                    deleted = true;
                                                    Log.e("lecturecard", "Deleted lecture with ID: '" + lectureSelectList.get(i).getID()
                                                     + "' via super long click");
                                                }
                                            }

                                            if (deleted)
                                                Toast.makeText(parentActivity, "Deleted lecture(s)", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .show();
                            return true;
                        }
                    }
                    return false;
                }
            });

            box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!lecture.getChecked()) {
                        box.setChecked(true);
                        lecture.setChecked(true);
                    } else {
                        box.setChecked(false);
                        lecture.setChecked(false);
                    }
                }
            });

            if (lecture.isBroken()) hide(lectureCard);
        }
    }

    @Override
    public int getItemCount() {
        return lectureSelectList.size();
    }

    private void hide(CardView card) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) card.getLayoutParams();
        card.setVisibility(View.GONE);
        layoutParams.height = 0;
        layoutParams.width = 0;
        card.setLayoutParams(layoutParams);
    }

    public interface OnLectureChecked {
        void onChecked(LectureSelectCard lecture);
    }

    public static void setLectureCheckedListener(OnLectureChecked eventListener) {
        mListener = eventListener;
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
