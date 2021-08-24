package at.ac.tuwien.inso.sqm.controller.student;

import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.service.StudentServiceInterface;
import at.ac.tuwien.inso.sqm.service.study_progress.StudyProgressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/student/meineLehrveranstaltungen")
public class StudentMyCoursesController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(StudentMyCoursesController.class);

    @Autowired
    private StudyProgressService studyProgressService;

    @Autowired
    private StudentServiceInterface studentService;

    @GetMapping
    public String myCourses(Model model, Principal principal) {
        LOGGER.debug("Showing study progress for student with username '{}'",
                principal.getName());

        StudentEntity student =
                studentService.findByUsername(principal.getName());

        model.addAttribute("studyProgress",
                studyProgressService.studyProgressFor(student));

        return "student/my-courses";
    }
}
