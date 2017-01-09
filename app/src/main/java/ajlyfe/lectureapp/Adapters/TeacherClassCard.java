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

import android.support.annotation.Nullable;

import ajlyfe.lectureapp.Activity.TeacherClassOverview;

public class TeacherClassCard {
    private String name;
    private String description;
    private String code;

    /** Class code can be null if it is a dummy class, otherwise we have a problem.**/
    public TeacherClassCard(String name, String description, @Nullable String code) {
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public TeacherClassCard setClassDescription(String description) {
        this.description = description;
        return this;
    }

    public TeacherClassCard setName(String cName) {
        this.name = cName;
        return this;
    }

    public boolean isDummy() {
        return description.equals(TeacherClassOverview.AUTO_DESCRIPTION);
    }
}
