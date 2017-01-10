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

public class ClassCard {
    private String className;
    private String classDescription;
    private String classCode;

    /** Class code can be null if it is a dummy class, otherwise we have a problem.**/
    public ClassCard(String className, String classDescription, @Nullable String classCode) {
        this.className = className;
        this.classDescription = classDescription;
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public String getClassCode() {
        return classCode;
    }

    public ClassCard setClassName(String cName) {
        this.className = cName;
        return this;
    }

    public ClassCard setClassDescription(String classDescription) {
        this.classDescription = classDescription;
        return this;
    }

    public ClassCard setClassCode(String classCode) {
        this.classCode = classCode;
        return this;
    }
}
