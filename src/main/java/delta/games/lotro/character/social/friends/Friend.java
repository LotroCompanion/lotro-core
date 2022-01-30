package delta.games.lotro.character.social.friends;

import java.util.Date;

import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.lore.crafting.CraftingData;
import delta.games.lotro.lore.crafting.CraftingSystem;
import delta.games.lotro.lore.crafting.Vocation;
import delta.games.lotro.lore.crafting.Vocations;
import delta.games.lotro.lore.maps.Area;
import delta.games.lotro.lore.maps.GeoAreasManager;

/**
 * Friend.
 * @author DAM
 */
public class Friend
{
  private InternalGameId _id;
  private String _characterName;
  private CharacterClass _class;
  private int _level;
  private Integer _vocationID;
  private String _vocation;
  private Long _lastLogoutDate;
  private Integer _areaID;
  private String _area;
  private String _kinshipName;
  private String _note;
  // Unused:
  // Alignment (always good)
  // Presence (boolean)
  // Group ID (0 or Instance ID)
  // Anonymous (boolean)

  /**
   * Constructor.
   */
  public Friend()
  {
    _id=null;
    _characterName="";
    _class=null;
    _level=0;
    _vocationID=null;
    _vocation=null;
    _lastLogoutDate=null;
    _areaID=null;
    _area=null;
    _kinshipName="";
    _note="";
  }

  /**
   * Copy constructor.
   * @param source Source character.
   */
  public Friend(Friend source)
  {
    if (source._id!=null)
    {
      _id=new InternalGameId(source._id);
    }
    _characterName=source._characterName;
    _class=source._class;
    _level=source._level;
    _vocationID=source._vocationID;
    _vocation=source._vocation;
    _lastLogoutDate=source._lastLogoutDate;
    _areaID=source._areaID;
    _area=source._area;
    _kinshipName=source._kinshipName;
    _note=source._note;
  }

  /**
   * Get the character identififer for this friend.
   * @return an identifier or <code>null</code> if not set.
   */
  public InternalGameId getId()
  {
    return _id;
  }

  /**
   * Set the character identifier for this friend.
   * @param id Identifier to set.
   */
  public void setId(InternalGameId id)
  {
    _id=id;
  }

  /**
   * Get the friend's character name.
   * @return a character name.
   */
  public String getName()
  {
    return _characterName;
  }

  /**
   * Set the friend's character name.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    if (name==null)
    {
      name="";
    }
    _characterName=name;
  }

  /**
   * Get the character's class.
   * @return the character's class.
   */
  public CharacterClass getCharacterClass()
  {
    return _class;
  }

  /**
   * Set the character's class.
   * @param characterClass the class to set.
   */
  public void setCharacterClass(CharacterClass characterClass)
  {
    _class=characterClass;
  }

  /**
   * Get the character's level.
   * @return the character's level.
   */
  public int getLevel()
  {
    return _level;
  }

  /**
   * Set the character's level.
   * @param level the level to set.
   */
  public void setLevel(int level)
  {
    _level=level;
  }

  /**
   * Get the vocation ID;
   * @return the vocation ID.
   */
  public Integer getVocationID()
  {
    return _vocationID;
  }

  /**
   * Set the vocation ID.
   * @param vocationID Vocation ID to set.
   */
  public void setVocationID(Integer vocationID)
  {
    _vocationID=vocationID;
    _vocation=null;
  }

  /**
   * Get the vocation label.
   * @return A label or <code>null</code> if no vocation.
   */
  public String getVocation()
  {
    if ((_vocationID!=null) && (_vocation==null))
    {
      CraftingData craftingData=CraftingSystem.getInstance().getData();
      Vocations vocations=craftingData.getVocationsRegistry();
      Vocation vocation=vocations.getVocationById(_vocationID.intValue());
      _vocation=(vocation!=null)?vocation.getName():"?";
    }
    return _vocation;
  }

  /**
   * Get the last logout date for this character.
   * @return a timestamp (milliseconds since Epoch) or <code>null</code>.
   */
  public Long getLastLogoutDate()
  {
    return _lastLogoutDate;
  }

  /**
   * Set the last logout date for this character.
   * @param lastLogoutDate the date to set.
   */
  public void setLastLogoutDate(Long lastLogoutDate)
  {
    _lastLogoutDate=lastLogoutDate;
  }

  /**
   * Get the area ID;
   * @return the area ID.
   */
  public Integer getAreaID()
  {
    return _areaID;
  }

  /**
   * Set the area ID.
   * @param areaID Area ID to set.
   */
  public void setAreaID(Integer areaID)
  {
    _areaID=areaID;
    _area=null;
  }

  /**
   * Get the area label.
   * @return A label or <code>null</code> if no vocation.
   */
  public String getArea()
  {
    if ((_areaID!=null) && (_area==null))
    {
      GeoAreasManager areasMgr=GeoAreasManager.getInstance();
      Area area=areasMgr.getAreaById(_areaID.intValue());
      _area=(area!=null)?area.getName():"?";
    }
    return _area;
  }

  /**
   * Get the kinship name.
   * @return A kinship name or an empty string (never <code>null</code>).
   */
  public String getKinshipName()
  {
    return _kinshipName;
  }

  /**
   * Set the kinship name.
   * @param kinshipName Kinship name to set.
   */
  public void setKinshipName(String kinshipName)
  {
    if (kinshipName==null)
    {
      kinshipName="";
    }
    _kinshipName=kinshipName; 
  }

  /**
   * Get the note.
   * @return A note or an empty string (never <code>null</code>).
   */
  public String getNote()
  {
    return _note;
  }

  /**
   * Set the note.
   * @param note Note to set.
   */
  public void setNote(String note)
  {
    if (note==null)
    {
      note="";
    }
    _note=note; 
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("ID [").append(_id).append("], ");
    sb.append("Name [").append(_characterName).append("], ");
    sb.append("Class [").append(_class).append("], ");
    sb.append("Level [").append(_level).append(']');
    if (_vocationID!=null)
    {
      sb.append(", Vocation ID [").append(_vocationID).append("] (").append(getVocation()).append(')');
    }
    if (_lastLogoutDate!=null)
    {
      sb.append(", Last logout date [").append(_lastLogoutDate);
      sb.append(" = ").append(new Date(_lastLogoutDate.longValue())).append("], ");
    }
    if (_areaID!=null)
    {
      sb.append(", Area ID [").append(_areaID).append("] (").append(getArea()).append(')');
    }
    sb.append(", kinship [").append(_kinshipName).append(']');
    sb.append(", note [").append(_note).append(']');
    return sb.toString();
  }
}
