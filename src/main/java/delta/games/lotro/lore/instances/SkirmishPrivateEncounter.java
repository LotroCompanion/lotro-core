package delta.games.lotro.lore.instances;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;

/**
 * Private encounter of type 'Skirmish/Big battle/Classic instance'.
 * @author DAM
 */
public class SkirmishPrivateEncounter extends PrivateEncounter
{
  // Rewards configuration
  // Difficulty tiers
  private List<String> _difficultyTiers;
  // Group sizes
  private List<String> _groupSizes;
  // Min/max level scale
  private int _minLevelScale;
  private int _maxLevelScale;
  // Category (Defensive...), may be null
  private String _category;
  // Type (Skirmish...), may be null
  private String _type;
  // Skirmish_RestrictionDescription: #1: You must have completed the 'Helm's Dike' battle.
  private Integer _levelScaling;

  /**
   * Constructor.
   * @param id Identifier.
   */
  public SkirmishPrivateEncounter(int id)
  {
    super(id);
    _difficultyTiers=new ArrayList<String>();
    _groupSizes=new ArrayList<String>();
    _minLevelScale=0;
    _maxLevelScale=0;
    _category=null;
    _levelScaling=null;
  }

  /**
   * Get the difficulty tiers.
   * @return a list of difficulty tiers.
   */
  public List<String> getDifficultyTiers()
  {
    return _difficultyTiers;
  }

  /**
   * Add a difficulty tier.
   * @param difficultyTier Difficulty tier to add.
   */
  public void addDifficultyTier(String difficultyTier)
  {
    _difficultyTiers.add(difficultyTier);
  }

  /**
   * Get the group sizes.
   * @return a list of group sizes.
   */
  public List<String> getGroupSizes()
  {
    return _groupSizes;
  }

  /**
   * Add a group size.
   * @param groupSize Group size to ad.
   */
  public void addGroupSize(String groupSize)
  {
    _groupSizes.add(groupSize);
  }

  /**
   * Get the minimum level for this instance.
   * @return a level.
   */
  public int getMinLevelScale()
  {
    return _minLevelScale;
  }

  /**
   * Get the maxium level for this instance.
   * @return a level.
   */
  public int getMaxLevelScale()
  {
    return _maxLevelScale;
  }

  /**
   * Set the minimum and maximum level.
   * @param minLevelScale Minimum level.
   * @param maxLevelScale Maximum level.
   */
  public void setLevelScale(int minLevelScale, int maxLevelScale)
  {
    _minLevelScale=minLevelScale;
    _maxLevelScale=maxLevelScale;
  }

  /**
   * Indicates if this instance is scalable or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isScalable()
  {
    return _minLevelScale!=_maxLevelScale;
  }

  /**
   * Get the category for this instance.
   * @return a category or <code>null</code>.
   */
  public String getCategory()
  {
    return _category;
  }

  /**
   * Set the category for this instance.
   * @param category Category to set.
   */
  public void setCategory(String category)
  {
    _category=category; 
  }

  /**
   * Get the instance type (Classic, Skirmish...).
   * @return a type.
   */
  public String getType()
  {
    return _type;
  }

  /**
   * Set the instance type.
   * @param type Instance type to set.
   */
  public void setType(String type)
  {
    _type=type; 
  }

  /**
   * Get the character level scaling for this instance.
   * @return A level or <code>null</code>.
   */
  public Integer getLevelScaling()
  {
    return _levelScaling;
  }

  /**
   * Set the character level scaling.
   * @param levelScaling Level to set (may be <code>null</code>).
   */
  public void setLevelScaling(Integer levelScaling)
  {
    _levelScaling=levelScaling;
  }

  protected void dump(StringBuilder sb)
  {
    super.dump(sb);
    sb.append("Difficulty tiers=").append(_difficultyTiers).append(EndOfLine.NATIVE_EOL);
    sb.append("Group sizes=").append(_groupSizes).append(EndOfLine.NATIVE_EOL);
    sb.append("Level scale: min=").append(_minLevelScale).append(", max=").append(_maxLevelScale).append(EndOfLine.NATIVE_EOL);
    sb.append("Category=").append(_category).append(EndOfLine.NATIVE_EOL);
    sb.append("Type=").append(_type).append(EndOfLine.NATIVE_EOL);
    if (_levelScaling!=null)
    {
      sb.append("Level scaling=").append(_levelScaling).append(EndOfLine.NATIVE_EOL);
    }
  }
}
