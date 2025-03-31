package delta.games.lotro.lore.trade.barter.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.requirements.io.xml.UsageRequirementsXMLParser;
import delta.games.lotro.lore.agents.npcs.NPCsManager;
import delta.games.lotro.lore.agents.npcs.NpcDescription;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionsRegistry;
import delta.games.lotro.lore.trade.barter.BarterEntry;
import delta.games.lotro.lore.trade.barter.BarterEntryElement;
import delta.games.lotro.lore.trade.barter.BarterNpc;
import delta.games.lotro.lore.trade.barter.BarterProfile;
import delta.games.lotro.lore.trade.barter.ItemBarterEntryElement;
import delta.games.lotro.lore.trade.barter.ReputationBarterEntryElement;
import delta.games.lotro.utils.i18n.I18nFacade;
import delta.games.lotro.utils.io.xml.SharedXMLUtils;

/**
 * Parser for barter data stored in XML.
 * @author DAM
 */
public class BarterXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(BarterXMLParser.class);

  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public BarterXMLParser()
  {
    _i18n=I18nFacade.getLabelsMgr("barterers");
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data.
   */
  public List<BarterNpc> parseXML(File source)
  {
    List<BarterNpc> ret=new ArrayList<BarterNpc>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      Map<Integer,BarterProfile> profiles=new HashMap<Integer,BarterProfile>();
      List<Element> profileTags=DOMParsingTools.getChildTagsByName(root,BarterXMLConstants.BARTER_PROFILE_TAG,false);
      for(Element profileTag : profileTags)
      {
        BarterProfile profile=parseBarterProfile(profileTag);
        profiles.put(Integer.valueOf(profile.getIdentifier()),profile);
      }
      List<Element> bartererTags=DOMParsingTools.getChildTagsByName(root,BarterXMLConstants.BARTERER_TAG);
      for(Element bartererTag : bartererTags)
      {
        BarterNpc barterer=parseBarterer(bartererTag,profiles);
        if (barterer!=null)
        {
          ret.add(barterer);
        }
      }
    }
    return ret;
  }

  private BarterNpc parseBarterer(Element root, Map<Integer,BarterProfile> profiles)
  {
    NamedNodeMap attrs=root.getAttributes();
    // NPC
    // - Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,BarterXMLConstants.BARTERER_ID,0);
    NpcDescription npc=NPCsManager.getInstance().getNPCById(id);
    if (npc==null)
    {
      LOGGER.warn("NPC not found: ID={}",Integer.valueOf(id));
      return null;
    }
    BarterNpc ret=new BarterNpc(npc);
    // Requirements
    UsageRequirementsXMLParser.parseRequirements(ret.getRequirements(),root);
    // Profiles
    List<Element> profileTags=DOMParsingTools.getChildTagsByName(root,BarterXMLConstants.BARTER_PROFILE_TAG);
    for(Element profileTag : profileTags)
    {
      int profileId=DOMParsingTools.getIntAttribute(profileTag.getAttributes(),BarterXMLConstants.BARTER_PROFILE_ID,0);
      BarterProfile profile=profiles.get(Integer.valueOf(profileId));
      ret.addBarterProfile(profile);
    }
    return ret;
  }

  private BarterProfile parseBarterProfile(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,BarterXMLConstants.BARTER_PROFILE_ID,0);
    BarterProfile profile=new BarterProfile(id);
    // Name
    String name=_i18n.getLabel(String.valueOf(id));
    profile.setName(name);
    // Requirements
    UsageRequirementsXMLParser.parseRequirements(profile.getRequirements(),root);
    // Entries
    List<Element> barterEntryTags=DOMParsingTools.getChildTagsByName(root,BarterXMLConstants.BARTER_ENTRY_TAG);
    for(Element barterEntryTag : barterEntryTags)
    {
      BarterEntry entry=new BarterEntry(profile);
      profile.addEntry(entry);
      // Give
      List<Element> giveTags=DOMParsingTools.getChildTagsByName(barterEntryTag,BarterXMLConstants.GIVE_TAG);
      for(Element giveTag : giveTags)
      {
        NamedNodeMap giveAttrs=giveTag.getAttributes();
        Item item=SharedXMLUtils.parseItem(giveTag);
        if (item!=null)
        {
          int quantity=DOMParsingTools.getIntAttribute(giveAttrs,BarterXMLConstants.ITEM_QUANTITY_ATTR,1);
          ItemBarterEntryElement entryElement=new ItemBarterEntryElement(item,quantity);
          entry.addElementToGive(entryElement);
        }
      }
      // Receive
      BarterEntryElement receive=null;
      Element receiveTag=DOMParsingTools.getChildTagByName(barterEntryTag,BarterXMLConstants.RECEIVE_TAG);
      if (receiveTag!=null)
      {
        receive=buildItemElement(receiveTag);
      }
      else
      {
        Element receiveReputationTag=DOMParsingTools.getChildTagByName(barterEntryTag,BarterXMLConstants.RECEIVE_REPUTATION_TAG);
        if (receiveReputationTag!=null)
        {
          receive=buildReputationElement(receiveReputationTag);
        }
      }
      entry.setElementToReceive(receive);
    }
    return profile;
  }

  private ItemBarterEntryElement buildItemElement(Element tag)
  {
    ItemBarterEntryElement element=null;
    NamedNodeMap attrs=tag.getAttributes();
    Item item=SharedXMLUtils.parseItem(tag);
    if (item!=null)
    {
      int quantity=DOMParsingTools.getIntAttribute(attrs,BarterXMLConstants.ITEM_QUANTITY_ATTR,1);
      element=new ItemBarterEntryElement(item,quantity);
    }
    return element;
  }

  private ReputationBarterEntryElement buildReputationElement(Element receiveTag)
  {
    ReputationBarterEntryElement element=null;
    NamedNodeMap attrs=receiveTag.getAttributes();
    int factionId=DOMParsingTools.getIntAttribute(attrs,BarterXMLConstants.RECEIVE_REPUTATION_FACTION_ID_ATTR,0);
    if (factionId!=0)
    {
      Faction faction=FactionsRegistry.getInstance().getById(factionId);
      int quantity=DOMParsingTools.getIntAttribute(attrs,BarterXMLConstants.RECEIVE_REPUTATION_QUANTITY_ATTR,0);
      element=new ReputationBarterEntryElement(faction);
      element.setAmount(quantity);
    }
    return element;
  }
}
