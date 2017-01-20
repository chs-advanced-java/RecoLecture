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

public class StudentSelectCard {
    private String studentName;
    private boolean checked;

    public StudentSelectCard(String sName) {
        studentName = sName;
        checked = false;
    }

    public String getClassName() {
        return studentName;
    }

    public StudentSelectCard setClassName(String sName) {
        this.studentName = sName;
        return this;
    }

    public void setChecked(boolean c){
        checked = c;
    }

    public boolean getChecked(){
        return checked;
    }

}

