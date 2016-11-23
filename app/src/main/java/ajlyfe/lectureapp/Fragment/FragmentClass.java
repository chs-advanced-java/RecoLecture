package ajlyfe.lectureapp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;

import ajlyfe.lectureapp.Adapters.ClassSelectCard;
import ajlyfe.lectureapp.Adapters.ClassSelectCardAdapter;
import ajlyfe.lectureapp.Adapters.StudentSelectCard;
import ajlyfe.lectureapp.Adapters.StudentSelectCardAdapter;
import ajlyfe.lectureapp.Adapters.TeacherClassCard;
import ajlyfe.lectureapp.R;
import ajlyfe.lectureapp.Utils;

public class FragmentClass extends Fragment {
    private View view;
    private ClassSelectCardAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = Utils.setCustomAdapterTheme(getActivity(), inflater, R.layout.slide_class, container, false);

        method();

        return view;
    }
    public void method() {

        /**pull classes here, use for loop to convert names to classList**/
        ArrayList<TeacherClassCard> temp = Utils.getClassList(getActivity());
        ArrayList<ClassSelectCard> classes = new ArrayList<>();
        try{
            for (int x = 0; x < temp.size(); x++){
                classes.add(new ClassSelectCard(temp.get(x).getName()));
            }
        }
        catch (IndexOutOfBoundsException exc){

        }

        RecyclerView recyclerViewStudents = (RecyclerView) view.findViewById(R.id.recyclerViewStudentSelect);
        adapter = new ClassSelectCardAdapter(classes, view.getContext());
        recyclerViewStudents.setAdapter(adapter);
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}