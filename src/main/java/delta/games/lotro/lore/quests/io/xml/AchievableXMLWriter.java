package delta.games.lotro.lore.quests.io.xml;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.ChallengeLevel;
import delta.games.lotro.common.requirements.AbstractAchievableRequirement;
import delta.games.lotro.common.requirements.io.xml.QuestsRequirementsXMLWriter;
import delta.games.lotro.lore.maps.MapDescription;
import delta.games.lotro.lore.maps.io.xml.MapDescriptionXMLWriter;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.lore.worldEvents.AbstractWorldEventCondition;
import delta.games.lotro.lore.worldEvents.io.xml.WorldEventConditionsXMLWriter;
import delta.games.lotro.utils.Proxy;

/**
 * Base class for achievable XML writers.
 * @author DAM
 */
public class AchievableXMLWriter
{
  protected void writeAttributes(AttributesImpl attrs, Achievable achievable)
  {
    // Identifier
    int id=achievable.getIdentifier();
    if (id!=0)
    {
      attrs.addAttribute("","",AchievableXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Name
    String name=achievable.getName();
    if (name.length()>0)
    {
      attrs.addAttribute("","",AchievableXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Category
    String category=achievable.getCategory();
    if (category.length()>0)
    {
      attrs.addAttribute("","",AchievableXMLConstants.CATEGORY_ATTR,XmlWriter.CDATA,category);
    }
    // Challenge level
    ChallengeLevel challengeLevel=achievable.getChallengeLevel();
    attrs.addAttribute("","",AchievableXMLConstants.LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(challengeLevel.getCode()));
  }

  protected void writePrerequisites(TransformerHandler hd, Achievable quest) throws SAXException
  {
    AbstractAchievableRequirement requirement=quest.getQuestRequirements();
    if (requirement!=null)
    {
      QuestsRequirementsXMLWriter.writeQuestRequirement(hd,requirement);
    }
  }

  protected void writeWorldEventsRequirement(TransformerHandler hd, Achievable quest) throws Exception
  {
    AbstractWorldEventCondition requirement=quest.getWorldEventsRequirement();
    if (requirement!=null)
    {
      WorldEventConditionsXMLWriter.writeWorldEventCondition(hd,requirement);
    }
  }

  protected void writeAchievableProxy(TransformerHandler hd, Proxy<Achievable> proxy, String tag) throws SAXException
  {
    if (proxy!=null)
    {
      AttributesImpl questAttrs=new AttributesImpl();
      int id=proxy.getId();
      questAttrs.addAttribute("","",AchievableXMLConstants.PROXY_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      String name=proxy.getName();
      if (name!=null)
      {
        questAttrs.addAttribute("","",AchievableXMLConstants.PROXY_NAME_ATTR,XmlWriter.CDATA,name);
      }
      hd.startElement("","",tag,questAttrs);
      hd.endElement("","",tag);
    }
  }

  protected void writeMaps(TransformerHandler hd, Achievable achievable) throws SAXException
  {
    for(MapDescription map : achievable.getMaps())
    {
      MapDescriptionXMLWriter.writeMapDescription(hd,map);
    }
  }
}
