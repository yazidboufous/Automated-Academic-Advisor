package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import app.GraduationPlanner;
import app.Init;
import app.InitUser;
import entities.Course;
import entities.Node;
import entities.Semester;
import entities.User;

/**
 * GUI of the student First thing to see when running.
 */
public class GUIStudent extends JFrame implements ActionListener {
  /**
   * 
   */
  private static final long serialVersionUID = 2686597542318598592L;
  private File file;
  private JTabbedPane tabs;
  private User user;

  public GUIStudent(File f) {
    tabs = new JTabbedPane(JTabbedPane.TOP);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.file = f;
    user = InitUser.loadUser(file);
    init();
  }

  private void init() {

    setSize(500, 500);
    setVisible(true);

    // Setup the menu bar
    setupMenuBar();

    // Create panels for new windows (content displayed within tabs)
    JPanel homePanel = createHomePanel();
    JPanel aboutPanel = createAboutPanel();
    JPanel courseCatalogPanel = createCatalogPanel();
    JPanel advisingPanel = createAdvisingPanel();
    JPanel userInfoPanel = createUserInfoPanel();

    // Add panels to the tabbed pane
    tabs.addTab("Home", homePanel);
    tabs.addTab("User Info", userInfoPanel);
    tabs.addTab("Advising", advisingPanel);
    tabs.addTab("Catalog", courseCatalogPanel);
    tabs.addTab("About", aboutPanel);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(tabs, BorderLayout.CENTER);

    setSize(700, 500);
    setVisible(true);
  }

  private void setupMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    // Create the "File" menu
    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    // Create and add the "Open" menu item
    JMenuItem openItem = new JMenuItem("Open");
    openItem.addActionListener(e -> openFile());
    fileMenu.add(openItem);

