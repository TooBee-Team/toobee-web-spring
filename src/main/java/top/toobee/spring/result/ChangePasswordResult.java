package top.toobee.spring.result;

import java.util.Locale;
import org.springframework.http.ResponseEntity;
import top.toobee.spring.result.dto.SimpleErrorDto;

public enum ChangePasswordResult implements ResultCommon {
    SUCCESS,
    WRONG_OLD_PASSWORD,
    NEW_PASSWORD_TOO_SHORT,
    NEW_PASSWORD_TOO_LONG;

    private final ResponseEntity<?> responseEntity =
            name().equals("SUCCESS")
                    ? ResponseEntity.ok().build()
                    : ResponseEntity.badRequest()
                            .body(
                                    new SimpleErrorDto(
                                            "user:change_password",
                                            name().toLowerCase(Locale.getDefault())));

    @Override
    public ResponseEntity<?> to() {
        return responseEntity;
    }
}
