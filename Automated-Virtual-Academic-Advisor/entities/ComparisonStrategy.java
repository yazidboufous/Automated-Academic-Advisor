package entities;

/**
 * An interface that represents the heuristics
 */
public interface ComparisonStrategy {
  int compare(Node a, Node b);
}