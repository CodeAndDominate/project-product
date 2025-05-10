package validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LoginTry {
    private final String sessionId;
    @Setter
    private int count;
    @Setter
    private LocalDateTime lastTry;
}
