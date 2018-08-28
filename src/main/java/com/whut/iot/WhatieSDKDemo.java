package com.whut.iot;

import com.alibaba.fastjson.JSONObject;
import com.whut.iot.common.Const;
import com.whut.iot.model.RequestMessage;
import com.whut.iot.model.ResponseMessage;
import com.whut.iot.model.WhatieCloudClient;
import com.whut.iot.util.CommonUtil;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZZK
 * @date 2018/8/24 21:08
 * @description
 */

public class WhatieSDKDemo {

    public static void main(String[] args) {
        requestExample();
    }

    private static void requestExample() {

        // Please fill in the correct accessId and accessKey first!
        String accessId = "****accessId****";
        String accessKey = "****accessKey****";

        Map<String, String> params = new HashMap<>();

        // 1.login example
        String apiUri = "https://users.whatie.net/api/v1/login";
        try {
            params.put("email", "xxx@gmail.com");
            params.put("password", CommonUtil.encodeMD5("xxxxxx"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 2.Device list example
//        String apiUri = "https://devices.whatie.net/api/v1/deviceList";
//        params.put("customerId", "459");

        // 3.Publish new dps example
//        String apiUri = "https://msg.whatie.net/api/v1/changeDps";
//        // (1) switch - turn on/off
//        params.put("devId", "0123456789abc");
//        params.put("dps", accumulateDps(0).toJSONString());

//        // (2) light
//        params.put("devId", "0123456789xyz");
//        // 1) adjust the brightness value of light
//        params.put("dps", accumulateDps(1).toJSONString());
//        // 2) change the color of light via RGB value
//        params.put("dps", accumulateDps(2).toJSONString());
//        // 3) turn on/off light
//        params.put("dps", accumulateDps(3).toJSONString());
//        // 4) Stream Light
//        params.put("dps", accumulateDps(4).toJSONString());


        // 4.Publish timer task
//        String apiUri = "https://msg.whatie.net/api/v1/setTimer";
//        params.put("deviceId", "269846");
//        params.put("dps", accumulateDps(0).toJSONString());
//        params.put("timerType", "0000000");
//        params.put("finishTime", "1518");
//        params.put("timezone", "+800");
//        params.put("customerId", "459");

        // 5.Remove timer task
//        String apiUri = "https://msg.whatie.net/api/v1/removeTimer";
//        params.put("timerId", "2248");
//        params.put("customerId", "459");

        // 6.List timer task
//        String apiUri = "https://msg.whatie.net/api/v1/timerList";
//        params.put("devId", "0123456789abc");

//        // Create WhatieCloudClient
//        ClientConfig clientConfig = new ClientConfig();
//        clientConfig.setConnectionTimeout(10000);
//        clientConfig.setSocketTimeout(10000);
//        clientConfig.setMaxConnections(100);
//        clientConfig.setMaxErrorRetry(5);
//        WhatieCloudClient client = new WhatieCloudClient(accessId, accessKey, apiUri, clientConfig);
        WhatieCloudClient client = new WhatieCloudClient(accessId, accessKey, apiUri);

        // Create new request object
        RequestMessage request = new RequestMessage();
        request.setApi(apiUri);
        request.setApiVersion("1.0");
        request.setLang(Const.MessageConst.LANG_EN);
//        request.setLang(Const.MessageConst.LANG_CN);

        request.setParams(params);

        // Send request and receive response
        // If the request was success, check result from the json object
        // If the request was failed, check the errorCode and errorMessage
//        long now = System.currentTimeMillis();
        ResponseMessage response = client.sendRequest(request);
//        System.out.println("elapse time:" + (System.currentTimeMillis() - now));
        if (response.isSuccess()) {
//            String data = JSONObject.toJSONString(response.getData(), true);
            String result = JSONObject.toJSONString(response, true);
//            System.out.println(data);
            System.out.println(result);
        } else {
            int errorCode = response.getCode();
            String errorMsg = response.getMessage();
            System.out.println(errorMsg);
        }
    }

    private static JSONObject accumulateDps(int type) {
        JSONObject dps = new JSONObject();
        switch (type) {
            case 0:
                // switch
                dps.put("1", true);
                dps.put("2", true);
                break;
            case 1:
                // light
                dps.put("l", "50");
                dps.put("lightMode", 1);
                break;
            case 2:
                dps.put("l", "50");
                dps.put("lightMode", 2);
                dps.put("rgb", "255_237_228");
                break;
            case 3:
                dps.put("lightMode", 4);
                dps.put("status", "true");
                break;
            case 4:
                dps.put("l", "50");
                dps.put("lightMode", 5);
                dps.put("rgb1", "255_221_123");
                dps.put("rgb2", "22_145_56");
                dps.put("rgb3", "146_237_97");
                dps.put("rgb4", "46_189_228");
                dps.put("t", "1000");
                break;
            default:
                break;
        }
        return dps;
    }

}
