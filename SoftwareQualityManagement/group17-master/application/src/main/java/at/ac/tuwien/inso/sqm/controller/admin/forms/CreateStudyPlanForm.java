package at.ac.tuwien.inso.sqm.controller.admin.forms;


import at.ac.tuwien.inso.sqm.entity.EctsDistributionEntity;
import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateStudyPlanForm {

    @NotEmpty
    private String name;

    @Min(1)
    @NotNull
    private BigDecimal mandatory;

    @Min(1)
    @NotNull
    private BigDecimal optional;

    @Min(1)
    @NotNull
    private BigDecimal freeChoice;

    protected CreateStudyPlanForm() {
    }

    public CreateStudyPlanForm(String name, BigDecimal mandatory,
            BigDecimal optional, BigDecimal freeChoice) {
        this.name = name;
        this.mandatory = mandatory;
        this.optional = optional;
        this.freeChoice = freeChoice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMandatory() {
        return mandatory;
    }

    public void setMandatory(BigDecimal mandatory) {
        this.mandatory = mandatory;
    }

    public BigDecimal getOptional() {
        return optional;
    }

    public void setOptional(BigDecimal optional) {
        this.optional = optional;
    }

    public BigDecimal getFreeChoice() {
        return freeChoice;
    }

    public void setFreeChoice(BigDecimal freeChoice) {
        this.freeChoice = freeChoice;
    }

    public StudyPlanEntity toStudyPlan() {
        return new StudyPlanEntity(name,
                new EctsDistributionEntity(mandatory, optional, freeChoice));
    }
}
