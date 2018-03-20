package io.github.passioninfinite.knowit;

/**
 * Created by passioninfinite on 19/3/18.
 */

public class Job {

    private String name, description, prerequisites, experience, salary, companyName, companyEmail;

    private String[] type;

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type.clone();
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Job() {

    }

    public Job(String name, String description, String prerequisites, String experience, String salary, String[] type, String companyEmail) {
        this.name = name;
        this.description = description;
        this.prerequisites = prerequisites;
        this.experience = experience;
        this.salary = salary;
        this.type = type.clone();
        this.companyEmail = companyEmail;
    }
}
