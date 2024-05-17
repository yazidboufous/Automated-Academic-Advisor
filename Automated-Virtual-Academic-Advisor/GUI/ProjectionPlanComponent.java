package GUI;

import java.util.List;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import entities.Node;
import entities.Semester;

public class ProjectionPlanComponent {

    public static JScrollPane createProjectionPlanPanel(List<Semester> plan) {
        JPanel planPanel = new JPanel();
        planPanel.setLayout(new BoxLayout(planPanel, BoxLayout.Y_AXIS));
        planPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        planPanel.setBackground(Color.WHITE);

        for (Semester semester : plan) {
            JPanel semesterPanel = new JPanel();
            semesterPanel.setLayout(new BoxLayout(semesterPanel, BoxLayout.Y_AXIS));
            semesterPanel.setBorder(
                    BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(184, 207, 229)),
                            semester.getType(), TitledBorder.LEFT, TitledBorder.TOP,
                            new Font("SansSerif", Font.BOLD, 12), new Color(10, 36, 106)));
            semesterPanel.setBackground(Color.WHITE);

            for (Node node : semester.getNodesAtSemester()) {
                JPanel coursePanel = new JPanel(new BorderLayout());
                coursePanel.setBorder(new EmptyBorder(5, 0, 5, 0));
                coursePanel.setBackground(Color.WHITE);

                JLabel nameLabel = new JLabel(node.getCourse().getCode().isEmpty() ? node.getCourse().getName()
                        : node.getCourse().getCode() + " - " + node.getCourse().getName());
                nameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
                coursePanel.add(nameLabel, BorderLayout.WEST);

                JLabel creditsLabel = new JLabel(node.getCourse().getCrds() + " credits");
                creditsLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
                coursePanel.add(creditsLabel, BorderLayout.EAST);

                semesterPanel.add(coursePanel);
            }

            JLabel spacer = new JLabel(" ");
            semesterPanel.add(spacer);

            JPanel totalCreditsPanel = new JPanel(new BorderLayout());
            JLabel totalCreditsLabel = new JLabel("Total Credits: " + semester.getCurrentCrds());
            totalCreditsLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
            totalCreditsPanel.add(totalCreditsLabel, BorderLayout.EAST);
            totalCreditsPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
            totalCreditsPanel.setBackground(Color.WHITE);
            semesterPanel.add(totalCreditsPanel);

            planPanel.add(semesterPanel);
        }

        JScrollPane scrollPane = new JScrollPane(planPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        return scrollPane;
    }
}
