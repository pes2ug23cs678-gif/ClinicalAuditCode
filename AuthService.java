package infrastructure;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
public final class AuthService {
    private final Map<String, String> users = new ConcurrentHashMap<>();
    public AuthService() {
        users.put("admin", "admin123");
        users.put("doctor", "doc123");
    }
    public boolean authenticate(String u, String p) {
        return users.containsKey(u) && users.get(u).equals(p);
    }
    public boolean isSuspicious(String u) {
        return false;
    }
}