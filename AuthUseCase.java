package app;

import infrastructure.AuthService;

public final class AuthUseCase {

    private final AuthService authService;

    public AuthUseCase(AuthService authService) {
        this.authService = authService;
    }

    public boolean login(String user, String pass) {
        return authService.authenticate(user, pass);
    }

    public boolean isSuspicious(String user) {
        return authService.isSuspicious(user);
    }
}