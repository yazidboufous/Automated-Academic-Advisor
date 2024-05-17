package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a course in general, like in the catalogue. I made a course
 * and a node two
 * seperate objects just fr the sake of clarity, because a course has many
 * unecessary details we
 * don't care about during traversal liek the full name of the course ..etc.
 * 
 */
public class Course implements Serializable {
  private String code;
  private String name;
  private boolean required;
  private boolean major;
  private boolean isLab; // whether the course is a lab or not
  private Course lab; // If course is a lab, this is the course of the lab. If this is a course,
                      // this is the lab of that course.
  private boolean isCompleted; // if couse is already taken or not.
  private int crds;
  private List<Course> prereq;
  private List<Course> coreq;

  // constructor
  public Course(String code, String name, boolean required, boolean major, boolean isLab, int crds,
      List<Course> prereq, boolean isCompleted) {
    super();
    this.code = code;
    this.name = name;
    this.required = required;
    this.major = major;
    this.isLab = isLab;
    this.crds = crds;
    this.prereq = prereq;
    this.isCompleted = isCompleted;

    this.coreq = new ArrayList<Course>();
  }

  public Course(String code, String name, boolean required, boolean major, boolean isLab, int crds,
      List<Course> prereq, List<Course> coreq, boolean isCompleted) {
    super();
    this.code = code;
    this.name = name;
    this.required = required;
    this.major = major;
    this.isLab = isLab;
    this.crds = crds;
    this.prereq = prereq;
    this.coreq = coreq;
    this.isCompleted = isCompleted;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public boolean isMajor() {
    return major;
  }

  public void setMajor(boolean major) {
    this.major = major;
  }

  public boolean isLab() {
    return isLab;
  }

  public void setisLab(boolean lab) {
    this.isLab = lab;
  }

  /** set the lab of a course */
  public void setLab(Course lab) {
    if (!this.isLab)
      this.lab = lab;
  }

  /** set the course of a lab */
  public void setCourse(Course course) {
    if (this.isLab)
      this.lab = course;
  }

  /** get the lab of a course */
  public Course getLab() {
    if (!this.isLab)
      return lab;
    else
      return null;
  }

  /** get the course of a lab */
  public Course getCourse() {
    if (this.isLab)
      return lab;
    else
      return null;
  }

  public int getCrds() {
    return crds;
  }

  public void setCrds(int crds) {
    this.crds = crds;
  }

  public List<Course> getPrereq() {
    return prereq;
  }

  public void setPrereq(List<Course> prereq) {
    this.prereq = prereq;
  }

  public List<Course> getCoreq() {
    return coreq;
  }

  public void setCoreq(List<Course> coreq) {
    this.coreq = coreq;
  }

  public String toString() {
    return name;
  }

  public boolean getisCompleted() {
    return this.isCompleted;
  }

  public void setisCompleted(boolean isCompleted) {
    this.isCompleted = isCompleted;
  }

  // two courses are equal if they have the same name
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    Course c = (Course) obj;
    return c.getName().equalsIgnoreCase(this.name);
  }

}