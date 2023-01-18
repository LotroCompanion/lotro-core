package delta.games.lotro.lore.allegiances;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.enums.AllegianceGroup;
import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Allegiance description.
 * @author DAM
 */
public class AllegianceDescription implements Identifiable,Named
{
  private int _identifier;
  private String _name;
  private int _iconId;
  private AllegianceGroup _group;
  private String _description;
  private Integer _minLevel;
  private SkillDescription _travelSkill;
  private List<DeedDescription> _deeds;

  /**
   * Constructor.
   */
  public AllegianceDescription()
  {
    _identifier=0;
    _name="";
    _iconId=0;
    _group=null;
    _description="";
    _minLevel=null;
    _travelSkill=null;
    _deeds=new ArrayList<DeedDescription>();
  }

  /**
   * Get the identifier of this allegiance.
   * @return the identifier of this allegiance.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this allegiance.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the name of this allegiance.
   * @return the name of this allegiance.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this allegiance.
   * @param name the name to set.
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
   * Get the icon ID of this allegiance.
   * @return the icon ID of this allegiance.
   */
  public int getIconId()
  {
    return _iconId;
  }

  /**
   * Set the icon ID of this allegiance.
   * @param iconId the icon ID to set.
   */
  public void setIconId(int iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the group of this allegiance.
   * @return the group of this allegiance.
   */
  public AllegianceGroup getGroup()
  {
    return _group;
  }

  /**
   * Set the group of this allegiance.
   * @param group the group to set.
   */
  public void setGroup(AllegianceGroup group)
  {
    _group=group;
  }

  /**
   * Get the description of this allegiance.
   * @return the description of this allegiance.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this allegiance.
   * @param description the description to set.
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
   * Get the minimum level.
   * @return A level value or <code>null</code>.
   */
  public Integer getMinLevel()
  {
    return _minLevel;
  }

  /**
   * Set the minimum level.
   * @param minLevel Minimum level to set.
   */
  public void setMinLevel(Integer minLevel)
  {
    _minLevel=minLevel;
  }

  /**
   * Get the associated travel skill.
   * @return A skill or <code>null</code>.
   */
  public SkillDescription getTravelSkill()
  {
    return _travelSkill;
  }

  /**
   * Set the associated travel skill.
   * @param travelSkill Skill to set.
   */
  public void setTravelSkill(SkillDescription travelSkill)
  {
    _travelSkill=travelSkill;
  }

  /**
   * Get the associated deeds.
   * @return A list of deeds.
   */
  public List<DeedDescription> getDeeds()
  {
    return new ArrayList<DeedDescription>(_deeds);
  }

  /**
   * Add a deed.
   * @param deed Deed to add.
   */
  public void addDeed(DeedDescription deed)
  {
    _deeds.add(deed);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Allegiance id=").append(_identifier);
    sb.append(", name=").append(_name);
    if (_iconId!=0)
    {
      sb.append(", icon=").append(_iconId);
    }
    sb.append(", group=").append(_group);
    if (_minLevel!=null)
    {
      sb.append(", min level=").append(_minLevel);
    }
    if (_travelSkill!=null)
    {
      sb.append(", travel skill: ").append(_travelSkill);
    }
    sb.append(", description=").append(_description).append(EndOfLine.NATIVE_EOL);
    for(DeedDescription deed : _deeds)
    {
      sb.append('\t').append(deed).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }
}
