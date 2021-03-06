package com.lykj.aextreme.afinal.utils;

/**
 * 常量
 *
 * @author HeYan
 * @time 2014年12月18日 下午4:36:33
 */
public class Constant {
    /**
     * 权限
     */
    public static final String PERMISSION = "android.permission.THYD";
    /**
     * 是否是首次进入
     */
    public static final String FIRST_ENTER = "FIRST_ENTER";
    /**
     * 用户是否登录
     */
    public static final String LAST_ACT = "LAST_ACT";
    /**
     * 用户信息
     */
    public static final String USER_INFO = "USER_INFO";
    /**
     * 用户是否登录
     */
    public static final String USER_ISLOGIN = "USER_LOGIN";
    /**
     * 用户TOKEN
     */
    public static final String USER_TOKEN = "USER_TOKEN";
    /**
     * 用户ID
     */
    public static String USER_ID = "USER_ID";
    /**
     * 上次记录的APP版本
     */
    public static final String LAST_VERSION = "LAST_VERSION";
    /**
     * 内部消息-网络改变
     */
    public static final String ACTION_RECEIVE_NET_CHANGE = "com.hy.frame.ACTION_NET_CHANGE";
    /**
     * 网络状态
     */
    public static final String NET_STATUS = "NET_STATUS";
    /**
     * 网络状态-断开
     */
    public static final int NET_DISCONNECT = -1;
    /**
     * 网络状态-断开
     */
    public static final int NET_CONNECTED = 1;
    /**
     * 标记
     */
    public static final String FLAG = "C_FLAG";
    /**
     * 标记 2
     */
    public static final String FLAG2 = "C_FLAG2";
    /**
     * 标记 3
     */
    public static final String FLAG3 = "C_FLAG3";
    /**
     * 标记ID
     */
    public static final String FLAG_ID = "C_FLAG_ID";
    /**
     * 标记标题
     */
    public static final String FLAG_TITLE = "C_FLAG_TITLE";
    /**
     * 默认请求码
     */
    public static final int FLAG_REQUESTCODE = 999;
    /**
     * 登录
     */
    public static final int FLAG_USER_LOGIN = 100;
    /**
     * 普通注册
     */
    public static final int FLAG_USER_REGIST = 101;
    /**
     * 每页的条数
     */
    public static final int PAGE_SIZE = 10;
    /**
     * 上次记录的手机号
     */
    public static final String LAST_PHONE = "LAST_PHONE";

    /**
     * 接受到推送消息，ID
     */
    public static final int FLAG_RECEIVE_PUSH_ID = 601;
    /**
     * 接受到用户登录消息
     */
    public static final int FLAG_RECEIVE_LOGIN = 602;
    /**
     * 接受到用户注销消息
     */
    public static final int FLAG_RECEIVE_LOGINOUT = 603;
    // /** 接受到注册推送 */
    // public static final int FLAG_RECEIVE_PUSH_REGIST = 604;
    /**
     * 接收到更新用户信息
     */
    public static final int FLAG_RECEIVE_UPDATE_USER = 606;
    /**
     * 提醒有新消息
     */
    public static final int FLAG_RECEIVE_NOTIFY = 607;
    /**
     * 提醒更新历史消息
     */
    public static final int FLAG_RECEIVE_UPDATE_MSG = 608;
    /**
     * 提醒强制退出
     */
    public static final int FLAG_RECEIVE_FORCE_LOGINOUT = 609;
    /**
     * 提醒通知点击
     */
    public static final int FLAG_RECEIVE_NOTIFY_CLICK = 610;

    public static final String HTTP_CONTENTTYPE = "application/x-www-form-urlencoded";
    public static final String HTTP_USERAGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.A.B.C Safari/525.13";
    public static final String HTTP_ACCEPT_JSON = "application/json";
    public static final String HTTP_AUTH = "AUTHORIZATION";
    /**
     * 拍照
     */
    public static final int FLAG_UPLOAD_TAKE_PICTURE = 10;
    /**
     * 拍照
     */
    public static final int REQUEST_TAKE_PHOTO = 1;
    /**
     * 选择图片
     */
    public static final int FLAG_UPLOAD_CHOOICE_IMAGE = 12;
    /**
     * 剪切
     */
    public static final int FLAG_UPLOAD_IMAGE_CUT = 13;


}
