package com.example.bcicare.api;

import com.google.gson.annotations.SerializedName;

public class ErrResponseDTO {
    /**
     * 1：成功，0：失败
     */
    @SerializedName("code")
    private Integer code;
    /**
     * 返回文字描述
     */
    @SerializedName("msg")
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
