package at.ac.tuwien.inso.sqm.controller.grade;


import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.pdf.GradePDFView;
import at.ac.tuwien.inso.sqm.service.GradeIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.util.UriComponentsBuilder;


@Controller
@RequestMapping("/public/grade")
public class PublicGradeController {

    @Autowired
    private GradeIService gradeService;

    @GetMapping
    public ModelAndView generateGradePDF(
            @RequestParam("identifier") String identifier, ModelMap modelMap,
            UriComponentsBuilder uriComponentsBuilder) throws Exception {

        View view = new GradePDFView();
        Grade grade = gradeService.getForValidation(identifier);
        if (grade == null) {
            return new ModelAndView("error/404");
        }
        modelMap.addAttribute("grade", grade);
        modelMap.addAttribute("validationLink",
                uriComponentsBuilder.path("/public/grade")
                        .queryParam("identifier", identifier).build()
                        .toString());
        ModelAndView modelAndView = new ModelAndView(view);
        return modelAndView;
    }


}
