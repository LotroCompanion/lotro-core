package delta.games.lotro.lore.items.sets.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.common.stats.io.xml.StatsProviderXMLWriter;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.sets.ItemsSet;
import delta.games.lotro.lore.items.sets.ItemsSetBonus;
import delta.games.lotro.utils.Proxy;

/**
 * Writes LOTRO items sets to XML files.
 * @author DAM
 */
public class ItemsSetXMLWriter
{
  /**
   * Write a file with sets.
   * @param toFile Output file.
   * @param sets Sets to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeSetsFile(File toFile, List<ItemsSet> sets)
  {
    ItemsSetXMLWriter writer=new ItemsSetXMLWriter();
    Collections.sort(sets,new IdentifiableComparator<ItemsSet>());
    boolean ok=writer.write(toFile,sets,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write some items sets to a XML file.
   * @param outFile Output file.
   * @param sets Sets to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final List<ItemsSet> sets, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeSets(hd,sets);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeSets(TransformerHandler hd, List<ItemsSet> sets) throws Exception
  {
    AttributesImpl itemAttrs=new AttributesImpl();
    hd.startElement("","",ItemsSetXMLConstants.ITEMS_SETS_TAG,itemAttrs);
    for(ItemsSet set : sets) 
    {
      writeSet(hd,set);
    }
    hd.endElement("","",ItemsSetXMLConstants.ITEMS_SETS_TAG);
  }

  /**
   * Write an items set to a XML document.
   * @param hd Output.
   * @param set Items set to write.
   * @throws Exception If a problem occurs.
   */
  private void writeSet(TransformerHandler hd, ItemsSet set) throws Exception
  {
    AttributesImpl itemAttrs=new AttributesImpl();

    // Identifier
    int id=set.getIdentifier();
    itemAttrs.addAttribute("","",ItemsSetXMLConstants.ITEMS_SET_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=set.getName();
    itemAttrs.addAttribute("","",ItemsSetXMLConstants.ITEMS_SET_NAME_ATTR,XmlWriter.CDATA,name);
    // Level
    int level=set.getLevel();
    itemAttrs.addAttribute("","",ItemsSetXMLConstants.ITEMS_SET_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(level));
    // Required level
    int requiredLevel=set.getRequiredLevel();
    if (requiredLevel!=1)
    {
      itemAttrs.addAttribute("","",ItemsSetXMLConstants.ITEMS_SET_REQUIRED_LEVEL_ATTR,XmlWriter.CDATA,String.valueOf(requiredLevel));
    }
    // Description
    String description=set.getDescription();
    itemAttrs.addAttribute("","",ItemsSetXMLConstants.ITEMS_SET_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    hd.startElement("","",ItemsSetXMLConstants.ITEMS_SET_TAG,itemAttrs);

    // Items
    List<Proxy<Item>> members=set.getMembers();
    for(Proxy<Item> member : members)
    {
      AttributesImpl attrs=new AttributesImpl();
      int memberId=member.getId();
      attrs.addAttribute("","",ItemsSetXMLConstants.ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(memberId));
      String memberName=member.getName();
      attrs.addAttribute("","",ItemsSetXMLConstants.ITEM_NAME_ATTR,XmlWriter.CDATA,memberName);
      hd.startElement("","",ItemsSetXMLConstants.ITEM_TAG,attrs);
      hd.endElement("","",ItemsSetXMLConstants.ITEM_TAG);
    }
    // Bonuses
    List<ItemsSetBonus> bonuses=set.getBonuses();
    for(ItemsSetBonus bonus : bonuses)
    {
      AttributesImpl attrs=new AttributesImpl();
      int count=bonus.getPiecesCount();
      attrs.addAttribute("","",ItemsSetXMLConstants.BONUS_NB_ITEMS_ATTR,XmlWriter.CDATA,String.valueOf(count));
      hd.startElement("","",ItemsSetXMLConstants.BONUS_TAG,attrs);
      // Stats
      StatsProvider statsProvider=bonus.getStatsProvider();
      StatsProviderXMLWriter.writeXml(hd,null,statsProvider,null);
      hd.endElement("","",ItemsSetXMLConstants.BONUS_TAG);
    }
    hd.endElement("","",ItemsSetXMLConstants.ITEMS_SET_TAG);
  }
}
