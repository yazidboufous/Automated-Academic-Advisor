package entities;

import java.io.Serializable;

public class Edge implements Serializable {
  private Node node; // child node (edge is pointing to this node)
  private int weight; // 0 for co req, 1 for pre req

  public Edge(Node node, int weight) {
    super();
    this.node = node;
    this.weight = weight;
  }

  public Node getNode() {
    return node;
  }

  public void setNode(Node node) {
    this.node = node;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  @Override
  public String toString() {
    return node.getCourse().getName();
  }

}
