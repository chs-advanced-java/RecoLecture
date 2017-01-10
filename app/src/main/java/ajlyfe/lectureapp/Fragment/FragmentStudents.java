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
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

import ajlyfe.lectureapp.Adapters.StudentSelectCard;
import ajlyfe.lectureapp.Adapters.StudentSelectCardAdapter;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class FragmentStudents extends Fragment {

    private boolean selectAllSelection;
    private View view;
    private Button selectAll;
    private StudentSelectCardAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.slide_students, container, false);

        method();

        return view;
    }

    public void method() {/**
        selectAll = (Button) view.findViewById(R.id.selectAllButton);

        ArrayList<StudentSelectCard> studentList = new ArrayList<>();
        CheckBox check = (CheckBox) view.findViewById(R.id.studentCheckBox);
        studentList.add(new StudentSelectCard("John", check));

        RecyclerView recyclerViewStudents = (RecyclerView) view.findViewById(R.id.recyclerViewStudentSelect);
        adapter = new StudentSelectCardAdapter(studentList, view.getContext());
        recyclerViewStudents.setAdapter(adapter);
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(view.getContext()));

        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });**/
    }
}