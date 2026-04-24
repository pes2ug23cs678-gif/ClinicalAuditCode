package infrastructure;
import java.util.UUID;

public final class Session {

    private final String username;
    private final String token;
    private final long createdAt;
    private final boolean decoy;

    public Session(String username, boolean decoy) {
        this.username = username;
        this.decoy = decoy;
        this.token = UUID.randomUUID().toString();
        this.createdAt = System.currentTimeMillis();
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - createdAt > 15 * 60 * 1000;
    }

    public boolean isDecoy() {
        return decoy;
    }
}