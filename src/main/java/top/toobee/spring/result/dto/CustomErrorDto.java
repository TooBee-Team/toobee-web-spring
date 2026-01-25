package top.toobee.spring.result.dto;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public record CustomErrorDto(@NonNull String type, @NonNull String id, @Nullable String message) {}
