/*Editor: Johnson Gao

 * Date This Class: Aug.7 2019
 * Description Of This Class: New version of waring, re-designed for user.
 */
package towerofhanoicalculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 * This class is a model of standard JFrames.
 *
 * @author Johnson Gao
 */
public class Warning extends JFrame implements ActionListener
{

    /**
     * A constant decleared for the font on the top.
     */
    final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 20);
    //final ImageManager IM = new ImageManager();
    /**
     * Holds things for the title.
     */
    private JLabel titleLabel;
    private JLabel headerLabel, reportLabel, hatLabel, nooLabel;

    private JTextArea descriptionArea, suggestionArea, detailArea;
    private JScrollPane descriptionScrollPane, suggestionScrollPane, detailPane;

    /**
     * This button disposes the frame.
     */
    private JButton returnButton;
    private JButton saveLogButton, operationButton;
    //Panels declearing seq: N - S - W - E
    /**
     * Mother panel on the north.
     */
    private JPanel northPanel;
    private JPanel southPanel;
    private Box centerBox;
    //private JPanel centralPanel;
    private Box westBox;
    private Runnable runnable;

    private File logFile;
    private Throwable throwable;

    private boolean disposeAfterFixRequested=true;
    /**
     * A description of SQL exception commonly occur in this application.
     */
    public static final String SQL_EXCEPTION_DESCTIPTION = "An exception has orrured while connecting to database or saving your data.";
    /**
     * A general suggestion for sloving SQL exception that a user may do.
     */
    public static final String SQL_EXCEPTION_SUGGESTION = "Please refer to the detail of the exception and check your data."
            + " \n Also, if that doesn't work, please report it to author"
            + "\n Johoson 932646988@qq.com"
            + "\n Thank you for your support.";
    
    
    /**
     * <body style="background-color:#99ffff;">
     * <p>
     * A general description for IOexception that may occur in this
     * application.</p>
     * </body>
     */
    public static final String IO_EXCPETION = "An IO exception has occures, that means an I/O operation has failed. \n"
            + "In this application, it is usually related to files.";

    /**
     * <body style="background-color:#99ffff;">
     * <p>
     * Some suggestions that a user may do in order to deal with an I/O
     * exception.</p>
     * </body>
     */
    public static final String IO_EXCEPTION_SUGGESTION = "1.Please make sure the file trying to open/edit is still in its original place.\n"
            + "2.Please make sure the file is a valid file for the current running function."
            + "\n 3. If all don't figure out, please save the Log file and report to author"
            + "\n Johnson E-mail: 932646988@qq.com"
            + "\nThanks for your support.";

    /**
     * <html>
     * <body style="background-color:#ffff99;">
     * <p>
     * This is a message to the user, it means the exception has logged in the
     * "detail" section.
     * <br>This massage should be used in "suggestion"area.
     * </p>
     * </body>
     * </html>
     */
    public static final String DETAIL_LISTED = "Details of this exception are listed below. If the suggestion doesn't figure out the problem,"
            + "\nPlease save the log and report to the author.";
    
    public static final String NUMBER_FM_EXCEPTION_DESCRIPTION="Please enter numbers instead of letters or other characters in the designated area.";
    
    public static final String NUMBER_FM_EXCEPTION_SUGGESTION="Please check your entrys and re-enter your numbers. If \"Fix Problem\" button is shown above, you can click on it.";

    public static final String IOOBE_EX_SUGGESTION_TABLE="Please select, or enter an existing number on this table.";
    
    public static final String IOOBE_EX_DESCRIPTION="The number you selected, or edited is invalid."
            + "It's out of bounce.";
    
    ///public static final int NUMBER_FM_EX = 1,INDEX_OOF_EX =2;
    
    /**
     * <body style="background-color:#99ff99;">
     * <p>
     * Create a warning dialog with no message and suggestion.</p>
     * </body>
     */
    public Warning()
    {
        super("Warning");
        initComponents();
        setVisible(true);
    }

    /**
     * <body style="background-color:#99ff99;">
     * <p>
     * Create a warning dialog with descriptions of the warning.</p>
     * </body>
     *
     * @param description The desctription of the error.
     */
    public Warning(String description)
    {
        this();
        java.awt.EventQueue.invokeLater(() ->
        {
            descriptionArea.setText(description);
            //setVisible(true);
        });
    }

    /**
     * Create a warning dialog with message description and if the error is
     * caused by user.
     *
     * @param description The desctiption of the exception.
     * @param isCausedByUser If the exception is caused by the user.
     */
    public Warning(String description, boolean isCausedByUser)
    {
        this(description);
        EventQueue.invokeLater(() ->
        {
            reportLabel.setVisible(!isCausedByUser);
            //setVisible(true);
        });
    }

    /**
     * Create a warning dialog with description and suggestion for the user.
     *
     * @param desctiption A patagraph that can describe the exception.
     * @param suggestion The suggestion for user to deal with the problem.
     */
    public Warning(String desctiption, String suggestion)
    {
        this(desctiption);
        EventQueue.invokeLater(() ->
        {
            suggestionScrollPane.setVisible(true);
            suggestionArea.setText(suggestion);
            repaint();
            //setVisible(true);
        });

    }

    /**
     * Create a warning dialog with description, suggestion and if it is caused
     * by the user.
     *
     * @param description The desctription of the error.
     * @param suggestion
     * @param isCausedbyUser
     */
    public Warning(String description, String suggestion, boolean isCausedbyUser)
    {
        initComponents();
        EventQueue.invokeLater(() ->
        {
            reportLabel.setVisible(!isCausedbyUser);
            suggestionScrollPane.setVisible(true);
            suggestionArea.setText(suggestion);
            descriptionArea.setText(description);
            repaint();
            setVisible(true);
        });
    }

    /**
     * Create a warning dialog with desctiption, suggestion and see the detail
     * of the exception.
     *
     * @param desctiption The desctription of the error.
     * @param suggestion The suggection provide to the user in odrer to slove
     * the problem.
     * @param throwable The exception.
     */
    public Warning(String desctiption, String suggestion, Throwable throwable)
    {
        this(desctiption, suggestion);
        EventQueue.invokeLater(() ->
        {
//            detailPane.setVisible(true);
            saveLogButton.setVisible(true);
            log(throwable);
            this.throwable = throwable;
            repaint();
            //setVisible(true);
        });
    }

    /**
     * Create a warning dialog with desctiption, suggestion and see the detail
     * of the exception.
     *
     * @param desctiption The desctription of the error.
     * @param suggestion The suggection provide to the user in odrer to slove
     * the problem.
     * @param cause The exception.
     * @param isCausedByUser If the error is caused by the user.
     */
    public Warning(String desctiption, String suggestion, Throwable cause, boolean isCausedByUser)
    {
        this(desctiption, suggestion, isCausedByUser);
        EventQueue.invokeLater(() ->
        {
            saveLogButton.setVisible(true);
            detailPane.setVisible(true);
            reportLabel.setVisible(!isCausedByUser);
            throwable = cause;
            log(throwable);
            repaint();
            //setVisible(true);
        });
    }

    /**
     * Create a warning dialog with desctiption, suggestion and see the detail
     * of the exception. And also provide a possible solution that is defined
     * for the user. Users can run the solution code with one-click.
     *
     * @param description The desctription of the error.
     * @param suggestion The suggection provide to the user in odrer to slove
     * the problem.
     * @param cause The exception.
     * @param solution The pre-defined solution for user to click.
     */
    public Warning(String description, String suggestion, Throwable cause, Runnable solution)
    {
        this(description, suggestion, cause);
        EventQueue.invokeLater(() ->
        {
            runnable = solution;
            operationButton.setVisible(true);
            saveLogButton.setVisible(true);
            repaint();
        });
    }

    /**
     * Create waring frame with all informations possible.
     *
     * @param description The description of this error.
     * @param suggestion The suggestion for users to slove the problem.
     * @param cause The exception occured.
     * @param isCausedByUser If the exception is caused by user.
     * @param solution The solution for user to slove the problem.
     */
    public Warning(String description, String suggestion, Throwable cause, boolean isCausedByUser, Runnable solution)
    {
        this(description, suggestion, cause, isCausedByUser);
        EventQueue.invokeLater(() ->
        {
            runnable = solution;
            operationButton.setVisible(true);
            saveLogButton.setVisible(true);
            repaint();
            setVisible(true);
        });
    }

    /**
     * Initalize the components.
     */
    private void initComponents()
    {
        //<editor-fold>
        /**
         * Construct and format basic settings of this frame.
         */
        //this.setBounds(100/*x align R*/, 100/*y align down*/, 791/*X-WIDTH*/, 494/*Y-Width*/);
        this.getContentPane().setBackground(new Color(255, 204, 204));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        /**
         * JLabel.
         */
        this.titleLabel = new JLabel("Warning");
        titleLabel.setFont(TITLE_FONT);
        headerLabel = new JLabel("<html>Dear valued user: <br>"
                + "Unfortunately an exception has occured. <br>"
                + "Please refer to information above<html>");
        //headerLabel.setFont(Fonts.MESSAGE_FONT);

        reportLabel = new JLabel("<html>"//I tried my best to reduce error"
                //+ " <br> but sometimes an exception does happens."
                + "<br>If you can't figure out, Please report it to author "
                + "<br>E-mail : 932646988@qq.com "
                + "Thanks for your support!</html>");
        //reportLabel.setVisible(false);
        //reportLabel.setFont(Fonts.MESSAGE_FONT.deriveFont((float) 17));

        //hatLabel = new JLabel(IM.openIcon(SourceAccessNames.DECORATE_IMAGES_FOLDER + "\\hat1.jpg", 251, 249));
        //nooLabel = new JLabel(IM.openIcon(SourceAccessNames.DECORATE_IMAGES_FOLDER + "\\Noo.jpg", 344, 313));
        //JTEXTAREA
        descriptionArea = new JTextArea(2, 9);
        descriptionArea.setOpaque(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);

        suggestionArea = new JTextArea(3, 9);
        suggestionArea.setOpaque(false);
        //suggestionArea.setText(" --No Sugges");
        suggestionArea.setLineWrap(true);
        suggestionArea.setEditable(false);

        detailArea = new JTextArea(9, 9);
        detailArea.setOpaque(false);
        detailArea.setLineWrap(true);
        detailArea.setEditable(false);

        //JScrollPane
        descriptionScrollPane = initScrollPane(descriptionArea, "Description:");
        suggestionScrollPane = initScrollPane(suggestionArea, "Suggestion: ");
        //suggestionScrollPane.setVisible(false);
        detailPane = initScrollPane(detailArea, "Detail:");
        //detailPane.setVisible(false);

        /**
         * Constructing and formatting JButton.
         */
        saveLogButton = new JButton("Save Log");
        saveLogButton.addActionListener(this);
        saveLogButton.setVisible(false);
        saveLogButton.setBackground(new Color(0, 255, 255));
        returnButton = new JButton("Return");
        returnButton.addActionListener(this);
        returnButton.setBackground(new Color(255, 204, 204));
        operationButton = new JButton("Fix Problem");
        operationButton.addActionListener(this);
        operationButton.setVisible(false);
        operationButton.setBackground(new Color(51, 255, 51));

        /**
         * Setting JPanel.
         */
        westBox = Box.createVerticalBox();
        westBox.add(headerLabel);
        westBox.add(reportLabel);
        westBox.add(descriptionScrollPane);
        westBox.add(suggestionScrollPane);
        westBox.add(detailPane);
        westBox.setOpaque(false);

        centerBox = Box.createVerticalBox();
        //centerBox.add(hatLabel);
        // centerBox.add(nooLabel);
        centerBox.setOpaque(false);

        this.northPanel = new JPanel(new FlowLayout());
        northPanel.add(titleLabel, SwingConstants.CENTER);
        northPanel.setOpaque(false);

        this.southPanel = new JPanel(new FlowLayout());
        southPanel.add(saveLogButton);
        southPanel.add(returnButton);
        southPanel.add(operationButton);
        southPanel.setOpaque(false);

        /**
         * Add components and finalize frame.
         */
        add(westBox, BorderLayout.WEST);
        add(centerBox, BorderLayout.CENTER);
        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);
        //this.setVisible(true);
        pack();
        //</editor-fold>
    }

    /**
     * Initalize a JScrollPane with a new Object.
     *
     * @param component The component to be added into the JViewPort embedded in
     * the JScrollPane.
     * @param title The title for the JScrollPane. Notice that the border of the
     * ScrollPane will be set as <code>TitledBorder</code>.
     * @return The new constructed scrollPane.
     */
    private JScrollPane initScrollPane(Component component, String title)
    {
        JScrollPane js = new JScrollPane(component);
        js.setOpaque(false);
        js.getViewport().setOpaque(false);
        js.setBorder(new TitledBorder(title));
        return js;
    }

    /**
     * Print the complete exception message into a the <code>detailArea</code>.
     *
     * @param throwable The exception to be printed.
     */
    private void log(Throwable throwable)
    {
        List<String> lines;
        try
        {
            logFile = File.createTempFile("errorinimg", ".log");
            logFile.deleteOnExit();
            try (PrintWriter writer = new PrintWriter(logFile))
            {
                //print the stack trace into the file writer.
                throwable.printStackTrace(writer);
                writer.flush();//As the writer are holding the data, flush them into the file.
            }
            lines = Files.readAllLines(logFile.toPath());//read all lines of the temp file.
            lines.forEach((line) ->
            {
                detailArea.append(line.replace("	", " ->") + '\n');
            });//Copy stuck trace into textarea.
            System.out.println("postalmailregisteration.Warning.log()");
        } catch (IOException ex)
        {
            Logger.getLogger(Warning.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Save the exception into a log file. The complete exception information
     * displayed will be shown in this log. The file will follow the format :
     * applicationname-exp date(yyyymmdd hhmmss).log; It will have ".log" as its
     * file name extension.
     *
     * @param path The parent directory of the new log file.
     * @param cause The exception to be logged.
     */
    private void saveLog(File path, Throwable cause)
    {
        File out;
        if (!path.exists())
        {
            path.mkdirs();
        }
        out = new File(path, "mailBox-exp " + new SimpleDateFormat("yyyymmdd hhmmss").format(new Date()) + ".log");
        try
        {

            out.createNewFile();
            int confirm;
            try (PrintWriter writer = new PrintWriter(out))
            {
                cause.printStackTrace(writer);
                writer.flush();
                System.out.println("postalmailregisteration.Warning.saveLog()");
            }
            confirm = javax.swing.JOptionPane.showConfirmDialog(this, "A log file has successfully created.@ " + out.getPath() + " Do you want to open now?", "Completed", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION)
            {
                Desktop.getDesktop().open(path);
            }
        } catch (IOException ex)
        {
            javax.swing.JOptionPane.showMessageDialog(this, "Failed to create log. " + ex.toString(), "Failed", javax.swing.JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Warning.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if (source.equals(returnButton))
        {
            this.dispose();
        } else if (source.equals(saveLogButton))
        {
            JFileChooser chooser = new JFileChooser();
            int confirm;
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            confirm = chooser.showSaveDialog(this);
            if (confirm == JFileChooser.APPROVE_OPTION)
            {
                saveLog(chooser.getCurrentDirectory(), throwable);
            }

        } else if (source.equals(operationButton))
        {
            //clickSound(SoundConstants.BUTTON_CLICKED_SOUND);
            runnable.run();
            
            if(disposeAfterFixRequested)
            {
                dispose();
            }

        }

    }

    /**
     * Construct a new warning dialog according to the exception.
     * The warning constructed is already truely visiable.
     * @param cause The excption caught.
     * @return The warning dialog constructed.
     */
    public static Warning createWarningDialog(Throwable cause)
    {
        if (cause instanceof IOException)
        {
            return new Warning(Warning.IO_EXCPETION, Warning.IO_EXCEPTION_SUGGESTION + Warning.DETAIL_LISTED, cause, false);
        } else if(cause instanceof SQLDataException)
        {
            return new Warning("You may have entered too long or invalid data. Please try to re-enter your data and try again."+Warning.SQL_EXCEPTION_DESCTIPTION, Warning.SQL_EXCEPTION_SUGGESTION + Warning.DETAIL_LISTED, cause, true);
        }
        else if (cause instanceof SQLException)
        {
            return new Warning(Warning.SQL_EXCEPTION_DESCTIPTION, Warning.SQL_EXCEPTION_SUGGESTION + Warning.DETAIL_LISTED, cause, false);
        }else if(cause instanceof NumberFormatException)
        {
            return new Warning(NUMBER_FM_EXCEPTION_DESCRIPTION,NUMBER_FM_EXCEPTION_SUGGESTION+DETAIL_LISTED,cause,true);
        }else if(cause instanceof IndexOutOfBoundsException)
        {
            return new Warning(IOOBE_EX_DESCRIPTION,IOOBE_EX_SUGGESTION_TABLE+DETAIL_LISTED,cause,true);
        }
        else
        {
            return new Warning("An exception has occured.", "Please refer to information above. If you can't figure out, please contact the author 932646988@qq.com " , cause);
        }
    }
    
    /**
     * Returns <code>true</code> if this frame will be disposed after user
     * clicked the "fix problem"button.
     *
     * @return  A booolean.
     */
    public boolean isDisposeAfterFixRequested()
    {
        return disposeAfterFixRequested;
    }

    public void setDisposeAfterFixRequested(boolean disposeAfterFixRequested)
    {
        this.disposeAfterFixRequested = disposeAfterFixRequested;
    }
    
    

    /**
     * Set a possible one-click executable solution for the problem
     * user met.
     * @param solution The runnable to run when user click the solution.
     */
    public void setSolution(Runnable solution)
    {
        if (solution != null)
        {
            runnable = solution;
            operationButton.setVisible(true);
        }else
        {
            System.err.println("Solution is invalid. It is null now, please check again.");
        }
    }

    public JLabel getTitleLabel()
    {
        return titleLabel;
    }

    public JLabel getHeaderLabel()
    {
        return headerLabel;
    }

    public JTextArea getDescriptionArea()
    {
        return descriptionArea;
    }

    public JTextArea getSuggestionArea()
    {
        return suggestionArea;
    }

    public JTextArea getDetailArea()
    {
        return detailArea;
    }

    public JScrollPane getDescriptionScrollPane()
    {
        return descriptionScrollPane;
    }

    public JScrollPane getDetailPane()
    {
        return detailPane;
    }

    public JButton getOperationButton()
    {
        return operationButton;
    }

    public Runnable getSolution()
    {
        return runnable;
    }

    public File getLogFile()
    {
        return logFile;
    }

    public Throwable getThrowable()
    {
        return throwable;
    }

    /**
     * Produce a little sound effect. It should be short enough since it's
     * uncontrolable.
     *
     * @param name The name of the sound to be displayed.
     */
    public void clickSound(String name)
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(name)));
            clip.start();
            clip.addLineListener((e) ->
            {
                if (e.getType().equals(LineEvent.Type.STOP))
                {
                    clip.close();
                }
            });
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex)
        {
            Logger.getLogger(Warning.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args)
    {
        try
        {
            Integer.parseInt("shabi");
        } catch (NumberFormatException e)
        {
            Warning.createWarningDialog(e);
        }

    }

}
