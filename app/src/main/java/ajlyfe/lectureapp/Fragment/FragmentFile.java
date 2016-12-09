package ajlyfe.lectureapp.Fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;

import ajlyfe.lectureapp.Adapters.LectureCheckbox;
import ajlyfe.lectureapp.Adapters.LectureSelectCard;
import ajlyfe.lectureapp.Adapters.LectureSelectCardAdapter;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class FragmentFile extends Fragment {
    private View view;
    private LectureSelectCardAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.slide_file, container, false);

        doThings();

        return view;
    }
    public void doThings() {
        File parentDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/RecoLecture/");
        File[] filesArray = parentDir.listFiles();

        ArrayList<LectureSelectCard> fileList = new ArrayList<>();

        if (fileList.size() == 0) { // Catch null pointer
            fileList.add(0, new LectureSelectCard("NULL"));
        }

        try {
            for (File thisFile : filesArray) {
                fileList.add(new LectureSelectCard(thisFile.getName()));
            }
        } catch (IndexOutOfBoundsException exc) {
            exc.printStackTrace();
        }

        RecyclerView recyclerViewFiles = (RecyclerView) view.findViewById(R.id.recyclerViewFileSelect);
        adapter = new LectureSelectCardAdapter(fileList, view.getContext());
        recyclerViewFiles.setAdapter(adapter);
        recyclerViewFiles.setLayoutManager(new LinearLayoutManager(view.getContext()));
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
}