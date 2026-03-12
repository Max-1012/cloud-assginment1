package sparta.cloudassignment.global.error;

public record BaseErrorResponse(
        int status,
        String code,
        String errorMessage
) {}
