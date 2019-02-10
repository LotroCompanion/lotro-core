package delta.games.lotro.lore.items.legendary.non_imbued;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.Effect;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.lore.items.legendary.LegacyType;

/**
 * Non-imbued legacy.
 * @author DAM
 */
public class NonImbuedLegacy
{
  private StatDescription _stat;
  private boolean _major;
  private List<NonImbuedLegacyTier> _tiers;
  private LegacyType _type;

  /**
   * Constructor.
   * @param stat Associated stat.
   */
  public NonImbuedLegacy(StatDescription stat)
  {
    _stat=stat;
    _major=false;
    _tiers=new ArrayList<NonImbuedLegacyTier>();
    _type=LegacyType.STAT;
  }

  /**
   * Get the associated stat.
   * @return a stat or <code>null</code> if not set.
   */
  public StatDescription getStat()
  {
    return _stat;
  }

  /**
   * Indicates if this is a major legacy or not.
   * @return <code>true</code> if it is major, <code>false</code> otherwise.
   */
  public boolean isMajor()
  {
    return _major;
  }

  /**
   * Set the value of the 'major' flag.
   * @param major Value to set.
   */
  public void setMajor(boolean major)
  {
    _major=major;
  }

  /**
   * Add a legacy tier.
   * @param tier Tier to add.
   * @param effect Associated effect.
   * @return the new legacy tier.
   */
  public NonImbuedLegacyTier addTier(int tier, Effect effect)
  {
    NonImbuedLegacyTier legacyTier=new NonImbuedLegacyTier(this,tier,effect);
    _tiers.add(legacyTier);
    return legacyTier;
  }

  /**
   * Get a legacy tier.
   * @param tier Tier to get.
   * @return A legacy tier or <code>null</code> if not found.
   */
  public NonImbuedLegacyTier getTier(int tier)
  {
    for(NonImbuedLegacyTier legacyTier : _tiers)
    {
      if (legacyTier.getTier()==tier)
      {
        return legacyTier;
      }
    }
    return null;
  }

  /**
   * Get the legacy type.
   * @return A legacy type.
   */
  public LegacyType getLegacyType()
  {
    return _type;
  }

  /**
   * Set the legacy type.
   * @param legacyType Type to set.
   */
  public void setLegacyType(LegacyType legacyType)
  {
    _type=legacyType;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Non imbued: ").append(_stat.getName());
    if (_type!=null)
    {
      sb.append(" (").append(_type).append(')');
    }
    if (_major)
    {
      sb.append(" (major)");
    }
    sb.append(EndOfLine.NATIVE_EOL);
    for(NonImbuedLegacyTier tier : _tiers)
    {
      sb.append('\t').append(tier).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString();
  }
}
