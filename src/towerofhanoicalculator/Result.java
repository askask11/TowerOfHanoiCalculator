/*Editor: Johnson Gao 
 * Date This File Created: 2020-2-2 17:01:19
 * Description Of This Class:
 */
package towerofhanoicalculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 *
 * @author Jianqing Gao
 */
public class Result extends JFrame implements ActionListener
{

    private GFG calculator;

    private JLabel title;
    private JScrollPane tablePane;
    private JTable table;

    private JButton exportButton, closeButton;
    private Box buttonBox;

    private Clip clip;

    public static final String[] TABLE_HEADER =
    {
        "#", "Disk Moved", "From", "To"
    };

    public Result(GFG gfg)
    {
        calculator = gfg;
        initComponents();
        try
        {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException ex)
        {
            Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, ex);
            Warning.createWarningDialog(ex);
        }
    }

    private void initComponents()
    {
        //basic setting
        //setBounds(300,300,900,500);
        getContentPane().setBackground(new Color(255, 255, 204));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //JLabel
        title = new JLabel(new ImageIcon(getClass().getResource("result.png")));
        title.setFont(new Font("Times New Roman", Font.BOLD, 15));
        title.setHorizontalTextPosition(SwingConstants.CENTER);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        //JButton
        closeButton = new JButton("Close");
        closeButton.setBackground(Color.red);
        closeButton.addActionListener(this);

        exportButton = new JButton("Export");
        exportButton.setBackground(new Color(204, 255, 204));
        exportButton.addActionListener(this);

        //JTable
        table = new JTable(getData(), TABLE_HEADER);
        table.setAutoCreateRowSorter(true);
        table.setOpaque(false);
        //scrollPane

        tablePane = new JScrollPane(table);
        tablePane.setOpaque(false);
        tablePane.getViewport().setOpaque(false);

        //box
        buttonBox = Box.createHorizontalBox();
        buttonBox.add(closeButton);
        buttonBox.add(exportButton);
        buttonBox.setOpaque(false);

        //add components
        this.add(title, BorderLayout.NORTH, SwingConstants.CENTER);
        add(tablePane, BorderLayout.CENTER, SwingConstants.CENTER);
        add(buttonBox, BorderLayout.SOUTH, SwingConstants.CENTER);

        //resize
        pack();

    }

    private String[][] getData()
    {
        //variable declear
        ArrayList<String[]> dataList = calculator.getResults();
        int size = dataList.size();
        String[][] data = new String[size/*row*/][TABLE_HEADER.length + 1/*column*/];
        //parse array
        for (int i = 0; i < size; i++)
        {
            String[] strings = dataList.get(i);
            data[i][0] = (i + 1) + "";
            data[i][1] = strings[0];
            data[i][2] = strings[1];
            data[i][3] = strings[2];
        }
        //return data
        return data;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if (source.equals(closeButton))
        {
            playSound(Sounds.CLICK);
            this.dispose();
        } else
        {
            try
            {
                playSound(Sounds.CLICK);
                exportResult();
            } catch (IOException ex)
            {
                Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, ex);
                Warning.createWarningDialog(ex);
            }
        }
    }

    public void exportResult() throws IOException
    {
        JFileChooser chooser = new JFileChooser();
        int approve = chooser.showSaveDialog(this);

        if (approve == JFileChooser.APPROVE_OPTION)
        {
            exportResult0(chooser.getSelectedFile());
        }
    }

    private void exportResult0(File dir) throws IOException
    {
//        File target = new File(dir, "exportResultTower.txt");
        dir.createNewFile();
        try (FileWriter out = new FileWriter(dir))
        {
            ArrayList<String[]> list = calculator.getResults();
            out.write("######Results Exported Using Johnson's TowerOfHanoi Calculator######"
                    + "\n*********Beginning of the result ********");

            for (int i = 0; i < list.size(); i++)
            {
                String[] row = list.get(i);
                out.write("\n --> Step# " + (i + 1) + " Disk " + row[0] + " moved from " + row[1] + " to " + row[2]);
            }
            out.write("\n ******Reached the end of the result*******");
            out.write("\n #######Thank you for using Johnson's TOH calculator!#######");
            out.flush();
        }
    }

    public static void main(String[] args)
    {
        new Result(new GFG()).setVisible(true);
    }
    
    public void playSound(String path)
    {
        try
        {
            clip.close();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(path)));
            clip.start();
        } catch (Exception e)
        {
            Warning.createWarningDialog(e);
        }
    }

}
