package top.toobee.spring.controller.dto;

public record RegisterRequest(
        String username,
        String password,
        String captcha,
        ContactType contactType,
        String contactInfo,
        String reason) {}
