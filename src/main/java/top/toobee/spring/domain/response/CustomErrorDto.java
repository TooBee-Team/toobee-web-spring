package top.toobee.spring.domain.response;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public record CustomErrorDto(@NonNull String type, @NonNull String id, @Nullable String message) {}
