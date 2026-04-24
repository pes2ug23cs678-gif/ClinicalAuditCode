package domain;
public final class Doctor {
    private final String name;
    private final String specialization;
    public Doctor(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }
    public boolean isValid() {
        return name != null && !name.isEmpty();
    }
    public String getName() {
        return name;
    }
}