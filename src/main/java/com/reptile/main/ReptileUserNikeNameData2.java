package com.reptile.main;

import com.alibaba.fastjson.JSONObject;
import com.reptile.entity.ActivityListData;
import com.reptile.utils.FileUtils;
import com.reptile.utils.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 爬取用户昵称
 */
public class ReptileUserNikeNameData2 {

    public static String token = "2500d9fe2ef4e29c04ac7e00ff9b6d28";
    public static String uuid = "3ebdd9b4713102643a352fbf0994eae6";

    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap();

        for (int m = 1; m < 10000; m++) {
            List<ActivityListData.DataDTO> homeList = getActivityList(m);

            for (int i = 0; i < homeList.size(); i++) {
                ActivityListData.DataDTO dataDTO = homeList.get(i);
                if (dataDTO.getSex() == 2) {
                    String nickName = dataDTO.getNickName();
                    if (nickName.contains("_")) {
                        nickName = nickName.split("_")[0];
                        if (nickName.length() >= 2 && nickName.length() <= 5) {
                            Integer integer = map.get(nickName);
                            if (integer == null) {
                                map.put(nickName, 1);
                                System.out.println(m+"--"+nickName);
                                FileUtils.appendToFile("bb.txt", nickName);
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * 获取主页数据
     *
     * @param page
     * @return
     */
    public static List<ActivityListData.DataDTO> getActivityList(int page) {
        String url = "http://small.onbyway.top/api/dating/dating_list";
        Map<String, Object> map = new HashMap();
        map.put("page", page);
        map.put("sex", 0);
        map.put("activity_type_id", 0);
        Map<String, Object> header = new HashMap();
        header.put("alipay", true);
        header.put("electric-charge", false);
        header.put("uuid", uuid);
        header.put("location", "114.424378&22.748955");

        String data = HttpUtils.doGet(url, map, token, header);
        ActivityListData data1 = JSONObject.parseObject(data, ActivityListData.class);
        List<ActivityListData.DataDTO> list = data1.getData();
        return list;
    }


}
