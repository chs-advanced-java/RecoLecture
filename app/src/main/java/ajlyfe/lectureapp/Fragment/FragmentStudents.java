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

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ajlyfe.lectureapp.Adapters.StudentSelectCard;
import ajlyfe.lectureapp.Adapters.StudentSelectCardAdapter;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import ajlyfe.lectureapp.WriteToDatabase;

public class FragmentStudents extends Fragment {

    private View view;
    private StudentSelectCardAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.slide_students, container, false);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            //getStudents(bundle.getStringArrayList("classCodes"));
        }

        return view;
    }

    private void getStudents(final ArrayList<String> classCodes) {
        String classCode = classCodes.get(0);

        class PushClass extends AsyncTask<String, Void, String> {
            private ProgressDialog loading;
            private WriteToDatabase ruc = new WriteToDatabase();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(view.getContext(), "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                ArrayList<StudentSelectCard> studentList = new ArrayList<>();

                try {
                    JSONObject result = new JSONObject(s);
                    JSONArray students = result.optJSONArray("result");

                    for (int i = 0; i < students.length(); i++) {
                        JSONObject post = students.optJSONObject(i);
                        studentList.add(new StudentSelectCard(post.optString("fName") + " " + post.optString("lName")));
                    }

                    RecyclerView recyclerViewStudents = (RecyclerView) view.findViewById(R.id.recyclerViewStudentSelect);
                    adapter = new StudentSelectCardAdapter(studentList, view.getContext());
                    recyclerViewStudents.setAdapter(adapter);
                    recyclerViewStudents.setLayoutManager(new LinearLayoutManager(view.getContext()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("classCode", params[0]);

                return ruc.sendPostRequest("http://www.chs.mcvsd.org/sandbox/getClassStudents.php", data);
            }
        }

        PushClass pc = new PushClass();
        pc.execute(classCode);
    }

    /*
    public void method() {
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
        });
    }*/
}