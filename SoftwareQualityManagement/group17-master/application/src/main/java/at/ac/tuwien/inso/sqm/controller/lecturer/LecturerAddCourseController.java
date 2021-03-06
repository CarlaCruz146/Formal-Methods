package at.ac.tuwien.inso.sqm.controller.lecturer;

import at.ac.tuwien.inso.sqm.dto.AddCourseForm;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.exception.ValidationException;
import at.ac.tuwien.inso.sqm.service.LehrveranstaltungServiceInterface;
import at.ac.tuwien.inso.sqm.service.Nachrichten;
import at.ac.tuwien.inso.sqm.service.SemesterServiceInterface;
import at.ac.tuwien.inso.sqm.service.SubjectIService;
import at.ac.tuwien.inso.sqm.service.TgaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lecturer/addCourse")
public class LecturerAddCourseController {

    @Autowired
    private SubjectIService subjectService;
    @Autowired
    private SemesterServiceInterface semesterService;
    @Autowired
    private LehrveranstaltungServiceInterface courseService;
    @Autowired
    private TgaService tgaService;

    @Autowired
    private Nachrichten messages;

    @ModelAttribute("subject")
    private Subject getSubject(@RequestParam("subjectId") Long subjectId) {
        return subjectService.findOne(subjectId);
    }

    @ModelAttribute("addCourseForm")
    private AddCourseForm getAddCourseForm(
            @RequestParam("subjectId") Long subjectId) {
        Semester semester =
                semesterService.getOrCreateCurrentSemester().toEntity();

        Course course =
                new Course(getSubject(subjectId), semester);

        AddCourseForm form = new AddCourseForm(course);
        form.setInitialTags(tgaService.findAllValid());
        return form;
    }


    @GetMapping
    private String getAddCoursesPage(
            @RequestParam("subjectId") Long subjectId) {
        return "lecturer/addCourse";
    }

    @PostMapping
    private String createCourse(@ModelAttribute AddCourseForm form,
            RedirectAttributes redirectAttributes) {
        try {
            Course course = courseService.saveCourse(form);
            redirectAttributes.addFlashAttribute("flashMessageNotLocalized",
                    messages.msg("lecturer.course.add.success",
                            course.getSubject().getName()));
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute("flashMessageNotLocalized",
                    e.getMessage());
        }


        return "redirect:/lecturer/courses";
    }

}
