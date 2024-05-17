package entities;

import java.util.Comparator;

public class ComparatorTool implements Comparator<Node> {
  private ComparisonStrategy strategy;

  public ComparatorTool() {
    this.strategy = null;
  }

  @Override
  public int compare(Node node1, Node node2) {
    return strategy.compare(node1, node2);
  }

  public void setStrategy(String s) {
    switch (s) {
      case "reachability":
        this.strategy = new CompareReachability();
        break;
      case "major":
        this.strategy = new CompareMajor();
        break;

    }
  }

  /**
   * All the different comparator heuristics below .
   * Rechability, major topic, LAB,
   */
  private class CompareReachability implements ComparisonStrategy {
    @Override
    public int compare(Node node1, Node node2) {
      return Integer.compare(node2.getReachability(), node1.getReachability());
    }
  }

  private class CompareMajor implements ComparisonStrategy {
    @Override
    public int compare(Node node1, Node node2) {
      return Boolean.compare(node2.getCourse().isMajor(), node1.getCourse().isMajor());
    }
  }
}
