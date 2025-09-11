package top.toobee.spring.domain.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

public enum LoginTokenResponse {
    OK(HttpStatus.OK),
    CREATED(HttpStatus.CREATED),
    UNREGISTERED(HttpStatus.UNAUTHORIZED),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED),
    BANNED(HttpStatus.UNAUTHORIZED),
    FROZEN(HttpStatus.UNAUTHORIZED);

    private final HttpStatus status;

    LoginTokenResponse(HttpStatus status) {
        this.status = status;
    }

    public ResponseEntity<?> wrap(@Nullable String result) {
        return ResponseEntity.status(status).body(this == OK || this == CREATED ?
                result : new CustomErrorDto("toobee:login_error", this.name(), null));
    }
}
