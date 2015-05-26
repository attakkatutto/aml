/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author DAVIDE
 */
public class DBSynthetic {

    private static final String DB_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String DB_CONNECTION = "jdbc:derby://localhost:1527/synthetic";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void insertRecordIntoTable(Transaction t) throws SQLException {

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO DBUSER"
                + "(ID, ID_SOURCE, ID_TARGET, MONTH, SOURCE_TYPE, TARGET_TYPE) VALUES"
                + "(?,?,?,?,?,?)";

        try {
            dbConnection = getDBConnection();
            dbConnection.setAutoCommit(true);
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, t.getId());
            preparedStatement.setString(2, t.getIdSource());
            preparedStatement.setString(3, t.getIdTarget());
            preparedStatement.setInt(4, t.getMonth());
            preparedStatement.setString(5, t.getSourceType());
            preparedStatement.setString(6, t.getTargetType());

            // execute insert SQL stetement
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }

}
