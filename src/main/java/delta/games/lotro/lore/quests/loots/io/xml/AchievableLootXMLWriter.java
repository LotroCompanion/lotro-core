package delta.games.lotro.lore.quests.loots.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.lore.agents.mobs.MobSelection;
import delta.games.lotro.lore.agents.mobs.io.xml.MobSelectionXMLIO;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.quests.loots.AchievableLoot;
import delta.games.lotro.lore.quests.loots.AchievableLoots;

/**
 * XML writer for achievable loots.
 * @author DAM
 */
public class AchievableLootXMLWriter
{
  /**
   * Write some achievable loots.
   * @param hd Output.
   * @param loots Data to write.
   * @throws SAXException If an error occurs.
   */
  public static void writeLoots(TransformerHandler hd, AchievableLoots loots) throws SAXException
  {
    if (loots==null)
    {
      return;
    }
    for(AchievableLoot loot : loots.getLoots())
    {
      writeLoot(hd,loot);
    }
  }

  private static void writeLoot(TransformerHandler hd, AchievableLoot loot) throws SAXException
  {
    Item item=loot.getItem();
    AttributesImpl lootAttrs=new AttributesImpl();

    // Item
    // - Identifier
    int id=item.getIdentifier();
    lootAttrs.addAttribute("","",AchievableLootXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // - Name
    String name=item.getName();
    lootAttrs.addAttribute("","",AchievableLootXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    // Probabilities
    int[] probabilities=loot.getProbabilities();
    String probabilitiesStr=asPersistentString(probabilities);
    lootAttrs.addAttribute("","",AchievableLootXMLConstants.PROBABILITIES_ATTR,XmlWriter.CDATA,probabilitiesStr);

    hd.startElement("","",AchievableLootXMLConstants.LOOT_TAG,lootAttrs);
    // Mob?
    MobDescription mob=loot.getMob();
    if (mob!=null)
    {
      writeMobReference(hd,mob);
    }
    // Monster specs
    List<MobSelection> monsterSpecs=loot.getMonsterSpecs();
    if (monsterSpecs!=null)
    {
      for(MobSelection monsterSpec : monsterSpecs)
      {
        MobSelectionXMLIO.writeMobSelection(AchievableLootXMLConstants.MONSTER_SPEC_TAG,hd,monsterSpec);
      }
    }
    // Excluded monster specs
    List<MobSelection> excludedMonsterSpecs=loot.getExcludedMonsterSpecs();
    if (excludedMonsterSpecs!=null)
    {
      for(MobSelection excludedMonsterSpec : excludedMonsterSpecs)
      {
        MobSelectionXMLIO.writeMobSelection(AchievableLootXMLConstants.EXCLUDED_MONSTER_SPEC_TAG,hd,excludedMonsterSpec);
      }
    }
    hd.endElement("","",AchievableLootXMLConstants.LOOT_TAG);
  }

  private static void writeMobReference(TransformerHandler hd, MobDescription mob) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // - Identifier
    int id=mob.getIdentifier();
    attrs.addAttribute("","",AchievableLootXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // - Name
    String name=mob.getName();
    attrs.addAttribute("","",AchievableLootXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",AchievableLootXMLConstants.MOB_TAG,attrs);
    hd.endElement("","",AchievableLootXMLConstants.MOB_TAG);
  }

  private static String asPersistentString(int[] values)
  {
    if (values.length==1)
    {
      return String.valueOf(values[0]);
    }
    StringBuilder sb=new StringBuilder();
    boolean notFirst=false;
    for(int value : values)
    {
      if (notFirst)
      {
        sb.append(',');
      }
      sb.append(value);
      notFirst=true;
    }
    return sb.toString();
  }
}
