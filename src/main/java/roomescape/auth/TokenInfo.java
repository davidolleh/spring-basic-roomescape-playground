package roomescape.auth;

public record TokenInfo(
        Long id,
        String name,
        String role
) {
}
