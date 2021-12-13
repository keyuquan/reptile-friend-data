package com.reptile.main;

import com.alibaba.fastjson.JSONObject;
import com.reptile.dao.ActivityDao;
import com.reptile.dao.UserDao;
import com.reptile.dao.UserHeartDao;
import com.reptile.dao.UserReptileDao;
import com.reptile.entity.*;
import com.reptile.utils.HttpUtils;
import com.reptile.utils.JdbcUtils;
import org.apache.commons.lang.StringUtils;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 爬取 app 的数据
 */
public class ReptileUserData {

    public static String token = "eb9aa3f08cac24686323d891e50838f2";
    public static String uuid = "9a356bc94622811290b27f482181bfb5";
    public static Integer reptileUserCount = 2;

    public static void main(String[] args) throws Exception {
        // 初始化城市和经纬度
        Map<String, String> myMap = new HashMap<String, String>();
        myMap.put("深圳", "22.547456_114.085947");
        myMap.put("广州", "22.647456_112.085947");
        myMap.put("东莞", "23.447456_114.000001");
        myMap.put("上海", "31.227456_121.480001");
        myMap.put("北京", "39.567456_116.200001");
        myMap.put("杭州", "30.227456_118.480001");
        myMap.put("成都", "30.227456_103.480001");
        myMap.put("佛山", "23.227456_113.480001");
        myMap.put("中山", "22.527456_113.380001");
        myMap.put("厦门", "23.527456_118.380001");
        myMap.put("武汉", "30.527456_114.380001");
        myMap.put("长沙", "28.027456_112.380001");
        myMap.put("襄阳", "32.047456_112.085947");
        myMap.put("惠州", "22.047456_114.085947");
        myMap.put("徐州", "34.260456_117.205947");
        Connection conn = JdbcUtils.getBoomConnection();
        List<UserReptileEntity> allUserList = UserReptileDao.getAllUserList(conn);

        List<String> cityList = new ArrayList();
        cityList.add("北京");
        cityList.add("上海");
        cityList.add("广州");
        cityList.add("深圳");
        cityList.add("东莞");

        for (int n = 0; n < cityList.size(); n++) {
            String city = cityList.get(n);
            // 1.爬取主页数据
            List<UserData.DataDTO> list = new ArrayList<UserData.DataDTO>();
            for (int m = 1; m < 100; m++) {
                List<UserListData.DataDTO> homeList = getHomeList(m,city);
                for (int i = 0; i < homeList.size(); i++) {
                    String userId = homeList.get(i).getUserId();
                    if (!isContain(allUserList, userId)) {
                        UserData.DataDTO userMsg = getUserMsg(userId);
                        if (userMsg != null) {
                            List<String> datingList = userMsg.getDatingList();
                            List<String> photoList = userMsg.getPhotoList();
                            if (photoList != null && photoList.size() > 0) {
                                if (datingList != null && datingList.size() > 0) {
                                    // 过滤掉没有活动的数据
                                    String weChat = "";
                                    if (userMsg.getShowWechat() == 2) {
                                        // 直接显示微信
                                        weChat = userMsg.getWechat();
                                    } else if (userMsg.getShowWechat() == 1) {
                                        // 显示微信，但是需要请求
                                        UserWeChatData.DataDTO userWeChat = getUserWeChat(userId).getData();
                                        if (userWeChat != null) {
                                            weChat = userWeChat.getWechat();
                                            userMsg.setWechat(weChat);
                                        }
                                    }
                                    if (StringUtils.isNotBlank(weChat)) {
                                        List<UserActivityData.DataDTO> userActivity = getUserActivity(userId);
                                        userMsg.setActivity(userActivity);
                                        list.add(userMsg);
                                        UserReptileDao.insert(conn, Integer.valueOf(userId));
                                        if (list.size() >= reptileUserCount) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (list.size() >= reptileUserCount) {
                    break;
                }
            }
            // 更新用户数据
            UserDao.insertUserData(list, conn);
            // 更新用户心跳数据
            List<UserEntity> userList = UserDao.getUserList(conn, city);
            UserHeartDao.updateUserHeartData(userList, city, myMap, conn);
            // 更新活动数据
            ActivityDao.insertUserActivityData(addUserId(list, userList), conn);
        }
        JdbcUtils.closeBoom();
    }

    public static List<UserData.DataDTO> addUserId(List<UserData.DataDTO> list, List<UserEntity> userList) {
        List<UserData.DataDTO> list2 = new ArrayList<UserData.DataDTO>();
        for (int i = 0; i < list.size(); i++) {
            UserData.DataDTO dataDTO = list.get(i);
            for (int j = 0; j < userList.size(); j++) {
                UserEntity userEntity = userList.get(j);
                if (dataDTO.getWechat().equals(userEntity.getWeChat())) {
                    dataDTO.setUserId2(userList.get(j).getUid());
                    list2.add(dataDTO);
                }
            }
        }

        return list2;
    }


    public static boolean isContain(List<UserReptileEntity> list, String userId) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            Integer hUserId = list.get(i).getHUserId();
            if (userId.equals(hUserId + "")) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取主页数据
     *
     * @param page
     * @return
     */
    public static List<UserListData.DataDTO> getHomeList(int page,String city) {
        String url = "http://small.onbyway.top/api/index/userList";
        Map<String, Object> map = new HashMap();
        map.put("name", "circle");
        map.put("city", city);
        map.put("page", page);
        map.put("sort", 0);
        Map<String, Object> header = new HashMap();
        header.put("alipay", true);
        header.put("electric-charge", false);
        header.put("uuid", uuid);
        header.put("location", "114.424378&22.748955");

        String data = HttpUtils.doGet(url, map, token, header);
        UserListData data1 = JSONObject.parseObject(data, UserListData.class);
        System.out.println(data);
        List<UserListData.DataDTO> list = data1.getData();
        return list;
    }

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    public static UserData.DataDTO getUserMsg(String userId) {
        String url = "http://small.onbyway.top/api/user/homepage";
        Map<String, Object> map = new HashMap();
        map.put("h_user_id", userId);

        Map<String, Object> header = new HashMap();
        header.put("alipay", true);
        header.put("electric-charge", false);
        header.put("uuid", uuid);
        header.put("location", "114.085947&22.547456");

        String data = HttpUtils.doGet(url, map, token, header);
        UserData data1 = JSONObject.parseObject(data, UserData.class);
        UserData.DataDTO data2 = data1.getData();

        System.out.println(data);
        return data2;
    }

    /**
     * 获取用户微信
     *
     * @param userId
     * @return
     */
    public static UserWeChatData getUserWeChat(String userId) {
        String url = "http://small.onbyway.top/api/user/userWechat";
        Map<String, Object> map = new HashMap();
        map.put("to_user_id", userId);

        Map<String, Object> header = new HashMap();
        header.put("alipay", true);
        header.put("electric-charge", false);
        header.put("uuid", uuid);
        header.put("location", "114.085947&22.547456");

        String data = HttpUtils.doGet(url, map, token, header);
        UserWeChatData userWeChatData = JSONObject.parseObject(data, UserWeChatData.class);
        return userWeChatData;
    }

    /**
     * 获取用户活动信息
     *
     * @param userId
     * @return
     */
    public static List<UserActivityData.DataDTO> getUserActivity(String userId) {

        String url = "http://small.onbyway.top/api/dating/user_dating_list";
        Map<String, Object> map = new HashMap();
        map.put("other_user_id", userId);
        map.put("page", 1);

        Map<String, Object> header = new HashMap();
        header.put("uuid", uuid);
        header.put("location", "114.085947&22.547456");

        String data = HttpUtils.doGet(url, map, token, header);
        UserActivityData data1 = JSONObject.parseObject(data, UserActivityData.class);
        List<UserActivityData.DataDTO> list = data1.getData();
        return list;
    }

}
