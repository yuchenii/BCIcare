package com.example.bcicare.api;

import com.google.gson.annotations.SerializedName;

public class PatientInfosDTO {

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
    /**
     * data
     */
    @SerializedName("data")
    private DataDTO data;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * patientInfo
         */
        @SerializedName("patient_info")
        private PatientInfoDTO patientInfo;

        public PatientInfoDTO getPatientInfo() {
            return patientInfo;
        }

        public void setPatientInfo(PatientInfoDTO patientInfo) {
            this.patientInfo = patientInfo;
        }

        public static class PatientInfoDTO {
            /**
             * 用户id
             */
            @SerializedName("user_id")
            private Integer userId;
            /**
             * 用户邮箱
             */
            @SerializedName("user_email")
            private String userEmail;
            /**
             * 性别：0: 男，1: 女
             */
            @SerializedName("gender")
            private Integer gender;
            /**
             * 患者类型
             */
            @SerializedName("patient_type")
            private String patientType;
            /**
             * 真实姓名
             */
            @SerializedName("real_name")
            private String realName;
            /**
             * 电话号码
             */
            @SerializedName("phone_number")
            private String phoneNumber;
            /**
             * 医生的id
             */
            @SerializedName("doctor_id")
            private Integer doctorId;

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public String getUserEmail() {
                return userEmail;
            }

            public void setUserEmail(String userEmail) {
                this.userEmail = userEmail;
            }

            public Integer getGender() {
                return gender;
            }

            public void setGender(Integer gender) {
                this.gender = gender;
            }

            public String getPatientType() {
                return patientType;
            }

            public void setPatientType(String patientType) {
                this.patientType = patientType;
            }

            public String getRealName() {
                return realName;
            }

            public void setRealName(String realName) {
                this.realName = realName;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public Integer getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(Integer doctorId) {
                this.doctorId = doctorId;
            }
        }
    }
}
