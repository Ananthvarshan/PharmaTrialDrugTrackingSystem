package model;

import java.sql.Date;

public class ClinicalTrial {

    private int id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int siteId;         // Foreign key to clinical_site
    private String currentPhase;
    private String phaseStatus; // Default: 'Ongoing'

    // --- Constructors ---
    public ClinicalTrial() {}

    public ClinicalTrial(int id, String title, String description, Date startDate, Date endDate,
                         int siteId, String currentPhase, String phaseStatus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.siteId = siteId;
        this.currentPhase = currentPhase;
        this.phaseStatus = phaseStatus;
    }

    // --- Getters and Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public String getPhaseStatus() {
        return phaseStatus;
    }

    public void setPhaseStatus(String phaseStatus) {
        this.phaseStatus = phaseStatus;
    }

    // --- toString() for debugging ---
    @Override
    public String toString() {
        return "ClinicalTrial{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", siteId=" + siteId +
                ", currentPhase='" + currentPhase + '\'' +
                ", phaseStatus='" + phaseStatus + '\'' +
                '}';
    }
}
