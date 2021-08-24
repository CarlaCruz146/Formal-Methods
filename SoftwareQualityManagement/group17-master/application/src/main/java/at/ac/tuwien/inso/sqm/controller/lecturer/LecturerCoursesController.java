package at.ac.tuwien.inso.sqm.controller.lecturer;

import at.ac.tuwien.inso.sqm.controller.Constants;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.Tag;
import at.ac.tuwien.inso.sqm.service.LecturerService;
import at.ac.tuwien.inso.sqm.service.LehrveranstaltungServiceInterface;
import at.ac.tuwien.inso.sqm.service.TgaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/lecturer/courses")
public class LecturerCoursesController {

    @Autowired
    private LehrveranstaltungServiceInterface courseService;
    @Autowired
    private LecturerService lecturerService;
    @Autowired
    private TgaService tgaService;


    @ModelAttribute("allCourses")
    private List<Course> getAllCourses() {
        LecturerEntity lecturer = lecturerService.getLoggedInLecturer();
        return courseService.findCoursesForCurrentSemesterForLecturer(lecturer);
    }

    @ModelAttribute("allCoursesForAllLecturers")
    private List<Course> getAllCoursesForAllLecturers() {
        return courseService.findAllCoursesForCurrentSemester();
    }

    @ModelAttribute("allCoursesForAllLecturersPagable")
    private Page<Course> getAllCoursesPageable(
            @RequestParam(value = "search", defaultValue = "") String search,
            @PageableDefault Pageable pageable) {
        if (pageable.getPageSize() > Constants.MAX_PAGE_SIZE) {
            pageable = new PageRequest(pageable.getPageNumber(),
                    Constants.MAX_PAGE_SIZE);
        }

        return courseService
                .findCourseForCurrentSemesterWithName(search, pageable);
    }

    @GetMapping
    public String courses() {
        return "lecturer/courses";
    }

    @GetMapping("json/tags")
    @ResponseBody
    private List<String> getTagsJson() {
        return tgaService.findAll().stream().map(Tag::getName)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "json/tags", params = "courseId")
    @ResponseBody
    private List<String> getTagsForCourseJson(
            @RequestParam("courseId") Long courseId) {
        Course course =
                courseService.findeLehrveranstaltung(courseId);
        return course.getTags().stream().map(Tag::getName)
                .collect(Collectors.toList());
    }

    @GetMapping("all")
    public String allCourses() {
        return "lecturer/allcourses";
    }
}
