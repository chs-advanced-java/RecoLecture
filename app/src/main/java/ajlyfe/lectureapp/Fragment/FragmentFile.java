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
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import ajlyfe.lectureapp.Activity.LoginActivity;
import ajlyfe.lectureapp.Activity.TeacherMainActivity;
import ajlyfe.lectureapp.Adapters.ClassCard;
import ajlyfe.lectureapp.Adapters.LectureCheckbox;
import ajlyfe.lectureapp.Adapters.LectureSelectCard;
import ajlyfe.lectureapp.Adapters.LectureSelectCardAdapter;
import ajlyfe.lectureapp.Adapters.TeacherClassCard;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

import static ajlyfe.lectureapp.Activity.TeacherClassOverview.NULL_CLASS;

public class FragmentFile extends Fragment {
    private View view;
    private LectureSelectCardAdapter adapter;
    private ArrayList<LectureSelectCard> lectureList = new ArrayList<>();
    private int counter = 0;

    public final String DATA_URL = "http://www.chs.mcvsd.org/sandbox/getLectureData.php?id=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.slide_file, container, false);

        doThings();

        return view;
    }

    public void doThings() {
        lectureList.add(0, new LectureSelectCard("NULL _ HEADER", false, null));
        File parentDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/RecoLecture/");
        File[] filesArray = parentDir.listFiles();

        try {
            for (File thisFile : filesArray) {
                if (thisFile.toString().contains(".mp3")) {
                    getData(thisFile.getName().substring(0, thisFile.getName().length() - 4), filesArray.length);
                }
            }
        } catch (IndexOutOfBoundsException exc) {
            exc.printStackTrace();
        }
    }

    public ArrayList<LectureSelectCard> getAdapterArrayList(){
        try {
            return adapter.getArrayList();
        }
        catch (NullPointerException exc) {
            return new ArrayList<>();
        }
    }

    private ArrayList<LectureCheckbox> parseTemporaryArray(ArrayList<String> tArray) {
        ArrayList<LectureCheckbox> returnMe = new ArrayList<>();

        for (int i = 0; i < tArray.size(); i++) {
            returnMe.add(i, new LectureCheckbox(tArray.get(i)));
        }

        return returnMe;
    }

    private void getData(final String id, final int size) {
        String url = DATA_URL + id;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                counter++;
                showJSON(response, id, size);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response, String id, int size) {
        String lectureName;
        String classCode;
        String teacherName;

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject data = result.getJSONObject(0);

            lectureName = data.getString("lectureName");
            classCode = data.getString("classCode");
            teacherName = data.getString("teacherName");

            if (!lectureName.equals("null")) {
                lectureList.add(new LectureSelectCard(lectureName, false, id));
            } else {
                lectureList.add(new LectureSelectCard(lectureName, true, id));
                Toast.makeText(getContext(), "Error: could not locate lecture(s)", Toast.LENGTH_SHORT).show();
                Log.e(getTag(), "Could not locate a lecture matching code: '" + id + "' with class code: '" +
                                classCode + "' with teacher name: '" + teacherName + "'");
            }

            if (counter == size) {
                RecyclerView recyclerViewFiles = (RecyclerView) view.findViewById(R.id.recyclerViewFileSelect);
                adapter = new LectureSelectCardAdapter(lectureList, getActivity());
                recyclerViewFiles.setAdapter(adapter);
                recyclerViewFiles.setLayoutManager(new LinearLayoutManager(view.getContext()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}