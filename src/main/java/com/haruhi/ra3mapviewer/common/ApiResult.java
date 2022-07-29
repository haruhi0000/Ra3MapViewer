package com.haruhi.ra3mapviewer.common;

/**
 * @author haruhi0000
 */
public class ApiResult<T> {
    private int code,pageNum,pageSize;
    private long total;
    private String message;
    private T data;

    public ApiResult() {

    }
    public ApiResult(int pageNum, int pageSize, long total, T data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;

        this.data = data;
    }

    public ApiResult(T data) {
        this.data = data;
    }
    public  static <T> ApiResult<T> success(int pageNum, int pageSize, long total, T data) {
        return new ApiResult<>(pageNum, pageSize, total, data);
    }
    public  static <T> ApiResult<T> failed(String message) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setMessage(message);
        apiResult.setCode(500);
        return apiResult;
    }
    public  static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(data);
    }

    public static <T> ApiResult<T> success() {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(0);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
