package delta.games.lotro.lore.travels;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.geo.ExtendedPosition;
import delta.games.lotro.lore.agents.npcs.NpcDescription;

/**
 * Travel data for a NPC.
 * @author DAM
 */
public class TravelNpc implements Identifiable,Named
{
  // Parent NPC
  private NpcDescription _npc;
  private ExtendedPosition _position;
  private Dimension _uiPosition;
  private List<Integer> _discounts;
  private float _sellFactor;
  private TravelNode _node;
  private boolean _mustBeDiscovered;

  /**
   * Constructor.
   * @param npc Associated NPC.
   */
  public TravelNpc(NpcDescription npc)
  {
    _npc=npc;
    _position=null;
    _uiPosition=null;
    _discounts=new ArrayList<Integer>();
    _sellFactor=1;
    _node=null;
    _mustBeDiscovered=true;
  }

  /**
   * Get the identifier of the parent NPC.
   * @return a NPC identifier.
   */
  public int getIdentifier()
  {
    return _npc.getIdentifier();
  }

  @Override
  public String getName()
  {
    return _npc.getName();
  }

  /**
   * Get the associated NPC.
   * @return a NPC.
   */
  public NpcDescription getNpc()
  {
    return _npc;
  }

  /**
   * Get the position.
   * @return the position.
   */
  public ExtendedPosition getPosition()
  {
    return _position;
  }

  /**
   * Set the position.
   * @param position the position to set.
   */
  public void setPosition(ExtendedPosition position)
  {
    _position=position;
  }

  /**
   * Get the UI position.
   * @return the UI position.
   */
  public Dimension getUIPosition()
  {
    return _uiPosition;
  }

  /**
   * Set the UI position.
   * @param uiPosition the UI position to set.
   */
  public void setUIPosition(Dimension uiPosition)
  {
    _uiPosition=uiPosition;
  }

  /**
   * Add a discount.
   * @param discountId Discount to add.
   */
  public void addDiscount(int discountId)
  {
    _discounts.add(Integer.valueOf(discountId));
  }

  /**
   * Get the discounts.
   * @return a list discounts.
   */
  public List<Integer> getDiscounts()
  {
    return _discounts;
  }

  /**
   * Get the sell factor.
   * @return a factor.
   */
  public float getSellFactor()
  {
    return _sellFactor;
  }

  /**
   * Set the sell factor.
   * @param sellFactor Factor to set.
   */
  public void setSellFactor(float sellFactor)
  {
    _sellFactor=sellFactor;
  }

  /**
   * Get the associated travel node.
   * @return a travel node.
   */
  public TravelNode getNode()
  {
    return _node;
  }

  /**
   * Set the associated travel node.
   * @param node the node to set.
   */
  public void setNode(TravelNode node)
  {
    _node=node;
  }

  /**
   * Indicates if this NPC shall be discovered.
   * @return <code>true</code> if it shall, <code>false</code> otherwise.
   */
  public boolean isMustBeDiscovered()
  {
    return _mustBeDiscovered;
  }

  /**
   * Set the value of the 'must be discovered' flag.
   * @param mustBeDiscovered the value to set.
   */
  public void setMustBeDiscovered(boolean mustBeDiscovered)
  {
    _mustBeDiscovered=mustBeDiscovered;
  }

  /**
   * Dump the contents of this NPC barter data as a readable string.
   * @return A displayable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_npc).append(EndOfLine.NATIVE_EOL);
    if (_position!=null)
    {
      sb.append(", position=").append(_position);
    }
    if (_node!=null)
    {
      sb.append(", node=").append(_node);
    }
    sb.append(", sell factor=").append(_sellFactor);
    if (_mustBeDiscovered)
    {
      sb.append(", must be discovered");
    }
    sb.append(", discounts=").append(_discounts);
    sb.append(EndOfLine.NATIVE_EOL);
    return sb.toString().trim();
  }

  @Override
  public String toString()
  {
    return _npc.toString();
  }
}
