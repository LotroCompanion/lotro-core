package delta.games.lotro.lore.items.essences;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.NumericTools;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.SocketType;

/**
 * Setup of essence slots.
 * @author DAM
 */
public class EssencesSlotsSetup
{
  private List<SocketType> _entries;

  /**
   * Constructor.
   */
  public EssencesSlotsSetup()
  {
    _entries=new ArrayList<SocketType>();
  }

  /**
   * Get the number of sockets.
   * @return A sockets count.
   */
  public int getSocketsCount()
  {
    return _entries.size();
  }

  /**
   * Add a slot.
   * @param type Type of slot to add.
   */
  public void addSlot(SocketType type)
  {
    _entries.add(type);
  }

  /**
   * Get a slot.
   * @param index Index of socket to get, starting at 0.
   * @return A socket.
   */
  public SocketType getSlotType(int index)
  {
    return _entries.get(index);
  }

  /**
   * Get a persistence string for this setup.
   * @return A string.
   */
  public String toPersistenceString()
  {
    if (_entries.isEmpty())
    {
      return "";
    }
    // All classic codes?
    if (isAllClassic())
    {
      return String.valueOf(_entries.size());
    }
    // Series of one letter codes (1 code per slot type)
    StringBuilder sb=new StringBuilder();
    for(SocketType type : _entries)
    {
      String key=type.getKey();
      if (key==null)
      {
        key="?";
      }
      sb.append(key);
    }
    return sb.toString();
  }

  private boolean isAllClassic()
  {
    for(SocketType type : _entries)
    {
      if (type!=SocketTypes.CLASSIC)
      {
        return false;
      }
    }
    return true;
  }

  /**
   * Load a setup from a persistence string.
   * @param input Input string.
   * @return A setup or <code>null</code> if empty.
   */
  public static EssencesSlotsSetup fromPersistenceString(String input)
  {
    if (input.isEmpty())
    {
      return null;
    }
    EssencesSlotsSetup ret=new EssencesSlotsSetup();
    LotroEnum<SocketType> socketTypeEnum=LotroEnumsRegistry.getInstance().get(SocketType.class);
    Integer count=NumericTools.parseInteger(input,false);
    if (count!=null)
    {
      SocketType classicSlot=SocketTypes.CLASSIC;
      for(int i=0;i<count.intValue();i++)
      {
        ret.addSlot(classicSlot);
      }
      return ret;
    }
    int nbSlots=input.length();
    for(int i=0;i<nbSlots;i++)
    {
      String key=input.substring(i,i+1);
      SocketType type=socketTypeEnum.getByKey(key);
      if (type!=null)
      {
        ret.addSlot(type);
      }
    }
    return ret;
  }
}
