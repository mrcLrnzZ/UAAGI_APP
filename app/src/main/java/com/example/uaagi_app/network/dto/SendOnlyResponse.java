package com.example.uaagi_app.network.dto;

public class SendOnlyResponse {
    public boolean success;
    public String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private SendOnlyResponse(Builder builder) {
        this.success = builder.success;
        this.message = builder.message;
    }
    public static class Builder {
        private boolean success;
        private String message;
        public Builder success(boolean success) { this.success = success; return this; }
        public Builder message(String message) { this.message = message; return this; }
        public SendOnlyResponse build() {
            return new SendOnlyResponse(this);
        }
    }
}
