package cn.itlym.shoulder.platform.notify.sms.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面响应 DTO
 */
public class UiResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public UiResult() {
        put("code", 0);
    }

    public static UiResult error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static UiResult error(String msg) {
        return error(500, msg);
    }

    public static UiResult error(int code, String msg) {
        UiResult r = new UiResult();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static UiResult ok(Object msg) {
        UiResult r = new UiResult();
        r.put("msg", msg);
        return r;
    }


    public static UiResult ok(Map<String, Object> map) {
        UiResult r = new UiResult();
        r.putAll(map);
        return r;
    }

    public static UiResult ok() {
        return new UiResult();
    }

    @Override
    public UiResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}