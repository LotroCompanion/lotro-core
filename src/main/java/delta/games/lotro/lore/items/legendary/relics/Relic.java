package delta.games.lotro.lore.items.legendary.relics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryKeyComparator;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryNameComparator;
import delta.games.lotro.common.requirements.UsageRequirement;
import delta.games.lotro.lore.items.EquipmentLocation;

/**
 * Relic description.
 * @author DAM
 */
public class Relic implements Identifiable,Named
{
  private int _identifier;
  private String _name;
  private Set<RelicType> _types;
  private Set<EquipmentLocation> _allowedSlots;
  private RelicsCategory _category;
  private String _iconFilename;
  private UsageRequirement _requirements; // contains min level and class
  private BasicStatsSet _stats;

  /**
   * Constructor.
   * @param identifier Identifier.
   * @param name Relic name.
   */
  public Relic(int identifier, String name)
  {
    _identifier=identifier;
    _name=name;
    _types=new HashSet<RelicType>();
    _allowedSlots=new HashSet<EquipmentLocation>();
    _category=null;
    _iconFilename=null;
    _requirements=new UsageRequirement();
    _stats=new BasicStatsSet();
  }

  /**
   * Get the identifier of this relic.
   * @return a relic identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the relic name.
   * @return the relic name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Indicates if this relic has the given type.
   * @param type the relic type to check.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasType(RelicType type)
  {
    return _types.contains(type);
  }

  /**
   * Add a supported type.
   * @param type Type to add.
   */
  public void addType(RelicType type)
  {
    _types.add(type);
  }

  /**
   * Get all supported types.
   * @return a list of types (usually 1 item).
   */
  public List<RelicType> getTypes()
  {
    List<RelicType> types=new ArrayList<RelicType>();
    types.addAll(_types);
    Collections.sort(types);
    return types;
  }

  /**
   * Get all types as a ',' separated string.
   * @return a string.
   */
  public String getTypesStr()
  {
    StringBuilder sb=new StringBuilder();
    for(RelicType type : getTypes())
    {
      if (sb.length()>0) sb.append(',');
      sb.append(type);
    }
    return sb.toString();
  }

  /**
   * Indicates if this relic goes in the given slot.
   * @param slot the slot to check.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean isSlotAllowed(EquipmentLocation slot)
  {
    return _allowedSlots.contains(slot);
  }

  /**
   * Add an allowed slot.
   * @param slot Slot to add.
   */
  public void addAllowedSlot(EquipmentLocation slot)
  {
    _allowedSlots.add(slot);
  }

  /**
   * Get all allowed slots (string for UI).
   * @return a displayable string.
   */
  public String getAllowedSlotsForUI()
  {
    List<EquipmentLocation> slots=new ArrayList<EquipmentLocation>(_allowedSlots);
    Collections.sort(slots,new LotroEnumEntryNameComparator<EquipmentLocation>());
    StringBuilder sb=new StringBuilder();
    for(EquipmentLocation slot : slots)
    {
      if (sb.length()>0) sb.append(", ");
      sb.append(slot.getLabel());
    }
    return sb.toString();
  }

  /**
   * Get all allowed slots (string for persistence).
   * @return a string usable for persistence.
   */
  public String getAllowedSlotsForPersistence()
  {
    List<EquipmentLocation> slots=new ArrayList<EquipmentLocation>(_allowedSlots);
    Collections.sort(slots,new LotroEnumEntryKeyComparator<EquipmentLocation>());
    StringBuilder sb=new StringBuilder();
    for(EquipmentLocation slot : slots)
    {
      if (sb.length()>0) sb.append(',');
      sb.append(slot.getKey());
    }
    return sb.toString();
  }

  /**
   * Get the required item level for this relic.
   * @return an item level.
   */
  public Integer getRequiredLevel()
  {
    return _requirements.getMinLevel();
  }

  /**
   * Set the required level.
   * @param requiredLevel Level to set or <code>null</code>.
   */
  public void setRequiredLevel(Integer requiredLevel)
  {
    _requirements.setMinLevel(requiredLevel);
  }

  /**
   * Get the usage requirements.
   * @return the usage requirements.
   */
  public UsageRequirement getUsageRequirement()
  {
    return _requirements;
  }

  /**
   * Get the stats of this relic.
   * @return a set of stats.
   */
  public BasicStatsSet getStats()
  {
    return _stats;
  }

  /**
   * Get the icon filename for this relic.
   * @return a icon filename.
   */
  public String getIconFilename()
  {
    return _iconFilename;
  }

  /**
   * Set icon filename.
   * @param iconFilename Filename to set.
   */
  public void setIconFilename(String iconFilename)
  {
    _iconFilename=iconFilename;
  }

  /**
   * Get the category of this relic.
   * @return a relic category.
   */
  public RelicsCategory getCategory()
  {
    return _category;
  }

  /**
   * Set category.
   * @param category Category to set.
   */
  public void setCategory(RelicsCategory category)
  {
    _category=category;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_name);
    sb.append(" (").append(_types).append(") ");
    if (_identifier!=0)
    {
      sb.append("(ID=").append(_identifier).append(") ");
    }
    if (!_requirements.isEmpty())
    {
      sb.append("(requires ").append(_requirements).append(") ");
    }
    sb.append("(slot(s)=").append(_allowedSlots);
    if (_iconFilename!=null)
    {
      sb.append("(icon=").append(_iconFilename).append(") ");
    }
    sb.append(_stats);
    return sb.toString();
  }
}
