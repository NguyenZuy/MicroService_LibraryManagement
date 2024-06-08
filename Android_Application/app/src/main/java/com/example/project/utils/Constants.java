package com.example.project.utils;

public class Constants {
    // main ipc final String SERVER_URL = "ws://192.168.1.11:3500"; // Replace with your server IP or hostname
    public static final String SERVER_URL = "http://192.168.0.112:9191"; // Replace with your server IP or hostname

    // events handlers
    public static final String EVENT_LOGIN = "login";
    public static final String EVENT_GET_DATA = "getData";
    public static final String EVENT_ORDER = "order";
    public static final String EVENT_ADD_BOOK = "addBook";
    public static final String EVENT_ADD_CATEGORY = "addCategory";
    public static final String EVENT_GET_CATEGORIES = "getCategory";
    public static final String EVENT_ADD_USER = "addUser";
    public static final String EVENT_CHECK_USERNAME = "checkUsernameExists";
    public static final String EVENT_CHANGE_PASSWORD = "changePassword";
    public static final String EVENT_REMARK = "remark";
    public static final String EVENT_UPDATE = "update";
    // log type tag
    public static final String TAG_WEBSOCKET = "Websocket";

    public enum TYPE_ALERT{
        OK, OK_CANCEL, TRYAGAIN_CANCEL
    }

    public enum MODE_SUB_TABS{
        HOME,
        NOTE,
    }
}
