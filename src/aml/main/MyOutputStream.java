package aml.main;
 
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JTextArea;
 
/**
 * This class extends from OutputStream to redirect output to a JTextArea
 * @author www.codejava.net
 *
 */
public class MyOutputStream extends OutputStream {
    private final JTextArea textArea;
     
    public MyOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
        textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
