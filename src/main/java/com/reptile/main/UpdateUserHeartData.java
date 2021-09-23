package com.reptile.main;

import com.reptile.dao.UserDao;
import com.reptile.dao.UserHeartDao;
import com.reptile.entity.UserEntity;
import com.reptile.utils.JdbcUtils;

import java.sql.Connection;
import java.util.List;

/**
 * 爬取有伴的数
 */
public class UpdateUserHeartData {

    public static void main(String[] args) throws Exception {
        Connection conn = JdbcUtils.getBoomConnection();
        List<UserEntity> userList = UserDao.getUserList(conn);
        UserHeartDao.updateUserHeartData(userList, conn);
        JdbcUtils.closeBoom();
    }


}
