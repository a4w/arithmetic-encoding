import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class WinArithmetic extends JFrame {

    public String probFile;
    JButton compress_btn;
    JButton decompress_btn;

    WinArithmetic() {
        super("Arithmetic compress/decompress");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
        this.setSize(200, 100);

        JButton select_prob_btn = new JButton("Select probability file");
        compress_btn = new JButton("Compress file");
        compress_btn.setEnabled(false);
        decompress_btn = new JButton("Decompress file");
        decompress_btn.setEnabled(false);


        select_prob_btn.addActionListener(new SelectProbFile(this));
        compress_btn.addActionListener(new CompressFile(this));
        decompress_btn.addActionListener(new DecompressFile(this));

        this.add(select_prob_btn);
        this.add(compress_btn);
        this.add(decompress_btn);
        this.pack();
        this.setVisible(true);
    }

    class SelectProbFile implements ActionListener {

        WinArithmetic parent;

        SelectProbFile(WinArithmetic parent) {
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(WinArithmetic.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                this.parent.probFile = fileChooser.getSelectedFile().toString();
                this.parent.compress_btn.setEnabled(true);
                this.parent.decompress_btn.setEnabled(true);
            }
        }

    }

    class CompressFile implements ActionListener {

        WinArithmetic parent;

        CompressFile(WinArithmetic parent) {
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(WinArithmetic.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                // Read file
                File selected = fileChooser.getSelectedFile();
                try {
                    ArithmeticEncodingFiles.encodeFile(this.parent.probFile, selected.toString(), selected.getAbsolutePath() + ".ari");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                }else{
                    // die
                }
        }
    }
    

    class DecompressFile implements ActionListener {

        WinArithmetic parent;

        DecompressFile(WinArithmetic parent) {
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(WinArithmetic.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                // Read file
                File selected = fileChooser.getSelectedFile();
                try {
                    ArithmeticEncodingFiles.decodeFile(this.parent.probFile, selected.toString(), selected.getAbsolutePath() + ".txt");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                }else{
                    // die
                }
        }
    }

}