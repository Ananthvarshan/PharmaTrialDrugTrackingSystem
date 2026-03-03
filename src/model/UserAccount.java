package model;

public class UserAccount {

    private int id;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private int roleId;
    private int clinicalSiteId;
    private String hospitalName;
    private Integer createdBy;  // Nullable (created_by can be NULL)
    private String status;
    private boolean canRegister;

    // --- Constructors ---
    public UserAccount() {}

    public UserAccount(int id, String username, String fullName, String email, String password,
                       int roleId, int clinicalSiteId, String hospitalName,
                       Integer createdBy, String status, boolean canRegister) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.clinicalSiteId = clinicalSiteId;
        this.hospitalName = hospitalName;
        this.createdBy = createdBy;
        this.status = status;
        this.canRegister = canRegister;
    }

    // --- Getters and Setters ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getClinicalSiteId() {
        return clinicalSiteId;
    }

    public void setClinicalSiteId(int clinicalSiteId) {
        this.clinicalSiteId = clinicalSiteId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCanRegister() {
        return canRegister;
    }

    public void setCanRegister(boolean canRegister) {
        this.canRegister = canRegister;
    }

    // --- toString() for debugging ---
    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                ", clinicalSiteId=" + clinicalSiteId +
                ", hospitalName='" + hospitalName + '\'' +
                ", createdBy=" + createdBy +
                ", status='" + status + '\'' +
                ", canRegister=" + canRegister +
                '}';
    }
}
