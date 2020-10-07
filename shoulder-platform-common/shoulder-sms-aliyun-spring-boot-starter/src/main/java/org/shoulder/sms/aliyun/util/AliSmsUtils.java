package org.shoulder.sms.aliyun.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.http.MethodType;
import com.google.gson.Gson;
import org.apache.http.util.Args;
import org.shoulder.sms.aliyun.consts.AliSmsAction;
import org.shoulder.sms.aliyun.dto.param.AliSmsBatchParam;
import org.shoulder.sms.aliyun.dto.param.AliSmsParam;
import org.shoulder.sms.aliyun.exception.AliSmsException;

import java.util.Map;
import java.util.Objects;

/**
 * sms 工具类.
 *
 * @author lym
 */
public class AliSmsUtils {

    /**
     * 成功响应码
     */
    private static final String SUCCESS_CODE = "OK";

    /**
     * 严格匹配中国大陆内支持短信功能的号码
     * https://github.com/VincentSit/ChinaMobilePhoneNumberRegex/blob/master/README-CN.md
     */
    private static final String PHONE_NUMBER_REGEX = "^(?:\\+?86)?1(?:3\\d{3}|5[^4\\D]\\d{2}|8\\d{3}|7(?:[01356789]\\d{2}|4(?:0\\d|1[0-2]|9\\d))|9[01356789]\\d{2}|6[2567]\\d{2}|4[579]\\d{2})\\d{6}$";


    /**
     * Map 转 json 字符串的简单实现.
     *
     * @param map the map
     * @return the json string
     */
    public static String toJsonStr(final Map<String, String> map) {
        if (null == map || map.isEmpty()) {
            return null;
        }

        final StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            sb.append('"')
                    .append(entry.getKey().replace("\"", "\\\""))
                    .append('"')
                    .append(':')
                    .append('"')
                    .append(entry.getValue().replace("\"", "\\\""))
                    .append('"')
                    .append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append('}');
        return sb.toString();
    }

    /**
     * 校验 AliSmsParam.
     *
     * @param param the AliSmsParam
     */
    public static void checkSmsParam(final AliSmsParam param) {
        Objects.requireNonNull(param);
        Args.notEmpty(param.getSignName(), "signName");
        Args.notEmpty(param.getTemplateCode(), "templateCode");
        Args.notEmpty(param.getPhoneNumbers(), "phoneNumbers");
        param.getPhoneNumbers().forEach(AliSmsUtils::checkPhoneNumber);
        Args.check(param.getPhoneNumbers().size() <= 1000, "phoneNumbers can't more than 1000");

    }

    /**
     * 校验 AliSmsBatchParam.
     *
     * @param param the AliSmsBatchParam
     */
    public static void checkBatchSmsParam(final AliSmsBatchParam param) {
        Objects.requireNonNull(param);
        Args.notEmpty(param.getSignNames(), "signNames");
        Args.notEmpty(param.getTemplateCode(), "templateCode");
        Args.notEmpty(param.getPhoneNumbers(), "phoneNumber");
        param.getPhoneNumbers().forEach(AliSmsUtils::checkPhoneNumber);
        Args.check(param.getSignNames().size() == param.getPhoneNumbers().size() &&
                param.getPhoneNumbers().size() == param.getTemplateParams().size(), "phoneNumbers, signNames, templateParams must have same size");

    }

    /**
     * 校验 SendSmsResponse 状态.
     *
     * @param response the SendSmsResponse
     */
    @SuppressWarnings("unchecked")
    public static void checkSmsResponse(final CommonResponse response) throws AliSmsException {
        if (null == response) {
            throw new AliSmsException("Response is null");
        }
        final Gson gson = new Gson();
        final Map<String, String> json = gson.fromJson(response.getData(), Map.class);
        if (!SUCCESS_CODE.equalsIgnoreCase(json.get("Code"))) {
            // todo 错误处理
            throw new AliSmsException("Http status: " + response.getHttpStatus() + ", response: " + response.getData());
        }
    }

    /**
     * 校验手机号码（中国）.
     *
     * @param phoneNumber the phone number
     */
    public static void checkPhoneNumber(final String phoneNumber) {
        if (null == phoneNumber || !phoneNumber.matches(PHONE_NUMBER_REGEX)) {
            throw new IllegalArgumentException("Invalid phone number");
        }
    }

    public static CommonRequest newRequest(AliSmsAction action) {
        final String aliCloudDomain = "dysmsapi.aliyuncs.com";
        final String aliCloudVersion = "2017-05-25";
        final String product = "Dysmsapi";

        final CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);

        request.setDomain(aliCloudDomain);
        request.setVersion(aliCloudVersion);
        request.setAction(action.getAction());
        request.setProduct(product);
        return request;
    }

}
