/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ddefalco
 */
public class SynthDB {

    private final String DB_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private final String DB_CONNECTION = "jdbc:derby://localhost:1527/synthetic";
    private final String DB_USER = "sa";
    private final String FILE_NAME = "C:\\SYNTHETIC.csv";
    private final String HEADER_FILE = " ID, ID_SOURCE, ID_TARGET, MONTH, AMOUNT, SOURCE_TYPE, TARGET_TYPE \n";
    private final String ROW_FILE = " %s, %s, %s, %s, %s, %s, $s \n";
    private final String DB_PASSWORD = "password";
    private final Object LOCK = new Object();
    private FileWriter fw;

    public SynthDB() {
        try {
            File file = new File(FILE_NAME);
            fw = new FileWriter(file.getAbsoluteFile(), true);
            fw.write(HEADER_FILE);
        } catch (IOException ex) {
            Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public static SynthDB instance() {
//        return SynthDBHolder.INSTANCE;
//    }

//    private static class SynthDBHolder {
//
//        private static final SynthDB INSTANCE = new SynthDB();
//    }

    public void insertRecordIntoTable(Transaction t) throws SQLException {
        synchronized (LOCK) {
            Connection dbConnection = null;
            PreparedStatement preparedStatement = null;
            String insertTableSQL = "INSERT INTO TRANSACTIONS"
                    + "(ID, ID_SOURCE, ID_TARGET, MONTH, AMOUNT, SOURCE_TYPE, TARGET_TYPE) VALUES"
                    + "(?,?,?,?,?,?,?)";
            try {

                dbConnection = getDBConnection();
                dbConnection.setAutoCommit(false);
                dbConnection.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
                preparedStatement = dbConnection.prepareStatement(insertTableSQL);
                preparedStatement.setString(1, t.getId());
                preparedStatement.setString(2, t.getIdSource());
                preparedStatement.setString(3, t.getIdTarget());
                preparedStatement.setInt(4, t.getMonth() + 1);
                preparedStatement.setDouble(5, t.getAmount());
                preparedStatement.setString(6, t.getSourceType());
                preparedStatement.setString(7, t.getTargetType());
                dbConnection.commit();
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
    }

    public void writeFile(Transaction t) {
        if (fw != null) {
            synchronized (LOCK) {
                try {
                    fw.write(String.format(ROW_FILE, t.getId(), t.getIdSource(), t.getIdTarget(), t.getMonth(), t.getAmount(), t.getSourceType(), t.getTargetType()));
                } catch (IOException ex) {
                    Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dbConnection;
    }
}
