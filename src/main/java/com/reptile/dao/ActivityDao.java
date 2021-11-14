package com.reptile.dao;

import com.alibaba.fastjson.JSONObject;
import com.reptile.entity.ActivityEntity;
import com.reptile.entity.UserActivityData;
import com.reptile.entity.UserData;
import com.reptile.utils.DateUtils;
import com.reptile.utils.FileDownloadUtil;
import com.reptile.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动表插入
 */
public class ActivityDao {
    public static void insertUserActivityData(List<UserData.DataDTO> list, Connection conn) throws Exception {
        String sql = "insert into  activity(type,user_id,city,dest_type,time_type,time,instr,images,status,create_time) values (?,?,?,?,?,?,?,?,?,?)";
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
                        ps.setObject(2, dataDTO.getUserId2());
                        ps.setObject(3, dataDTO.getLocation());
                        ps.setObject(4, destType);
                        ps.setObject(5, timeType);
                        ps.setObject(6, DateUtils.addDay(DateUtils.getSysDate(), 10));
                        ps.setObject(7, dataDTO1.getContent());

                        List<UserActivityData.DataDTO.PhotoDTO> photo = dataDTO1.getPhoto();
                        List<String> listPhoto = new ArrayList<String>();
                        for (int k = 0; k < photo.size(); k++) {
                            listPhoto.add(photo.get(k).getUrl()
                                    .replaceAll("https://dating-1256663796.file.myqcloud.com/dating/", "https://g7-stone.oss-cn-guangzhou.aliyuncs.com/uploads/pic/")
                                    .replaceAll("https://dating-1256663796.file.myqcloud.com/report/", "https://g7-stone.oss-cn-guangzhou.aliyuncs.com/uploads/pic/")
                                    .replaceAll("https://dating-1256663796.cos.ap-shanghai.myqcloud.com/photo/", "https://g7-stone.oss-cn-guangzhou.aliyuncs.com/uploads/pic/"));
                            FileDownloadUtil.downloadHttpUrl(photo.get(k).getUrl(), "pic");
                        }
                        ps.setObject(8, JSONObject.toJSONString(listPhoto));
                        ps.setObject(9, 1);
                        ps.setObject(10, DateUtils.addSecond(DateUtils.getSysFullDate(), 0 - (int) (Math.random() * 3 * 3600 * 24 + 1)));
                        if (listPhoto.size() > 0) {
                            ps.addBatch();
                        }

                    }
                }
            }
            ps.executeBatch();
            ps.close();
        }
    }


    /**
     * 获取深圳的虚拟客户
     *
     * @param conn
     * @return
     */
    public static List<ActivityEntity> getActivityList(Connection conn) {
        try {
            String sql = "SELECT  images  from  activity where  images  is  not null";
            List<ActivityEntity> list = (List<ActivityEntity>) new QueryRunner().query(conn, sql, new BeanListHandler(ActivityEntity.class));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        Connection conn = JdbcUtils.getBoomConnection();
        List<ActivityEntity> list = getActivityList(conn);

        for (int i = 0; i < list.size(); i++) {
            ActivityEntity activityEntity = list.get(i);
            String images = activityEntity.getImages();
            ArrayList arrayList = JSONObject.parseObject(images, ArrayList.class);


            for (int j = 0; j < arrayList.size(); j++) {
                String s = arrayList.get(j).toString();

                String s2 = s.replaceAll("https://g7-stone.oss-cn-guangzhou.aliyuncs.com/uploads/pic/", "https://dating-1256663796.file.myqcloud.com/dating/");
                String s3 = s.replaceAll("https://g7-stone.oss-cn-guangzhou.aliyuncs.com/uploads/pic/", "https://dating-1256663796.file.myqcloud.com/report/");
                String s4 = s.replaceAll("https://g7-stone.oss-cn-guangzhou.aliyuncs.com/uploads/pic/", "https://dating-1256663796.cos.ap-shanghai.myqcloud.com/photo/");

                FileDownloadUtil.downloadHttpUrl(s2, "pic");
                FileDownloadUtil.downloadHttpUrl(s3, "pic");
                FileDownloadUtil.downloadHttpUrl(s4, "pic");
            }
        }

    }

}
