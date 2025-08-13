package delta.games.lotro.character.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Monster class description.
 * @author DAM
 */
public class MonsterClassDescription extends AbstractClassDescription
{
  private List<ClassVirtue> _virtues;

  /**
   * Constructor.
   * @param id Identifier.
   * @param code Internal LOTRO code.
   * @param key Key Internal LC string identifier.
   */
  public MonsterClassDescription(int id, int code, String key)
  {
    super(id,code,key);
    _virtues=new ArrayList<ClassVirtue>();
  }

  /**
   * Add a class virtue.
   * @param virtue Virtue to add.
   */
  public void addVirtue(ClassVirtue virtue)
  {
    _virtues.add(virtue);
  }

  /**
   * Get all the virtues of this class.
   * @return A list of class virtues.
   */
  public List<ClassVirtue> getVirtues()
  {
    return _virtues;
  }
}
