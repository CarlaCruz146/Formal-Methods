package at.ac.tuwien.inso.sqm.controller.student;

import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.service.LehrveranstaltungServiceInterface;
import at.ac.tuwien.inso.sqm.service.Nachrichten;
import at.ac.tuwien.inso.sqm.service.StudentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/student")
public class StudentLehrveranstaltungRegistrierungsController {

    @Autowired
    private LehrveranstaltungServiceInterface lehrveranstaltungService;

    @Autowired
    private StudentServiceInterface studentService;

    @Autowired
    private Nachrichten nachrichten;

    @PostMapping("/anmelden/{lehrveranstaltungsID}")
    public String studentAnmelden(@PathVariable Long lehrveranstaltungsID,
            RedirectAttributes redirectAttributes) {
        Course course = lehrveranstaltungService
                .findeLehrveranstaltung(lehrveranstaltungsID);

        if (lehrveranstaltungService
                .studentZurLehrveranstaltungAnmelden(course)) {
            String lehrveranstaltungsName =
                    course.getSubject().getName();
            String erfolgreicheNachricht = nachrichten
                    .msg("student.meine.lehrveranstaltungen.anmeldung" +
                                    ".erfolgreich",
                            lehrveranstaltungsName);
            redirectAttributes.addFlashAttribute("flashMessageNotLocalized",
                    erfolgreicheNachricht);
            return "redirect:/student/lehrveranstaltungen";
        }

        String fehlgeschlageneNachricht = nachrichten
                .msg("student.meine.lehrveranstaltungen.anmeldung" +
                                ".fehlgeschlagen",
                        course.getSubject().getName());
        redirectAttributes.addFlashAttribute("flashMessageNotLocalized",
                fehlgeschlageneNachricht);
        return "redirect:/student/lehrveranstaltungen";
    }

    @PostMapping("/abmelden")
    public String studentAbmelden(@RequestParam Long course,
            RedirectAttributes redirectAttributes, Principal principal) {
        StudentEntity student =
                studentService.findByUsername(principal.getName());
        Course deregisterFromCourse =
                lehrveranstaltungService
                        .studentVonLehrveranstaltungAbmelden(student, course);

        String erfolgreicheNachricht = nachrichten
                .msg("student.my.courses.unregister.success",
                        deregisterFromCourse.getSubject().getName());
        redirectAttributes.addFlashAttribute("flashMessageNotLocalized",
                erfolgreicheNachricht);
        return "redirect:/student/meineLehrveranstaltungen";
    }
}
