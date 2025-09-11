package top.toobee.spring.domain.response;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public record CustomErrorDto(
    @NonNull String type,
    @NonNull String id,
    @Nullable String message
) {}
