package com.reptile.main;

import com.reptile.dao.UserDao;
import com.reptile.entity.UserEntity;
import com.reptile.utils.JdbcUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Random;

/**
 * 爬取有伴的数
 */
public class UpdateUserHeartData {

    public static void main(String[] args) throws Exception {
        Connection conn = JdbcUtils.getBoomConnection();
        List<UserEntity> userList = UserDao.getUserList(conn);
        updateUserHeartData(userList, conn);
        JdbcUtils.closeBoom();
    }


    public static void updateUserHeartData(List<UserEntity> list, Connection conn) throws Exception {
        String sql = "insert into  user_heart(username,city,latitude,longitude)  values (?,?,?,?) on  duplicate key update  city=?,latitude=?,longitude=?";
        if (list != null && list.size() > 0) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.clearBatch();
            for (int i = 0; i < list.size(); i++) {
                UserEntity user = list.get(i);
                double a = new BigDecimal(22.547456 + new Random().nextDouble() ).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
                double b = new BigDecimal(114.085947 + new Random().nextDouble() * 2).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
                System.out.println(user.getUsername() + "|" + "深圳" + "|" + a + "|" + b);
                ps.setObject(1, user.getUsername());
                ps.setObject(2, "深圳");
                ps.setObject(3, a);
                ps.setObject(4, b);
                ps.setObject(5, "深圳");
                ps.setObject(6, a);
                ps.setObject(7, b);
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
        }
    }

}
