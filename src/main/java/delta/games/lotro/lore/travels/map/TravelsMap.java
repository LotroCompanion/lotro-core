package delta.games.lotro.lore.travels.map;

import java.util.ArrayList;
import java.util.List;

/**
 * Gather data about the travels map.
 * @author DAM
 */
public class TravelsMap
{
  private List<TravelsMapNode> _nodes;
  private List<TravelsMapLabel> _labels;

  /**
   * Constructor.
   */
  public TravelsMap()
  {
    _nodes=new ArrayList<TravelsMapNode>();
    _labels=new ArrayList<TravelsMapLabel>();
  }

  /**
   * Get the travels map nodes.
   * @return the travels map nodes.
   */
  public List<TravelsMapNode> getNodes()
  {
    return _nodes;
  }

  /**
   * Add a node.
   * @param node Node to add.
   */
  public void addNode(TravelsMapNode node)
  {
    _nodes.add(node);
  }

  /**
   * Get the travels map labels.
   * @return the travels map labels.
   */
  public List<TravelsMapLabel> getLabels()
  {
    return _labels;
  }

  /**
   * Add a label.
   * @param label Node to add.
   */
  public void addLabel(TravelsMapLabel label)
  {
    _labels.add(label);
  }
}
