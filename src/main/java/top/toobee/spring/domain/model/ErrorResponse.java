package top.toobee.spring.domain.model;

import java.util.Objects;

public class ErrorResponse {
    private int status;
    private String error;
    private String message;

    public ErrorResponse() {}

    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{"
                + "status="
                + status
                + ", error='"
                + error
                + '\''
                + ", message='"
                + message
                + '\''
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, error, message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse that = (ErrorResponse) o;
        return status == that.status && error.equals(that.error) && message.equals(that.message);
    }

    public static class Builder {
        private int status;
        private String error;
        private String message;

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponse build() {
            ErrorResponse response = new ErrorResponse();
            response.setStatus(status);
            response.setError(error);
            response.setMessage(message);
            return response;
        }

        public static Builder builder() {
            return new Builder();
        }
    }
}
