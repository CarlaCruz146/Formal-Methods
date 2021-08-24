package at.ac.tuwien.inso.sqm.dto;

import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;

public class StudyPlanRegistrationDto {

    private StudyPlanEntity stduyplan;

    private SemesterDto registeredSince;

    public StudyPlanEntity getStudyplan() {
        return stduyplan;
    }

    public void setStudyplan(StudyPlanEntity studyplan) {
        this.stduyplan = studyplan;
    }

    public SemesterDto getRegisteredSince() {
        return registeredSince;
    }

    public void setRegisteredSince(SemesterDto registeredSince) {
        this.registeredSince = registeredSince;
    }

}
