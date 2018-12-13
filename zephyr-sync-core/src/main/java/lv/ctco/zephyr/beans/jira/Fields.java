package lv.ctco.zephyr.beans.jira;

import lv.ctco.zephyr.beans.Metafield;
import lv.ctco.zephyr.enums.ConfigProperty;
import lv.ctco.zephyr.util.ConfigBasedJsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fields {
    private String summary;
    private String description;
    private String testCaseUniqueId;
    private Metafield project;
    private Metafield assignee;
    private Metafield issuetype;
    private Metafield priority;
    private Metafield severity;
    private List<Metafield> versions;
    private String[] labels;

//    private Metafield team;
    private List<Metafield> components;

    public Fields() {
//        this.team = new Metafield();
//        team.setName("Eclair");

        this.components = new ArrayList<>();
        Metafield component = new Metafield();
        component.setName("Marketplace Test");
        components.add(component);
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ConfigBasedJsonProperty(ConfigProperty.TEST_CASE_UNIQUE_ID)
    public String getTestCaseUniqueId() {
        return testCaseUniqueId;
    }

    @ConfigBasedJsonProperty(ConfigProperty.TEST_CASE_UNIQUE_ID)
    public void setTestCaseUniqueId(String testCaseUniqueId) {
        this.testCaseUniqueId = testCaseUniqueId;
    }

    public Metafield getProject() {
        return project;
    }

    public void setProject(Metafield project) {
        this.project = project;
    }

    public Metafield getAssignee() {
        return assignee;
    }

    public void setAssignee(Metafield assignee) {
        this.assignee = assignee;
    }

    public Metafield getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(Metafield issuetype) {
        this.issuetype = issuetype;
    }

    public List<Metafield> getVersions() {
        return versions;
    }

    public void setVersions(List<Metafield> versions) {
        this.versions = versions;
    }

    public Metafield getPriority() {
        return priority;
    }

    public void setPriority(Metafield priority) {
        this.priority = priority;
    }

    @ConfigBasedJsonProperty(ConfigProperty.SEVERITY)
    public Metafield getSeverity() {
        return severity;
    }

    @ConfigBasedJsonProperty(ConfigProperty.SEVERITY)
    public void setSeverity(Metafield severity) {
        this.severity = severity;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

//    public Metafield getTeam() {
//        return team;
//    }
//
//    public void setTeam(Metafield team) {
//        this.team = team;
//    }

    public List<Metafield> getComponents() {
        return components;
    }

    public void setComponents(List<Metafield> components) {
        this.components = components;
    }

    @Override
    public String toString() {
        return "Fields{" +
                "summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", testCaseUniqueId='" + testCaseUniqueId + '\'' +
                ", project=" + project +
                ", assignee=" + assignee +
                ", issuetype=" + issuetype +
                ", priority=" + priority +
                ", severity=" + severity +
                ", versions=" + versions +
                ", labels=" + Arrays.toString(labels) +
                ", components=" + components +
                '}';
    }
}
