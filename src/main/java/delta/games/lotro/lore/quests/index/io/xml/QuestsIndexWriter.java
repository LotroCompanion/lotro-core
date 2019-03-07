package delta.games.lotro.lore.quests.index.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.lore.quests.index.QuestCategory;
import delta.games.lotro.lore.quests.index.QuestSummary;
import delta.games.lotro.lore.quests.index.QuestsIndex;

/**
 * Writes LOTRO quests indexes to XML files.
 * @author DAM
 */
public class QuestsIndexWriter
{
  /**
   * Write a quest index to a XML file.
   * @param outFile Output file.
   * @param index Index to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final QuestsIndex index, String encoding)
  {
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,index);
      }
    };
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void write(TransformerHandler hd, QuestsIndex index) throws Exception
  {
    AttributesImpl indexAttrs=new AttributesImpl();
    hd.startElement("","",QuestsIndexXMLConstants.INDEX_TAG,indexAttrs);

    String[] categories=index.getCategories();
    for(String category : categories)
    {
      AttributesImpl attrs=new AttributesImpl();
      attrs.addAttribute("","",QuestsIndexXMLConstants.CATEGORY_NAME_ATTR,XmlWriter.CDATA,category);
      hd.startElement("","",QuestsIndexXMLConstants.CATEGORY_TAG,attrs);
      QuestCategory c=index.getCategory(category);
      QuestSummary[] quests=c.getQuests();
      for(QuestSummary quest : quests)
      {
        AttributesImpl questAttrs=new AttributesImpl();
        int identifier=quest.getIdentifier();
        if (identifier!=0)
        {
          questAttrs.addAttribute("","",QuestsIndexXMLConstants.QUEST_ID_ATTR,XmlWriter.CDATA,String.valueOf(identifier));
        }
        questAttrs.addAttribute("","",QuestsIndexXMLConstants.QUEST_KEY_ATTR,XmlWriter.CDATA,quest.getKey());
        questAttrs.addAttribute("","",QuestsIndexXMLConstants.QUEST_NAME_ATTR,XmlWriter.CDATA,quest.getName());
        hd.startElement("","",QuestsIndexXMLConstants.QUEST_TAG,questAttrs);
        hd.endElement("","",QuestsIndexXMLConstants.QUEST_TAG);
      }
      hd.endElement("","",QuestsIndexXMLConstants.CATEGORY_TAG);
    }
    hd.endElement("","",QuestsIndexXMLConstants.INDEX_TAG);
  }
}
