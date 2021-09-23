package com.reptile.dao;

import com.reptile.entity.UserReptileEntity;
import com.reptile.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserReptileDao {
    /**
     * 获取巨量token 和 广告主ID
     *
     * @param conn
     * @return
     */
    public static List<UserReptileEntity> getAllUserList(Connection conn) {
        try {
            String sql = "select  id,h_user_id hUserId from  user_reptile ";
            List<UserReptileEntity> list = (List<UserReptileEntity>) new QueryRunner().query(conn, sql, new BeanListHandler(UserReptileEntity.class));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void insert(Connection conn, Integer hUserId) {
        try {
            String sql = "INSERT INTO user_reptile (h_user_id) VALUES(?)  on  duplicate key update update_time=now() ";
            Object[] params = {hUserId};
            int update = new QueryRunner().update(conn, sql, params);
            System.out.println("update="+update);
        } catch (SQLException e) {

        }
    }

    public static void main(String[] args) throws Exception {
        Connection conn = JdbcUtils.getBoomConnection();
        insert(conn, 100) ;
    }
}
