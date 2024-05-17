package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import entities.Course;

/**
 * Intializes courses. It creates the object courses, appedn them to a list, and
 * write them into a
 * file. Also included a function to modify into the file again, and read from
 * it
 */
public class InitCourses {
        private static final String COURSES_FILE = "courses.co";

        @SuppressWarnings("unchecked")
        public static List<Course> loadCourses() {
                List<Course> courses = new ArrayList<>();
                File file = new File(COURSES_FILE);

                if (file.exists()) {
                        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                                courses = (List<Course>) ois.readObject();
                        } catch (ClassNotFoundException | IOException e) {
                                System.err.println("Error reading courses from file: " + e.getMessage());
                                return new ArrayList<>(); // Return empty list if error
                        }
                } else {
                        courses = initCourses(); // Initialize if no file exists
                        saveCourses(courses); // Save the newly initialized list
                }
                return courses;
        }

        public static void saveCourses(List<Course> courses) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(COURSES_FILE))) {
                        oos.writeObject(courses);
                } catch (IOException e) {
                        System.err.println("Error saving courses to file: " + e.getMessage());
                }
        }

        public static List<Course> initCourses() {
                List<Course> courses = new ArrayList<>();
                Course csc243 = new Course("CSC243", "Introduction to Object-Oriented Programming", true, true, false,
                                3,
                                new ArrayList<Course>(),
                                new ArrayList<Course>(), false);
                Course mth201 = new Course("MTH201", "Calculus III", true, true, false, 3, new ArrayList<Course>(),
                                new ArrayList<Course>(), false);
                Course com203 = new Course("COM203", "Fundamentals of Oral Communications", true, false, false, 3,
                                new ArrayList<Course>(), new ArrayList<Course>(), false);
                Course eng202 = new Course("ENG202", "Advanced Academic English", true, false, false, 3,
                                new ArrayList<Course>(),
                                new ArrayList<Course>(), false);
                Course las204 = new Course("LAS204", "Technology, Ethics, and the Global Society", true, false, false,
                                3,
                                new ArrayList<Course>(),
                                new ArrayList<Course>(), false);
                Course bio209 = new Course("BIO209", "Basic Biology for Computer Science", true, false, false, 3,
                                new ArrayList<Course>(),
                                new ArrayList<Course>(), false);
                Course csc245 = new Course("CSC245", "Objects & Data Abstraction", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(csc243)), new ArrayList<Course>(), false);
                Course mth207 = new Course("MTH207", "Discrete Structures I", true, true, false, 3,
                                new ArrayList<Course>(),
                                new ArrayList<Course>(), false);
                Course csc320 = new Course("CSC320", "Computer Organization", true, true, false, 3,
                                new ArrayList<Course>(),
                                new ArrayList<Course>(Arrays.asList(csc245, mth207)), false);
                Course csc320L = new Course("CSC322", "Computer Organization Lab", true, true, true, 1,
                                new ArrayList<Course>(),
                                new ArrayList<Course>(Arrays.asList(csc320)), false);
                csc320.setLab(csc320L);
                Course csc310 = new Course("CSC310", "Algorithms & Data Structures", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(csc245, mth207)), new ArrayList<Course>(), false);
                Course csc375 = new Course("CSC375", "Database Management Systems", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(csc245, mth207)), new ArrayList<Course>(), false);
                Course csc326 = new Course("CSC326", "Operating Systems", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(csc245, csc320)), new ArrayList<Course>(), false);
                Course csc447 = new Course("CSC447", "Parallel Programming for Multicore & Cluster Systems", true, true,
                                false, 3,
                                new ArrayList<Course>(Arrays.asList(csc326, csc310)), new ArrayList<Course>(), false);
                Course csc490 = new Course("CSC490", "Software Engineering", true, true, false, 3,
                                new ArrayList<Course>(), new ArrayList<Course>(Arrays.asList(csc375)), false);
                Course mth307 = new Course("MTH307", "Discrete Structures II", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(mth207, mth201)), new ArrayList<Course>(), false);
                Course csc430 = new Course("CSC430", "Computer Networks", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(csc326)), new ArrayList<Course>(), false);
                Course csc599 = new Course("CSC599", "Capstone Project", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(csc490)),
                                new ArrayList<Course>(Arrays.asList(eng202, com203)), false);
                Course mth305 = new Course("MTH305", "Probability & Statistics", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(mth201)), new ArrayList<Course>(), false);
                Course csc491 = new Course("CSC491", "Professional Experience", true, true, false, 1,
                                new ArrayList<Course>(), new ArrayList<Course>(Arrays.asList(csc375)), false);
                Course csc1 = new Course("CSC361", "CSC elective 1", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(csc310)), new ArrayList<Course>(), false);
                Course csc2 = new Course("CSC362", "CSC elective 2", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(csc310)), new ArrayList<Course>(), false);
                Course csc3 = new Course("CSC363", "CSC elective 3", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(csc310)), new ArrayList<Course>(), false);
                Course csc4 = new Course("CSC364", "CSC elective 4", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(csc310)), new ArrayList<Course>(), false);
                Course csc5 = new Course("CSC365", "CSC elective 5", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(csc310)), new ArrayList<Course>(), false);
                Course mth1 = new Course("MTH311", "Math Elective", true, true, false, 3,
                                new ArrayList<Course>(Arrays.asList(mth201)), new ArrayList<Course>(), false);
                Course hum1 = new Course("LAC301", "LAC Elective 1", true, false, false, 3, new ArrayList<Course>(),
                                new ArrayList<Course>(), false);
                Course hum2 = new Course("LAC302", "LAC Elective 2", true, false, false, 3, new ArrayList<Course>(),
                                new ArrayList<Course>(), false);
                Course hum3 = new Course("LAC303", "LAC Elective 3", true, false, false, 3, new ArrayList<Course>(),
                                new ArrayList<Course>(), false);
                Course hum4 = new Course("LAC304", "LAC Elective 4", true, false, false, 3, new ArrayList<Course>(),
                                new ArrayList<Course>(), false);
                Course hum5 = new Course("LAC305", "LAC Elective 5", true, false, false, 3, new ArrayList<Course>(),
                                new ArrayList<Course>(), false);
                Course felec = new Course("LAC306", "Free Elective", true, false, false, 3, new ArrayList<Course>(),
                                new ArrayList<Course>(), false);

                courses.add(csc243);
                courses.add(mth201);
                courses.add(com203);
                courses.add(eng202);
                courses.add(las204);
                courses.add(bio209);
                courses.add(csc245);
                courses.add(mth207);
                courses.add(csc320);
                courses.add(csc320L);
                courses.add(csc310);
                courses.add(csc375);
                courses.add(csc326);
                courses.add(csc447);
                courses.add(csc490);
                courses.add(mth307);
                courses.add(csc430);
                courses.add(csc599);
                courses.add(mth305);
                courses.add(csc491);
                courses.add(csc1);
                courses.add(csc2);
                courses.add(csc3);
                courses.add(csc4);
                courses.add(csc5);
                courses.add(mth1);
                courses.add(hum1);
                courses.add(hum2);
                courses.add(hum3);
                courses.add(hum4);
                courses.add(hum5);
                courses.add(felec);
                return courses;
        }

        public static void main(String[] args) {
                List<Course> courses = loadCourses();
                System.out.println("Loaded courses: " + courses);
        }
}
