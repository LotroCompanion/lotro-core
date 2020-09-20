package delta.games.lotro.lore.instances;

import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.geo.BlockReference;

/**
 * Private encounter description.
 * @author DAM
 */
public class PrivateEncounter implements Identifiable
{
  private int _id;
  private String _name;
  private String _description;
  private int _contentLayerId;
  private List<BlockReference> _blocks;
  private Integer _questId;
  // Start position
  // Success position
  // Requirements
  // - quests
  // - world conditions
  // Creation permissions
  // - quests
  // - world conditions
  // Maximum number of players
  private int _maxPlayers;

  public int getIdentifier()
  {
    return _id;
  }
}
