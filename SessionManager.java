package infrastructure;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
public final class SessionManager {
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();
    public void add(Session s) {
        sessions.put(s.getToken(), s);
    }
    public boolean isValid(String token, String username) {
        Session s = sessions.get(token);
        if (s == null) return false;
        if (s.isExpired()) {
            sessions.remove(token);
            return false;
        }
        return s.getUsername().equals(username);
    }
}