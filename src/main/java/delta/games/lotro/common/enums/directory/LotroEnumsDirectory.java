package delta.games.lotro.common.enums.directory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;

/**
 * Directory of managed enums.
 * @author DAM
 */
public class LotroEnumsDirectory
{
  private Map<Integer,LotroEnumDescription> _directory;

  /**
   * Constructor.
   */
  public LotroEnumsDirectory()
  {
    _directory=new HashMap<Integer,LotroEnumDescription>();
  }

  /**
   * Get an enum using its identifier.
   * @param id Identifier of the enum to get.
   * @return An enum description or <code>null</code> if not found.
   */
  public LotroEnumDescription getById(int id)
  {
    return _directory.get(Integer.valueOf(id));
  }

  /**
   * Register an enum.
   * @param enumDescription Description to add.
   */
  public void registerEnum(LotroEnumDescription enumDescription)
  {
    Integer key=Integer.valueOf(enumDescription.getIdentifier());
    _directory.put(key,enumDescription);
  }

  /**
   * Get all managed enums.
   * @return A list of enum descriptions, sorted by identifier.
   */
  public List<LotroEnumDescription> getAll()
  {
    List<LotroEnumDescription> ret=new ArrayList<LotroEnumDescription>();
    ret.addAll(_directory.values());
    Collections.sort(ret,new IdentifiableComparator<LotroEnumDescription>());
    return ret;
  }
}
