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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.util.ArrayList;

import ajlyfe.lectureapp.Activity.TeacherMainActivity;
import ajlyfe.lectureapp.Adapters.ClassSelectCard;
import ajlyfe.lectureapp.Adapters.ClassSelectCardAdapter;
import ajlyfe.lectureapp.Adapters.TeacherClassCard;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class FragmentClass extends Fragment {
    private View view;
    private ClassSelectCardAdapter adapter;
    public ArrayList<ClassSelectCard> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.slide_class, container, false);

        method(getActivity());

        return view;
    }
    public void method(final Activity activity) {
        ArrayList<TeacherClassCard> classes = Utils.getTeacherClassList(activity);
        ArrayList<ClassSelectCard> useMe = new ArrayList<>();

        if (classes != null) {
            for (int i = 0; i < classes.size(); i++) {
                // CATCH HEADER ITEM
                if (i != 0) {
                    useMe.add(new ClassSelectCard(classes.get(i).getName()));
                }
            }
        } else {
            new MaterialDialog.Builder(activity)
                    .title("Attention!")
                    .content("You have not created any classes yet")
                    .positiveText("Ok")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            startActivity(new Intent(activity.getApplicationContext(), TeacherMainActivity.class));
                        }
                    })
                    .show();
        }

        RecyclerView recyclerViewStudents = (RecyclerView) view.findViewById(R.id.recyclerViewClassSelect);

        try {
            adapter = new ClassSelectCardAdapter(useMe, view.getContext());
            list = adapter.getArrayList();
            recyclerViewStudents.setAdapter(adapter);
            recyclerViewStudents.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
        catch (NullPointerException exc) {
            Toast.makeText(getContext(), "Error, User has no classes.", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<ClassSelectCard> getAdapterArrayList(){
        try {
            return list;
        }
        catch(NullPointerException exc) {
            return new ArrayList<>();
        }
    }
}