    // Set the menu bar on the JFrame
    setJMenuBar(menuBar);
  }

  private void openFile() {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("User Files (*.us)", "us");
    fileChooser.setFileFilter(filter);
    File projectDir = new File(System.getProperty("user.dir"));
    fileChooser.setCurrentDirectory(projectDir);
    int result = fileChooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      new GUIStudent(selectedFile);
    }

  }

  private JPanel createHomePanel() {
    JPanel homePanel = new JPanel(new BorderLayout());
    homePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    homePanel.add(Box.createHorizontalStrut(60), BorderLayout.WEST);
    homePanel.add(Box.createHorizontalStrut(60), BorderLayout.EAST);

    // Welcome panel that is always displayed
    JPanel outerPanel = panelHelperWelcome("Welcome to the Automatic Academic Advisor",
        user.getProjection() != null && !user.getProjection().isEmpty()
            ? "Head to the Advising tab to modify your projection plan."
            : "You do not have a projection plan yet. Head to the Advising tab to generate one.");
    homePanel.add(outerPanel, BorderLayout.NORTH);

    // Optionally display the projection plan if it exists
    if (user.getProjection() != null && !user.getProjection().isEmpty()) {
      JScrollPane planPanel = ProjectionPlanComponent.createProjectionPlanPanel(user.getProjection());
      homePanel.add(new JScrollPane(planPanel), BorderLayout.CENTER);
    }

    // Copyright panel
    JPanel copyRightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel copyrightLabel = new JLabel(
        "© 2024 Lebanese American University. All rights reserved.");
    copyrightLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
    copyRightPanel.add(copyrightLabel);
    homePanel.add(copyRightPanel, BorderLayout.SOUTH);

    return homePanel;
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

  // CATALOG displays courses and their infos and all
  private JPanel createCatalogPanel() {

    Init init = new Init(user.getCompletedCourses());
    List<Node> allCourses = init.getAllcoursesNodes(); // Fetch all courses

    JPanel catalogPanel = new JPanel(new BorderLayout());
    catalogPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
    catalogPanel.add(Box.createHorizontalStrut(60), BorderLayout.WEST);
    catalogPanel.add(Box.createHorizontalStrut(60), BorderLayout.EAST);

    // Initialize and add the CourseCatalogPanel
    CourseCatalog courseCatalogPanel = new CourseCatalog(allCourses);
    catalogPanel.add(courseCatalogPanel, BorderLayout.CENTER); // Add the course catalog panel to the main panel

    // Copyright panel
    JPanel copyRightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel copyrightLabel = new JLabel(
        "© 2024 Lebanese American University. All rights reserved.");
    copyrightLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
    copyRightPanel.add(copyrightLabel);
    catalogPanel.add(copyRightPanel, BorderLayout.SOUTH);

    return catalogPanel;
  }

  // USER INFO displays courses and their infos and all
  private JPanel createUserInfoPanel() {
    JPanel userInfoPanel = new JPanel(new BorderLayout());
    userInfoPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    userInfoPanel.setBackground(new Color(245, 245, 245));

    // Header Panel with welcome message
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(new Color(233, 236, 239));
    JLabel welcomeLabel = new JLabel("Your Profile", JLabel.CENTER);
    welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
    headerPanel.add(welcomeLabel, BorderLayout.CENTER);
    userInfoPanel.add(headerPanel, BorderLayout.NORTH);

    // Main content panel for user details
    JPanel detailsPanel = new JPanel();
    detailsPanel.setLayout(new GridLayout(0, 2, 10, 10));
    detailsPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
    detailsPanel.setBackground(new Color(245, 245, 245));

    // Add user details fields
    detailsPanel.add(createDetailComponent("First name", user.getFname()));
    detailsPanel.add(createDetailComponent("Last name", user.getLname()));
    detailsPanel.add(createDetailComponent("ID", user.getID()));
    detailsPanel.add(createDetailComponent("Major", user.getMajor()));
    detailsPanel.add(createDetailComponent("Minor", user.getMinor()));
    detailsPanel.add(createDetailComponent("First Enrolled Semester", user.getStartSemester()));
    detailsPanel.add(createDetailComponent("Credits Completed",
        Integer.toString(user.getCreditsCompleted()) + " crds"));
    detailsPanel.add(createDetailComponent("Standing", user.getStanding()));
    detailsPanel.add(createDetailComponent("Semesters Completed", Integer.toString(user.getNbSemestersCompleted())));

    userInfoPanel.add(detailsPanel, BorderLayout.CENTER);

    // Footer panel for copyright information
    JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    footerPanel.setBackground(new Color(233, 236, 239));
    JLabel copyrightLabel = new JLabel("© 2024 Lebanese American University. All rights reserved.");
    copyrightLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
    footerPanel.add(copyrightLabel);
    userInfoPanel.add(footerPanel, BorderLayout.SOUTH);

    return userInfoPanel;
  }

  private JPanel createDetailComponent(String label, String value) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(245, 245, 245)); // Match the background with the main panel

    JLabel labelComponent = new JLabel(label + ": ");
    labelComponent.setFont(new Font("SansSerif", Font.BOLD, 14));
    panel.add(labelComponent, BorderLayout.WEST);

    JLabel valueComponent = new JLabel(value);
    valueComponent.setFont(new Font("SansSerif", Font.PLAIN, 14));
    panel.add(valueComponent, BorderLayout.CENTER);

    return panel;
  }

  /**
   * Helper fucntion to create the explanatory text at the begining to each panel
   */
  private JPanel panelHelperWelcome(String title, String text) {

    FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 0, 0);
    JPanel outerPanel = new JPanel(flowLayout);
    JPanel textPanel = new JPanel(new GridLayout(0, 1));
    textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    JLabel welcomeLabel = new JLabel(title);
    textPanel.add(welcomeLabel);
    JLabel optionLabel = new JLabel(text);
    optionLabel.setFont(optionLabel.getFont().deriveFont(Font.PLAIN, 12));
    textPanel.add(optionLabel);
    outerPanel.add(textPanel);

    return outerPanel;
  }

  // ADVISING PANEL displays the advising
  private JPanel createAdvisingPanel() {
    JPanel advisingPanel = new JPanel(new BorderLayout());
    advisingPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

    // Title
    JLabel titleLabel = new JLabel("Course Projection Plan");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    advisingPanel.add(titleLabel, BorderLayout.NORTH);

    if (user.getProjection() != null && !user.getProjection().isEmpty()) {
      // If there is an existing plan, display it with a customize button
      JScrollPane planScrollPane = ProjectionPlanComponent.createProjectionPlanPanel(user.getProjection());
      advisingPanel.add(planScrollPane, BorderLayout.CENTER);

      JButton customizeButton = new JButton("Customize");
      customizeButton.addActionListener(e -> customizePlan());
      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      buttonPanel.add(customizeButton);
      advisingPanel.add(buttonPanel, BorderLayout.SOUTH);
    } else {
      JTextPane textPane = new JTextPane();
      textPane.setContentType("text/html");
      textPane.setEditable(false);
      textPane.setBackground(new Color(245, 245, 245));
      textPane.setText(
          "<html><body style='font-family:Segoe UI; font-size:12pt;'>" +
              "<p>Based on your data, we will be generating an initial course plan for the upcoming semesters.</p>" +
              "<ul>" +
              "<li><b>No more than 18 credits per semester</b> are allowed, except for the last semester, which can have <b>21 credits</b>.</li>"
              +
              "<li>A course cannot be taken unless <b>all its prerequisites</b> have been completed.</li>" +
              "<li>Lab must be taken at the same time as the course on the first try.</li>" +
              "<li>Up to <b>13 major-related credits</b> are allowed in a semester.</li>" +
              "<li><b>Order of importance:</b> Full credits, major-related, summer semesters.</li>" +
              "</ul>" +
              "<p>Projection will be attempted in the following order:</p>" +
              "<ol>" +
              "<li>Without summers, maximum regular credits, prioritizing major-related courses.</li>" +
              "<li>Without summers, allowing for a heavier CS semester.</li>" +
              "<li>Without summers, extra credits allowed (18/21).</li>" +
              "<li>Allowing summers.</li>" +
              "</ol>" +
              "</body></html>");
      JScrollPane scrollPane = new JScrollPane(textPane);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      advisingPanel.add(scrollPane, BorderLayout.CENTER);

      // Button to generate course projection plan
      JButton generateButton = new JButton("Generate Course Projection Plan");
      generateButton.addActionListener(e -> generateGraduationPlanFromGUI(null, 0, -1));
      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      buttonPanel.add(generateButton);
      advisingPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    return advisingPanel;
  }

  // Method to extract user data from GUI and call projection plan method
  private void generateGraduationPlanFromGUI(int[] summerPreferences, int customSemesterNumber,
      int customNumberOfCredits) {
    // Extract user data from user profile
    List<Course> completedCourses = user.getCompletedCourses();
    int completedSemesters = user.getNbSemestersCompleted();
    int completedCredits = user.getCreditsCompleted();
    int startSemester;

    if (user.getStartSemester().equals("FALL")) {
      startSemester = 0;
    } else {
      startSemester = 1;
    }

    // Call projection plan method from the GraduationPlanner class
    List<Semester> plan = GraduationPlanner.generateGraduationPlan(completedCourses, completedSemesters,
        completedCredits, startSemester, summerPreferences, customSemesterNumber,
        customNumberOfCredits);

    // Check if the plan is not empty and display it
    if (plan != null && !plan.isEmpty()) {
      displayPlan(plan);
    } else {
      displayErrorMessage("No valid graduation plan could be found."); // Display an error message if the plan is null
                                                                       // or empty
    }
  }

  private void displayPlan(List<Semester> plan) {
    JScrollPane planScrollPane = ProjectionPlanComponent.createProjectionPlanPanel(plan);

    // Buttons for plan management
    JButton saveButton = new JButton("Save");
    saveButton.addActionListener(e -> saveUserPlan(plan));
    JButton customizeButton = new JButton("Customize");
    customizeButton.addActionListener(e -> customizePlan());

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.add(saveButton);
    buttonPanel.add(customizeButton);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(planScrollPane, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    JPanel advisingPanel = (JPanel) tabs.getComponentAt(tabs.indexOfTab("Advising"));
    advisingPanel.removeAll();
    advisingPanel.add(mainPanel, BorderLayout.CENTER);
    advisingPanel.revalidate();
    advisingPanel.repaint();
  }

  private void saveUserPlan(List<Semester> plan) {
    user.setProjection(plan);
    File updatedFile = InitUser.saveUser(user);
    if (updatedFile != null) {
      JOptionPane.showMessageDialog(this, "Plan saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
      // reloadUserData(updatedFile);
    } else {
      JOptionPane.showMessageDialog(this, "Failed to save the plan.", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void customizePlan() {

    // Create a panel to hold buttons
    JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
    JButton btnSpecifySummer = new JButton("Specify Summer");
    JButton btnSpecifyCredits = new JButton("Specify Credits");
    JButton btnSpecifyCourses = new JButton("Specify Courses");

    // Add buttons to panel
    panel.add(btnSpecifySummer);
    panel.add(btnSpecifyCredits);
    panel.add(btnSpecifyCourses);

    // Create a dialog that contains the panel
    JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null,
        new Object[] {}, null);

    JDialog dialog = optionPane.createDialog("Customize Plan Options");

    // Adding action listeners to buttons
    btnSpecifySummer.addActionListener(e -> {
      specifySummer();
      dialog.dispose();
      return;
    });
    btnSpecifyCredits.addActionListener(e -> {
      specifyCredits();
      dialog.dispose();
      return;
    });
    btnSpecifyCourses.addActionListener(e -> {
      specifyCourses();
      dialog.dispose();
      return;
    });

    dialog.setVisible(true);

  }

  private void specifySummer() {
    int startSemester = user.getStartSemester().equals("FALL") ? 0 : 1;
    List<Semester> semesters = user.getProjection();
    int numOfRegularSemestersinPlan = semesters.stream().filter(s -> !s.isSummer()).mapToInt(s -> 1).sum();

    // here we get the number of semesters already taken by the user ...
    int numOfSummerSemestersinPlan = (int) semesters.stream().filter(Semester::isSummer).count();
    // the max number of summer in a any plan , so if we have 6 semester , the max
    // is 2 wtc...
    int maxNumOfSummers = Semester.getMaxSummers(numOfRegularSemestersinPlan, startSemester);
    // so the allowed number of summers the user can take:
    int allowedNumofSummers = maxNumOfSummers - numOfSummerSemestersinPlan;

    System.out.println(numOfRegularSemestersinPlan + " " + numOfSummerSemestersinPlan + " " + maxNumOfSummers + " "
        + allowedNumofSummers);

    List<int[]> summerCombinations = Semester.generateCombinations(maxNumOfSummers);
    String[] options = new String[summerCombinations.size()];

    for (int i = 0; i < summerCombinations.size(); i++) {
      int[] combination = summerCombinations.get(i);
      options[i] = Arrays.stream(combination)
          .mapToObj(num -> num == 0 ? "No" : "Yes")
          .collect(Collectors.joining(", "));
      options[i] = "Summer 1: " + options[i].split(", ")[0] + ", Summer 2: " + options[i].split(", ")[1];
    }

    int selectedIndex = JOptionPane.showOptionDialog(null, "Select your preferred combination of summer semesters:",
        "Customize Summer Semesters", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
        options[0]);

    if (selectedIndex >= 0) {
      int[] selectedCombination = summerCombinations.get(selectedIndex);
      generateGraduationPlanFromGUI(selectedCombination, 0, -1);
    } else {
      JOptionPane.showMessageDialog(null, "No selection was made.", "No Selection", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void specifyCredits() {
    JTextField semesterField = new JTextField(5);
    JTextField creditsField = new JTextField(5);

    JPanel myPanel = new JPanel();
    myPanel.add(new JLabel("Semester Number:"));
    myPanel.add(semesterField);
    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
    myPanel.add(new JLabel("Number of Credits:"));
    myPanel.add(creditsField);

    int result = JOptionPane.showConfirmDialog(null, myPanel,
        "Please Enter Semester Number and Credits", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      try {
        int semesterNumber = Integer.parseInt(semesterField.getText());
        int numberOfCredits = Integer.parseInt(creditsField.getText());
        System.out.println("Semester: " + semesterNumber);
        System.out.println("Credits: " + numberOfCredits);
        // Proceed with using these inputs in your system
        generateGraduationPlanFromGUI(null, semesterNumber - 1, numberOfCredits);
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void specifyCourses() {
    // Get the next level courses based on the user's completed courses
    List<Node> nextLevelCourses = GraduationPlanner.getNextLevelCourses(user.getCompletedCourses(), 0);

    // Create a list model to hold these courses
    DefaultListModel<Course> model = new DefaultListModel<>();
    nextLevelCourses.stream().map(Node::getCourse).forEach(model::addElement);

    // Setup the JList for course selection
    JList<Course> courseList = new JList<>(model);
    courseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    JScrollPane listScroller = new JScrollPane(courseList);
    listScroller.setPreferredSize(new Dimension(250, 80));

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(listScroller, BorderLayout.CENTER);
    panel.add(new JLabel("Select Courses for Next Semester:"), BorderLayout.NORTH);

    JLabel noteLabel = new JLabel(
        "<html><body style='padding-top:5px'><i>Note: Make sure to check course availability for next semester.</i></body></html>");
    panel.add(noteLabel, BorderLayout.SOUTH);

    int result = JOptionPane.showConfirmDialog(null, panel, "Choose Courses for Next Semester",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      List<Course> selectedCourses = courseList.getSelectedValuesList();
      List<Course> userCompletedCourses = user.getCompletedCourses();
      userCompletedCourses.addAll(selectedCourses);
      user.setCompletedCourses(userCompletedCourses);
      generateGraduationPlanFromGUI(null, 0, -1);

      System.out.println("Selected Courses: " + selectedCourses);
    }
  }

  private void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Graduation Plan Error", JOptionPane.ERROR_MESSAGE);
  }

  /** Click behaviors on different elements */
  @Override
  public void actionPerformed(ActionEvent e) {

  }

}
