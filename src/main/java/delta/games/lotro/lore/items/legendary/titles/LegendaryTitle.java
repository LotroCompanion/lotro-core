package delta.games.lotro.lore.items.legendary.titles;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.enums.Genus;
import delta.games.lotro.common.enums.LegendaryTitleCategory;
import delta.games.lotro.common.enums.LegendaryTitleTier;
import delta.games.lotro.lore.items.DamageType;

/**
 * Legendary title description.
 * @author DAM
 */
public class LegendaryTitle implements Identifiable
{
  private int _id;
  private String _name;
  private LegendaryTitleCategory _category;
  private LegendaryTitleTier _tier;
  private DamageType _damageType;
  private Genus _slayerGenusType;
  private BasicStatsSet _stats;

  /**
   * Constructor.
   */
  public LegendaryTitle()
  {
    _stats=new BasicStatsSet();
  }

  @Override
  public int getIdentifier()
  {
    return _id;
  }

  /**
   * Set the identifier of this title.
   * @param id the identifier to set.
   */
  public void setIdentifier(int id)
  {
    _id=id;
  }

  /**
   * Get the name of this title.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this title.
   * @param name the name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the category of this title.
   * @return a category.
   */
  public LegendaryTitleCategory getCategory()
  {
    return _category;
  }

  /**
   * Set the category of this title.
   * @param category the category to set.
   */
  public void setCategory(LegendaryTitleCategory category)
  {
    _category=category;
  }

  /**
   * Get the tier of this title.
   * @return a tier.
   */
  public LegendaryTitleTier getTier()
  {
    return _tier;
  }

  /**
   * Set the tier of this title.
   * @param tier the tier to set.
   */
  public void setTier(LegendaryTitleTier tier)
  {
    _tier=tier;
  }

  /**
   * Get the damage type.
   * @return the damage type.
   */
  public DamageType getDamageType()
  {
    return _damageType;
  }

  /**
   * Set the damage type.
   * @param damageType the damage type to set.
   */
  public void setDamageType(DamageType damageType)
  {
    _damageType=damageType;
  }

  /**
   * Get the slayer genus of this title.
   * @return a slayer genus or <code>null</code>.
   */
  public Genus getSlayerGenusType()
  {
    return _slayerGenusType;
  }

  /**
   * Set the slayer genus of this title.
   * @param slayerGenusType the slayer genus to set (may be <code>null</code>.
   */
  public void setSlayerGenusType(Genus slayerGenusType)
  {
    _slayerGenusType=slayerGenusType;
  }

  /**
   * Get the stats for this title.
   * @return a set of stats.
   */
  public BasicStatsSet getStats()
  {
    return _stats;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("Legendary title: ");
    sb.append("ID=").append(_id);
    sb.append(", name=").append(_name);
    sb.append(", category=").append(_category);
    sb.append(", tier=").append(_tier);
    sb.append(", damage type=").append(_damageType);
    sb.append(", slayer genus=").append(_slayerGenusType);
    sb.append(", stats=").append(_stats);
    return sb.toString();
  }
}
