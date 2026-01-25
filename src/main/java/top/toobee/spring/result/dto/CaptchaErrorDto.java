package top.toobee.spring.result.dto;

public record CaptchaErrorDto(String type, int code, String message) {
    public CaptchaErrorDto(int code, String message) {
        this("captcha", code, message);
    }
}
