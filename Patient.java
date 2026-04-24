package domain;
public final class Patient {
    private final String name;
    private final String ssn;
    private final String ailments;
    public Patient(String name, String ssn, String ailments) {
        this.name = name;
        this.ssn = ssn;
        this.ailments = ailments;
    }
    public boolean isValid() {
        return name.matches("^[A-Za-z ]{2,50}$") && ssn.matches("XXXX-XXXX-\\d{4}");
    }
    public String getAilments() {
        return ailments;
    }
    public String getName() {
        return name;
    }
}