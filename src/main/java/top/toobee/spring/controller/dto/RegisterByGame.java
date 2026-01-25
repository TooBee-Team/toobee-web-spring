package top.toobee.spring.controller.dto;

public record RegisterByGame(
        String username,
        String password,
        String captcha,
        ContactType contactType,
        String contactInfo,
        String playerName,
        String playerPass) {}
