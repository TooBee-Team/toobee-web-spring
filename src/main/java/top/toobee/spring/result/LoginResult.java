package top.toobee.spring.result;

import com.anji.captcha.model.common.ResponseModel;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import top.toobee.spring.result.dto.CaptchaErrorDto;
import top.toobee.spring.result.dto.IpBannedErrorDto;
import top.toobee.spring.result.dto.SimpleErrorDto;

public sealed interface LoginResult extends ResultCommon {
    record Success(String token) implements LoginResult {
        @Override
        public ResponseEntity<String> to() {
            return ResponseEntity.ok(token);
        }
    }

    enum SimpleError implements LoginResult {
        INVALID_USERNAME,
        WRONG_PASSWORD,
        UNREGISTERED,
        LOCKED;

        private final ResponseEntity<SimpleErrorDto> response =
                ResponseEntity.badRequest()
                        .body(
                                new SimpleErrorDto(
                                        "user:login", name().toLowerCase(Locale.getDefault())));

        @Override
        public ResponseEntity<SimpleErrorDto> to() {
            return this.response;
        }
    }

    record IpBanned(int banTime) implements LoginResult {
        @Override
        public ResponseEntity<IpBannedErrorDto> to() {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new IpBannedErrorDto("user:login", banTime));
        }
    }

    record CaptchaError(int code, String message) implements LoginResult {
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
