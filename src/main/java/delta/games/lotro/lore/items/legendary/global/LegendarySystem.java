package delta.games.lotro.lore.items.legendary.global;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.common.stats.ScalableStatProvider;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.ItemQuality;
import delta.games.lotro.lore.items.legendary.Legendary;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;
import delta.games.lotro.lore.items.legendary.global.io.xml.LegendaryDataXMLParser;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegaciesManager;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegacyTier;
import delta.games.lotro.utils.maths.ArrayProgression;
import delta.games.lotro.utils.maths.Progression;

/**
 * Provides facilities related to the legendary items system.
 * @author DAM
 */
public class LegendarySystem
{
  private static final Logger LOGGER=Logger.getLogger(LegendarySystem.class);

  private LegendaryData _data;

  /**
   * Constructor.
   */
  public LegendarySystem()
  {
    File inputFile=LotroCoreConfig.getInstance().getFile(DataFiles.LEGENDARY_DATA);
    _data=LegendaryDataXMLParser.parseLegendaryDataFile(inputFile);
  }

  /**
   * Get the possible internal ranks for the main legacy of an item instance.
   * @param itemInstance Item instance.
   * @return An array of rank values (usually 7) or <code>null</code> if computation fails.
   */
  public int[] getRanksForMainLegacy(ItemInstance<? extends Item> itemInstance)
  {
    if (itemInstance==null)
    {
      return null;
    }
    Item item=itemInstance.getReference();
    if (!(item instanceof Legendary))
    {
      return null;
    }
    Integer itemLevelInt=itemInstance.getEffectiveItemLevel();
    int itemLevel=(itemLevelInt!=null)?itemLevelInt.intValue():1;
    return getRanksForMainLegacy(item,itemLevel);
  }

  /**
   * Get the possible internal ranks for the main legacy of an item instance.
   * @param item Reference item.
   * @param itemLevel Effective item level of the instance.
   * @return An array of rank values (usually 7) or <code>null</code> if computation fails.
   */
  public int[] getRanksForMainLegacy(Item item, int itemLevel)
  {
    Legendary legendary=(Legendary)item;
    LegendaryAttrs legendaryAttrs=legendary.getLegendaryAttrs();
    int baseRank=legendaryAttrs.getMainLegacyBaseRank();

    // Adjust with item level if needed
    Integer refItemLevel=item.getItemLevel();
    if (refItemLevel!=null)
    {
      int itemLevelDelta=itemLevel-refItemLevel.intValue();
      baseRank+=itemLevelDelta;
    }
    // TODO Adjust with item upgrades if needed
    int mainLegacyId=legendaryAttrs.getMainLegacyId();
    NonImbuedLegaciesManager legaciesMgr=NonImbuedLegaciesManager.getInstance();
    DefaultNonImbuedLegacy legacy=legaciesMgr.getDefaultLegacy(mainLegacyId);
    if (legacy==null)
    {
      return null;
    }
    StatProvider provider=legacy.getEffect().getStatsProvider().getStatProvider(0);
    int maxRanks=7; // TODO Fetch constant from DAT file
    return getRanks(baseRank,provider,maxRanks);
  }

  /**
   * Get the internal rank for a UI rank.
   * @param itemInstance Item instance.
   * @param legacy Legacy.
   * @param uiRank UI rank (starting at 1).
   * @return An internal rank or <code>null</code> if computation fails.
   */
  public Integer getRankForUiRank(ItemInstance<? extends Item> itemInstance, NonImbuedLegacyTier legacy, int uiRank)
  {
    Integer ret=null;
    int[] ranks=getRanksForLegacyTier(itemInstance,legacy);
    if ((uiRank>0) && (ranks!=null) && (uiRank<=ranks.length))
    {
      ret=Integer.valueOf(ranks[uiRank-1]);
    }
    return ret;
  }

  /**
   * Get the possible internal ranks for a legacy on an item instance.
   * @param itemInstance Item instance.
   * @param legacy Legacy.
   * @return An array of rank values (usually 9) or <code>null</code> if computation fails.
   */
  public int[] getRanksForLegacyTier(ItemInstance<? extends Item> itemInstance, NonImbuedLegacyTier legacy)
  {
    if (itemInstance==null)
    {
      return null;
    }
    Integer itemLevel=itemInstance.getEffectiveItemLevel();
    if (itemLevel==null)
    {
      return null;
    }
    // TODO Use item upgrades to tune item level
    Item item=itemInstance.getReference();
    ItemQuality quality=item.getQuality();
    return getRanksForLegacyTier(itemLevel.intValue(),quality,legacy);
  }

  /**
   * Get the possible internal ranks for a legacy on an item instance.
   * @param itemLevel Effective item level of the item instance.
   * @param quality Item quality.
   * @param legacy Legacy.
   * @return An array of rank values (usually 9) or <code>null</code> if computation fails.
   */
  public int[] getRanksForLegacyTier(int itemLevel, ItemQuality quality, NonImbuedLegacyTier legacy)
  {
    Integer startRank=getStartRank(itemLevel,quality,legacy);
    if (startRank==null)
    {
      return null;
    }
    StatProvider provider=legacy.getEffect().getStatsProvider().getStatProvider(0);
    int maxRanks=_data.getMaxUiRank();
    return getRanks(startRank.intValue(),provider,maxRanks);
  }

  private Integer getStartRank(int itemLevel, ItemQuality quality, NonImbuedLegacyTier legacy)
  {
    Integer rank=legacy.getStartRank();
    if (rank==null)
    {
      QualityBasedData qualityData=_data.getQualityData(quality,false);
      if (qualityData!=null)
      {
        rank=qualityData.getStartLevel(itemLevel);
      }
    }
    return rank;
  }

  private int[] getRanks(int baseRank, StatProvider provider, int maxRanks)
  {
    ArrayProgression progression=getProgression(provider);
    if (progression==null)
    {
      return null;
    }
    List<Integer> ranks=getValuesFromRank(baseRank,progression,maxRanks);
    if (ranks==null)
    {
      return null;
    }
    int[] ret=new int[ranks.size()];
    for(int i=0;i<ranks.size();i++)
    {
      ret[i]=ranks.get(i).intValue();
    }
    return ret;
  }

  private List<Integer> getValuesFromRank(int startRank, ArrayProgression progression, int maxRanks)
  {
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Values for progression: "+progression.getIdentifier()+": "+progression);
    }
    List<Float> values=new ArrayList<Float>();
    List<Integer> ranks=new ArrayList<Integer>();
    int nbPoints=progression.getNumberOfPoints();
    int maxX=progression.getX(nbPoints-1);
    float lastValue=Float.MIN_VALUE;
    int x=startRank;
    while(true)
    {
      if (x>maxX) break;
      Float value=progression.getValue(x);
      if (value==null) break;
      if (value.floatValue()!=lastValue)
      {
        values.add(value);
        ranks.add(Integer.valueOf(x));
        lastValue=value.floatValue();
        if (ranks.size()==maxRanks) break;
      }
      x++;
    }
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Ranks: "+ranks);
      LOGGER.debug("Values: "+values);
    }
    return ranks;
  }

  private ArrayProgression getProgression(StatProvider provider)
  {
    if (provider instanceof ScalableStatProvider)
    {
      ScalableStatProvider scalableStatProvider=(ScalableStatProvider)provider;
      Progression progression=scalableStatProvider.getProgression();
      if (progression instanceof ArrayProgression)
      {
        return (ArrayProgression)progression;
      }
      LOGGER.warn("Not an array progression!");
    }
    else
    {
      LOGGER.warn("Not a scalable provider!");
    }
    return null;
  }
}
