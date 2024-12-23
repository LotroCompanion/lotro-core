package delta.games.lotro.character.skills.combos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Combos of a skill.
 * @author DAM
 */
public class SkillCombos
{
  private int _comboPropertyID;
  private List<SkillComboElement> _elements;

  /**
   * Constructor.
   * @param comboPropertyID Combo property identifier.
   */
  public SkillCombos(int comboPropertyID)
  {
    _comboPropertyID=comboPropertyID;
    _elements=new ArrayList<SkillComboElement>();
  }

  /**
   * Get the ID of the combo property.
   * @return A property identifier.
   */
  public int getComboPropertyID()
  {
    return _comboPropertyID;
  }

  /**
   * Add a combo element.
   * @param element Element to add.
   */
  public void addElement(SkillComboElement element)
  {
    _elements.add(element);
  }

  /**
   * Get the managed combo elements, sorted by property value.
   * @return A list of combo elements.
   */
  public List<SkillComboElement> getElements()
  {
    List<SkillComboElement> ret=new ArrayList<SkillComboElement>(_elements);
    Comparator<SkillComboElement> c=new Comparator<SkillComboElement>()
    {
      public int compare(SkillComboElement e1, SkillComboElement e2)
      {
        return Integer.compare(e1.getValue(),e2.getValue());
      }
    };
    Collections.sort(ret,c);
    return ret;
  }
}
