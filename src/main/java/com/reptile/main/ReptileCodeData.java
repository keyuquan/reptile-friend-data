package com.reptile.main;

import com.reptile.entity.UserData;
import com.reptile.utils.HttpUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * 有伴发送验证码
 */
public class ReptileCodeData {

    public static void main(String[] args) throws Exception {
        isFree("18818406784");
        sendCode("18818406784");

//        for (int i = 0; i < 11111111; i++) {
//            long a = 18818406784l;
//            String phone = (a + (long) i) + "";
//            sendCode(phone);
//            System.out.println("count="+i);
//        }
    }

    /**
     * 获取用户信息
     *  http://small.onbyway.top/api/register/isFreePhone?phone=18818406784
     * @return
     */
    public static UserData.DataDTO sendCode(String phone) {
        String url = "http://small.onbyway.top/api/register/sendSmsCode";
        Map<String, Object> map = new HashMap();
        map.put("phone", phone);
        map.put("action", "register");

        Map<String, Object> header = new HashMap();
        map.put("location", "114.085947&22.547456");

        String data = HttpUtils.doPost(url, map, null);
        System.out.println(data);
        return null;
    }


    /**
     * 获取用户信息
     *  http://small.onbyway.top/api/register/isFreePhone?phone=18818406784
     * @return
     */
    public static UserData.DataDTO isFree(String phone) {
        String url = "http://small.onbyway.top/api/register/isFreePhone";
        Map<String, Object> map = new HashMap();
        map.put("phone", phone);
//        map.put("action", "login");

        Map<String, Object> header = new HashMap();
        map.put("location", "114.085947&22.547456");

        String data = HttpUtils.doPost(url, map, null);
        System.out.println(data);
        return null;
    }
}
