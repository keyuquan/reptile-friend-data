package com.reptile.main;

import com.alibaba.fastjson.JSONObject;
import com.reptile.dao.UserDao;
import com.reptile.entity.UserActivityData;
import com.reptile.entity.UserData;
import com.reptile.entity.UserEntity;
import com.reptile.entity.UserListData;
import com.reptile.utils.DateUtils;
import com.reptile.utils.HttpUtils;
import com.reptile.utils.JdbcUtils;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public static void main(String[] args) throws Exception {
        getUserActivity("");
        Connection conn = JdbcUtils.getBoomConnection();
        List<UserEntity> userList = UserDao.getAllUserList(conn);

        for (int m = 20; m < 50; m++) {
            List<UserListData.DataDTO> homeList = getHomeList(m);
            List<UserData.DataDTO> list = new ArrayList<UserData.DataDTO>();
            for (int i = 0; i < homeList.size(); i++) {
                String userId = homeList.get(i).getUserId();
                UserData.DataDTO userMsg = getUserMsg(userId);
                if (!isContain(userList, userMsg.getWechat())) {
                    if (StringUtils.isNotBlank(userMsg.getWechat())) {
                        list.add(userMsg);
                    }
                }
            }
            insertUserData(list, userList, conn);
            JdbcUtils.execute(conn, "update user set  username = concat('a',100000 + id)  where username is null ");
            // 更新用户活动信息

        }
        JdbcUtils.closeBoom();
    }

    public static void insertUserData(List<UserData.DataDTO> list, List<UserEntity> userList, Connection conn) throws Exception {
        String sql = "insert  into  user (nickname,avatarurl,gender,birth,height,weight,photos,vip_level,vip_end,city,self_desc,profession,salary_year,wechat_account,flags) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if (list != null && list.size() > 0) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.clearBatch();
            for (int i = 0; i < list.size(); i++) {
                UserData.DataDTO user = list.get(i);
                ps.setObject(1, user.getNickName());
                ps.setObject(2, user.getAvatar());
                ps.setObject(3, user.getSex() - 1);
                ps.setObject(4, DateUtils.addDay(DateUtils.getSysDate(), (0 - user.getAge()) * 365));
                ps.setObject(5, user.getHeight());
                ps.setObject(6, 48);
                ps.setObject(7, JSONObject.toJSONString(user.getPhotoList()));
                ps.setObject(8, 1);
                ps.setObject(9, "2021-11-01");
                ps.setObject(10, user.getLocation());
                ps.setObject(11, user.getSignature());
                ps.setObject(12, user.getProfession());
                ps.setObject(13, user.getAnnualIncome());
                ps.setObject(14, user.getWechat());

                List<UserData.DataDTO.InterestLabelDTO> interestLabel = user.getInterestLabel();
                String flag = "";
                for (int j = 0; j < interestLabel.size(); j++) {
                    UserData.DataDTO.InterestLabelDTO interestLabelDTO = interestLabel.get(j);
                    List<String> child = interestLabelDTO.getChild();
                    for (int k = 0; k < child.size(); k++) {
                        String s = child.get(k);
                        flag = flag + s + ",";
                    }
                }
                ps.setObject(15, flag);
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
        }
    }

    public static boolean isContain(List<UserEntity> list, String weChat) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            String wechat = list.get(i).getWeChat();
            if (wechat != null && wechat.equals(weChat)) {
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
        map.put("city", "%E6%B7%B1%E5%9C%B3");
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
        Integer showWechat = data2.getShowWechat();
        // System.out.println(data);
        if (showWechat == 1) {
            // 获取微信
            System.out.println(userId);
            getUserWeChat(userId);
            // data2.setWechat();
        }
        return data2;
    }

    /**
     * 获取用户微信
     *
     * @param userId
     * @return
     */
    public static UserData.DataDTO getUserWeChat(String userId) {
        String url = "http://small.onbyway.top/api/user/userWechat";
        Map<String, Object> map = new HashMap();
        map.put("to_user_id", userId);

        Map<String, Object> header = new HashMap();
        map.put("alipay", true);
        map.put("electric-charge", false);
        map.put("uuid", uuid);
        map.put("location", "114.085947&22.547456");

        String data = HttpUtils.doGet(url, map, token, header);
        System.out.println(data);
        return null;
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
