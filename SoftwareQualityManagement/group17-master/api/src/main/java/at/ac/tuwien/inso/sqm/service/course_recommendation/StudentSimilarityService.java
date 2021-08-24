package at.ac.tuwien.inso.sqm.service.course_recommendation; //FIXME package
// naming convention?!

import at.ac.tuwien.inso.sqm.entity.StudentEntity;

import java.util.List;

//TODO javadoc
public interface StudentSimilarityService {

    //TODO how is similarity defined? - add javadoc
    List<StudentEntity> getSimilarStudents(StudentEntity student);
}
