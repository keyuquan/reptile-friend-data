package com.reptile.dao;

import com.reptile.entity.UserEntity;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class UserHeartDao {

    /**
     * 更新深圳用户的心跳数据
     *
     * @param list
     * @param conn
     * @throws Exception
     */
    public static void updateUserHeartData(List<UserEntity> list, String city, Map<String, String> myMap, Connection conn) throws Exception {

        String s = myMap.get(city);
        Double m = Double.valueOf(s.split("_")[0]);
        Double n = Double.valueOf(s.split("_")[1]);
        String sql = "update   user set  city=?,latitude=?,longitude=? where  uid=? and  latitude is  null";
        if (list != null && list.size() > 0) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.clearBatch();
            for (int i = 0; i < list.size(); i++) {
                UserEntity user = list.get(i);
                double a = new BigDecimal(m + new Random().nextDouble()).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
                double b = new BigDecimal(n + new Random().nextDouble()).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
                System.out.println(user.getUid() + "|" + city + "|" + a + "|" + b);
                ps.setObject(1, city);
                ps.setObject(2, a);
                ps.setObject(3, b);
                ps.setObject(4, user.getUid());
                ps.addBatch();
            }
            ps.executeBatch();
            ps.close();
        }
    }
}
