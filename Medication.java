package domain;
public final class Medication {
    private final String name;

    public Medication(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid medication");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}