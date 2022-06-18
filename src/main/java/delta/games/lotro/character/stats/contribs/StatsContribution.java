package delta.games.lotro.character.stats.contribs;

import delta.games.lotro.character.gear.GearSlot;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.buffs.Buff;
import delta.games.lotro.character.stats.buffs.BuffInstance;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.sets.ItemsSet;
import delta.games.lotro.utils.FixedDecimalsInteger;

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
  public static final String STAT_SEED="Stat: ";
  // Tome:<statId>
  /**
   * Source identifier seed: stat tome.
   */
  public static final String TOME_SEED="Tome: ";
  // Virtue:<virtueId>
  /**
   * Source identifier seed: virtue.
   */
  public static final String VIRTUE_SEED="Virtue: ";
  // Equipment:<slot>:<itemId>
  /**
   * Source identifier seed: piece of equipment.
   */
  public static final String EQUIPMENT="Equipment: ";
  // ItemsSet:<setId>
  /**
   * Source identifier seed: items set.
   */
  public static final String ITEMS_SET="ItemsSet: ";
  // TraceriesSet:<setId>
  /**
   * Source identifier seed: traceries set.
   */
  public static final String TRACERIES_SET="TraceriesSet: ";
  // Buff:<buffId>
  /**
   * Source identifier seed: buff.
   */
  public static final String BUFF_SEED="Buff: ";
  /**
   * Source identifier seed: hope.
   */
  public static final String HOPE="Hope";
  /**
   * Source identifier seed: additional stats.
   */
  public static final String ADDITIONAL_STATS="Additional: ";
  /**
   * Source identifier for passive stats of virtues.
   */
  public static final String PASSIVE_VIRTUE_SEED="PassiveVirtue: ";

  private StatContributionSource _source;
  private BasicStatsSet _stats;

  /**
   * Constructor.
   * @param sourceId Source identifier.
   * @param label Label.
   * @param stats Contributed stats.
   */
  public StatsContribution(String sourceId, String label, BasicStatsSet stats)
  {
    _source=new StatContributionSource(sourceId,label);
    _stats=stats;
  }

  /**
   * Build a stat contribution for the body of the character.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getBodyContrib(BasicStatsSet stats)
  {
    return new StatsContribution(BODY,"Body",stats);
  }

  /**
   * Build a stat contribution for a source stat.
   * @param sourceStat Source stat.
   * @param factor Factor used to build contribution.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getStatContrib(StatDescription sourceStat, FixedDecimalsInteger factor, BasicStatsSet stats)
  {
    String source=STAT_SEED+sourceStat.getName()+"x"+factor.floatValue();
    return new StatsContribution(source,sourceStat.getName(),stats);
  }

  /**
   * Build a stat contribution for a virtue.
   * @param virtue Source virtue.
   * @param rank Rank.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getVirtueContrib(VirtueDescription virtue, int rank, BasicStatsSet stats)
  {
    String source=VIRTUE_SEED+virtue.getPersistenceKey();
    String label=virtue.getName()+", tier "+rank;
    return new StatsContribution(source,label,stats);
  }

  /**
   * Build a stat contribution for passive virtues.
   * @param virtue Source virtue.
   * @param rank Rank.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getPassiveVirtuesContrib(VirtueDescription virtue, int rank, BasicStatsSet stats)
  {
    String source=PASSIVE_VIRTUE_SEED+virtue.getPersistenceKey();
    String label=virtue.getName()+", tier "+rank;
    return new StatsContribution(source,label,stats);
  }

  /**
   * Build a stat contribution for a stat tome.
   * @param stat Stat of tome.
   * @param rank Rank.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getTomeContrib(StatDescription stat, int rank, BasicStatsSet stats)
  {
    String source=TOME_SEED+stat.getIdentifier();
    String label="Tome of "+stat.getName()+" "+rank;
    return new StatsContribution(source,label,stats);
  }

  /**
   * Build a stat contribution for a buff.
   * @param buffInstance Source buff.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getBuffContrib(BuffInstance buffInstance, BasicStatsSet stats)
  {
    Buff buff=buffInstance.getBuff();
    String source=BUFF_SEED+buff.getId();
    String label="Buff '"+buff.getLabel()+"'";
    return new StatsContribution(source,label,stats);
  }

  /**
   * Build a stat contribution for a buff.
   * @param slot Source slot.
   * @param item Source item.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getGearContrib(GearSlot slot, ItemInstance<? extends Item> item, BasicStatsSet stats)
  {
    String source=EQUIPMENT+slot.name()+":"+item.getIdentifier();
    String label=slot.getLabel()+": "+item.getName();
    return new StatsContribution(source,label,stats);
  }

  /**
   * Build a stat contribution for an items set.
   * @param set Source items set.
   * @param count Number of equipped items for this set.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getItemsSetContrib(ItemsSet set, int count, BasicStatsSet stats)
  {
    String source=ITEMS_SET+set.getIdentifier();
    String label="Items set: "+set.getName()+" ("+count+" pieces)";
    return new StatsContribution(source,label,stats);
  }

  /**
   * Build a stat contribution for a traceries set.
   * @param set Source set.
   * @param count Number of equipped traceries for this set.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getTraceriesSetContrib(ItemsSet set, int count, BasicStatsSet stats)
  {
    String source=TRACERIES_SET+set.getIdentifier();
    String label="Traceries set: "+set.getName()+" ("+count+" pieces)";
    return new StatsContribution(source,label,stats);
  }

  /**
   * Build an 'additional stats' contribution.
   * @param stats Contributed stats.
   * @return A stat contribution.
   */
  public static StatsContribution getAdditionalContrib(BasicStatsSet stats)
  {
    String source=ADDITIONAL_STATS;
    String label="Additional stats";
    return new StatsContribution(source,label,stats);
  }

  /**
   * Get the source.
   * @return the source.
   */
  public StatContributionSource getSource()
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
    return _source+": "+_stats;
  }
}
