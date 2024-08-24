package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.enums.ResistCategory;

/**
 * "Dispel by Resist" effect.
 * @author DAM
 */
public class DispelByResistEffect extends InstantEffect
{
  private int _maxDispelCount;
  private List<ResistCategory> _resistCategories;
  private boolean _useStrengthRestriction;
  private Integer _strengthOffset;

  /**
   * Constructor.
   */
  public DispelByResistEffect()
  {
    super();
    _maxDispelCount=-1;
    _resistCategories=new ArrayList<ResistCategory>();
    _useStrengthRestriction=false;
    _strengthOffset=null;
  }

  /**
   * Get the maximum dispel count.
   * @return A strictly positive value, or -1 for all.
   */
  public int getMaxDispelCount()
  {
    return _maxDispelCount;
  }

  /**
   * Set the maximum dispel count.
   * @param maxDispelCount A count (see the getter for value meaning).
   */
  public void setMaxDispelCount(int maxDispelCount)
  {
    _maxDispelCount=maxDispelCount;
  }

  /**
   * Add a resist category.
   * @param category Category to add.
   */
  public void addResistCategory(ResistCategory category)
  {
    _resistCategories.add(category);
  }

  /**
   * Get the resist categories.
   * @return A list of categories.
   */
  public List<ResistCategory> getResistCategories()
  {
    return _resistCategories;
  }

  /**
   * Indicates if the effect uses a strength restriction or not.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean useStrengthRestriction()
  {
    return _useStrengthRestriction;
  }

  /**
   * Set the 'use strength restriction' flag.
   * @param useStrengthRestriction Value to set.
   */
  public void setUseStrengthRestriction(boolean useStrengthRestriction)
  {
    _useStrengthRestriction=useStrengthRestriction;
  }

  /**
   * Get the strength offset, if any.
   * @return A strength offset, or <code>null</code> to use the default one.
   */
  public Integer getStrengthOffset()
  {
    return _strengthOffset;
  }

  /**
   * Set the strength offset.
   * @param strengthOffset Offset to set (may be <code>null</code>).
   */
  public void setStrengthOffset(Integer strengthOffset)
  {
    _strengthOffset=strengthOffset;
  }
}
