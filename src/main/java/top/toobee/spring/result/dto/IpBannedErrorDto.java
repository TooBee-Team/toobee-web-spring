package top.toobee.spring.result.dto;

public record IpBannedErrorDto(String type, String id, int time) {
    public IpBannedErrorDto(String type, int time) {
        this(type, "ip_banned", time);
    }
}
