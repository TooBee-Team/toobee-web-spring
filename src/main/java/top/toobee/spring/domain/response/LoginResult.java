package top.toobee.spring.domain.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

public enum LoginResult {
    OK(HttpStatus.OK),
    CREATED(HttpStatus.CREATED),
    ILLEGAL_USERNAME,
    UNREGISTERED,
    WRONG_PASSWORD,
    BANNED,
    FROZEN;

    private final HttpStatus status;

    LoginResult() {
        this.status = HttpStatus.UNAUTHORIZED;
    }

    LoginResult(HttpStatus status) {
        this.status = status;
    }

    public ResponseEntity<?> wrap(@Nullable String result) {
        return ResponseEntity.status(status).body(this == OK || this == CREATED ?
                result : new CustomErrorDto("toobee:login_error", this.name().toLowerCase(), null));
    }
}
