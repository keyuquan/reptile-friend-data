package com.reptile.dao;

import com.alibaba.fastjson.JSONObject;
import com.reptile.entity.UserData;
import com.reptile.entity.UserEntity;
import com.reptile.utils.DateUtils;
import com.reptile.utils.FileDownloadUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

    /**
     * 获取深圳的虚拟客户
     *
     * @param conn
     * @return
     */
    public static List<UserEntity> getUserList(Connection conn, String city) {
        try {
            String sql = "select uid , name,we_chat_account weChat from  user  where  city like'%" + city + "%'  and mobile is null";
            List<UserEntity> list = (List<UserEntity>) new QueryRunner().query(conn, sql, new BeanListHandler(UserEntity.class));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 插入用户数据
     *
     * @param list
     * @param conn
     * @throws Exception
     */
    public static void insertUserData(List<UserData.DataDTO> list, Connection conn) throws Exception {
        String sql = "insert  into  user (name,icon,gender,birth,height,weight,photos,vip_level,vip_end,city,self_desc,profession,salary_year,we_chat_account,flags,create_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if (list != null && list.size() > 0) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.clearBatch();
            for (int i = 0; i < list.size(); i++) {
                UserData.DataDTO user = list.get(i);
                ps.setObject(1, user.getNickName());
                ps.setObject(2, user.getAvatar().replace("https://dating-1256663796.file.myqcloud.com/avatar/", "https://g7-stone.oss-cn-guangzhou.aliyuncs.com/uploads/heads/"));
                FileDownloadUtil.downloadHttpUrl(user.getAvatar(), "heads");
                ps.setObject(3, user.getSex() - 1);
                ps.setObject(4, DateUtils.addDay(DateUtils.getSysDate(), (0 - user.getAge()) * 365));
                ps.setObject(5, user.getHeight());
                ps.setObject(6, 48);
                List<String> photoList = user.getPhotoList();
                for (int j = 0; j < photoList.size(); j++) {
                    FileDownloadUtil.downloadHttpUrl(photoList.get(j), "pic");
                }
                ps.setObject(7, JSONObject.toJSONString(user.getPhotoList())
                        .replaceAll("https://dating-1256663796.file.myqcloud.com/dating/", "https://g7-stone.oss-cn-guangzhou.aliyuncs.com/uploads/pic/")
                        .replaceAll("https://dating-1256663796.file.myqcloud.com/report/", "https://g7-stone.oss-cn-guangzhou.aliyuncs.com/uploads/pic/")
                        .replaceAll("https://dating-1256663796.cos.ap-shanghai.myqcloud.com/photo/", "https://g7-stone.oss-cn-guangzhou.aliyuncs.com/uploads/pic/"));
                ps.setObject(8, 1);
                ps.setObject(9, "2030-11-01");
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
                ps.setObject(16, DateUtils.addSecond(DateUtils.getSysFullDate(), 0 - (int) (Math.random() * 7200 + 7201)));
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
        }
    }


}
