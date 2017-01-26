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

package ajlyfe.lectureapp;

import java.util.Random;

//oops

public class CodeGenerator {

    static String character[] = {"2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "K", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static String generate() {
        Random r = new Random();
        String char1 = character[r.nextInt(character.length - 1)];
        String char2 = character[r.nextInt(character.length - 1)];
        String char3 = character[r.nextInt(character.length - 1)];
        String char4 = character[r.nextInt(character.length - 1)];
        String char5 = character[r.nextInt(character.length - 1)];
        String char6 = character[r.nextInt(character.length - 1)];
        String char7 = character[r.nextInt(character.length - 1)];
        String char8 = character[r.nextInt(character.length - 1)];
        return char1 + char2 + char3 + char4 + char5 + char6 + char7 + char8;
    }
}