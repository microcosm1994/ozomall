package com.ozomall.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.util.Random;

public class Sms {


    public static String GenValidateCode(int len) {
        String code = "";
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            code += random.nextInt(10);
        }
        return code;
    }

    ;

    public static String sendMessage(String phone) throws ClientException {
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        final String accessKeyId = "LTAIXx905tkhWOmO";//accessKeyId
        final String accessKeySecret = "3UgvOW1islp4IVrvXiVk6JfL9eQp4n";//accessKeySecret
        //初始化ascClient
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        SendSmsRequest smsRequest = new SendSmsRequest();
        //使用post提交
        smsRequest.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        smsRequest.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        smsRequest.setSignName("chatApp");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        smsRequest.setTemplateCode("SMS_182682802");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
//参考：request.setTemplateParam("{\"变量1\":\"值1\",\"变量2\":\"值2\",\"变量3\":\"值3\"}")
        String code = GenValidateCode(6);
        smsRequest.setTemplateParam("{\"code\":\"" + code + "\"}");

//请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(smsRequest);
        System.out.println(sendSmsResponse.getCode());
        System.out.println(sendSmsResponse.getMessage());
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
//请求成功
            return code;
        }
        return null;
    }
}
