package delta.games.lotro.lore.portraitFrames;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Description of a portrait frame.
 * @author DAM
 */
public class PortraitFrameDescription implements Identifiable,Named
{
  private int _identifier;
  private String _name;
  private int _code;
  private String _iconName;
  private boolean _isForFreeps;
  private boolean _isForCreeps;
  private boolean _isForPvpCharacters;

  /**
   * Constructor.
   */
  public PortraitFrameDescription()
  {
    _identifier=0;
    _name="";
    _code=0;
    _iconName="";
    _isForFreeps=true;
    _isForCreeps=false;
    _isForPvpCharacters=false;
  }

  /**
   * Get the identifier of this frame.
   * @return the identifier of this frame.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this frame.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  /**
   * Get the name of this frame.
   * @return the name of this frame.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this frame.
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
   * Get the internal code for this frame.
   * @return the code of this frame.
   */
  public int getCode()
  {
    return _code;
  }

  /**
   * Set the internal code for this frame.
   * @param code the code to set.
   */
  public void setCode(int code)
  {
    _code=code;
  }

  /**
   * Get the icon name of this frame.
   * @return the icon name of this frame.
   */
  public String getIconName()
  {
    return _iconName;
  }

  /**
   * Set the icon name of this frame.
   * @param iconName the icon name to set.
   */
  public void setIconName(String iconName)
  {
    if (iconName==null)
    {
      iconName="";
    }
    _iconName=iconName;
  }

  /**
   * Indicates if this frame is for freeps.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isForFreeps()
  {
    return _isForFreeps;
  }

  /**
   * Set the value of the 'is for freeps' flag.
   * @param isForFreeps Value to set.
   */
  public void setIsForFreeps(boolean isForFreeps)
  {
    _isForFreeps=isForFreeps;
  }

  /**
   * Indicates if this frame is for creeps.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isForCreeps()
  {
    return _isForCreeps;
  }

  /**
   * Set the value of the 'is for creeps' flag.
   * @param isForCreeps Value to set.
   */
  public void setIsForCreeps(boolean isForCreeps)
  {
    _isForCreeps=isForCreeps;
  }

  /**
   * Indicates if this frame is for PVP characters.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isForPvpCharacters()
  {
    return _isForPvpCharacters;
  }

  /**
   * Set the value of the 'is for PVP characters' flag.
   * @param isForPvpCharacters Value to set.
   */
  public void setIsForPvpCharacters(boolean isForPvpCharacters)
  {
    _isForPvpCharacters=isForPvpCharacters;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Portrait frame id=").append(_identifier);
    sb.append(", name=").append(_name);
    if (_code!=0)
    {
      sb.append(", code=").append(_code);
    }
    sb.append(", icon=").append(_iconName);
    if (_isForFreeps)
    {
      sb.append(" (for freeps)");
    }
    if (_isForCreeps)
    {
      sb.append(" (for creeps)");
    }
    if (_isForPvpCharacters)
    {
      sb.append(" (for PVP characters)");
    }
    return sb.toString();
  }
}
