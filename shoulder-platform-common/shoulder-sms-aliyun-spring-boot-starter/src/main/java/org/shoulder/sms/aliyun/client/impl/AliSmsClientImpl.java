package org.shoulder.sms.aliyun.client.impl;

import com.aliyuncs.AcsRequest;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;
import org.apache.http.util.Args;
import org.shoulder.sms.aliyun.client.AliSmsClient;
import org.shoulder.sms.aliyun.dto.param.AliSmsBatchParam;
import org.shoulder.sms.aliyun.dto.param.AliSmsParam;
import org.shoulder.sms.aliyun.exception.AliSmsException;
import org.shoulder.sms.aliyun.util.AliSmsUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 阿里云 SMS 客户端.
 *
 * @author lym
 */
public class AliSmsClientImpl implements AliSmsClient {

    private final IAcsClient acsClient;
    private final Map<String, AliSmsParam> smsTemplateTemplate;
    private final Gson gson = new Gson();

    /**
     * construction
     *
     * @param accessKeyId     阿里云短信 accessKeyId
     * @param accessKeySecret 阿里云短信 accessKeySecret
     */
    public AliSmsClientImpl(final String accessKeyId, final String accessKeySecret) {
        this(accessKeyId, accessKeySecret, Collections.emptyMap());
    }

    /**
     * construction
     *
     * @param accessKeyId         阿里云短信 accessKeyId
     * @param accessKeySecret     阿里云短信 accessKeySecret
     * @param smsTemplateTemplate 预置短信模板
     */
    public AliSmsClientImpl(final String accessKeyId,
                            final String accessKeySecret,
                            final Map<String, AliSmsParam> smsTemplateTemplate) {
        Args.notEmpty(accessKeyId, "'accessKeyId' must be not empty");
        Args.notEmpty(accessKeySecret, "'accessKeySecret' must be not empty");

        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
        } catch (ClientException e) {
            e.printStackTrace();
        }


        final IClientProfile clientProfile = DefaultProfile.getProfile(
                "cn-hangzhou", accessKeyId, accessKeySecret);

        this.acsClient = new DefaultAcsClient(clientProfile);
        this.smsTemplateTemplate = smsTemplateTemplate;
    }

    /**
     * construction
     *
     * @param acsClient           IAcsClient
     * @param smsTemplateTemplate 预置短信模板
     */
    public AliSmsClientImpl(final IAcsClient acsClient, final Map<String, AliSmsParam> smsTemplateTemplate) {
        this.acsClient = acsClient;
        this.smsTemplateTemplate = smsTemplateTemplate;
    }

    // --------------------------------------------------------

    /**
     * 发送短信.
     *
     * @param aliSmsParam 短信模板
     */
    @Override
    public boolean send(final AliSmsParam aliSmsParam) throws AliSmsException {

        AliSmsUtils.checkSmsParam(aliSmsParam);

        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(String.join(",", aliSmsParam.getPhoneNumbers()));
        request.setSignName(aliSmsParam.getSignName());
        request.setTemplateCode(aliSmsParam.getTemplateCode());
        request.setTemplateParam(AliSmsUtils.toJsonStr(aliSmsParam.getTemplateParam()));
        return "OK".equals(getAcsResponse(request).getCode());
    }

    /**
     * 批量发送短信.
     *
     * <p>
     * 批量发送短信接口，支持在一次请求中分别向多个不同的手机号码发送不同签名的短信。
     * 手机号码，签名，模板参数字段个数相同，一一对应，短信服务根据字段的顺序判断发往指定手机号码的签名。
     *
     * <p>
     * 如果您需要往多个手机号码中发送同样签名的短信，请使用 {@link #send(AliSmsParam)}。
     *
     * @param aliSmsBatchParam 批量发送短信模板
     */
    @Override
    public boolean send(final AliSmsBatchParam aliSmsBatchParam) throws AliSmsException {
        Objects.requireNonNull(aliSmsBatchParam);
        AliSmsUtils.checkBatchSmsParam(aliSmsBatchParam);

        SendBatchSmsRequest request = new SendBatchSmsRequest();

        request.setPhoneNumberJson(gson.toJson(aliSmsBatchParam.getPhoneNumbers()));
        request.setSignNameJson(gson.toJson(aliSmsBatchParam.getSignNames()));
        request.setTemplateCode(aliSmsBatchParam.getTemplateCode());
        request.setTemplateParamJson(gson.toJson(aliSmsBatchParam.getTemplateParams()));

        return "OK".equals(getAcsResponse(request).getCode());
    }

    /* just for test
    public static void main(String[] args) throws AliSmsException {
        AliSmsClientImpl client = new AliSmsClientImpl("xxx", "xxx");

        AliSmsParam param = AliSmsParam.newBuilder()
                .signName("xxxx")
                .templateCode("SMS_xxxx")
                .phoneNumber("123")
                .addTemplateParam("code", "123456")
                .build();
        client.send(param);
    }*/

    private <T extends AcsResponse> T getAcsResponse(AcsRequest<T> request)
            throws AliSmsException {
        try {
            return this.acsClient.getAcsResponse(request);
        } catch (ServerException e) {
            throw new AliSmsException("server exception", e);
        } catch (ClientException e) {
            throw new AliSmsException("client exception", e);
        }
    }

}
