package delta.games.lotro.lore.items.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.character.stats.Slice;
import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.ItemQuality;
import delta.games.lotro.lore.items.stats.ItemStatSliceData.SliceComparator;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * COomputes item stats from slice-based formulas.
 * @author DAM
 */
public class SlicesBasedItemStatsProvider implements ItemStatsProvider
{
  private static final Logger LOGGER=Logger.getLogger(SlicesBasedItemStatsProvider.class);

  private static ScaledArmourComputer _armorComputer=new ScaledArmourComputer();
  private List<ItemStatSliceData> _slices;
  private BasicStatsSet _stats;

  /**
   * Constructor.
   */
  public SlicesBasedItemStatsProvider()
  {
    _slices=new ArrayList<ItemStatSliceData>();
    _stats=new BasicStatsSet();
  }

  /**
   * Add a new slice.
   * @param slice Slice parameters.
   */
  public void addSlice(ItemStatSliceData slice)
  {
    _slices.add(slice);
  }

  /**
   * Set a stat value.
   * @param stat Targeted stat.
   * @param value Stat value.
   */
  public void setStat(STAT stat, FixedDecimalsInteger value)
  {
    _stats.setStat(stat,value);
  }

  /**
   * Get the number of slices.
   * @return a count.
   */
  public int getSlices()
  {
    return _slices.size();
  }

  /**
   * Get the number of row stats.
   * @return a count.
   */
  public int getStatsCount()
  {
    return _stats.getStatsCount();
  }

  public BasicStatsSet getStats(int itemLevel)
  {
    BasicStatsSet stats=new BasicStatsSet();
    for(ItemStatSliceData slice : _slices)
    {
      STAT stat=slice.getStat();
      float statValue=getStatValue(slice,itemLevel);
      stats.setStat(stat,statValue);
    }
    stats.setStats(_stats);
    return stats;
  }

  /**
   * Get the value of a stat.
   * @param slice Slice to use.
   * @param itemLevel Item level.
   * @return A stat value.
   */
  public static float getStatValue(ItemStatSliceData slice, int itemLevel)
  {
    double value=getSliceValue(itemLevel, slice);
    // For the moment, round value to keep computed values the same as old,
    // non computed values
    float statValue=Math.round(value);
    return statValue;
  }

  private static double getSliceValue(int itemLevel, ItemStatSliceData slice)
  {
    STAT stat=slice.getStat();
    Float sliceCountFloat=slice.getSliceCount();
    float sliceCount=(sliceCountFloat!=null)?sliceCountFloat.floatValue():1;
    if ((stat==STAT.MIGHT) || (stat==STAT.AGILITY) || (stat==STAT.WILL) ||
        (stat==STAT.VITALITY) || (stat==STAT.FATE))
    {
      return Slice.getBaseStat(itemLevel,sliceCount);
    }
    if (stat==STAT.MORALE)
    {
      return Slice.getMorale(itemLevel,sliceCount);
    }
    if (stat==STAT.POWER)
    {
      return Slice.getPower(itemLevel,sliceCount);
    }
    if (stat==STAT.PHYSICAL_MASTERY)
    {
      return Slice.getPhysicalMastery(itemLevel,sliceCount);
    }
    if (stat==STAT.TACTICAL_MASTERY)
    {
      return Slice.getTacticalMastery(itemLevel,sliceCount);
    }
    if (stat==STAT.CRITICAL_RATING)
    {
      return Slice.getCriticalRating(itemLevel,sliceCount);
    }
    if (stat==STAT.FINESSE)
    {
      return Slice.getFinesse(itemLevel,sliceCount);
    }
    if (stat==STAT.INCOMING_HEALING)
    {
      return Slice.getIncomingHealing(itemLevel,sliceCount);
    }
    if (stat==STAT.RESISTANCE)
    {
      return Slice.getResist(itemLevel,sliceCount);
    }
    if ((stat==STAT.BLOCK) || (stat==STAT.PARRY) || (stat==STAT.EVADE))
    {
      return Slice.getBPE(itemLevel,sliceCount);
    }
    if (stat==STAT.PHYSICAL_MITIGATION)
    {
      return Slice.getPhysicalMitigation(itemLevel,sliceCount);
    }
    if (stat==STAT.TACTICAL_MITIGATION)
    {
      return Slice.getTacticalMitigation(itemLevel,sliceCount);
    }
    if (stat==STAT.CRITICAL_DEFENCE)
    {
      return Slice.getCriticalDefence(itemLevel,sliceCount);
    }
    if (stat==STAT.ARMOUR)
    {
      String armType=slice.getAdditionalParameter();
      return getArmorStat(armType.toUpperCase(),itemLevel,sliceCount);
    }
    return 0;
  }

