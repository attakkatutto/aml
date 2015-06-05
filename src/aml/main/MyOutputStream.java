package aml.main;

import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * This class extends from OutputStream to redirect output to a JTextArea
 *
 * @author www.codejava.net
 *
 */
public class MyOutputStream extends OutputStream {

    private final JTextArea textArea;    

    public MyOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException
    {
        final String text = new String (buffer, offset, length);
        SwingUtilities.invokeLater(() -> {
            textArea.append (text);
        });
    }

    @Override
    public void write(int i) throws IOException {
        write (new byte [] {(byte)i}, 0, 1);
    }
       
}
