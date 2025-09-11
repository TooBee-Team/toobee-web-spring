package top.toobee.spring.domain.response;

public enum ChangePasswordResult {
    SUCCESS,
    UNKNOWN_USER,
    WRONG_OLD_PASSWORD,
    ILLEGAL_NEW_PASSWORD
}
