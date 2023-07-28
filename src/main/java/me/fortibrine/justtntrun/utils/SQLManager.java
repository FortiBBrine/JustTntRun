package me.fortibrine.justtntrun.utils;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public class SQLManager implements Closeable {

    private Connection connection;

    public SQLManager() {
        try {
            String url = "jdbc:sqlite:plugins/JustTntRun/statistic.db";

            connection = DriverManager.getConnection(url);

            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS block (" +
                    "uuid TEXT PRIMARY KEY," +
                    "blocks INTEGER" +
                    ")");

            preparedStatement.executeUpdate();
            preparedStatement.close();

            preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS wins (" +
                    "uuid TEXT PRIMARY KEY," +
                    "wins INTEGER" +
                    ")");

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public int getWins(String uuid) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT wins FROM wins WHERE uuid = ?");

            preparedStatement.setString(1, uuid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int result = resultSet.getInt("blocks");

                preparedStatement.close();
                resultSet.close();

                return result;
            } else {

                preparedStatement.close();
                resultSet.close();

                return 0;
            }

        } catch (SQLException exception) {
            return 0;
        }
    }

    public void addWin(String uuid) {
        try {

            int wins = this.getWins(uuid) + 1;

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE wins SET wins = ? WHERE uuid = ?");

            preparedStatement.setInt(1, wins);
            preparedStatement.setString(2, uuid);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated == 0) {
                PreparedStatement preparedStatementInsert = connection.prepareStatement("INSERT INTO wins (uuid, wins) VALUES (?, ?)");
                preparedStatementInsert.setString(1, uuid);
                preparedStatementInsert.setInt(2, wins);

                rowsUpdated = preparedStatementInsert.executeUpdate();

                preparedStatementInsert.close();
            }

            preparedStatement.close();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public int getBlocks(String uuid) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT blocks FROM block WHERE uuid = ?");

            preparedStatement.setString(1, uuid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int result = resultSet.getInt("blocks");

                preparedStatement.close();
                resultSet.close();

                return result;
            } else {

                preparedStatement.close();
                resultSet.close();

                return 0;
            }

        } catch (SQLException exception) {
            return 0;
        }

    }

    public void setBlocks(String uuid, int blocks) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE block SET blocks = ? WHERE uuid = ?");

            preparedStatement.setInt(1, blocks);
            preparedStatement.setString(2, uuid);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated == 0) {
                PreparedStatement preparedStatementInsert = connection.prepareStatement("INSERT INTO block (uuid, blocks) VALUES (?, ?)");
                preparedStatementInsert.setString(1, uuid);
                preparedStatementInsert.setInt(2, blocks);

                rowsUpdated = preparedStatementInsert.executeUpdate();

                preparedStatementInsert.close();
            }

            preparedStatement.close();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void close() {

        try {
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
