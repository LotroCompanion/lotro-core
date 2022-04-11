package delta.games.lotro.character;

import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Base class for LOTRO character summaries.
 * @author DAM
 */
public class CharacterReference implements Named
{
  private InternalGameId _id;
  private String _characterName;
  private CharacterClass _class;
  private int _level;

  /**
   * Constructor.
   */
  public CharacterReference()
  {
    _id=null;
    _characterName="";
    _class=null;
    _level=0;
  }

  /**
   * Copy constructor.
   * @param source Source character.
   */
  public CharacterReference(CharacterReference source)
  {
    if (source._id!=null)
    {
      _id=new InternalGameId(source._id);
    }
    _characterName=source._characterName;
    _class=source._class;
    _level=source._level;
  }

  /**
   * Get the identififer for this character.
   * @return an identifier or <code>null</code> if not set.
   */
  public InternalGameId getId()
  {
    return _id;
  }

  /**
   * Set the identifier for this character.
   * @param id Identifier to set.
   */
  public void setId(InternalGameId id)
  {
    _id=id;
  }

  /**
   * Get the character's name.
   * @return the character's name.
   */
  public String getName()
  {
    return _characterName;
  }

  /**
   * Set the character's name.
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

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("ID [").append(_id).append("], ");
    sb.append("Name [").append(_characterName).append("], ");
    sb.append("Class [").append(_class).append("], ");
    sb.append("Level [").append(_level).append(']');
    return sb.toString();
  }
}
