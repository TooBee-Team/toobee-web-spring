package top.toobee.spring.result.dto;

import org.jspecify.annotations.NonNull;

public record SimpleErrorDto(@NonNull String type, @NonNull String id) {}
