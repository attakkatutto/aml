/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.entity;

import aml.global.Config;
import aml.global.Enums.PersistenceMode;
import aml.graph.MyNode;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * DB/File csv manager create synthetic database
 *
 * @author ddefalco
 */
public class SynthDB {

    public final String DB_DRIVER;
    public final String DB_CONNECTION;
    public final String DB_USER;
    public final String DB_PASSWORD;
    private final String HEADER_TRANSACTION_FILE = " ID, ID_SOURCE, ID_TARGET, MONTH, YEAR, AMOUNT, SOURCE_TYPE, TARGET_TYPE, HONEST \n";
    private final String ROW_TRANSACTION_FILE = " %s, %s, %s, %s, %s, %s, %s, %s, %s \n";

    private final String HEADER_ENTITY_FILE = " ID, TYPE, HONEST, FRAUD \n";
    private final String ROW_ENTITY_FILE = " %s, %s, %s, %s \n";
//    
    BufferedWriter bwt, bwp;
    PersistenceMode mode;
    Connection dbConnection;

    public SynthDB() {
        this.mode = Config.instance().getPersistenceMode();
        DB_DRIVER = Config.instance().getDataBaseDriver();
        DB_CONNECTION = Config.instance().getDataBaseConnection();
        DB_USER = Config.instance().getDataBaseUsername();
        DB_PASSWORD = Config.instance().getDataBasePassword();
        switch (mode) {
            case FILE:
                try {
                    createFiles();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case DATABASE:
                dbConnection = initDBConnection();
                cleanTables();
                break;
            case ALL:
                try {
                    createFiles();
                    dbConnection = initDBConnection();
                    cleanTables();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    public void writeEntity(MyNode n) {
        switch (mode) {
            case FILE: {
                try {
                    writeEntityFile(n);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case DATABASE: {
                try {
                    insertEntityIntoTable(n);
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case ALL: {
                try {
                    writeEntityFile(n);
                    insertEntityIntoTable(n);
                } catch (SQLException | IOException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void writeTransaction(Transaction t) {
        switch (mode) {
            case FILE: {
                try {
                    writeTransactionFile(t);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case DATABASE: {
                try {
                    insertTransactionIntoTable(t);
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case ALL: {
                try {
                    writeTransactionFile(t);
                    insertTransactionIntoTable(t);
                } catch (SQLException | IOException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void insertTransactionIntoTable(Transaction t) throws SQLException {
        PreparedStatement preparedStatement = null;
        String insertTableSQL = "INSERT INTO TRANSACTIONS"
                + "(ID, ID_SOURCE, ID_TARGET, MONTH, YEAR, AMOUNT, SOURCE_TYPE, TARGET_TYPE, FRAUD) VALUES"
                + "(?,?,?,?,?,?,?,?)";
        try {
            dbConnection.setAutoCommit(true);
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, t.getId());
            preparedStatement.setString(2, t.getIdSource());
            preparedStatement.setString(3, t.getIdTarget());
            preparedStatement.setInt(4, t.getMonth() + 1);
            preparedStatement.setInt(5, t.getYear());
            preparedStatement.setDouble(6, t.getAmount());
            preparedStatement.setString(7, t.getSourceType());
            preparedStatement.setString(8, t.getTargetType());
            // execute insert SQL stetement
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    private void cleanTables() {
        try {
            Statement stTruncate1 = dbConnection.createStatement();
            stTruncate1.executeUpdate("TRUNCATE TABLE TRANSACTIONS");

            Statement stTruncate2 = dbConnection.createStatement();
            stTruncate2.executeUpdate("TRUNCATE TABLE ENTITIES");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createFiles() throws IOException {
        /*Create the entities file*/
        File filep = new File("." + File.separator + "dbfiles" + File.separator + String.format(Config.instance().getFileNameEntity(), System.currentTimeMillis()));
        FileWriter fwp = new FileWriter(filep.getAbsoluteFile(), true);
        bwp = new BufferedWriter(fwp);
        bwp.write(HEADER_ENTITY_FILE);
        /*Create the transaction file*/
        File filet = new File("." + File.separator + "dbfiles" + File.separator + String.format(Config.instance().getFileNameTransaction(), System.currentTimeMillis()));
        FileWriter fwt = new FileWriter(filet.getAbsoluteFile(), true);
        bwt = new BufferedWriter(fwt);
        bwt.write(HEADER_TRANSACTION_FILE);
    }

    private void writeTransactionFile(Transaction t) throws IOException {
        if (bwt != null) {
            bwt.write(String.format(ROW_TRANSACTION_FILE, t.getId(), t.getIdSource(), t.getIdTarget(), t.getMonth(), t.getYear(), t.getAmount(), t.getSourceType(), t.getTargetType(), t.getFraud()));
        }
    }

    private void writeEntityFile(MyNode n) throws IOException {
        if (bwp != null) {
            bwp.write(String.format(ROW_ENTITY_FILE, n.getId(), n.getType(), (n.isHonest()) ? "YES" : "NO", "0"));
        }
    }

    private void insertEntityIntoTable(MyNode n) throws SQLException {
        PreparedStatement preparedStatement = null;
        String insertTableSQL = "INSERT INTO ENTITIES"
                + "(ID, TYPE, HONEST, FRAUD) VALUES"
                + "(?,?,?,?)";
        try {
            dbConnection.setAutoCommit(true);
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, n.getId());
            preparedStatement.setString(2, n.getType().name());
            preparedStatement.setString(3, (n.isHonest()) ? "YES" : "NO");
            preparedStatement.setDouble(4, 0);            
            // execute insert SQL stetement
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    private void closeFiles() throws IOException {
        if (bwt != null) {
            bwt.close();
        }
        if (bwp != null) {
            bwp.close();
        }
    }

    private void closeDB() throws SQLException {
        if (dbConnection != null) {
            dbConnection.close();
        }
    }

    public void close() {
        switch (mode) {
            case FILE: {
                try {
                    closeFiles();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case DATABASE: {
                try {
                    closeDB();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case ALL: {
                try {
                    closeFiles();
                    closeDB();
                } catch (IOException | SQLException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
        }
    }

    private Connection initDBConnection() {
        try {
            Class.forName(DB_DRIVER);
            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(SynthDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dbConnection;
    }
}
