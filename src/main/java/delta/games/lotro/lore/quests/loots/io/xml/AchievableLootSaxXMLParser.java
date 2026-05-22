package delta.games.lotro.lore.quests.loots.io.xml;

import org.xml.sax.Attributes;

import delta.common.utils.NumericTools;
import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.SAXParserValve;
import delta.games.lotro.lore.agents.AgentClassification;
import delta.games.lotro.lore.agents.io.xml.AgentsXMLIO;
import delta.games.lotro.lore.agents.mobs.MobDescription;
import delta.games.lotro.lore.agents.mobs.MobLocation;
import delta.games.lotro.lore.agents.mobs.MobsManager;
import delta.games.lotro.lore.agents.mobs.io.xml.MobLocationXMLIO;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.quests.loots.AchievableLoot;
import delta.games.lotro.lore.quests.loots.AchievableLootMonsterSpec;
import delta.games.lotro.lore.quests.loots.AchievableLoots;

/**
 * Parser for quests/deeds objectives stored in XML.
 * @author DAM
 */
public class AchievableLootSaxXMLParser extends SAXParserValve<AchievableLoot>
{
  private AchievableLoot _loot;

  /**
   * Initialize a new loot.
   * @param achievable PArent achievable.
   * @param attrs Loot attributes.
   */
  public void initLoot(Achievable achievable, Attributes attrs)
  {
    // Item
    int itemID=SAXParsingTools.getIntAttribute(attrs,AchievableLootXMLConstants.ID_ATTR,0);
    Item item=ItemsManager.getInstance().getItem(itemID);
    _loot=new AchievableLoot(item);
    // Probabilities
    parseProbabilities(attrs);
    // Register loot
    AchievableLoots loots=achievable.getLoots();
    if (loots==null)
    {
      loots=new AchievableLoots();
      achievable.setLoots(loots);
    }
    loots.addLoot(_loot);
  }

  private void parseProbabilities(Attributes attrs)
  {
    String probabilitiesStr=SAXParsingTools.getStringAttribute(attrs,AchievableLootXMLConstants.PROBABILITIES_ATTR,null);
    if (probabilitiesStr!=null)
    {
      String[] probabilityStrs=probabilitiesStr.split(",");
      int[] probabilities=new int[probabilityStrs.length];
      for(int i=0;i<probabilities.length;i++)
      {
        probabilities[i]=NumericTools.parseInt(probabilityStrs[i],0);
      }
      _loot.setProbabilities(probabilities);
    }
  }

  private AchievableLootMonsterSpec parseMobSpec(Attributes attrs)
  {
    MobLocation location=MobLocationXMLIO.parseMobLocation(attrs);
    AgentClassification classification=new AgentClassification();
    AgentsXMLIO.parseClassification(classification,attrs);
    AchievableLootMonsterSpec spec=new AchievableLootMonsterSpec(location,classification);
    return spec;
  }

  @Override
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
  {
    if (AchievableLootXMLConstants.MOB_TAG.equals(tagName))
    {
      int mobID=SAXParsingTools.getIntAttribute(attrs,AchievableLootXMLConstants.ID_ATTR,0);
      MobDescription mob=MobsManager.getInstance().getMobById(mobID);
      _loot.setMob(mob);
    }
    else if (AchievableLootXMLConstants.MONSTER_SPEC_TAG.equals(tagName))
    {
      AchievableLootMonsterSpec mobSpec=parseMobSpec(attrs);
      _loot.addMonsterSpec(mobSpec);
    }
    else if (AchievableLootXMLConstants.EXCLUDED_MONSTER_SPEC_TAG.equals(tagName))
    {
      AchievableLootMonsterSpec mobSpec=parseMobSpec(attrs);
      _loot.addMonsterSpec(mobSpec);
    }
    return this;
  }

  @Override
  public SAXParserValve<?> handleEndTag(String tagName)
  {
    if (AchievableLootXMLConstants.LOOT_TAG.equals(tagName))
    {
      return getParent();
    }
    return this;
  }
}
