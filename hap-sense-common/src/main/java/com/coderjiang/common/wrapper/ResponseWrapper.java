package com.coderjiang.common.wrapper;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResponseWrapper implements Serializable {

    private int status;
    private String message;
    private Object data;
    private Date time;

    private ResponseWrapper() {
    }

    public ResponseWrapper(int status, String message) {
        this.status = status;
        this.message = message;
        this.time = new Date();
    }

    public ResponseWrapper(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.time = new Date();
    }

    public static ResponseWrapper success() {
        return new ResponseWrapper(1, "Success.");
    }

    public static ResponseWrapper failed() {
        return new ResponseWrapper(0, "Failed!");
    }

    public static ResponseWrapper error() {
        return new ResponseWrapper(-1, "Error!");
    }

    public ResponseWrapper message(String message) {
        this.message = message;
        return this;
    }

    public ResponseWrapper data(Object data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return this.status == 1;
    }

    public boolean isFailed() {
        return this.status == 0;
    }

    public boolean isError() {
        return this.status == -1;
    }

}
