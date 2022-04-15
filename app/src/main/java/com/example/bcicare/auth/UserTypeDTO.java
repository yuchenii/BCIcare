package com.example.bcicare.auth;

import com.google.gson.annotations.SerializedName;

public class UserTypeDTO {

    /**
     * 1：成功，0：失败
     */
    @SerializedName("code")
    private Integer code;
    /**
     * data
     */
    @SerializedName("data")
    private DataDTO data;
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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataDTO {
        /**
         * 用户id
         */
        @SerializedName("user_id")
        private Integer userId;
        /**
         * 用户类型：0：患者1：医生2：管理员
         */
        @SerializedName("user_type")
        private Integer userType;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getUserType() {
            return userType;
        }

        public void setUserType(Integer userType) {
            this.userType = userType;
        }
    }
}
