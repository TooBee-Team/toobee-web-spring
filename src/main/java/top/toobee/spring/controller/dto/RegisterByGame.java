package top.toobee.spring.controller.dto;

import top.toobee.spring.domain.enums.ContactType;

public record RegisterByGame(
        String username,
        String password,
        String captcha,
        ContactType contactType,
        String contactInfo,
        String playerName,
        String playerPass) {}
