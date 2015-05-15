/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aml.global;

import static aml.global.Constant.WRITER_FILENAME;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

/**
 * Write to file CSV and building the DataBase
 *
 * @author ddefalco
 */
public class Writer {

    BufferedWriter writer;

    public Writer() throws IOException {
        writer = new BufferedWriter(new FileWriter(String.format(WRITER_FILENAME,System.currentTimeMillis())));
        writeHeader();
    }

    private void writeHeader() throws IOException {
        writer.write("idTransaction,idMainUser,classMainUser,month,amount,idSecondaryUser,classSecondaryUser\n");
    }

    public void write(String idTransaction, String idSourceUser, String classMainUser, 
            int month, double amount,String idTargetUser, String classSecondaryUser) 
            throws IOException {
        writer.write(idTransaction + "," + idSourceUser + "," + classMainUser + "," + month + "," + amount + "," + idTargetUser + "," + classSecondaryUser + "\n");
    }

    public void close() throws IOException {
        writer.close();
    }
}
