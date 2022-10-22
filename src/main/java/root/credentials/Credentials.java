package root.credentials;

public final class Credentials {
    public final String partnerUserID;
    public final String partnerUserSecret;
    public final String employeeEmail;

    public Credentials(String partnerUserID, String partnerUserSecret, String employeeEmail) {
        this.partnerUserID = partnerUserID;
        this.partnerUserSecret = partnerUserSecret;
        this.employeeEmail = employeeEmail;
    }
}
