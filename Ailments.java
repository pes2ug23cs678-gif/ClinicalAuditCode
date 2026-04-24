package domain;
public final class Ailments {
    private final String name;
    public Ailments(String name) {
        if (!name.matches("^[A-Za-z ]+$"))
            throw new IllegalArgumentException("Invalid ailment");
        this.name = name;
    }
    public String getName() { return name; }
}