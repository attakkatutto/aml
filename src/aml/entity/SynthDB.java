/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.entity;

import aml.global.Enums.PersistenceMode;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//import java.sql.Connection;
//import static java.sql.Connection.TRANSACTION_SERIALIZABLE;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ddefalco
 */
public class SynthDB {

//    private final String DB_DRIVER = "org.apache.derby.jdbc.ClientDriver";
//    private final String DB_CONNECTION = "jdbc:derby://localhost:1527/synthetic";
//    private final String DB_USER = "sa";
    private final String FILE_NAME = "C:\\SYNTHETIC_%s.csv";
    private final String HEADER_FILE = " ID, ID_SOURCE, ID_TARGET, MONTH, AMOUNT, SOURCE_TYPE, TARGET_TYPE \n";
    private final String ROW_FILE = " %s, %s, %s, %s, %s, %s, %s \n";
//    private final String DB_PASSWORD = "password";
    BufferedWriter bw;

    public SynthDB(PersistenceMode mode) {
        switch (mode) {
            case FILE:
                try {
                    createFile();
                } catch (IOException ex) {
                    Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case DATABASE:
                throw new UnsupportedOperationException("Not supported yet.");
        }
    }

//    public static SynthDB instance() {
//        return SynthDBHolder.INSTANCE;
//    }
//    private static class SynthDBHolder {
//
//        private static final SynthDB INSTANCE = new SynthDB();
//    }
//    public synchronized void insertRecordIntoTable(Transaction t) throws SQLException {
//        Connection dbConnection = null;
//        PreparedStatement preparedStatement = null;
//        String insertTableSQL = "INSERT INTO TRANSACTIONS"
//                + "(ID, ID_SOURCE, ID_TARGET, MONTH, AMOUNT, SOURCE_TYPE, TARGET_TYPE) VALUES"
//                + "(?,?,?,?,?,?,?)";
//        try {
//
//            dbConnection = getDBConnection();
//            dbConnection.setAutoCommit(false);
//            dbConnection.setTransactionIsolation(TRANSACTION_SERIALIZABLE);
//            preparedStatement = dbConnection.prepareStatement(insertTableSQL);
//            preparedStatement.setString(1, t.getId());
//            preparedStatement.setString(2, t.getIdSource());
//            preparedStatement.setString(3, t.getIdTarget());
//            preparedStatement.setInt(4, t.getMonth() + 1);
//            preparedStatement.setDouble(5, t.getAmount());
//            preparedStatement.setString(6, t.getSourceType());
//            preparedStatement.setString(7, t.getTargetType());
//            dbConnection.commit();
//            // execute insert SQL stetement
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            if (preparedStatement != null) {
//                preparedStatement.close();
//            }
//            if (dbConnection != null) {
//                dbConnection.close();
//            }
//        }
//    }
    private void createFile() throws IOException {
        File file = new File(String.format(FILE_NAME, System.currentTimeMillis()));
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        bw = new BufferedWriter(fw);
        bw.write(HEADER_FILE);
    }

    public synchronized void writeFile(Transaction t) throws IOException {
        if (bw != null) {
            bw.write(String.format(ROW_FILE, t.getId(), t.getIdSource(), t.getIdTarget(), t.getMonth(), t.getAmount(), t.getSourceType(), t.getTargetType()));
        }
    }

    public void closeFile() throws IOException {
        if (bw != null) {
            bw.close();
        }
    }

//    private Connection getDBConnection() {
//        Connection dbConnection = null;
//        try {
//            Class.forName(DB_DRIVER);
//            dbConnection = DriverManager.getConnection(
//                    DB_CONNECTION, DB_USER, DB_PASSWORD);
//            return dbConnection;
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return dbConnection;
//    }
}
