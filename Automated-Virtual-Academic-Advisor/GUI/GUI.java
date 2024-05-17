package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Initial GUI. First thing to see when running.
 */
public class GUI extends JFrame implements ActionListener {
  private boolean isCardFrontVisible;
  private JTabbedPane tabs;
  private CardLayout cardLayout;
  private JPanel cardContainer, cardFront, cardBack;
  private JButton new_student_btn, current_student_btn, open_file_btn, add_manually_btn;

  public GUI() {
    tabs = new JTabbedPane(JTabbedPane.TOP);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    init();
  }

  private void init() {

    // Create panels for new windows (content displayed within tabs)
    JPanel homePanel = createHomePanel();
    JPanel aboutPanel = createAboutPanel();

    // Add panels to the tabbed pane
    tabs.addTab("Home", homePanel);
    tabs.addTab("About", aboutPanel);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(tabs, BorderLayout.CENTER);

    setSize(700, 500);
    setVisible(true);
  }

  private JPanel createHomePanel() {
    JPanel homePanel;
    homePanel = new JPanel(new BorderLayout());
    homePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    homePanel.add(Box.createHorizontalStrut(60), BorderLayout.WEST);
    homePanel.add(Box.createHorizontalStrut(60), BorderLayout.EAST);

    // Welcome,and select option text fields
    FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
    JPanel outerPanel = new JPanel(flowLayout);
    JPanel textPanel = new JPanel(new GridLayout(0, 1));
    textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Main title and description
    JLabel titleLabel = new JLabel("Welcome to the Automatic Academic Advisor");
    titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
    titleLabel.setHorizontalAlignment(JLabel.CENTER);
    textPanel.add(titleLabel, BorderLayout.CENTER);

    JLabel descriptionLabel = new JLabel("To start, please select an option below:");
    descriptionLabel.setFont(new Font("Serif", Font.PLAIN, 18));
    descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
    textPanel.add(descriptionLabel, BorderLayout.SOUTH);

    homePanel.add(textPanel, BorderLayout.NORTH);

    // copyright panel
    JPanel copyRightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel copyrightLabel = new JLabel(
        "© 2024 Lebanese American University. All rights reserved.");
    copyrightLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
    copyRightPanel.add(copyrightLabel);
    homePanel.add(copyRightPanel, BorderLayout.SOUTH);

    // panel with both choices
    JPanel choicesPanel = new JPanel();

    // choice of current student is a card to flip
    cardFront = createCardFrontPanel();
    cardBack = createCardBackPanel();
    cardContainer = new JPanel();
    cardLayout = new CardLayout();
    cardContainer.setLayout(cardLayout);
    cardContainer.add(cardFront, "FRONT");
    cardContainer.add(cardBack, "BACK");
    isCardFrontVisible = true;

    // choice of new student is a button
    new_student_btn = new JButton("New Student");
    new_student_btn.setPreferredSize(new Dimension(150, 100));
    new_student_btn.addActionListener(this);

    choicesPanel.add(new_student_btn);
    choicesPanel.add(cardContainer);

    cardContainer.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {

        if (isCardFrontVisible) {
          cardLayout.show(cardContainer, "BACK");
          isCardFrontVisible = false;
        } else {
          cardLayout.show(cardContainer, "FRONT");
          isCardFrontVisible = true;
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
      }

      @Override
      public void mouseReleased(MouseEvent e) {
      }

      @Override
      public void mouseEntered(MouseEvent e) {
      }

      @Override
      public void mouseExited(MouseEvent e) {
      };
    });

    homePanel.add(choicesPanel, BorderLayout.CENTER);

    // Add the home panel to the frame
    return homePanel;
  }

  private JPanel createCardFrontPanel() {
    JPanel panel = new JPanel();
    current_student_btn = new JButton("Current Student");
    current_student_btn.setPreferredSize(new Dimension(150, 100));
    current_student_btn.addActionListener(this);
    panel.add(current_student_btn);
    return panel;
  }

  private JPanel createCardBackPanel() {
    JPanel panel = new JPanel(new GridLayout(0, 1));
    panel.setBorder(BorderFactory.createEtchedBorder());
    open_file_btn = new JButton("Open File");
    open_file_btn.setPreferredSize(new Dimension(20, 20));
    open_file_btn.addActionListener(this);
    add_manually_btn = new JButton("Enter manually");
    add_manually_btn.setPreferredSize(new Dimension(20, 20));
    add_manually_btn.addActionListener(this);
    panel.add(open_file_btn);
    panel.add(add_manually_btn);
    return panel;
  }

  private JPanel createAboutPanel() {
    JPanel aboutPanel = new JPanel(new BorderLayout());
    aboutPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // More balanced padding

    JTextPane textPane = new JTextPane();
    textPane.setContentType("text/html");
    textPane.setEditable(false);
    textPane.setText(
        "<html><body style='font-family: SansSerif; font-size: 12pt;'>" +
            "<h1>Welcome to the Automatic Academic Advisor</h1>" +
            "<p>This application is designed to assist students in planning their academic journey effectively. " +
            "Below you can find information about course scheduling, degree requirements, and more.</p>" +
            "<p>For more information, please visit our <a href='http://www.lau.edu.lb'>website</a>.</p>" +
            "</body></html>");
    JScrollPane scrollPane = new JScrollPane(textPane);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    aboutPanel.add(scrollPane, BorderLayout.CENTER);

    // Enhanced copyright panel
    JPanel copyRightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel copyrightLabel = new JLabel(
        "© 2024 Lebanese American University. All rights reserved.");
    copyrightLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
    copyRightPanel.add(copyrightLabel);
    aboutPanel.add(copyRightPanel, BorderLayout.SOUTH);

    return aboutPanel;
  }

  /** Click behaviors on different elements */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == new_student_btn) {
      GUICreateStudent.createStudent(true).init();

    } else if (e.getSource() == add_manually_btn) {
      GUICreateStudent.createStudent(false).init();

    } else if (e.getSource() == open_file_btn) {
      File f = openFile();
      if (f != null) {

        new GUIStudent(f);
        // this.setVisible(false);
        // dispose();
      }

    } else if (e.getSource() == current_student_btn) {
      cardLayout.show(cardContainer, "BACK");
      isCardFrontVisible = false;
    } else {
      if (!isCardFrontVisible) {
        cardLayout.show(cardContainer, "FRONT");
        isCardFrontVisible = true;
      }
    }

  }

  private File openFile() {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("User Files (*.us)", "us");
    fileChooser.setFileFilter(filter);
    File projectDir = new File(System.getProperty("user.dir"));
    fileChooser.setCurrentDirectory(projectDir);
    int result = fileChooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      return selectedFile;
    } else {
      return null;
    }
  }

  public static void main(String[] args) {
    new GUI();
  }

}
