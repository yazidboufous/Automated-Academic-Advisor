package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import entities.Course;
import entities.Node;

import java.awt.*;
import java.util.List;

public class CourseCatalog extends JPanel {
    private JTable courseTable;
    private DefaultTableModel tableModel;

    public CourseCatalog(List<Node> courses) {
        setLayout(new BorderLayout());
        initializeUI(courses);
    }

    private void initializeUI(List<Node> courses) {
        // Column Names for the JTable
        String[] columnNames = { "Course Code", "Course Name", "Credits", "Type", "Prerequisites", "Corequisites" };

        // Creating a model for the table
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // make table cells non-editable
                return false;
            }
        };
        courseTable = new JTable(tableModel);
        styleTable();

        fillTable(courses);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void styleTable() {
        courseTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        courseTable.setRowHeight(25);
        courseTable.setGridColor(new Color(0, 0, 0));
        courseTable.setShowVerticalLines(false);

        JTableHeader header = courseTable.getTableHeader();
        header.setBackground(new Color(33, 150, 243));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        TableColumnModel columnModel = courseTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100); // Course Code
        columnModel.getColumn(1).setPreferredWidth(200); // Course Name
        columnModel.getColumn(2).setPreferredWidth(50); // Credits
        columnModel.getColumn(3).setPreferredWidth(100); // Type
        columnModel.getColumn(4).setPreferredWidth(150); // Prerequisites
        columnModel.getColumn(5).setPreferredWidth(150); // Corequisites
    }

    private void fillTable(List<Node> courses) {
        for (Node node : courses) {
            String prereqs = formatCourseList(node.getCourse().getPrereq());
            String coreqs = formatCourseList(node.getCourse().getCoreq());
            Object[] row = {
                    node.getCourse().getCode(),
                    node.getCourse().getName(),
                    node.getCourse().getCrds(),
                    node.getCourse().isMajor() ? "Major" : "Elective",
                    prereqs,
                    coreqs
            };
            tableModel.addRow(row);
        }
    }

    private String formatCourseList(List<Course> courses) {
        if (courses == null || courses.isEmpty()) {
            return "None";
        }
        StringBuilder sb = new StringBuilder();
        for (Course course : courses) {
            sb.append(course.getCode());
            if (!course.equals(courses.get(courses.size() - 1))) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

}
