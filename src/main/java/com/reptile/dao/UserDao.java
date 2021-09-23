package com.reptile.dao;

import com.reptile.entity.UserEntity;
import com.reptile.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

    /**
     * 获取巨量token 和 广告主ID
     *
     * @param conn
     * @return
     */
    public static List<UserEntity> getUserList(Connection conn) {
        try {
            String sql = "select  username,wechat_account weChat from  user  where  city like'%深圳%'  and phone is null";
            List<UserEntity> list = (List<UserEntity>) new QueryRunner().query(conn, sql, new BeanListHandler(UserEntity.class));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取巨量token 和 广告主ID
     *
     * @param conn
     * @return
     */
    public static List<UserEntity> getAllUserList(Connection conn) {
        try {
            String sql = "select  username,wechat_account weChat from  user ";
            List<UserEntity> list = (List<UserEntity>) new QueryRunner().query(conn, sql, new BeanListHandler(UserEntity.class));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) throws Exception {
        Connection conn = JdbcUtils.getBoomConnection();
        List<UserEntity> userList = getUserList(conn);
        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i).getUsername());
        }
    }

}
