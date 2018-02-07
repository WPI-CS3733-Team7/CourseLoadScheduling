package org.dselent.scheduling.server.returnobject;

import org.dselent.scheduling.server.model.Course;
import org.dselent.scheduling.server.model.Instructor;

import java.util.List;

public class ClickSchedulerTabReturnObject {
    List<Instructor> instructorList;
    List<Course> courseList;

    public ClickSchedulerTabReturnObject(List<Instructor> instructorList, List<Course> courseList) {
        this.instructorList = instructorList;
        this.courseList = courseList;
    }
}
