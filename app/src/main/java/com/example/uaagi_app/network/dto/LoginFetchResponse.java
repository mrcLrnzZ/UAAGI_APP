package com.example.uaagi_app.network.dto;

public class LoginFetchResponse {
    public boolean success;
    public String message;
    public boolean formExist;

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

    public boolean isFormExist() {
        return formExist;
    }

    public void setFormExist(boolean formExist) {
        this.formExist = formExist;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int userId;
    private LoginFetchResponse(Builder builder) {
        this.success = builder.success;
        this.message = builder.message;
        this.formExist = builder.formExist;
        this.userId = builder.userId;
    }
    public static class Builder {
        private boolean success;
        private String message;
        private boolean formExist;
        private int userId;

        public Builder() {}
        public Builder success(boolean success) { this.success = success; return this; }
        public Builder message(String message) { this.message = message; return this; }
        public Builder formExist(boolean formExist) { this.formExist = formExist; return this; }
        public Builder userId(int userId) { this.userId = userId; return this; }

        public LoginFetchResponse build() {
            return new LoginFetchResponse(this);
        }

    }

}