  private static double getArmorStat(String armType, int itemLevel, float sliceCount)
  {
    String armorClass=armType.substring(3,4);
    String armorType=armType.substring(4,5);
    String armorColor=armType.substring(5,6);
    if (("S".equals(armorType)) && ("H".equals(armorColor)))
    {
      armorType="SH";
      armorColor=armType.substring(6,7);
    }
    if (("C".equals(armorClass)) && ("L".equals(armorType)))
    {
      armorClass="L";
      armorType="CL";
      armorColor=armType.substring(5,6);
    }
    EquipmentLocation slot=null;
    if ("H".equals(armorType)) slot=EquipmentLocation.HEAD;
    if ("S".equals(armorType)) slot=EquipmentLocation.SHOULDER;
    if ("C".equals(armorType)) slot=EquipmentLocation.CHEST;
    if ("G".equals(armorType)) slot=EquipmentLocation.HAND;
    if ("L".equals(armorType)) slot=EquipmentLocation.LEGS;
    if ("B".equals(armorType)) slot=EquipmentLocation.FEET;
    if ("SH".equals(armorType)) slot=EquipmentLocation.OFF_HAND;
    if ("CL".equals(armorType)) slot=EquipmentLocation.BACK;

    ArmourType type=null;
    if ("H".equals(armorClass)) type=ArmourType.HEAVY;
    if ("M".equals(armorClass)) type=ArmourType.MEDIUM;
    if ("L".equals(armorClass)) type=ArmourType.LIGHT;

    ItemQuality quality=null;
    if ("G".equals(armorColor)) quality=ItemQuality.LEGENDARY;
    if ("P".equals(armorColor)) quality=ItemQuality.RARE;
    if ("T".equals(armorColor)) quality=ItemQuality.INCOMPARABLE;
    if ("Y".equals(armorColor)) quality=ItemQuality.UNCOMMON;

    if ((slot!=null) && (type!=null) && (quality!=null))
    {
      return _armorComputer.getArmour(itemLevel,type,slot,quality,sliceCount);
    }
    LOGGER.warn("Unmanaged armor type:" + armType);
    return 0;
  }

  /**
   * Build the armour description from location/quality and armour type.
   * @param location Item location.
   * @param quality Item quality.
   * @param type Armour type.
   * @return An armour description.
   */
  public static String getArmorLabel(EquipmentLocation location, ItemQuality quality, ArmourType type)
  {
    String locStr=null;
    if (location==EquipmentLocation.HEAD) locStr="H";
    else if (location==EquipmentLocation.SHOULDER) locStr="S";
    else if (location==EquipmentLocation.CHEST) locStr="C";
    else if (location==EquipmentLocation.HAND) locStr="G";
    else if (location==EquipmentLocation.LEGS) locStr="L";
    else if (location==EquipmentLocation.FEET) locStr="B";
    else if (location==EquipmentLocation.OFF_HAND) locStr="Sh";
    else if (location==EquipmentLocation.BACK) locStr="CL";

    String typeStr=null;
    if (type==ArmourType.HEAVY) typeStr="H";
    else if (type==ArmourType.MEDIUM) typeStr="M";
    else if (type==ArmourType.LIGHT) typeStr="L";
    else if (type==ArmourType.HEAVY_SHIELD) { typeStr="H"; locStr="SH"; }
    else if (type==ArmourType.WARDEN_SHIELD) { typeStr="M"; locStr="SH"; }
    else if (type==ArmourType.SHIELD) { typeStr="L"; locStr="SH"; }
    if (location==EquipmentLocation.BACK) typeStr="";

    String qualityStr=null;
    if (quality==ItemQuality.LEGENDARY) qualityStr="G";
    else if (quality==ItemQuality.RARE) qualityStr="P";
    else if (quality==ItemQuality.INCOMPARABLE) qualityStr="T";
    else if (quality==ItemQuality.UNCOMMON) qualityStr="Y";
    return "Arm"+typeStr+locStr+qualityStr;
  }

