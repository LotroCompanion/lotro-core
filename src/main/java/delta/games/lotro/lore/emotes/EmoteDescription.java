package delta.games.lotro.lore.emotes;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * LOTRO emote description.
 * @author DAM
 */
public class EmoteDescription implements Identifiable,Named
{
  private int _identifier;
  private String _command;
  private int _iconId;
  private String _description;
  private boolean _auto;

  /**
   * Constructor.
   */
  public EmoteDescription()
  {
    _identifier=0;
    _command="";
    _description="";
    _iconId=0;
    _auto=true;
  }

  /**
   * Get the identifier of this emote.
   * @return the identifier of this emote.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Set the identifier of this emote.
   * @param identifier the identifier to set.
   */
  public void setIdentifier(int identifier)
  {
    _identifier=identifier;
  }

  @Override
  public String getName()
  {
    return _command;
  }

  /**
   * Get the command of this emote.
   * @return the command of this emote.
   */
  public String getCommand()
  {
    return _command;
  }

  /**
   * Set the command of this emote.
   * @param command the command to set.
   */
  public void setCommand(String command)
  {
    if (command==null) command="";
    _command=command;
  }

  /**
   * Get the icon ID of this emote.
   * @return the icon ID of this emote.
   */
  public int getIconId()
  {
    return _iconId;
  }

  /**
   * Set the icon ID of this emote.
   * @param iconId the icon ID to set.
   */
  public void setIconId(int iconId)
  {
    _iconId=iconId;
  }

  /**
   * Get the description of this emote.
   * @return the description of this emote.
   */
  public String getDescription()
  {
    return _description;
  }

  /**
   * Set the description of this emote.
   * @param description the description to set.
   */
  public void setDescription(String description)
  {
    if (description==null) description="";
    _description=description;
  }

  /**
   * Indicates if this emote is automatically acquired, or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isAuto() {
    return _auto;
  }

  /**
   * Set the value of the 'auto' flag.
   * @param auto Value to set.
   */
  public void setAuto(boolean auto)
  {
    _auto=auto;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Emote id=").append(_identifier);
    sb.append(", command=").append(_command);
    if (_iconId!=0)
    {
      sb.append(", icon=").append(_iconId);
    }
    if (_auto)
    {
      sb.append(", auto");
    }
    sb.append(", description=").append(_description);
    return sb.toString();
  }
}
