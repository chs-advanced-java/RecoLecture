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

public class LectureSelectCard {
    private String fileName;
    private boolean checked;
    private String id;
    private boolean broken;

    public LectureSelectCard(String fName, boolean broken, String id) {
        fileName = fName;
        checked = false;
        this.broken = broken;
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }
  
    public String getFileName() {
        return fileName;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean c) {
        checked = c;
    }

    public LectureSelectCard setFileName(String fName) {
        this.fileName = fName;
        return this;
    }
}
