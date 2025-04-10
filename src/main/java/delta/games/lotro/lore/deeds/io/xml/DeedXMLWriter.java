package delta.games.lotro.lore.deeds.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.common.ChallengeLevel;
import delta.games.lotro.common.enums.DeedCategory;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLWriter;
import delta.games.lotro.common.rewards.io.xml.RewardsXMLWriter;
import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedType;
import delta.games.lotro.lore.quests.io.xml.AchievableXMLConstants;
import delta.games.lotro.lore.quests.io.xml.AchievableXMLWriter;
import delta.games.lotro.lore.quests.objectives.io.xml.ObjectivesXMLWriter;
import delta.games.lotro.lore.webStore.WebStoreItem;

/**
 * Writes LOTRO deeds to XML files.
 * @author DAM
 */
public class DeedXMLWriter extends AchievableXMLWriter
{
  /**
   * Write deeds to a XML file.
   * @param outFile Output file.
   * @param deeds Deeds to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeDeeds(File outFile, final List<DeedDescription> deeds, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",DeedXMLConstants.DEEDS_TAG,new AttributesImpl());
        for(DeedDescription deed : deeds)
        {
          writeDeed(hd,deed);
        }
        hd.endElement("","",DeedXMLConstants.DEEDS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeDeed(TransformerHandler hd, DeedDescription deed) throws Exception
  {
    AttributesImpl deedAttrs=new AttributesImpl();

    // In-game identifier
    int id=deed.getIdentifier();
    if (id!=0)
    {
      deedAttrs.addAttribute("","",AchievableXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // String key (from lotro-wiki)
    String key=deed.getKey();
    if (key!=null)
    {
      deedAttrs.addAttribute("","",DeedXMLConstants.DEED_KEY_ATTR,XmlWriter.CDATA,key);
    }
    // Name
    String name=deed.getName();
    if (!name.isEmpty())
    {
      deedAttrs.addAttribute("","",AchievableXMLConstants.NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Raw name
    String rawName=deed.getRawName();
    if (!rawName.isEmpty())
    {
      deedAttrs.addAttribute("","",AchievableXMLConstants.RAW_NAME_ATTR,XmlWriter.CDATA,rawName);
    }
    // Type
    DeedType type=deed.getType();
    deedAttrs.addAttribute("","",DeedXMLConstants.DEED_TYPE_ATTR,XmlWriter.CDATA,type.getKey());
    // Requirements
    UsageRequirementsXMLWriter.write(deedAttrs,deed.getUsageRequirement());
    // Category
    DeedCategory category=deed.getCategory();
    if (category!=null)
    {
      int categoryCode=category.getCode();
      deedAttrs.addAttribute("","",DeedXMLConstants.CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(categoryCode));
    }
    // Challenge level
    ChallengeLevel challengeLevel=deed.getChallengeLevel();
    deedAttrs.addAttribute("","",AchievableXMLConstants.LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(challengeLevel.getCode()));
    // Hidden?
    boolean hidden=deed.isHidden();
    if (hidden)
    {
      deedAttrs.addAttribute("","",AchievableXMLConstants.HIDDEN_ATTR,XmlWriter.CDATA,String.valueOf(hidden));
    }
    // Monster play?
    boolean monsterPlay=deed.isMonsterPlay();
    if (monsterPlay)
    {
      deedAttrs.addAttribute("","",AchievableXMLConstants.MONSTER_PLAY_ATTR,XmlWriter.CDATA,String.valueOf(monsterPlay));
    }
    // Web store item
    WebStoreItem webStoreItem=deed.getWebStoreItem();
    if (webStoreItem!=null)
    {
      deedAttrs.addAttribute("","",AchievableXMLConstants.WEB_STORE_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(webStoreItem.getIdentifier()));
    }
    // Description
    String description=deed.getDescription();
    if (!description.isEmpty())
    {
      deedAttrs.addAttribute("","",AchievableXMLConstants.DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    hd.startElement("","",DeedXMLConstants.DEED_TAG,deedAttrs);

    // Objectives
    ObjectivesXMLWriter.write(hd,deed.getObjectives());
    // Pre-requisites
    writePrerequisites(hd,deed);
    // World event requirements
    writeWorldEventsRequirement(hd,deed);
    // Maps
    writeMaps(hd,deed);

    // Rewards
    RewardsXMLWriter.write(hd,deed.getRewards());
    hd.endElement("","",DeedXMLConstants.DEED_TAG);
  }
}
