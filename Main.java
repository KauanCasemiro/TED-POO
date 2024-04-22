import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface RequiresAuthentication {}

interface AuthenticationStrategy {
    boolean authenticate(String username, String password);
}

class BasicAuthenticationStrategy implements AuthenticationStrategy {
    private final Map<String, String> users = new ConcurrentHashMap<>();

    public BasicAuthenticationStrategy() {
        users.put("user1", "password1");
        users.put("user2", "password2");
    }

    @Override
    public boolean authenticate(String username, String password) {
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}

class SecureService {
    private final AuthenticationStrategy authenticationStrategy;

    public SecureService(AuthenticationStrategy authenticationStrategy) {
        this.authenticationStrategy = authenticationStrategy;
    }

    public boolean authenticate(String username, String password) {
        return authenticationStrategy.authenticate(username, password);
    }

    @RequiresAuthentication
    public void secureMethod() {
        System.out.println("Método seguro executado com sucesso!");
    }
}

class AuthenticationHandler {
    private final SecureService secureService;
    private final AuthenticationStrategy authenticationStrategy;

    public AuthenticationHandler(SecureService secureService, AuthenticationStrategy authenticationStrategy) {
        this.secureService = secureService;
        this.authenticationStrategy = authenticationStrategy;
    }

    public void executeSecureMethod(String username, String password) {
        if (authenticationStrategy.authenticate(username, password)) {
            Method[] methods = SecureService.class.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequiresAuthentication.class)) {
                    try {
                        method.invoke(secureService);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.out.println("Erro ao invocar o método seguro: " + e.getMessage());
                    }
                }
            }
        } else {
            System.out.println("Falha na autenticação. Acesso negado.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SecureService secureService = new SecureService(new BasicAuthenticationStrategy());

        AuthenticationHandler authenticationHandler = new AuthenticationHandler(secureService, new BasicAuthenticationStrategy());

        authenticationHandler.executeSecureMethod("user1", "password1");

        authenticationHandler.executeSecureMethod("user3", "password3");
    }
}
