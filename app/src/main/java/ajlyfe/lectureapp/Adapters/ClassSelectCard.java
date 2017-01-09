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

package ajlyfe.lectureapp.Adapters;

import java.util.ArrayList;

public class ClassSelectCard {
    private String className;
    private boolean checked;


    public ClassSelectCard(String cName) {
        className = cName;
        checked = false;
    }

    public String getClassName() {
        return className;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean c) {
        checked = c;
    }

    public ClassSelectCard setClassName(String cName) {
        this.className = cName;
        return this;
    }


    public static ArrayList<ClassSelectCard> createList(int numClasses) {
        ArrayList<ClassSelectCard> classList = new ArrayList<>();

        //TODO: Pull from the database here::::
        for (int i = 1; i <= numClasses; i++) {
            classList.add(new ClassSelectCard("Class Name"));
        }

        return classList;
    }
}

