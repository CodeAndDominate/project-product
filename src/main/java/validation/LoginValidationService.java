package validation;

import exception.UserTooManyTriesException;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginValidationService {
    private Map<String, LoginTry> loginTryMap = new ConcurrentHashMap<>();

    public void checkIfLoginIsAllowed(HttpServletRequest request) throws UserTooManyTriesException {
        String requestedSessionId = request.getRequestedSessionId();
        LoginTry newLoginTry = new LoginTry(requestedSessionId, 0, LocalDateTime.now());
        LoginTry loginTry = loginTryMap.getOrDefault(requestedSessionId, newLoginTry);
        if (LocalDateTime.now().isAfter(loginTry.getLastTry().plusMinutes(1))) {
            loginTry.setCount(0);
            loginTry.setLastTry(LocalDateTime.now());
        }
        if (loginTry.getCount() >= 3) {
            throw new UserTooManyTriesException();
        }
        loginTry.setCount(loginTry.getCount() + 1);
        loginTry.setLastTry(LocalDateTime.now());
        loginTryMap.put(requestedSessionId, loginTry);
    }
}
