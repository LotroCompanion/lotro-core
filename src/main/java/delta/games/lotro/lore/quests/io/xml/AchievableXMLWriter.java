package delta.games.lotro.lore.quests.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.ChallengeLevel;
import delta.games.lotro.lore.quests.Achievable;
import delta.games.lotro.utils.Proxy;

/**
 * Base class for achievable XML writers.
 * @author DAM
 */
public class AchievableXMLWriter
{
  protected void writeAttributes(TransformerHandler hd, AttributesImpl attrs, Achievable achievable) throws Exception
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

  protected void writePrerequisites(TransformerHandler hd, Achievable quest) throws Exception
  {
    List<Proxy<Achievable>> prerequisites=quest.getPrerequisites();
    for(Proxy<Achievable> prerequisite : prerequisites)
    {
      writeAchievableProxy(hd,prerequisite,AchievableXMLConstants.PREREQUISITE_TAG);
    }
  }

  protected void writeAchievableProxy(TransformerHandler hd, Proxy<Achievable> proxy, String tag) throws Exception
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
}
