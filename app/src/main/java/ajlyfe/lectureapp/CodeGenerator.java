package ajlyfe.lectureapp;

import java.util.Random;

public class CodeGenerator {

    String character[] = {"2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "K", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public String generate() {
        String code = "";
        Random r = new Random();
        String char1 = character[r.nextInt(character.length - 1)];
        String char2 = character[r.nextInt(character.length - 1)];
        String char3 = character[r.nextInt(character.length - 1)];
        String char4 = character[r.nextInt(character.length - 1)];
        String char5 = character[r.nextInt(character.length - 1)];
        String char6 = character[r.nextInt(character.length - 1)];
        String char7 = character[r.nextInt(character.length - 1)];
        String char8 = character[r.nextInt(character.length - 1)];
        code = char1 + char2 + char3 + char4 + char5 + char6 + char7 + char8;
        return code;
    }
}