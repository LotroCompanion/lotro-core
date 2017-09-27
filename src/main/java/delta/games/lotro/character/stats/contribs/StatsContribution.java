package delta.games.lotro.character.stats.contribs;

import delta.games.lotro.character.CharacterEquipment.EQUIMENT_SLOT;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.common.VirtueId;

/**
 * Gathers all contributions of a single source.
 * @author DAM
 */
public class StatsContribution
{
  /**
   * Source identifier: body.
   */
  public static final String BODY="Body";
  // Stat:<statId>
  /**
   * Source identifier seed: stat.
   */
  public static final String STAT_SEED="Stat:";
  // Tome:<statId>
  /**
   * Source identifier seed: stat tome.
   */
  public static final String TOME_SEED="Tome:";
  // Virtue:<virtueId>
  /**
   * Source identifier seed: virtue.
   */
  public static final String VIRTUE_SEED="Virtue:";
  // Equipment:<slot>:<itemId>
  /**
   * Source identifier seed: piece of equipment.
   */
  public static final String EQUIPMENT="Equipment:";
  // Buff:<buffId>
  /**
   * Source identifier seed: buff.
   */
  public static final String BUFF_SEED="Buff:";
  /**
   * Source identifier seed: hope.
   */
  public static final String HOPE="Hope";

  private String _source;
  private BasicStatsSet _stats;

  /**
   * Constructor.
   * @param id Source identifier.
   * @param stats Contributed stats.
   */
  public StatsContribution(String id, BasicStatsSet stats)
  {
    _source=id;
    _stats=stats;
  }

  /**
   * Build a stat contribution for the body of the character.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getBodyContrib(BasicStatsSet stats)
  {
    return new StatsContribution(BODY,stats);
  }

  /**
   * Build a stat contribution for a source stat.
   * @param stat Source stat.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getStatContrib(STAT stat, BasicStatsSet stats)
  {
    String source=STAT_SEED+stat.name();
    return new StatsContribution(source,stats);
  }

  /**
   * Build a stat contribution for a virtue.
   * @param virtueId Source virtue.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getVirtueContrib(VirtueId virtueId, BasicStatsSet stats)
  {
    String source=VIRTUE_SEED+virtueId.name();
    return new StatsContribution(source,stats);
  }

  /**
   * Build a stat contribution for a stat tome.
   * @param stat Stat of tome.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getTomeContrib(STAT stat, BasicStatsSet stats)
  {
    String source=TOME_SEED+stat.name();
    return new StatsContribution(source,stats);
  }

  /**
   * Build a stat contribution for a buff.
   * @param buffId Source buff.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getBuffContrib(String buffId, BasicStatsSet stats)
  {
    String source=BUFF_SEED+buffId;
    return new StatsContribution(source,stats);
  }

  /**
   * Build a stat contribution for a buff.
   * @param slot Source slot.
   * @param itemId Identifier of the source item.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getGearContrib(EQUIMENT_SLOT slot, int itemId, BasicStatsSet stats)
  {
    String source=EQUIPMENT+slot.name()+":"+itemId;
    return new StatsContribution(source,stats);
  }

  /**
   * Get the source identifier.
   * @return the source identifier.
   */
  public String getSource()
  {
    return _source;
  }

  /**
   * Get the contributed stats.
   * @return the contributed stats.
   */
  public BasicStatsSet getStats()
  {
    return _stats;
  }

  @Override
  public String toString()
  {
    return _source+":"+_stats;
  }
}
