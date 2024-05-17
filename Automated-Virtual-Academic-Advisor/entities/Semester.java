package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Semester implements Serializable {

  private String name;
  private boolean[] type; // [FALL, SPRING, summer] like [false, false, true] for a summer
  private boolean isFinal; // is the last semester, this will be useful once we set a schedule
  private int userCredits; // probably not needed
  private int currentCrds; // curentCrds in this semester, probably not needed
  private List<Node> nodesAtSemester;
  public static final int REGULAR_SEMESTER_CREDIT_LIMIT = 18;
  public static final int SUMMER_SEMESTER_CREDIT_LIMIT = 9;
  public static final int FINAL_SEMESTER_CREDIT_LIMIT = 21;

  public Semester(String name, boolean[] type) {
    this.name = name;
    this.type = type;
    this.isFinal = false;
    this.nodesAtSemester = new ArrayList<>();
  }

  public Semester(String name, boolean[] type, int userCredits) {
    this.name = name;
    this.type = type;
    this.isFinal = false;
    this.nodesAtSemester = new ArrayList<>();
    this.userCredits = userCredits;
  }

  public List<Node> getNodesAtSemester() {
    return nodesAtSemester;
  }

  public void setNodesAtSemester(List<Node> nodesAtSemester) {
    this.nodesAtSemester = nodesAtSemester;
    this.currentCrds = getCurrentCrds();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    if (this.type[0]) {
      return "FALL";
    } else if (this.type[1])
      return "SPRING";
    else
      return "SUMMER";
  }

  public boolean isSummer() {
    return this.type[2]; // summer bit is the third in the array
  }

  public boolean isFinal() {
    return isFinal;
  }

  public void setFinal(boolean isFinal) {
    this.isFinal = isFinal;
  }

  public int getMajorCrds() {
    int countMajorCrds = 0;
    for (Node n : nodesAtSemester) {
      if (n.getCourse().isMajor())
        countMajorCrds += n.getCourse().getCrds();
    }
    return countMajorCrds;
  }

  public String toString() {
    return name + "(Total: " + getCurrentCrds() + ")(Major: " + getMajorCrds() + ")" +
        nodesAtSemester;
    // return name;
  }

  public int getUserCredits() {
    return userCredits;
  }

  public void setUserCredits(int userCredits) {
    this.userCredits = userCredits;
  }

  public int getCurrentCrds() {
    int x = 0;
    for (Node n : nodesAtSemester) {
      x += n.getCourse().getCrds();
    }

    return x;
  }

  public void setCurrentCrds(int current_crds) {
    this.currentCrds = current_crds;
  }

  /**
   * Function that generates the combinations of summers across the projection
   * plan. Takes k is the
   * maximum amount of summers allowed, generate combinations like [summer 1: yes,
   * summer 2: no],
   * [summer 1: no, summer 2: no] ...etc
   */
  public static List<int[]> generateCombinations(int k) {
    List<int[]> combinations = new ArrayList<>();
    int[] options = { 1, 0 };

    for (int i = 0; i < Math.pow(2, k); i++) {
      int[] combo = new int[k];
      for (int j = 0; j < k; j++) {
        combo[j] = options[(i >> j) & 1];
      }
      combinations.add(combo);
      // System.out.println(Arrays.toString(combo));
    }

    return combinations;
  }

  /**
   * Returns a list of all semester combinations, starting from the combination
   * with no summers.
   * 
   * @param maxRegSemesters
   *                        isthe total amount of regular semester needed (6 for a
   *                        regular projection plan).
   * @param startSemester
   *                        is 0 for a start in FALL, and 1 for a start in SPRING
   *                        example: [r,r,s,r,r,s,r,r] or
   *                        [r,r,s,r,r,r,r]
   */
  public static List<List<Semester>> generateSemestersCombinations(int maxRegSemesters,
      int startSemester) {

    // calculate how many sumemrs we cna add based on number of semesters
    int max_summers = getMaxSummers(maxRegSemesters, startSemester);

    // generate our combinations of summers
    List<int[]> summerCombinations = generateCombinations(max_summers);
    List<List<Semester>> semesterCombinations = new ArrayList<>();

    for (int i = summerCombinations.size() - 1; i >= 0; i--)
    // for every combination of summers , starting from no summers [0,0]

    {
      List<Semester> currentCombination = new ArrayList<Semester>();
      int summersAdded = 0;
      if (startSemester == 0) // for a projection plan that start in fall
      {
        for (int j = 0; j < maxRegSemesters; j++) {

          if (j % 2 != 0) // index for a summer
          {
            if (summerCombinations.get(i)[summersAdded] == 1) {
              currentCombination.add(new Semester("Summer", new boolean[] { false, false, true }));

            }
            summersAdded++;
            currentCombination.add(new Semester("Fall", new boolean[] { true, false, false }));
          } else
            currentCombination.add(new Semester("Spring", new boolean[] { false, true, false }));
        }
      } else if (startSemester == 1) // for a projection plan that starts in spring
      {
        for (int j = 0; j < maxRegSemesters; j++) {

          if (j % 2 != 0) // index for a summer
          {
            if (summerCombinations.get(i)[summersAdded] == 1) {
              currentCombination.add(new Semester("Summer", new boolean[] { false, false, true }));

            }
            summersAdded++;
            currentCombination.add(new Semester("Fall", new boolean[] { true, false, false }));
          } else
            currentCombination.add(new Semester("Spring", new boolean[] { false, true, false }));
        }

      }
      currentCombination.get(currentCombination.size() - 1).setFinal(true);
      semesterCombinations.add(currentCombination);

    }
    return semesterCombinations;
  }

  // similar as the above but takes into consideration customization ( specific
  // credits in a semester):
  public static List<List<Semester>> generateSemestersCombinations(int maxRegSemesters, int startSemester,
      int customSemesterIndex, int customCredits) {

    // calculate how many sumemrs we cna add based on number of semesters
    int max_summers = getMaxSummers(maxRegSemesters, startSemester);

    // generate our combinations of summers
    List<int[]> summerCombinations = generateCombinations(max_summers);
    List<List<Semester>> semesterCombinations = new ArrayList<>();

    for (int i = summerCombinations.size() - 1; i >= 0; i--)
    // for every combination of summers , starting from no summers [0,0]

    {
      List<Semester> currentCombination = new ArrayList<Semester>();
      int summersAdded = 0;
      if (startSemester == 0) // for a projection plan that start in FALL
      {
        for (int j = 0; j < maxRegSemesters; j++) {
          if (j == 0) {
            if (j == customSemesterIndex) {
              currentCombination
                  .add(new Semester("FALL", new boolean[] { true, false, false }, customCredits));
            } else {
              currentCombination.add(new Semester("FALL", new boolean[] { true, false, false }));
            }
          } else if (j != 0 && j % 2 == 0) // index for a summer
          {
            if (summerCombinations.get(i)[summersAdded] == 1) {
              if (j == customSemesterIndex) {
                currentCombination
                    .add(new Semester("SUMMER", new boolean[] { false, false, true }, customCredits));
              } else {
                currentCombination.add(new Semester("SUMMER", new boolean[] { false, false, true }));
              }
            }
            summersAdded++;
            if (j == customSemesterIndex) {
              currentCombination
                  .add(new Semester("FALL", new boolean[] { true, false, false }, customCredits));
            } else {
              currentCombination.add(new Semester("FALL", new boolean[] { true, false, false }));
            }
          } else {
            if (j == customSemesterIndex) {
              currentCombination
                  .add(new Semester("SPRING", new boolean[] { false, true, false }, customCredits));
            } else {
              currentCombination.add(new Semester("SPRING", new boolean[] { false, true, false }));
            }
          }
        }
      } else if (startSemester == 1) // for a projection plan that starts in SPRING
      {
        for (int j = 0; j < maxRegSemesters; j++) {

          if (j % 2 != 0) // index for a summer
          {
            if (summerCombinations.get(i)[summersAdded] == 1) {
              if (j == customSemesterIndex) {
                currentCombination
                    .add(new Semester("SUMMER", new boolean[] { false, false, true }, customCredits));
              } else {
                currentCombination.add(new Semester("SUMMER", new boolean[] { false, false, true }));
              }
            }
            summersAdded++;
            if (j == customSemesterIndex) {
              currentCombination
                  .add(new Semester("FALL", new boolean[] { true, false, false }, customCredits));
            } else {
              currentCombination.add(new Semester("FALL", new boolean[] { true, false, false }));
            }
          } else if (j == customSemesterIndex) {
            currentCombination
                .add(new Semester("SPRING", new boolean[] { false, true, false }, customCredits));
          } else {
            currentCombination.add(new Semester("SPRING", new boolean[] { false, true, false }));
          }
        }

      }
      currentCombination.get(currentCombination.size() - 1).setFinal(true);
      semesterCombinations.add(currentCombination);

    }
    return semesterCombinations;
  }

  /**
   * Similar function as above, however, this function takes the user input
   * 
   * @param summersUserPreference
   *                              of the form [0,0] for no summer, no summer.
   *                              example: [0,0,1] means third summer is
   *                              yes. first two summers are no
   */

  public static List<List<Semester>> generateSemestersCombinations(int maxRegSemesters,
      int startSemester, int[] summersUserPreference) {

    List<List<Semester>> semesterCombinations = new ArrayList<>();

    List<Semester> currentCombination = new ArrayList<Semester>();
    int summersAdded = 0;
    if (startSemester == 0) // for a projection plan that start in FALL
    {
      for (int j = 0; j < maxRegSemesters; j++) {
        if (j == 0)
          currentCombination.add(new Semester("FALL", new boolean[] { true, false, false }));
        else if (j != 0 && j % 2 == 0) // index for a summer
        {
          if (summersUserPreference[summersAdded] == 1) {
            currentCombination.add(new Semester("SUMMER", new boolean[] { false, false, true }));

          }
          summersAdded++;
          currentCombination.add(new Semester("FALL", new boolean[] { true, false, false }));
        } else
          currentCombination.add(new Semester("SPRING", new boolean[] { false, true, false }));
      }
    } else if (startSemester == 1) // for a projection plan that starts in SPRING
    {
      for (int j = 0; j < maxRegSemesters; j++) {

        if (j % 2 != 0) // index for a summer
        {
          if (summersUserPreference[summersAdded] == 1) {
            currentCombination.add(new Semester("SUMMER", new boolean[] { false, false, true }));

          }
          summersAdded++;
          currentCombination.add(new Semester("FALL", new boolean[] { true, false, false }));
        } else
          currentCombination.add(new Semester("SPRING", new boolean[] { false, true, false }));
      }

    }
    currentCombination.get(currentCombination.size() - 1).setFinal(true);
    semesterCombinations.add(currentCombination);

    return semesterCombinations;
  }

  /**
   * Function that calculate how many summers we can add based on number of
   * regular semesters
   * allowed and the start semester. For example, for 6 semesters starting in
   * FALL, we can only
   * allow 2 summers. For 6 semesters starting in SPRING, we can allow 3 summers.
   * 
   * @param startSeemster
   *                      is 0 for start in FALL, 1 for start in SPRING
   */

  public static int getMaxSummers(int maxRegSemesters, int startSemester) {

    int max_summers;

    if (startSemester == 1 && maxRegSemesters % 2 == 0)
      max_summers = (int) (Math.ceil(maxRegSemesters / 2.0));
    else
      max_summers = (int) (Math.ceil(maxRegSemesters / 2.0) - 1);

    return max_summers;

  }

  public static void main(String args[]) {

  }

}
