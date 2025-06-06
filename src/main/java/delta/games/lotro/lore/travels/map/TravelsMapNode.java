package delta.games.lotro.lore.travels.map;

import java.awt.Dimension;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.lore.travels.TravelNpc;

/**
 * Travel map node.
 * @author DAM
 */
public class TravelsMapNode implements Identifiable,Named
{
  // Parent NPC
  private TravelNpc _npc;
  private Dimension _uiPosition;
  private String _tooltip;
  private boolean _capital;

  /**
   * Constructor.
   * @param npc Associated NPC.
   * @param uiPosition UI position.
   * @param tooltip Tooltip.
   * @param capital Capital flag.
   */
  public TravelsMapNode(TravelNpc npc, Dimension uiPosition, String tooltip, boolean capital)
  {
    _npc=npc;
    _uiPosition=uiPosition;
    _tooltip=tooltip;
    _capital=capital;
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
  public TravelNpc getNpc()
  {
    return _npc;
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
   * Get the tooltip.
   * @return the tooltip.
   */
  public String getTooltip()
  {
    return _tooltip;
  }

  /**
   * Get the 'capital' flag.
   * @return the 'capital' flag.
   */
  public boolean isCapital()
  {
    return _capital;
  }

  @Override
  public String toString()
  {
    return _npc.toString();
  }
}
