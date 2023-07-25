package me.fortibrine.justtntrun.utils;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public class SQLManager implements Closeable {

    private Connection connection;
    private Statement statement;

    public SQLManager() {
        try {
            String url = "jdbc:sqlite:plugins/JustTntRun/statistic.db";

            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();

            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS block (" +
                            "uuid TEXT PRIMARY KEY," +
                            "blocks INTEGER" +
                            ")"
            );

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public int getBlocks(String uuid) {
        try {

            ResultSet resultSet = statement.executeQuery("SELECT blocks FROM block WHERE uuid = '" + uuid + "'");

            if (resultSet.next()) {
                return resultSet.getInt("blocks");
            } else {
                return 0;
            }
        } catch (SQLException exception) {
            return 0;
        }

    }

    public void setBlocks(String uuid, int blocks) {
        try {

            int rowsUpdated = statement.executeUpdate("UPDATE block SET blocks = " + blocks + " WHERE uuid = '" + uuid + "'");

            if (rowsUpdated == 0) {
                statement.executeUpdate("INSERT INTO block (uuid, blocks) VALUES ("+uuid+", "+blocks+")");

            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void close() {

        try {
            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
