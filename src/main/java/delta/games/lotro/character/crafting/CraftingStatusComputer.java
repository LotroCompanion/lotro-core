package delta.games.lotro.character.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.NumericTools;
import delta.common.utils.text.TextTools;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.log.CharacterLog;
import delta.games.lotro.character.log.CharacterLogItem;
import delta.games.lotro.character.log.CharacterLogItem.LogItemType;
import delta.games.lotro.crafting.Profession;
import delta.games.lotro.crafting.Vocation;
import delta.games.lotro.crafting.Vocations;

/**
 * Computes a crafting status using the character log.
 * @author DAM
 */
public class CraftingStatusComputer
{
  private static final Logger _logger=Logger.getLogger(CraftingStatusComputer.class);

  private CraftingStatus _status;

  /**
   * Build the crafting status for a toon.
   * @param toon Toon to use.
   * @return A crafting status.
   */
  public CraftingStatus buildCraftingStatus(CharacterFile toon)
  {
    String name=toon.getName();
    _status=new CraftingStatus(name);
    CharacterLog log=toon.getLastCharacterLog();
    if (log!=null)
    {
      loadLog(log);
    }
    return _status;
  }

  /**
   * Load data from a character log.
   * @param log Character log to use.
   */
  private void loadLog(CharacterLog log)
  {
    List<CharacterLogItem> items=getCraftingItems(log);
    parseItems(items);
    if (log!=null)
    {
      int nbItems=log.getNbItems();
      if (nbItems>0)
      {
        CharacterLogItem lastItem=log.getLogItem(0);
        long date=lastItem.getDate();
        for(ProfessionStatus stat : _status.getProfessionStatus())
        {
          stat.setLastLogItemDate(date);
        }
      }
    }
  }

  private List<CharacterLogItem> getCraftingItems(CharacterLog log)
  {
    List<CharacterLogItem> ret=new ArrayList<CharacterLogItem>();
    if (log!=null)
    {
      int nb=log.getNbItems();
      for(int i=0;i<nb;i++)
      {
        CharacterLogItem item=log.getLogItem(i);
        if (item!=null)
        {
          LogItemType type=item.getLogItemType();
          if ((type==LogItemType.PROFESSION) || (type==LogItemType.VOCATION))
          {
            ret.add(item);
          }
        }
      }
      Collections.reverse(ret);
    }
    return ret;
  }

  private void parseItems(List<CharacterLogItem> items)
  {
    for(CharacterLogItem item : items)
    {
      parseItem(item);
    }
  }

  private void parseItem(CharacterLogItem item)
  {
    LogItemType type=item.getLogItemType();
    if (type==LogItemType.PROFESSION)
    {
      String label=item.getLabel();
      long date=item.getDate();
      parseProfessionItem(label,date);
    }
    else if (type==LogItemType.VOCATION)
    {
      String label=item.getLabel();
      long date=item.getDate();
      parseVocationItem(label,date);
    }
  }

  private void parseProfessionItem(String label, long date)
  {
    // Advanced 'Woodworker' (Mastery 6 / Proficiency 5)
    try
    {
      String professionStr=TextTools.findBetween(label,"'","'").trim();
      Profession profession=Profession.getByLabel(professionStr);
      String masteryStr=TextTools.findBetween(label,"Mastery","/").trim();
      String proficiencyStr=TextTools.findBetween(label,"Proficiency",")").trim();
      // Labels are wrong in the character log
      // Proficiency is the brown anvil, mastery is the yellow anvil
      // so that mastery<=proficiency.
      int mastery=NumericTools.parseInt(proficiencyStr,-1);
      int proficiency=NumericTools.parseInt(masteryStr,-1);
      if ((profession!=null) && (mastery!=-1) && (proficiency!=-1))
      {
        ProfessionStatus stat=_status.getProfessionStatus(profession,true);
        stat.addUpdate(mastery,proficiency,date);
      }
    }
    catch(Exception e)
    {
      _logger.error("Error when parsing profession item ["+label+"]",e);
    }
  }

  private void parseVocationItem(String label, long date)
  {
    // Learned 'Woodsman'
    String vocationName=TextTools.findBetween(label,"'","'").trim();
    Vocation v=Vocations.getInstance().getVocationByName(vocationName);
    if (v!=null)
    {
      _status.resetVocation(v,date);
    }
  }
}
