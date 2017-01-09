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

package ajlyfe.lectureapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ajlyfe.lectureapp.Adapters.LectureCheckbox;
import ajlyfe.lectureapp.Adapters.LectureCheckboxAdapter;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class FragmentUpload extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.slide_upload, container, false);

        // Edit the layout
        doThings();

        // Inflate the layout
        return view;
    }

    public void doThings() {
        ArrayList<LectureCheckbox> lecturesChecked = parseTemporaryArray(getArguments().getStringArrayList("lecturesCheckedOff"));

        RecyclerView recyclerViewLectures = (RecyclerView) view.findViewById(R.id.checkboxRecycler);
        LectureCheckboxAdapter adapter = new LectureCheckboxAdapter(lecturesChecked);
        recyclerViewLectures.setAdapter(adapter);
        recyclerViewLectures.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private ArrayList<LectureCheckbox> parseTemporaryArray(ArrayList<String> tArray) {
        ArrayList<LectureCheckbox> returnMe = new ArrayList<>();

        for (int i = 0; i < tArray.size(); i++) {
            returnMe.add(i, new LectureCheckbox(tArray.get(i)));
        }

        return returnMe;
    }
}