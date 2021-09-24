package com.reptile.dao;

import com.alibaba.fastjson.JSONObject;
import com.reptile.entity.UserActivityData;
import com.reptile.entity.UserData;
import com.reptile.utils.DateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动表插入
 */
public class ActivityDao {
    public static void insertUserActivityData(List<UserData.DataDTO> list, Connection conn) throws Exception {
        String sql = "insert into  activity(type,username,city,dest_type,time_type,time,instr,images,status) values (?,?,?,?,?,?,?,?,?)";
        if (list != null && list.size() > 0) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.clearBatch();
            for (int i = 0; i < list.size(); i++) {
                UserData.DataDTO dataDTO = list.get(i);
                List<UserActivityData.DataDTO> activity = dataDTO.getActivity();
                if (activity != null && activity.size() > 0) {
                    for (int j = 0; j < activity.size(); j++) {
                        UserActivityData.DataDTO dataDTO1 = activity.get(j);
                        Integer type = (int) (Math.random() * 4 + 1);
                        Integer destType = (int) (Math.random() * 6 + 1);

                        Integer timeType = (int) (Math.random() * 2 + 1);
                        ps.setObject(1, type);
                        ps.setObject(2, dataDTO.getUsername());
                        ps.setObject(3, dataDTO.getLocation());
                        ps.setObject(4, destType);
                        ps.setObject(5, timeType);
                        ps.setObject(6, DateUtils.addDay(DateUtils.getSysDate(), 10));
                        ps.setObject(7, dataDTO1.getContent());

                        List<UserActivityData.DataDTO.PhotoDTO> photo = dataDTO1.getPhoto();
                        List<String> listPhoto = new ArrayList<String>();
                        for (int k = 0; k < photo.size(); k++) {
                            listPhoto.add(photo.get(k).getUrl());
                        }
                        ps.setObject(8, JSONObject.toJSONString(listPhoto));
                        ps.setObject(9, 1);
                        ps.addBatch();
                    }
                }
            }
            ps.executeBatch();
            ps.close();
        }
    }
}
