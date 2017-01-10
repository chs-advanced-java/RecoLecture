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
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ajlyfe.lectureapp.Activity.LoginActivity;
import ajlyfe.lectureapp.Activity.StudentActivityMain;
import ajlyfe.lectureapp.Activity.StudentClassPage;
import ajlyfe.lectureapp.Activity.StudentEnterClassCode;
import ajlyfe.lectureapp.Activity.TeacherClassView;
import ajlyfe.lectureapp.Adapters.ClassCard;
import ajlyfe.lectureapp.Adapters.StudentCard;
import ajlyfe.lectureapp.Adapters.StudentCardAdapter;
import ajlyfe.lectureapp.Adapters.TeacherClassCard;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class FragmentTabStudents extends Fragment {

    private View view;
    private Bundle bundle;
    public final String DATA_URL = "http://www.chs.mcvsd.org/sandbox/getAllStudents.php?classCode=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.fragment_tab_students, container, false);

        Bundle bundle = this.getArguments();
        ArrayList<? extends StudentCard> studentList = null;

        if (bundle != null) {
            studentList = bundle.getParcelableArrayList("CLASS_LIST");
        } else {

        }

        RecyclerView recyclerViewMainTeacher = (RecyclerView) view.findViewById(R.id.manageStudentsRecyclerView);
        final StudentCardAdapter adapter = new StudentCardAdapter(studentList, view);
        recyclerViewMainTeacher.setAdapter(adapter);
        recyclerViewMainTeacher.setLayoutManager(new LinearLayoutManager(getContext()));
        OverScrollDecoratorHelper.setUpOverScroll(recyclerViewMainTeacher, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
