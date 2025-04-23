package com.urise.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

public class SqlHelper {
    public static Connection createConnection(ConnectionFactory connectionFactory)  {
        try {
            return connectionFactory.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
