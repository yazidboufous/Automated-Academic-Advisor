package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable {
  private Course course;
  private List<Edge> neighbors;
  private int reachability;
  private boolean visited; // this will be relevant when we will need to finish a projection plan rather
                           // than start it from scratch
  private int level;

  public Node(Course course, List<Edge> arrayList) {
    super();
    this.course = course;
    this.neighbors = arrayList;
    this.reachability = 0;
    this.visited = false;

  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public List<Edge> getNeighbors() {
    return neighbors;
  }

  public List<Node> getNeighborNodes() {
    List<Node> nodes = new ArrayList<Node>();

    for (Edge edge : getNeighbors()) {
      nodes.add(edge.getNode());
    }
    return nodes;
  }

  public void setNeighbors(List<Edge> neighbors) {
    this.neighbors = neighbors;
  }

  public void addNeighbor(Node neighbor, int weight) {
    this.neighbors.add(new Edge(neighbor, weight));
  }

  public int getReachability() {
    return reachability;
  }

  public void setReachability(int reachability) {
    this.reachability = reachability;
  }

  public boolean getVisited() {
    return visited;
  }

  public void setVisited(boolean b) {
    this.visited = b;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  @Override
  public String toString() {
    return course.getName() + "(" + course.getCrds() + ")";
  }

  // two nodes
  // are equal if
  // they are
  // the same course

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    Node n = (Node) obj;
    return n.getCourse().equals(this.course);
  }
}