package delta.games.lotro.lore.hobbies.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.hobbies.HobbyDescription;
import delta.games.lotro.lore.hobbies.HobbyTitleEntry;
import delta.games.lotro.lore.hobbies.rewards.HobbyRewardEntry;
import delta.games.lotro.lore.hobbies.rewards.HobbyRewards;
import delta.games.lotro.lore.hobbies.rewards.HobbyRewardsProfile;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.maps.GeoAreasManager;
import delta.games.lotro.lore.maps.Territory;
import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Writes hobbies to XML files.
 * @author DAM
 */
public class HobbyDescriptionXMLWriter
{
  /**
   * Write some hobbies to a XML file.
   * @param toFile File to write to.
   * @param hobbies Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<HobbyDescription> hobbies)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",HobbyDescriptionXMLConstants.HOBBIES_TAG,new AttributesImpl());
        for(HobbyDescription hobby : hobbies)
        {
          writeHobby(hd,hobby);
        }
        hd.endElement("","",HobbyDescriptionXMLConstants.HOBBIES_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeHobby(TransformerHandler hd, HobbyDescription hobby) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=hobby.getIdentifier();
    attrs.addAttribute("","",HobbyDescriptionXMLConstants.HOBBY_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=hobby.getName();
    attrs.addAttribute("","",HobbyDescriptionXMLConstants.HOBBY_NAME_ATTR,XmlWriter.CDATA,name);
    // Type
    int type=hobby.getHobbyType();
    attrs.addAttribute("","",HobbyDescriptionXMLConstants.HOBBY_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(type));
    // Description
    String description=hobby.getDescription();
    if (!description.isEmpty())
    {
      attrs.addAttribute("","",HobbyDescriptionXMLConstants.HOBBY_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    // Trainer info
    String trainerInfo=hobby.getTrainerDisplayInfo();
    if (!trainerInfo.isEmpty())
    {
      attrs.addAttribute("","",HobbyDescriptionXMLConstants.HOBBY_TRAINER_INFO_ATTR,XmlWriter.CDATA,trainerInfo);
    }
    // Icon ID
    int iconId=hobby.getIconId();
    attrs.addAttribute("","",HobbyDescriptionXMLConstants.HOBBY_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    // Daily gain proficiency limit
    int dailyGainProficiencyLimit=hobby.getDailyProficiencyGainLimit();
    attrs.addAttribute("","",HobbyDescriptionXMLConstants.HOBBY_DAILY_PROFICIENCY_GAIN_LIMIT_ATTR,XmlWriter.CDATA,String.valueOf(dailyGainProficiencyLimit));
    // Min level
    int minLevel=hobby.getMinLevel();
    attrs.addAttribute("","",HobbyDescriptionXMLConstants.HOBBY_MIN_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(minLevel));
    // Proficiency property name
    String proficiencyPropertyName=hobby.getProficiencyPropertyName();
    attrs.addAttribute("","",HobbyDescriptionXMLConstants.HOBBY_PROFICIENCY_PROPERTY_NAME_ATTR,XmlWriter.CDATA,proficiencyPropertyName);
    // Proficiency modifier property name
    String proficiencyModifierPropertyName=hobby.getProficiencyModifierPropertyName();
    attrs.addAttribute("","",HobbyDescriptionXMLConstants.HOBBY_PROFICIENCY_MODIFIER_PROPERTY_NAME_ATTR,XmlWriter.CDATA,proficiencyModifierPropertyName);
    // Proficiency modifier property name
    String treasureProfileOverridePropertyName=hobby.getTreasureProfileOverridePropertyName();
    attrs.addAttribute("","",HobbyDescriptionXMLConstants.HOBBY_TREASURE_PROFILE_OVERRIDE_PROPERTY_ATTR,XmlWriter.CDATA,treasureProfileOverridePropertyName);
    hd.startElement("","",HobbyDescriptionXMLConstants.HOBBY_TAG,attrs);

    // Items
    for(Item item : hobby.getItems())
    {
      AttributesImpl itemAttrs=new AttributesImpl();
      // Identifier
      int itemID=item.getIdentifier();
      itemAttrs.addAttribute("","",HobbyDescriptionXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemID));
      // Name
      String itemName=item.getName();
      itemAttrs.addAttribute("","",HobbyDescriptionXMLConstants.ITEM_NAME_ATTR,XmlWriter.CDATA,itemName);
      hd.startElement("","",HobbyDescriptionXMLConstants.ITEM_TAG,itemAttrs);
      hd.endElement("","",HobbyDescriptionXMLConstants.ITEM_TAG);
    }
    // Titles
    for(HobbyTitleEntry titleEntry : hobby.getTitles())
    {
      AttributesImpl titleAttrs=new AttributesImpl();
      TitleDescription title=titleEntry.getTitle();
      // Identifier
      int titleID=title.getIdentifier();
      titleAttrs.addAttribute("","",HobbyDescriptionXMLConstants.TITLE_ID_ATTR,XmlWriter.CDATA,String.valueOf(titleID));
      // Name
      String titleName=title.getName();
      titleAttrs.addAttribute("","",HobbyDescriptionXMLConstants.TITLE_NAME_ATTR,XmlWriter.CDATA,titleName);
      // Proficiency
      int proficiency=titleEntry.getProficiency();
      titleAttrs.addAttribute("","",HobbyDescriptionXMLConstants.TITLE_PROFICIENCY_ATTR,XmlWriter.CDATA,String.valueOf(proficiency));
      hd.startElement("","",HobbyDescriptionXMLConstants.TITLE_TAG,titleAttrs);
      hd.endElement("","",HobbyDescriptionXMLConstants.TITLE_TAG);
    }
    // Rewards
    writeRewards(hd,hobby.getRewards());
    hd.endElement("","",HobbyDescriptionXMLConstants.HOBBY_TAG);
  }

  private static void writeRewards(TransformerHandler hd, HobbyRewards rewards) throws SAXException
  {
    // Territories
    for(Integer territoryID : rewards.getKnownTerritories())
    {
      HobbyRewardsProfile profile=rewards.getProfile(territoryID.intValue());
      AttributesImpl attrs=new AttributesImpl();
      // Territory
      // - ID
      attrs.addAttribute("","",HobbyDescriptionXMLConstants.TERRITORY_ID_ATTR,XmlWriter.CDATA,territoryID.toString());
      // - Name
      Territory territory=GeoAreasManager.getInstance().getTerritoryById(territoryID.intValue());
      if (territory!=null)
      {
        String territoryName=territory.getName();
        attrs.addAttribute("","",HobbyDescriptionXMLConstants.TERRITORY_NAME_ATTR,XmlWriter.CDATA,territoryName);
      }
      // Profile
      if (profile!=null)
      {
        int profileID=profile.getIdentifier();
        attrs.addAttribute("","",HobbyDescriptionXMLConstants.TERRITORY_PROFILE_ATTR,XmlWriter.CDATA,String.valueOf(profileID));
      }
      hd.startElement("","",HobbyDescriptionXMLConstants.TERRITORY_TAG,attrs);
      hd.endElement("","",HobbyDescriptionXMLConstants.TERRITORY_TAG);
    }
    // Profiles
    for(HobbyRewardsProfile profile : rewards.getKnownProfiles())
    {
      AttributesImpl attrs=new AttributesImpl();
      // ID
      int profileID=profile.getIdentifier();
      attrs.addAttribute("","",HobbyDescriptionXMLConstants.PROFILE_ID_ATTR,XmlWriter.CDATA,String.valueOf(profileID));
      hd.startElement("","",HobbyDescriptionXMLConstants.PROFILE_TAG,attrs);
      // Entries
      for(HobbyRewardEntry entry : profile.getEntries())
      {
        AttributesImpl entryAttrs=new AttributesImpl();
        Item item=entry.getItem();
        int itemID=item.getIdentifier();
        entryAttrs.addAttribute("","",HobbyDescriptionXMLConstants.ENTRY_ITEM_ID,XmlWriter.CDATA,String.valueOf(itemID));
        String itemName=item.getName();
        entryAttrs.addAttribute("","",HobbyDescriptionXMLConstants.ENTRY_ITEM_NAME,XmlWriter.CDATA,itemName);
        int minProficiency=entry.getMinProficiency();
        entryAttrs.addAttribute("","",HobbyDescriptionXMLConstants.ENTRY_MIN_PROFICIENCY,XmlWriter.CDATA,String.valueOf(minProficiency));
        int maxProficiency=entry.getMaxProficiency();
        entryAttrs.addAttribute("","",HobbyDescriptionXMLConstants.ENTRY_MAX_PROFICIENCY,XmlWriter.CDATA,String.valueOf(maxProficiency));
        int weight=entry.getWeight();
        entryAttrs.addAttribute("","",HobbyDescriptionXMLConstants.ENTRY_WEIGHT,XmlWriter.CDATA,String.valueOf(weight));
        hd.startElement("","",HobbyDescriptionXMLConstants.ENTRY_TAG,entryAttrs);
        hd.endElement("","",HobbyDescriptionXMLConstants.ENTRY_TAG);
      }
      hd.endElement("","",HobbyDescriptionXMLConstants.PROFILE_TAG);
    }
  }
}
