package com.builderfly.parsing_app;

public class Constants {

    // Response Variable
    public static final int SUCCESS_CODE = 1;
    public static final int FAILURE_CODE = 0;
    public static final int SOCIAL_CODE = 2;
    public static final int NOT_FOUND_CODE = 404;
    public static final int NO_CONTENT_CODE = 204;
    public static final int INTERNAL_ERROR_CODE = 500;
    public static final int BAD_REQUEST_CODE = 400;
    public static final int CONFLICT_CODE = 409;
    public static final int UNAUTHORIZED_CODE = 401;
    public static final int CODE_ALL_CATEGORIES = 3;
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failed";
    public static final String API_KEY = "ICXE1wOphgKgcyMoHr0hVHbbJ";
    // Base url
    public static String BASE_URL = "http://192.168.15.162/zapstore-ethan/web2/";

    // Custom api url
    public static final String USER_REGISTRATION = BASE_URL + "customerRegistration.php";
    public static final String USER_LOGIN = BASE_URL + "customerLogin.php";

    // Custom api code
    public static final int CODE_USER_REGISTERATION = 1;
    public static final int CODE_USER_LOGIN = 2;

    // Fragment tag
    public static String TAG_HOME_FRAGMENT = "HomeFragment";

    // shared preferences key
    public static String PREFERENCE_NAME = "sparkUpPrefs";
    public static String ACCESS_TOKEN = "accessTokenKey";
    public static String DEVICE_TOKEN = "deviceTokenKey";
    public static String USER_ID = "userIdKey";
    public static String USER_NAME = "userIdKey";

    public static class Urls {
        static String baseUrl = "http://192.168.15.162/zapstore-ethan/web2/";

        public static String USER_LOGIN = baseUrl + "customerLogin.php";
        public static String USER_REGISTRATION = baseUrl + "customerRegistration.php";
        public static String ALL_CATEGORIES = baseUrl + "allCategory.php";

    }

}
