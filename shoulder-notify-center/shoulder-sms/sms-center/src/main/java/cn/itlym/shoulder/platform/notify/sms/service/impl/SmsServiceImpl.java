package cn.itlym.shoulder.platform.notify.sms.service.impl;

import cn.itlym.shoulder.platform.notify.sms.param.SmsBatchParam;
import cn.itlym.shoulder.platform.notify.sms.param.SmsParam;
import cn.itlym.shoulder.platform.notify.sms.service.SmsService;
import org.shoulder.sms.aliyun.client.AliSmsClient;
import org.shoulder.sms.aliyun.dto.param.AliSmsBatchParam;
import org.shoulder.sms.aliyun.dto.param.AliSmsParam;
import org.shoulder.sms.aliyun.exception.AliSmsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lym
 */
@Service
public class SmsServiceImpl implements SmsService {

    private final AliSmsClient aliSmsClient;

    private final String signName;

    public SmsServiceImpl(AliSmsClient smsClient, @Value("智汇信息") String signName) {
        this.aliSmsClient = smsClient;
        this.signName = signName;
    }


    @Override
    public boolean sendSms(SmsParam smsParam) {

        try {
            return aliSmsClient.send(convert(smsParam));
        } catch (AliSmsException e) {
            // todo
            return false;
        }
    }

    @Override
    public boolean sendSms(SmsBatchParam smsBatchParam) {
        try {
            return aliSmsClient.send(convert(smsBatchParam));
        } catch (AliSmsException e) {
            // todo
            return false;
        }
    }

    // --------------------------- convert -------------------------

    public AliSmsParam convert(SmsParam smsParam) {
        return AliSmsParam.newBuilder()
                .phoneNumbers(smsParam.getPhoneNumbers())
                .templateCode(smsParam.getTemplateCode())
                .templateParam(smsParam.getTemplateParam())
                .signName(signName)
                .build();
    }

    public AliSmsBatchParam convert(SmsBatchParam smsBatchParam) {
        int batchSize = smsBatchParam.getPhoneNumbers().size();
        List<String> signNames = new ArrayList<>(batchSize);
        for (int i = 0; i < batchSize; i++) {
            signNames.add(signName);
        }
        return AliSmsBatchParam.newBuilder()
                .phoneNumbers(smsBatchParam.getPhoneNumbers())
                .signNames(signNames)
                .templateCode(smsBatchParam.getTemplateCode())
                .templateParams(smsBatchParam.getTemplateParams())
                .build();
    }

}