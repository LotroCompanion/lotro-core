package delta.games.lotro.common.enums.directory;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Description of an enum.
 * @author DAM
 */
public class LotroEnumDescription implements Identifiable,Named
{
  private int _id;
  private String _name;
  private String _entryClassName;

  /**
   * Constructor.
   * @param id Internal ID.
   * @param name Name.
   * @param entryClassName Entry class name.
   */
  public LotroEnumDescription(int id, String name, String entryClassName)
  {
    _id=id;
    _name=name;
    _entryClassName=entryClassName;
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  @Override
  public String getName()
  {
    return _name;
  }

  /**
   * Get the entry implementation class.
   * @return A class.
   */
  public String getEntryClassName()
  {
    return _entryClassName;
  }
}