  /**
   * Build a new instance from a persisted string.
   * @param value Value to use.
   * @return the newly built instance or <code>null</code> if not valid.
   */
  public static SlicesBasedItemStatsProvider fromPersistedString(String value)
  {
    SlicesBasedItemStatsProvider provider=new SlicesBasedItemStatsProvider();
    String[] parts=value.split(";");
    if (parts!=null)
    {
      for(String part : parts)
      {
        boolean isSlice=(part.indexOf(':')!=-1);
        if (isSlice)
        {
          ItemStatSliceData data=parseSliceData(part);
          if (data!=null)
          {
            provider.addSlice(data);
          }
        }
        boolean isStat=(part.indexOf('=')!=-1);
        if (isStat)
        {
          provider.parseStatData(part);
        }
      }
    }
    if ((provider.getSlices()==0) && (provider.getStatsCount()==0))
    {
      provider=null;
    }
    return provider;
  }

  private void parseStatData(String params)
  {
    int index=params.indexOf('=');
    if (index!=-1)
    {
      String internalValueStr=params.substring(index+1);
      String statStr=params.substring(0,index);
      STAT stat=STAT.getByName(statStr);
      FixedDecimalsInteger value=FixedDecimalsInteger.fromString(internalValueStr);
      if ((stat!=null) && (value!=null))
      {
        _stats.addStat(stat,value);
      }
    }
  }

  private static ItemStatSliceData parseSliceData(String params)
  {
    ItemStatSliceData ret=null;
    int index=params.indexOf(':');
    if (index!=-1)
    {
      String sliceCountStr=params.substring(index+1);
      String statStr=params.substring(0,index);
      String additionalParameter=null;
      int indexStartParenthesis=statStr.indexOf('(');
      if (indexStartParenthesis!=-1)
      {
        int indexEndParenthesis=statStr.indexOf(')',indexStartParenthesis+1);
        if (indexEndParenthesis!=-1)
        {
          additionalParameter=statStr.substring(indexStartParenthesis+1,indexEndParenthesis);
          statStr=statStr.substring(0,indexStartParenthesis);
        }
      }
      STAT stat=STAT.getByName(statStr);
      Float sliceCount=NumericTools.parseFloat(sliceCountStr);
      if ((stat!=null) && (sliceCount!=null))
      {
        ret=new ItemStatSliceData(stat,sliceCount,additionalParameter);
      }
    }
    return ret;
  }

  /**
   * Get a persistable string for this object.
   * @return A persistable stirng.
   */
  public String toPersistableString()
  {
    String ret="";
    int nbSlices=_slices.size();
    int nbStats=_stats.getStatsCount();
    if ((nbSlices>0) || (nbStats>0))
    {
      StringBuilder sb=new StringBuilder();
      if (nbSlices>0)
      {
        Collections.sort(_slices,new SliceComparator());
        for(ItemStatSliceData slice :_slices)
        {
          if (sb.length()>0)
          {
            sb.append(';');
          }
          slice.toString(sb);
        }
      }
      if (nbStats>0)
      {
        List<STAT> stats=new ArrayList<STAT>(_stats.getStats());
        Collections.sort(stats);
        for(STAT stat : stats)
        {
          if (sb.length()>0)
          {
            sb.append(';');
          }
          FixedDecimalsInteger value=_stats.getStat(stat);
          sb.append(stat.name());
          sb.append('=');
          sb.append(value.getInternalValue());
        }
      }
      ret=sb.toString();
    }
    return ret;
  }
}

