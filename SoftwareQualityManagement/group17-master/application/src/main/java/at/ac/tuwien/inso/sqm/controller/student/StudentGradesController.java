package at.ac.tuwien.inso.sqm.controller.student;

import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.service.GradeIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student/grades")
public class StudentGradesController {

    @Autowired
    private GradeIService gradeService;

    @ModelAttribute("grades")
    private List<Grade> getGrades() {
        return gradeService.getGradesForLoggedInStudent();
    }

    @GetMapping
    private String getPage() {
        return "student/grades";
    }

}
