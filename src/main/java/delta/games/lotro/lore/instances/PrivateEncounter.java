package delta.games.lotro.lore.instances;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Private encounter description.
 * @author DAM
 */
public class PrivateEncounter implements Identifiable,Named
{
  private int _id;
  private String _name;
  private String _description;
  private int _contentLayerId;
  private List<Integer> _additionalContentLayers;
  private List<InstanceMapDescription> _mapDescriptions;
  // Related quests
  private PrivateEncounterQuests _quests;
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
    _additionalContentLayers=new ArrayList<Integer>();
    _mapDescriptions=new ArrayList<InstanceMapDescription>();
    _quests=new PrivateEncounterQuests();
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
   * Add an additional content layer.
   * @param contentLayerId Content layer identifier.
   */
  public void addAdditionalContentLayer(int contentLayerId)
  {
    _additionalContentLayers.add(Integer.valueOf(contentLayerId));
  }

  /**
   * Get the additional content layers.
   * @return A possibly empty but never <code>null</code> list of content layer identifiers.
   */
  public List<Integer> getAdditionalContentLayers()
  {
    return _additionalContentLayers;
  }

  /**
   * Add a map description.
   * @param mapDescription Map to add.
   */
  public void addMapDescription(InstanceMapDescription mapDescription)
  {
    _mapDescriptions.add(mapDescription);
  }

  /**
   * Get the maps for this instance.
   * @return a list of maps items.
   */
  public List<InstanceMapDescription> getMapDescriptions()
  {
    return _mapDescriptions;
  }

  /**
   * Remove a map description.
   * @param mapIdToRemove Identifier of the map to remove.
   */
  public void removeMapDescription(int mapIdToRemove)
  {
    InstanceMapDescription toRemove=getMapById(mapIdToRemove);
    if (toRemove!=null)
    {
      _mapDescriptions.remove(toRemove);
    }
  }

  /**
   * Remove a zone.
   * @param zoneId Zone identifier.
   */
  public void removeZone(int zoneId)
  {
    for(InstanceMapDescription map : _mapDescriptions)
    {
      map.removeZone(zoneId);
    }
  }

  private InstanceMapDescription getMapById(int mapIdToSearch)
  {
    for(InstanceMapDescription map : _mapDescriptions)
    {
      Integer mapId=map.getMap().getMapId();
      if ((mapId!=null) && (mapId.intValue()==mapIdToSearch))
      {
        return map;
      }
    }
    return null;
  }

  /**
   * Get the quests data for this encounter.
   * @return the quests data.
   */
  public PrivateEncounterQuests getQuests()
  {
    return _quests;
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
    if (_mapDescriptions.size()>0)
    {
      sb.append("Maps=").append(EndOfLine.NATIVE_EOL);
      for(InstanceMapDescription map : _mapDescriptions)
      {
        sb.append("\t").append(map).append(EndOfLine.NATIVE_EOL);
      }
    }
    _quests.dump(sb);
    if (_maxPlayers!=null)
    {
      sb.append("Max players=").append(_maxPlayers).append(EndOfLine.NATIVE_EOL);
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("ID=").append(_id).append(", name=").append(_name);
    return sb.toString();
  }
}
