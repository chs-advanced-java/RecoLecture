package ajlyfe.lectureapp.Activity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class CodeGenerator {

    ArrayList<String> classCodes = new ArrayList<>();
    String character[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public void generate(){
        String code = "";
        boolean generating = true;
        Random r = new Random();
        boolean exc = false;
        while (generating){
            String char1 = character[r.nextInt(61)];
            String char2 = character[r.nextInt(61)];
            String char3 = character[r.nextInt(61)];
            String char4 = character[r.nextInt(61)];
            String char5 = character[r.nextInt(61)];
            String char6 = character[r.nextInt(61)];
            String char7 = character[r.nextInt(61)];
            String char8 = character[r.nextInt(61)];
            code = char1 + char2 + char3 + char4 + char5 + char6 + char7 + char8;
            try{
                classCodes.get(0);
            }
            catch (IndexOutOfBoundsException e){
                exc = true;
            }
            if (exc){
                classCodes.add(code);
                generating = false;
            }
            else {
                boolean check = true;
                for (int x = 0; x < classCodes.size(); x++){
                    if (classCodes.get(x).equals(code)){
                        check = false;
                    }
                }
                if (check){
                    classCodes.add(code);
                    generating = false;
                }
            }
        }
    }

    public ArrayList<String> getClassCodes(){
        return classCodes;
    }

    public void setClassCodes(ArrayList<String> codes){
        classCodes = codes;
    }

}