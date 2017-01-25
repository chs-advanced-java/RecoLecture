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

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class LectureCard implements Parcelable{
    private String lectureName;
    private String teacherName;
    private File lectureFile;

    public LectureCard(String lectureName, String teacherName, File lectureFile) {
        this.lectureName = lectureName;
        this.teacherName = teacherName;
        this.lectureFile = lectureFile;
    }

    public String getLectureName() {
        return lectureName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public File getLectureFile() {
        return lectureFile;
    }

    public LectureCard setLectureName(String lName) {
        this.lectureName = lName;
        return this;
    }

    public LectureCard setTeacherName(String tName) {
        this.teacherName = tName;
        return this;
    }

    public LectureCard setLectureFile(File lectureFile) {
        this.lectureFile = lectureFile;
        return this;
    }

    private LectureCard(Parcel in) {
        lectureName = in.readString();
        teacherName = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        // Again this order must match the Question(Parcel) constructor
        out.writeString(lectureName);
        out.writeString(teacherName);
        // Again continue doing this for the rest of your member data
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Just cut and paste this for now
    public static final Parcelable.Creator<LectureCard> CREATOR = new Parcelable.Creator<LectureCard>() {
        public LectureCard createFromParcel(Parcel in) {
            return new LectureCard(in);
        }

        public LectureCard[] newArray(int size) {
            return new LectureCard[size];
        }
    };
}