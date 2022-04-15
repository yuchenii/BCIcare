package com.example.bcicare.config;


/**
 * @author yuchen
 */
public class Urls {


    public static final String BASE_URL = "https://bcicare.yuchen.tech/api/v1";

    /**
     * 登录
     */
    public static final String AUTH_LOGIN = BASE_URL + "/auth/login";

    /**
     * revoke access_token
     */
    public static final String REVOKE_ACCESS = BASE_URL + "/auth/revoke_access";

    /**
     * revoke refresh_token
     */
    public static final String REVOKE_REFRESH = BASE_URL + "/auth/revoke_refresh";

    /**
     * refresh access_token
     */
    public static final String REFRESH_TOKEN = BASE_URL + "/auth/refresh";

    /**
     * 注册
     */
    public static final String USER_REGISTER = BASE_URL + "/user/register";

    /**
     * 用户类型
     */
    public static final String USER_TYPE = BASE_URL + "/auth/user_type";

    /**
     * 患者信息
     */
    public static final String PATIENT_INFO = BASE_URL + "/patient";


}
