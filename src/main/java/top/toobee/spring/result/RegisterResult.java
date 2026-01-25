package top.toobee.spring.result;

import com.anji.captcha.model.common.ResponseModel;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import top.toobee.spring.result.dto.CaptchaErrorDto;
import top.toobee.spring.result.dto.IpBannedErrorDto;
import top.toobee.spring.result.dto.SimpleErrorDto;

public sealed interface RegisterResult extends ResultCommon {
    final class RequestSuccess implements RegisterResult {
        private static final ResponseEntity<Void> response = ResponseEntity.ok().build();
        public static final RequestSuccess INSTANCE = new RequestSuccess();

        private RequestSuccess() {}

        @Override
        public ResponseEntity<Void> to() {
            return response;
        }
    }

    record FromGameSuccess(String jwt) implements RegisterResult {
        @Override
        public ResponseEntity<String> to() {
            return ResponseEntity.ok(jwt);
        }
    }

    enum SimpleError implements RegisterResult {
        // common errors
        USERNAME_TOO_SHORT,
        USERNAME_TOO_LONG,
        INVALID_USERNAME,
        PASSWORD_TOO_SHORT,
        PASSWORD_TOO_LONG,
        EXISTING_USER,
        INVALID_CONTACT_FORM,
        // from game errors
        UNKNOWN_PLAYER,
        FAKE_PLAYER,
        WRONG_PLAYER_PASSWORD,
        // from request errors
        EMPTY_REASON;

        private final ResponseEntity<SimpleErrorDto> responseEntity =
                ResponseEntity.badRequest()
                        .body(
                                new SimpleErrorDto(
                                        "user:register", name().toLowerCase(Locale.getDefault())));

        @Override
        public ResponseEntity<SimpleErrorDto> to() {
            return responseEntity;
        }
    }

    record IpBanned(int banTime) implements RegisterResult {
        @Override
        public ResponseEntity<IpBannedErrorDto> to() {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new IpBannedErrorDto("user:register", banTime));
        }
    }

    record CaptchaError(int code, String message) implements RegisterResult {
        public static CaptchaError fromResponseModel(ResponseModel model) {
            return new CaptchaError(Integer.parseInt(model.getRepCode()), model.getRepMsg());
        }

        @Override
        public ResponseEntity<CaptchaErrorDto> to() {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new CaptchaErrorDto(code, message));
        }
    }
}
