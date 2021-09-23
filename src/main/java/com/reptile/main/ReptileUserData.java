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
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 爬取有伴的数
 */
public class ReptileUserData {

    static String token = "3ebdd9b4713102643a352fbf0c94eae6";
    static String uuid = "3ebdd9b4713102643a352fbf0994eae6";
    static String city = "深圳";

    public static void main(String[] args) throws Exception {
        Connection conn = JdbcUtils.getBoomConnection();
        List<UserReptileEntity> allUserList = UserReptileDao.getAllUserList(conn);

        // 1.爬取主页数据
        List<UserData.DataDTO> list = new ArrayList<UserData.DataDTO>();
        for (int m = 1; m < 1000; m++) {
            List<UserListData.DataDTO> homeList = getHomeList(m);
            for (int i = 0; i < homeList.size(); i++) {
                String userId = homeList.get(i).getUserId();
                if (!isContain(allUserList, userId)) {
                    // 过滤掉已经爬取过的数据
                    UserData.DataDTO userMsg = getUserMsg(userId);
                    List<String> datingList = userMsg.getDatingList();
                    if (datingList != null && datingList.size() > 0) {
                        // 过滤掉没有活动的数据
                        System.out.println(userMsg.getUserId() + "showWechat == " + userMsg.getShowWechat() + " wechat == " + userMsg.getWechat());
                        String weChat = "";
                        if (userMsg.getShowWechat() == 2) {
                            // 直接显示微信
                            weChat = userMsg.getWechat();
                        } else if (userMsg.getShowWechat() == 1) {
                            // 显示微信，但是需要请求
                            UserWeChatData.DataDTO userWeChat = getUserWeChat(userId);
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
                            if (list.size() >= 1) {
                                break;
                            }
                        }
                    }
                }
            }
            if (list.size() >= 10) {
                break;
            }
        }
        // 更新用户数据
        UserDao.insertUserData(list, conn);
        JdbcUtils.execute(conn, "update user set  username = concat('a',100000 + id)  where username is null ");
        // 更新用户心跳数据
        List<UserEntity> userList = UserDao.getSZUserList(conn);
        UserHeartDao.updateSzUserHeartData(userList, conn);
        // 更新活动数据
        ActivityDao.insertUserActivityData(addUserName(list, userList), conn);
        JdbcUtils.closeBoom();
    }

    public static List<UserData.DataDTO> addUserName(List<UserData.DataDTO> list, List<UserEntity> userList) {
        List<UserData.DataDTO> list2 = new ArrayList<UserData.DataDTO>();
        for (int i = 0; i < list.size(); i++) {
            UserData.DataDTO dataDTO = list.get(i);
            for (int j = 0; j < userList.size(); j++) {
                UserEntity userEntity = userList.get(j);
                if (dataDTO.getShowWechat().equals(userEntity.getWeChat())) {
                    dataDTO.setUsername(userList.get(j).getUsername());
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
    public static List<UserListData.DataDTO> getHomeList(int page) {
        String url = "http://app.lightyeara.cn/api/index/userList";
        Map<String, Object> map = new HashMap();
        map.put("name", "circle");
        map.put("city", URLEncoder.encode(city));
        map.put("page", page);
        map.put("sort", 0);
        Map<String, Object> header = new HashMap();
        map.put("alipay", true);
        map.put("electric-charge", false);
        map.put("uuid", uuid);
        map.put("location", "114.085947&22.547456");

        String data = HttpUtils.doGet(url, map, token, header);
        UserListData data1 = JSONObject.parseObject(data, UserListData.class);
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
        map.put("alipay", true);
        map.put("electric-charge", false);
        map.put("uuid", uuid);
        map.put("location", "114.085947&22.547456");

        String data = HttpUtils.doGet(url, map, token, header);
        UserData data1 = JSONObject.parseObject(data, UserData.class);
        UserData.DataDTO data2 = data1.getData();
        return data2;
    }

    /**
     * 获取用户微信
     *
     * @param userId
     * @return
     */
    public static UserWeChatData.DataDTO getUserWeChat(String userId) {
        String url = "http://small.onbyway.top/api/user/userWechat";
        Map<String, Object> map = new HashMap();
        map.put("to_user_id", userId);

        Map<String, Object> header = new HashMap();
        map.put("alipay", true);
        map.put("electric-charge", false);
        map.put("uuid", uuid);
        map.put("location", "114.085947&22.547456");

        String data = HttpUtils.doGet(url, map, token, header);
        UserWeChatData userWeChatData = JSONObject.parseObject(data, UserWeChatData.class);
        System.out.println(data);
        return userWeChatData.getData();
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
        map.put("other_user_id", 1333628);
        map.put("page", 1);

        Map<String, Object> header = new HashMap();
        map.put("uuid", uuid);
        map.put("location", "114.085947&22.547456");

        String data = HttpUtils.doGet(url, map, token, header);
        UserActivityData data1 = JSONObject.parseObject(data, UserActivityData.class);
        List<UserActivityData.DataDTO> list = data1.getData();
        return list;
    }

}
