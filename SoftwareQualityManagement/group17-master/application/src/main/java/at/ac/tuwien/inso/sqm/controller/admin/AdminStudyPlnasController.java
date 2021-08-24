package at.ac.tuwien.inso.sqm.controller.admin;

import at.ac.tuwien.inso.sqm.controller.admin.forms.CreateStudyPlanForm;
import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.StudyPlanRegistration;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.SubjectForStudyPlanEntity;
import at.ac.tuwien.inso.sqm.exception.BusinessObjectNotFoundException;
import at.ac.tuwien.inso.sqm.exception.ValidationException;
import at.ac.tuwien.inso.sqm.service.Nachrichten;
import at.ac.tuwien.inso.sqm.service.StudentServiceInterface;
import at.ac.tuwien.inso.sqm.service.StudyPlanService;
import at.ac.tuwien.inso.sqm.service.SubjectIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/studyplans")
public class AdminStudyPlnasController {

    @Autowired
    private StudyPlanService studyPlanService;

    @Autowired
    private SubjectIService subjectService;

    @Autowired
    private StudentServiceInterface studentService;

    @Autowired
    private Nachrichten messages;

    @GetMapping
    public String getStudyplansView() {
        return "admin/studyplans";
    }

    @ModelAttribute("studyPlans")
    private Iterable<StudyPlanEntity> getStudyPlans() {
        return studyPlanService.findAll();
    }

    @GetMapping(params = "id")
    private String getStudyplanDetailsView(@RequestParam(value = "id") Long id,
            Model model) {
        StudyPlanEntity studyPlan = studyPlanService.findOne(id);

        List<SubjectForStudyPlanEntity> subjectsForStudyPlan =
                studyPlanService.getSubjectsForStudyPlan(id);
        List<SubjectForStudyPlanEntity> mandatory =
                subjectsForStudyPlan.stream()
                        .filter(SubjectForStudyPlanEntity::getMandatory)
                        .collect(Collectors.toList());
        List<SubjectForStudyPlanEntity> optional =
                subjectsForStudyPlan.stream().filter(s -> !s.getMandatory())
                        .collect(Collectors.toList());
        double addedMandatoryEcts = mandatory.stream()
                .mapToDouble(s -> s.getSubject().getEcts().doubleValue()).sum();
        double addedOptionalEcts = optional.stream()
                .mapToDouble(s -> s.getSubject().getEcts().doubleValue()).sum();

        model.addAttribute("studyPlan", studyPlan);
        model.addAttribute("mandatory", mandatory);
        model.addAttribute("addedMandatoryEcts",
                BigDecimal.valueOf(addedMandatoryEcts).setScale(2));
        model.addAttribute("optional", optional);
        model.addAttribute("addedOptionalEcts",
                new BigDecimal(addedOptionalEcts).setScale(2));

        return "admin/studyplan-details";
    }

    @PostMapping(value = "/disable", params = {"id"})
    public String disableStudyPlan(@RequestParam(value = "id") Long id,
            RedirectAttributes redirectAttributes) {
        StudyPlanEntity studyPlan = studyPlanService.disableStudyPlan(id);
        redirectAttributes.addFlashAttribute("flashMessageNotLocalized",
                messages.msg("admin.studyplans.disable.success",
                        studyPlan.getName()));
        return "redirect:/admin/studyplans";
    }

    /**
     * Create a study plan.
     * @param form the form
     * @param bindingResult the binding result
     * @param model the model
     * @param redirectAttributes the redirect attributes
     * @return the page to redirect
     */
    @PostMapping("/create")
    public String createStudyPlan(@Valid CreateStudyPlanForm form,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("flashMessage",
                    "admin.studyplans.create.error");
            return "redirect:/admin/studyplans";
        }
        try {
            StudyPlanEntity studyPlan =
                    studyPlanService.create(form.toStudyPlan());
            model.addAttribute("studyPlan", studyPlan);
            model.addAttribute("addedMandatoryEcts", 0.0);
            model.addAttribute("addedOptionalEcts", 0.0);
            model.addAttribute("flashMessageNotLocalized",
                    messages.msg("admin.studyplans.create.success",
                            studyPlan.getName()));
        } catch (DataAccessException e) {
            redirectAttributes.addFlashAttribute("flashMessage",
                    "admin.studyplans.create.error");
            return "redirect:/admin/studyplans";
        }


