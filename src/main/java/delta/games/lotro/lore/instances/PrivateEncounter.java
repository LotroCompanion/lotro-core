package delta.games.lotro.lore.instances;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
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
  // Quests to bestow (array)
  private List<Integer> _questsToBestow;
  // TODO LATER
  // Start position
  // Success position
  // Requirements
  // - quests
  // - world conditions
  // Creation permissions
  // - quests
  // - world conditions
  // Maximum number of players
  private Integer _maxPlayers;

  /**
   * Constructor.
   * @param id Identifier.
   */
  public PrivateEncounter(int id)
  {
    _id=id;
    _name="";
    _description="";
    _blocks=new ArrayList<BlockReference>();
    _questsToBestow=new ArrayList<Integer>();
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Get the instance name.
   * @return the instance name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the instance name.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    if (name==null)
    {
      name="";
    }
    _name=name;
  }

  /**
   * Get the instance description.
   * @return the instance description.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the instance description.
   * @param description Description to set.
   */
  public void setDescription(String description)
  {
    if (description==null)
    {
      description="";
    }
    _description=description;
  }

  /**
   * Get the identifier of the associated content layer.
   * @return a content layer identifier.
   */
  public int getContentLayerId()
  {
    return _contentLayerId;
  }

  /**
   * Set the content layer identifier.
   * @param contentLayerId Identifier to set.
   */
  public void setContentLayerId(int contentLayerId)
  {
    _contentLayerId=contentLayerId;
  }

  /**
   * Add a block reference.
   * @param block Bloc reference to add.
   */
  public void addBlock(BlockReference block)
  {
    _blocks.add(block);
  }

  /**
   * Get the blocks for this instance.
   * @return a list of block references.
   */
  public List<BlockReference> getBlocks()
  {
    return _blocks;
  }

  /**
   * Get the identifier of the associated quest.
   * @return A quest identifier or <code>null</code>.
   */
  public Integer getQuestId()
  {
    return _questId;
  }

  /**
   * Set the identifier of the associated quest.
   * @param questId Quest identifier (may be <code>null</code>).
   */
  public void setQuestId(Integer questId)
  {
    _questId=questId;
  }

  /**
   * Add an identifier for a 'quest to bestow'.
   * @param questId Quest identifier.
   */
  public void addQuestToBestow(int questId)
  {
    _questsToBestow.add(Integer.valueOf(questId));
  }

  /**
   * Get the quests to bestow for this instance.
   * @return a list of quest identifiers.
   */
  public List<Integer> getQuestsToBestow()
  {
    return _questsToBestow;
  }

  /**
   * Get the maximum number of players in the instance, if any.
   * @return A player count, or <code>null</code>.
   */
  public Integer getMaxPlayers()
  {
    return _maxPlayers;
  }

  /**
   * Set the maximum number of players in this instance.
   * @param maxPlayers Player count to set, may be <code>null</code>.
   */
  public void setMaxPlayers(Integer maxPlayers)
  {
    _maxPlayers=maxPlayers;
  }

  protected void dump(StringBuilder sb)
  {
    String className=getClass().getSimpleName();
    sb.append(className).append(": ID=").append(_id);
    sb.append(", name=").append(_name);
    sb.append(", content layer=").append(_contentLayerId).append(EndOfLine.NATIVE_EOL);
    if (_description.length()>0)
    {
      sb.append("Description=").append(_description).append(EndOfLine.NATIVE_EOL);
    }
    sb.append("Blocks=").append(_blocks).append(EndOfLine.NATIVE_EOL);
    if (_questId!=null)
    {
      sb.append("Quest=").append(_questId).append(EndOfLine.NATIVE_EOL);
    }
    if (_questsToBestow.size()>0)
    {
      sb.append("Quest(s) to bestow=").append(_questsToBestow).append(EndOfLine.NATIVE_EOL);
    }
    if (_maxPlayers!=null)
    {
      sb.append("Max players=").append(_maxPlayers).append(EndOfLine.NATIVE_EOL);
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    dump(sb);
    return sb.toString().trim();
  }
}
