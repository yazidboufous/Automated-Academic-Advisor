//package app;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import entities.ComparatorTool;
//import entities.Course;
//import entities.Edge;
//import entities.Graph;
//import entities.Node;
//
///**
// * Previous version of the code without summers, and with no Semester Object
// */
//public class MainOG
//{
//
//  public static int maxSemesters = 4; // starts from 0
//  public static final int TOTAL_CREDIT_REQUIREMENTS = 92;
//  public static final int RECOMMENDED_CREDIT_LIMIT = 16;
//  public static final int REGULAR_SEMESTER_CREDIT_LIMIT = 18;
//  public static final int FINAL_SEMESTER_CREDIT_LIMIT = 21;
//  public static final int MAJOR_CREDITS_SEMESTER_LIMIT = 13;
//  public static Graph g;
//
//  public static void main(String[] args)
//  {
//    // all courses
//    Course csc243 = new Course("CP1", true, true, false, 3, new ArrayList<Course>());
//    // Course csc243L = new Course("CP1 LAB", true, true, true, 1, new
//    // ArrayList<Course>(),
//    // new ArrayList<Course>(Arrays.asList(csc243)));
//    // csc243.setLab(csc243L);
//    // csc243L.setLab(csc243);
//    Course mth201 = new Course("Calculus 3", true, true, false, 3, new ArrayList<Course>());
//    Course com203 = new Course("Public Communication", true, false, false, 3,
//        new ArrayList<Course>());
//    Course eng202 = new Course("English", true, false, false, 3, new ArrayList<Course>());
//    Course las204 = new Course("Ethics", true, false, false, 3, new ArrayList<Course>());
//    Course bio209 = new Course("Basic Biology", true, false, false, 3, new ArrayList<Course>());
//    Course csc245 = new Course("CP2", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(csc243)));
//    // Course csc245L = new Course("CP2 LAB", true, true, true, 1, new
//    // ArrayList<Course>(),
//    // new ArrayList<Course>(Arrays.asList(csc245)));
//    // csc245.setLab(csc245L);
//    // csc245L.setLab(csc245);
//    Course mth207 = new Course("Discrete 1", true, true, false, 3, new ArrayList<Course>());
//    Course csc230 = new Course("CO", true, true, false, 3, new ArrayList<Course>(),
//        new ArrayList<Course>(Arrays.asList(csc245, mth207)));
//    Course csc230L = new Course("CO LAB", true, true, true, 1, new ArrayList<Course>(),
//        new ArrayList<Course>(Arrays.asList(csc230)));
//    csc230.setLab(csc230L);
//    csc230L.setLab(csc230);
//    Course csc310 = new Course("CP3", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(csc245, mth207)));
//    // Course csc310L = new Course("CP3 LAB", true, true, true, 1, new
//    // ArrayList<Course>(),
//    // new ArrayList<Course>(Arrays.asList(csc310)));
//    // csc310.setLab(csc310L);
//    // csc310L.setLab(csc310);
//    Course csc375 = new Course("Database Management", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(csc245, mth207)));
//    Course csc326 = new Course("Operating Systems", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(csc245, csc230)));
//    Course parallel = new Course("Parallel", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(csc326, csc310)));
//    Course csc340 = new Course("Software Engineering", true, true, false, 3,
//        new ArrayList<Course>(), new ArrayList<Course>(Arrays.asList(csc375)));
//    Course csc380 = new Course("Discrete 2", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(mth207, mth201)));
//    Course csc430 = new Course("Networks", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(csc326)));
//    Course csc400 = new Course("Capstone", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(csc340)),
//        new ArrayList<Course>(Arrays.asList(eng202, com203)));
//    Course mth305 = new Course("Statistics", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(mth201)));
//    Course csc500 = new Course("Professional Internship", true, true, false, 1,
//        new ArrayList<Course>(), new ArrayList<Course>(Arrays.asList(csc375)));
//    Course csc1 = new Course("CS elective 1", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(csc310)));
//    Course csc2 = new Course("CS elective 2", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(csc310)));
//    Course csc3 = new Course("CS elective 3", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(csc310)));
//    Course csc4 = new Course("CS elective 4", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(csc310)));
//    Course csc5 = new Course("CS elective 5", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(csc310)));
//    Course mth1 = new Course("MTH elective", true, true, false, 3,
//        new ArrayList<Course>(Arrays.asList(mth201)));
//    Course hum1 = new Course("Humanities 1", true, false, false, 3, new ArrayList<Course>());
//    Course hum2 = new Course("Humanities 2", true, false, false, 3, new ArrayList<Course>());
//    Course hum3 = new Course("Humanities 3", true, false, false, 3, new ArrayList<Course>());
//    Course hum4 = new Course("Humanities 4", true, false, false, 3, new ArrayList<Course>());
//    Course hum5 = new Course("Humanities 5", true, false, false, 3, new ArrayList<Course>());
//    Course felec = new Course("Free Elective", true, false, false, 3, new ArrayList<Course>());
//
//    // Nodes with edges of appropriate weights
//
//    Node nhum1 = new Node(hum1, new ArrayList<Edge>());
//    Node nhum2 = new Node(hum2, new ArrayList<Edge>());
//    Node nhum3 = new Node(hum3, new ArrayList<Edge>());
//    Node nhum4 = new Node(hum4, new ArrayList<Edge>());
//    Node nhum5 = new Node(hum5, new ArrayList<Edge>());
//    Node nfelec = new Node(felec, new ArrayList<Edge>());
//    Node nlas204 = new Node(las204, new ArrayList<Edge>());
//    Node nbio209 = new Node(bio209, new ArrayList<Edge>());
//    Node nmth1 = new Node(mth1, new ArrayList<Edge>());
//    Node ncsc380 = new Node(csc380, new ArrayList<Edge>());
//    Node ncsc430 = new Node(csc430, new ArrayList<Edge>());
//    Node ncsc400 = new Node(csc400, new ArrayList<Edge>());
//    Node nmth305 = new Node(mth305, new ArrayList<Edge>());
//    Node ncsc500 = new Node(csc500, new ArrayList<Edge>());
//    Node ncsc1 = new Node(csc1, new ArrayList<Edge>());
//    Node ncsc2 = new Node(csc2, new ArrayList<Edge>());
//    Node ncsc3 = new Node(csc3, new ArrayList<Edge>());
//    Node ncsc4 = new Node(csc4, new ArrayList<Edge>());
//    Node ncsc5 = new Node(csc5, new ArrayList<Edge>());
//    Node nparallel = new Node(parallel, new ArrayList<Edge>());
//    Node ncsc340 = new Node(csc340,
//        new ArrayList<Edge>(Arrays.asList(new Edge(ncsc400, 1), new Edge(ncsc500, 0))));
//    Node ncsc230L = new Node(csc230L, new ArrayList<Edge>());
//    // Node ncsc310L = new Node(csc310L, new ArrayList<Edge>());
//    // Node ncsc245L = new Node(csc245L, new ArrayList<Edge>());
//    // Node ncsc243L = new Node(csc243L, new ArrayList<Edge>());
//    Node ncsc310 = new Node(csc310,
//        new ArrayList<Edge>(Arrays.asList(new Edge(ncsc1, 1), new Edge(ncsc2, 1),
//            new Edge(ncsc3, 1), new Edge(ncsc4, 1), new Edge(ncsc5, 1), new Edge(nparallel, 1))));
//    Node ncsc375 = new Node(csc375,
//        new ArrayList<Edge>(Arrays.asList(new Edge(ncsc500, 0), new Edge(ncsc340, 0))));
//
//    Node ncsc326 = new Node(csc326,
//        new ArrayList<Edge>(Arrays.asList(new Edge(nparallel, 1), new Edge(ncsc430, 1))));
//    Node ncsc230 = new Node(csc230,
//        new ArrayList<Edge>(Arrays.asList(new Edge(ncsc326, 1), new Edge(ncsc230L, 0))));
//    Node ncsc245 = new Node(csc245, new ArrayList<Edge>(Arrays.asList(new Edge(ncsc310, 1),
//        new Edge(ncsc326, 1), new Edge(ncsc375, 1), new Edge(ncsc230, 0))));
//    Node nmth207 = new Node(mth207, new ArrayList<Edge>(Arrays.asList(new Edge(ncsc380, 1),
//        new Edge(ncsc375, 1), new Edge(ncsc310, 1), new Edge(ncsc230, 0))));
//    Node ncsc243 = new Node(csc243, new ArrayList<Edge>(Arrays.asList(new Edge(ncsc245, 1))));
//    Node nmth201 = new Node(mth201, new ArrayList<Edge>(
//        Arrays.asList(new Edge(nmth1, 1), new Edge(nmth305, 1), new Edge(ncsc380, 1))));
//    Node ncom203 = new Node(com203, new ArrayList<Edge>(Arrays.asList(new Edge(ncsc400, 0))));
//    Node neng202 = new Node(eng202, new ArrayList<Edge>(Arrays.asList(new Edge(ncsc400, 0))));
//    g = new Graph();
//
//    g.addNode(ncsc243);
//    g.addNode(nhum1);
//    g.addNode(nhum2);
//    g.addNode(nhum3);
//    g.addNode(nhum4);
//    g.addNode(nhum5);
//    g.addNode(nfelec);
//    g.addNode(nmth201);
//    g.addNode(ncom203);
//    g.addNode(neng202);
//    g.addNode(nlas204);
//    g.addNode(nbio209);
//    g.addNode(ncsc245);
//    g.addNode(nmth207);
//    g.addNode(ncsc230);
//    g.addNode(ncsc230L);
//    // g.addNode(ncsc243L);
//    // g.addNode(ncsc245L);
//    // g.addNode(ncsc310L);
//    g.addNode(ncsc310);
//    g.addNode(ncsc375);
//    g.addNode(ncsc326);
//    g.addNode(nparallel);
//    g.addNode(ncsc340);
//    g.addNode(ncsc380);
//    g.addNode(ncsc430);
//    g.addNode(ncsc400);
//    g.addNode(nmth305);
//    g.addNode(ncsc500);
//    g.addNode(ncsc1);
//    g.addNode(nmth1);
//    g.addNode(ncsc2);
//    g.addNode(ncsc3);
//    g.addNode(ncsc4);
//    g.addNode(ncsc5);
//
//    /******************************************************************************************************
//     * INTIALIZATION OF GRAPH FINISHED
//     */
//
//    // compute reachability of the nodes
//    g.computeReachability();
//
//    HashMap<Integer, List<Node>> semesters = new HashMap<>(); // contains what nodes each semester
//                                                              // has, s1[a,b,c], s2[e,f,g] ...etc
//
//    int currentLevel = 0;
//    int totalCredits = 0; // total amount of credits within the graduation plan
//
//    /******************************************************************************************************
//     * INITIALIZATION OF GRAPH FINISHED
//     ******************************************************************************************************/
//
//    // compute reachability of the nodes
//    g.computeReachability();
//
//    // perform first levelization
//    g.levelizeGraph();
//    HashMap<Integer, List<Node>> map = g.computeLevelMap(); // contains what nodes each level has,
//                                                            // level 0 [a,b,b], level 1 [e,f,g]
//                                                            // ...etc
//
//    // System.out.println(map);
//
//    /******************************************************************************************************
//     * REACHABILITY AND LEVELIZATION OF GRAPH FINISHED- PROCEED TO SEMESTER SPLITTING
//     ******************************************************************************************************/
//
//    boolean scheduleFound = scheduleCourses(g, map, semesters, currentLevel, totalCredits, 0);
//
//    if (!scheduleFound)
//    {
//      System.out
//          .println("Failed to find a feasible schedule within " + maxSemesters + " semesters.");
//    }
//    else
//    {
//      System.out.println("found");
//    }
//
//  }
//
//  public static HashMap<Integer, List<Node>> deepCopyHashMap(Map<Integer, List<Node>> originalMap)
//  {
//    try
//    {
//      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
//
//      // Serialize the original map to the byte stream
//      objectOutputStream.writeObject(originalMap);
//      objectOutputStream.close();
//
//      // Deserialize the byte stream to create a new copy of the map
//      ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//      ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
//
//      // Return the deep copy of the map
//      return (HashMap<Integer, List<Node>>) objectInputStream.readObject();
//    }
//    catch (IOException | ClassNotFoundException e)
//    {
//      e.printStackTrace();
//      return null;
//    }
//  }
//
//  /**
//   * This function searches for the lab-course pair. When passed a course it searches for its lab.
//   * When passed a lab, it searches for its course. if found return it, else return null.
//   */
//  private static Node findLabNode(Node node, List<Node> nodes)
//  {
//    for (Node n : nodes)
//    {
//      if (node.getCourse().getLab().equals(n.getCourse()))
//      {
//        return n;
//      }
//    }
//    return null;
//  }
//
//  /******************************************************************************************************
//   * RECURSIVE FUNCTION FOR SCHEDULE GENERATION It backtracks to previous semester when a solution
//   * isn't found. Uses ordering by reachability. So far, it implements only the max Credits
//   * heuristic. It creates a new copy of the grapha and levels at every recursion, which is
//   * terrible.
//   ******************************************************************************************************/
//
//  private static boolean scheduleCourses(Graph g, HashMap<Integer, List<Node>> m,
//      HashMap<Integer, List<Node>> semesters, int currentLevel, int totalCredits,
//      int currentSemester)
//  {
//
//    // if finished the creditRequirements, great
//    if (totalCredits == TOTAL_CREDIT_REQUIREMENTS)
//    {
//      // print resulting projection plan
//      System.out.println("*************************SOLUTION FOUND*****************************");
//      for (Integer key : semesters.keySet())
//      {
//        int crds = 0;
//        for (Node n : semesters.get(key))
//        {
//          crds += n.getCourse().getCrds();
//        }
//        System.out.println("Semester " + (key + 1) + " (" + crds + "crds) " + semesters.get(key));
//      }
//      return true;
//    }
//    // if requirements aren't finished but there's still semesters to add
//    if (currentSemester <= maxSemesters)
//    {
//      // for this currentsemester and level
//
//      int currentMaxCrds;
//      int currentCredits;
//      int[] creditsHeuristic = new int[] {RECOMMENDED_CREDIT_LIMIT, REGULAR_SEMESTER_CREDIT_LIMIT};
//      if (currentSemester == maxSemesters)
//      { // assign max credits as 21 if this is the last semester
//        creditsHeuristic = new int[] {RECOMMENDED_CREDIT_LIMIT, REGULAR_SEMESTER_CREDIT_LIMIT,
//            FINAL_SEMESTER_CREDIT_LIMIT};
//      }
//
//      for (int i : creditsHeuristic)
//      {
//        // try course assignment with 16 crdts first, the try other options
//        currentCredits = 0;
//        currentMaxCrds = i;
//        Graph graph = g.deepCopy();
//
//        // sort by reachability
//        HashMap<Integer, List<Node>> map = graph.computeLevelMap();
//        ComparatorTool comparator = new ComparatorTool();
//        comparator.setStrategy("reachability");
//        Collections.sort(map.get(currentLevel), comparator);
//
//        List<Node> coursesToConsider = map.get(currentLevel); // putting them here so that we can
//                                                              // modify this array during processing
//
//        // starts filling semester level by level
//        for (int j = 0; j < coursesToConsider.size(); j++)
//        {
//          Node n = coursesToConsider.get(j);
//          // System.out.println(currentLevel + ":" + n);
//          if (currentCredits + n.getCourse().getCrds() <= currentMaxCrds)
//          {
//            // LAB MANDATORY HEURISTIC
//            boolean hasLab = n.getCourse().getLab() != null;
//            boolean isLab = n.getCourse().isLab();
//
//            if (isLab || hasLab)
//            // ensure both lab and course are taken together, or none is taken
//            {
//              Node labNode = findLabNode(n, coursesToConsider);
//              if (currentCredits + n.getCourse().getCrds()
//                  + labNode.getCourse().getCrds() <= currentMaxCrds)
//              {
//                List<Node> coursesAtCurrentSemester = semesters.getOrDefault(currentSemester,
//                    new ArrayList<>());
//                coursesAtCurrentSemester.add(n);
//                coursesAtCurrentSemester.add(labNode);
//                semesters.put(currentSemester, coursesAtCurrentSemester);
//                currentCredits += n.getCourse().getCrds() + labNode.getCourse().getCrds();
//
//              }
//              // Prevent lab/course from being processed again
//              coursesToConsider.remove(labNode);
//
//            }
//            else // no lab issue
//            {
//              List<Node> coursesAtCurrentSemester = semesters.getOrDefault(currentSemester,
//                  new ArrayList<>());
//              coursesAtCurrentSemester.add(n);
//              semesters.put(currentSemester, coursesAtCurrentSemester);
//              currentCredits += n.getCourse().getCrds();
//            }
//          }
//
//        }
//
//        // Handling courses that were not picked for this current semester.
//        /**
//         * Explanation as to why we are doing this in two loops instead of one We have different
//         * courses that need to be pushed down and their subsequent pre req to be pushed too. And
//         * sometimes, they have pre reqs in common. So if we push down kelchi one level down in one
//         * go, some classes with overlapping pre req will be pushed multiple levels below because it
//         * pushing down 1 level for the first deffered course, then a second time for the second
//         * deffered course. Therefore, we need to push down all prereq courses of the first defered
//         * course, update the graph levels then when the second deferred course has to push down its
//         * pre req, any common prereq would be already pushed down, so no need to push down again.
//         */
//        for (Node course : map.get(currentLevel))
//        {
//          if (!semesters.get(currentLevel).contains(course))
//          {
//            course.setLevel(course.getLevel() + 1); // push one level down
//
//          }
//        }
//        for (Node course : map.get(currentLevel))
//        {
//
//          if (!semesters.get(currentSemester).contains(course))
//          {
//            graph.levelizefromRoot(course); // push all levels below
//            map = graph.computeLevelMap();
//          }
//        }
//
//        // move to next semester
//        if (scheduleCourses(graph, map, semesters, currentLevel + 1, totalCredits + currentCredits,
//            currentSemester + 1) == true)
//          return true;
//        // if didn't work, backtrack and change combination of heuristics for this
//        // semester
//        semesters.remove(currentSemester);
//      }
//    }
//
//    // no more semester, and plan was incomplete
//    System.out.println("******RETURNED FALSE AT " + currentSemester + "(" + totalCredits + ")");
//    // for (Integer key : semesters.keySet()) {
//    // int crds = 0;
//    // for (Node n : semesters.get(key)) {
//    // crds += n.getCourse().getCrds();
//    // }
//    // System.out.println("Semester " + (key + 1) + " (" + crds + "crds) " +
//    // semesters.get(key));
//    // }
//    return false;
//
//  }
//}