        return "admin/studyplan-details";
    }

    @PostMapping(value = "/addSubject",
            params = {"subjectId", "studyPlanId", "semester", "mandatory"})
    public String addSubjectToStudyPlan(RedirectAttributes redirectAttributes,
            @RequestParam Long subjectId, @RequestParam Long studyPlanId,
            @RequestParam Integer semester, @RequestParam Boolean mandatory) {

        StudyPlanEntity studyPlan = new StudyPlanEntity();
        studyPlan.setId(studyPlanId);
        try {
            Subject subject = subjectService.findOne(subjectId);
            studyPlanService.addSubjectToStudyPlan(
                    new SubjectForStudyPlanEntity(subject, studyPlan, mandatory,
                            semester));
            String successMsg =
                    messages.msg("admin.studyplans.details.subject.add.success",
                            subject.getName());
            redirectAttributes
                    .addFlashAttribute("flashMessageNotLocalized", successMsg);
        } catch (ValidationException | BusinessObjectNotFoundException e) {
            redirectAttributes.addFlashAttribute("flashMessageNotLocalized",
                    messages.msg("error.subject.missing"));
        }

        return "redirect:/admin/studyplans/?id=" + studyPlanId;
    }

    @PostMapping(value = "/remove", params = {"studyPlanId", "subjectId"})
    public String removeSubjectFromStudyPlan(
            RedirectAttributes redirectAttributes,
            @RequestParam Long studyPlanId, @RequestParam Long subjectId) {
        StudyPlanEntity studyPlan = studyPlanService.findOne(studyPlanId);
        Subject subject = subjectService.findOne(subjectId);
        studyPlanService.removeSubjectFromStudyPlan(studyPlan, subject);
        String successMsg =
                messages.msg("admin.studyplans.details.subject.remove.success",
                        subject.getName());
        redirectAttributes
                .addFlashAttribute("flashMessageNotLocalized", successMsg);
        return "redirect:/admin/studyplans/?id=" + studyPlanId;
    }


    @GetMapping(value = "/json/availableSubjects", params = {"id", "query"})
    @ResponseBody
    public List<Subject> getAvailableSubjects(@RequestParam Long id,
                                              @RequestParam String query) {
        return studyPlanService.getAvailableSubjectsForStudyPlan(id, query);
    }


    @PostMapping(value = "/studentAnmelden", params = "studentId")
    public String studentAnmelden(RedirectAttributes redirectAttributes,
            @RequestParam Long studentId, @RequestParam Long studyPlanId) {
        StudyPlanEntity studyPlan = studyPlanService.findOne(studyPlanId);
        StudentEntity student = studentService.findOne(studentId);
        studentService.registerStudentToStudyPlan(student, studyPlan);
        redirectAttributes.addFlashAttribute("flashMessageNotLocalized",
                messages.msg("admin.student.register.success",
                        studyPlan.getName()));

        return "redirect:/admin/users/" + student.getId();
    }

    /**
     * Add a student.
     * @param studentToAddId the id of the student to add
     * @param model the model
     * @return the redirect page
     */
    @GetMapping(value = "/studentAnmelden", params = "studentToAddId")
    public String registerStudentView(@RequestParam Long studentToAddId,
            Model model) {
        StudentEntity student = studentService.findOne(studentToAddId);

        model.addAttribute("user", student);
        model.addAttribute("test", "testString");

        List<StudyPlanEntity> toShow = new ArrayList<StudyPlanEntity>();
        for (StudyPlanEntity sp : studyPlanService.findAll()) {
            boolean error = false;
            for (StudyPlanRegistration studentSp : student.getStudyplans()) {
                if (sp.equals(studentSp.getStudyplan())) {
                    error = true;
                }
            }
            if (!error && sp.isEnabled()) {
                toShow.add(sp);
            }
        }

        model.addAttribute("studyPlans", toShow);

        return "admin/add-study-plan-to-student";
    }


}
