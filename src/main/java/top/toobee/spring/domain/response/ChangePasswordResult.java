package top.toobee.spring.domain.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public enum ChangePasswordResult {
    SUCCESS(HttpStatus.OK),
    UNKNOWN_USER,
    SAME_PASSWORD,
    WRONG_OLD_PASSWORD,
    ILLEGAL_NEW_PASSWORD;

    private final HttpStatus status;

    ChangePasswordResult() {
        this.status = HttpStatus.FORBIDDEN;
    }

    ChangePasswordResult(HttpStatus status) {
        this.status = status;
    }

    public ResponseEntity<?> wrap() {
        return this == SUCCESS ? ResponseEntity.ok(null) :
                ResponseEntity.status(status).body(new CustomErrorDto("toobee:change_password_error", this.name().toLowerCase(), null));
    }
}
