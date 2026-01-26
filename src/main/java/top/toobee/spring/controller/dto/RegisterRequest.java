package top.toobee.spring.controller.dto;

import top.toobee.spring.domain.enums.ContactType;

public record RegisterRequest(
        String username,
        String password,
        String captcha,
        ContactType contactType,
        String contactInfo,
        String reason) {}
