package model;

public class ClinicalSite {

    private int id;
    private String name;
    private String location;
    private String imageUrl;

    // Capacity fields
    private int maxDoctors;
    private int maxPatients;

    // Current counts
    private int currentDoctors;
    private int currentPatients;

    private int totalWorkers;

    // --- Constructors ---
    public ClinicalSite() {}

    public ClinicalSite(int id, String name, String location, String imageUrl,
                        int maxDoctors, int maxPatients,
                        int currentDoctors, int currentPatients, int totalWorkers) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.maxDoctors = maxDoctors;
        this.maxPatients = maxPatients;
        this.currentDoctors = currentDoctors;
        this.currentPatients = currentPatients;
        this.totalWorkers = totalWorkers;
    }

    // --- Getters and Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getMaxDoctors() {
        return maxDoctors;
    }

    public void setMaxDoctors(int maxDoctors) {
        this.maxDoctors = maxDoctors;
    }

    public int getMaxPatients() {
        return maxPatients;
    }

    public void setMaxPatients(int maxPatients) {
        this.maxPatients = maxPatients;
    }

    public int getCurrentDoctors() {
        return currentDoctors;
    }

    public void setCurrentDoctors(int currentDoctors) {
        this.currentDoctors = currentDoctors;
    }

    public int getCurrentPatients() {
        return currentPatients;
    }

    public void setCurrentPatients(int currentPatients) {
        this.currentPatients = currentPatients;
    }

    public int getTotalWorkers() {
        return totalWorkers;
    }

    public void setTotalWorkers(int totalWorkers) {
        this.totalWorkers = totalWorkers;
    }

    // --- toString() for easy debugging ---
    @Override
    public String toString() {
        return "ClinicalSite{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", maxDoctors=" + maxDoctors +
                ", maxPatients=" + maxPatients +
                ", currentDoctors=" + currentDoctors +
                ", currentPatients=" + currentPatients +
                ", totalWorkers=" + totalWorkers +
                '}';
    }
}
