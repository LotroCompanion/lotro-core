package delta.games.lotro.lore.trade.barter.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLWriter;
import delta.games.lotro.lore.agents.npcs.NpcDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.trade.barter.BarterEntry;
import delta.games.lotro.lore.trade.barter.BarterEntryElement;
import delta.games.lotro.lore.trade.barter.BarterNpc;
import delta.games.lotro.lore.trade.barter.BarterProfile;
import delta.games.lotro.lore.trade.barter.ItemBarterEntryElement;
import delta.games.lotro.lore.trade.barter.ReputationBarterEntryElement;
import delta.games.lotro.utils.Proxy;

/**
 * Writes barter tables to XML files.
 * @author DAM
 */
public class BarterXMLWriter
{
  /**
   * Write a file with barter tables.
   * @param toFile Output file.
   * @param barterers Tables to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeBarterTablesFile(File toFile, final List<BarterNpc> barterers)
  {
    Collections.sort(barterers,new IdentifiableComparator<BarterNpc>());
    XmlWriter xmlWriter=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",BarterXMLConstants.BARTERERS_TAG,new AttributesImpl());
        writeBarterTables(hd,barterers);
        hd.endElement("","",BarterXMLConstants.BARTERERS_TAG);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,xmlWriter);
    return ret;
  }

  /**
   * Write barter tables to a XML file.
   * @param hd Output.
   * @param barterers Tables to write.
   * @throws SAXException if an error occurs.
   */
  private static void writeBarterTables(TransformerHandler hd, final List<BarterNpc> barterers) throws SAXException
  {
    Map<Integer,BarterProfile> profilesMap=new HashMap<Integer,BarterProfile>();
    for(BarterNpc barterer : barterers)
    {
      for(BarterProfile profile : barterer.getBarterProfiles())
      {
        profilesMap.put(Integer.valueOf(profile.getIdentifier()),profile);
      }
      writeBarterer(hd,barterer);
    }
    List<BarterProfile> profiles=new ArrayList<BarterProfile>(profilesMap.values());
    Collections.sort(profiles,new IdentifiableComparator<BarterProfile>());
    for(BarterProfile profile : profiles)
    {
      writeProfile(hd,profile);
    }
  }

  private static void writeBarterer(TransformerHandler hd, BarterNpc barterer) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=barterer.getIdentifier();
    attrs.addAttribute("","",BarterXMLConstants.BARTERER_ID,XmlWriter.CDATA,String.valueOf(id));
    // NPC
    NpcDescription npc=barterer.getNpc();
    // Name
    String name=npc.getName();
    attrs.addAttribute("","",BarterXMLConstants.BARTERER_NAME,XmlWriter.CDATA,name);
    // Title
    String title=npc.getTitle();
    if (title.length()>0)
    {
      attrs.addAttribute("","",BarterXMLConstants.BARTERER_TITLE,XmlWriter.CDATA,title);
    }
    // Requirements
    UsageRequirementsXMLWriter.write(attrs,barterer.getRequirements());
    hd.startElement("","",BarterXMLConstants.BARTERER_TAG,attrs);
    // Profiles
    for(BarterProfile profile : barterer.getBarterProfiles())
    {
      AttributesImpl profileAttrs=new AttributesImpl();
      // Identifier
      int profileId=profile.getIdentifier();
      profileAttrs.addAttribute("","",BarterXMLConstants.BARTER_PROFILE_ID,XmlWriter.CDATA,String.valueOf(profileId));
      hd.startElement("","",BarterXMLConstants.BARTER_PROFILE_TAG,profileAttrs);
      hd.endElement("","",BarterXMLConstants.BARTER_PROFILE_TAG);
    }
    hd.endElement("","",BarterXMLConstants.BARTERER_TAG);
  }

  private static void writeProfile(TransformerHandler hd, BarterProfile profile) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    // Identifier
    int id=profile.getIdentifier();
    attrs.addAttribute("","",BarterXMLConstants.BARTER_PROFILE_ID,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=profile.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",BarterXMLConstants.BARTER_PROFILE_NAME,XmlWriter.CDATA,name);
    }
    // Requirements
    UsageRequirementsXMLWriter.write(attrs,profile.getRequirements());
    hd.startElement("","",BarterXMLConstants.BARTER_PROFILE_TAG,attrs);
    // Entries
    for(BarterEntry entry : profile.getEntries())
    {
      hd.startElement("","",BarterXMLConstants.BARTER_ENTRY_TAG,new AttributesImpl());
      for(BarterEntryElement give : entry.getElementsToGive())
      {
        writeBarterEntryElement(hd,BarterXMLConstants.GIVE_TAG,give);
      }
      writeBarterEntryElement(hd,BarterXMLConstants.RECEIVE_TAG,entry.getElementToReceive());
      hd.endElement("","",BarterXMLConstants.BARTER_ENTRY_TAG);
    }
    hd.endElement("","",BarterXMLConstants.BARTER_PROFILE_TAG);
  }

  private static void writeBarterEntryElement(TransformerHandler hd, String tag, BarterEntryElement element) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();

    if (element instanceof ItemBarterEntryElement)
    {
      ItemBarterEntryElement itemElement=(ItemBarterEntryElement)element;
      // Item
      Proxy<Item> itemProxy=itemElement.getItemProxy();
      // - Identifier
      int id=itemProxy.getId();
      attrs.addAttribute("","",BarterXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // - Name
      String name=itemProxy.getName();
      attrs.addAttribute("","",BarterXMLConstants.ITEM_NAME_ATTR,XmlWriter.CDATA,name);
      // Quantity
      int quantity=itemElement.getQuantity();
      if (quantity!=1)
      {
        attrs.addAttribute("","",BarterXMLConstants.ITEM_QUANTITY_ATTR,XmlWriter.CDATA,String.valueOf(quantity));
      }
      hd.startElement("","",tag,attrs);
      hd.endElement("","",tag);
    }
    else if (element instanceof ReputationBarterEntryElement)
    {
      ReputationBarterEntryElement reputationElement=(ReputationBarterEntryElement)element;
      // Faction
      Faction faction=reputationElement.getFaction();
      // - Identifier
      int id=faction.getIdentifier();
      attrs.addAttribute("","",BarterXMLConstants.RECEIVE_REPUTATION_FACTION_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // - Name
      String name=faction.getName();
      attrs.addAttribute("","",BarterXMLConstants.RECEIVE_REPUTATION_FACTION_NAME_ATTR,XmlWriter.CDATA,name);
      // Quantity
      int quantity=reputationElement.getAmount();
      attrs.addAttribute("","",BarterXMLConstants.RECEIVE_REPUTATION_QUANTITY_ATTR,XmlWriter.CDATA,String.valueOf(quantity));
      hd.startElement("","",BarterXMLConstants.RECEIVE_REPUTATION_TAG,attrs);
      hd.endElement("","",BarterXMLConstants.RECEIVE_REPUTATION_TAG);
    }
  }
}
