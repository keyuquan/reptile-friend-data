package com.reptile.utils;


import java.sql.*;
import java.util.List;

public class JdbcUtils {
    private static Connection conn_boom = null;

    /**
     * 加载驱动
     */
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//注册加载驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 Connetion
     *
     * @return
     * @throws SQLException
     */
    public static Connection getBoomConnection() throws Exception {
        conn_boom = DriverManager.getConnection("jdbc:mysql://8.134.59.113:3306/friend?useUnicode=true&characterEncoding=utf8", "root", "FFS0PG3pBWZ3dji6Yn");
        return conn_boom;
    }


    /**
     * 关闭 conn
     */
    public static void closeBoom() {
        if (conn_boom != null) {
            try {
                conn_boom.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void execute(Connection conn, String sql) throws SQLException {
        long start = System.currentTimeMillis();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.execute();
        stmt.close();
        long end = System.currentTimeMillis();
    }


    public static void doBatch(String sql, List<List<Object>> list, Connection conn) throws Exception {
        if (list != null && list.size() > 0) {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.clearBatch();
            for (int i = 0; i < list.size(); i++) {
                List<Object> p = list.get(i);
                for (int j = 0; j < p.size(); j++) {
                    preparedStatement.setObject(j + 1, p.get(j));
                }
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            preparedStatement.close();
        }

    }

    public static void main(String[] args) throws Exception {
        Connection boomConnection = getBoomConnection();
    }
}
