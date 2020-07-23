package com.model;

import java.io.Serializable;

/**
 * @author IceCube
 * @date 2020/7/22 18:02
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 4079953224121860695L;
    private String message;
    private String error;
    private Integer exitValue;

    @Override
    public String toString() {
        return "Result{" +
                "message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", exitValue=" + exitValue +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getExitValue() {
        return exitValue;
    }

    public void setExitValue(Integer exitValue) {
        this.exitValue = exitValue;
    }
}
