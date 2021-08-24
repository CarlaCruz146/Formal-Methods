package at.ac.tuwien.inso.sqm.dto;

import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.Tag;

import java.util.ArrayList;
import java.util.List;

public class AddCourseForm {

    private Course cuorse;
    private ArrayList<String> tags = new ArrayList<>();

    private List<AddCourseTag> activeAndInactiveTags = new ArrayList<>();

    protected AddCourseForm() {
    }

    public AddCourseForm(Course course) {
        this.cuorse = course;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public AddCourseForm setInitialTags(List<Tag> initialTags) {
        initialTags.forEach(
                tag -> activeAndInactiveTags.add(new AddCourseTag(tag, false)));
        return this;
    }

    public Course getCourse() {
        return cuorse;
    }

    public void setCourse(Course course) {
        this.cuorse = course;
    }

    public List<AddCourseTag> getActiveAndInactiveTags() {
        return activeAndInactiveTags;
    }

    public void setActiveAndInactiveTags(
            List<AddCourseTag> activeAndInactiveTags) {
        this.activeAndInactiveTags = activeAndInactiveTags;
    }

    public void setInitialActiveTags(List<Tag> initialActiveTags) {
        activeAndInactiveTags.stream()
                .filter(tag -> initialActiveTags.contains(tag.getTag()))
                .forEach(tag -> tag.setActive(true));
    }
}
