package delta.games.lotro.lore.tasks.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.tasks.Task;

/**
 * Writes tasks to XML files.
 * @author DAM
 */
public class TasksXMLWriter
{
  /**
   * Write some tasks to a XML file.
   * @param toFile File to write to.
   * @param tasks Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<Task> tasks)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",TasksXMLConstants.TASKS_TAG,new AttributesImpl());
        for(Task task : tasks)
        {
          writeTask(hd,task);
        }
        hd.endElement("","",TasksXMLConstants.TASKS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeTask(TransformerHandler hd, Task task) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    QuestDescription quest=task.getQuest();
    // Identifier
    int id=quest.getIdentifier();
    attrs.addAttribute("","",TasksXMLConstants.TASK_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=quest.getName();
    attrs.addAttribute("","",TasksXMLConstants.TASK_NAME_ATTR,XmlWriter.CDATA,name);
    // Item ID
    Item item=task.getItem();
    int itemId=item.getIdentifier();
    attrs.addAttribute("","",TasksXMLConstants.TASK_ITEM_ID_ATTR,XmlWriter.CDATA,String.valueOf(itemId));
    // Item Name
    String itemName=item.getName();
    attrs.addAttribute("","",TasksXMLConstants.TASK_ITEM_NAME_ATTR,XmlWriter.CDATA,itemName);
    // Item count
    int itemCount=task.getItemCount();
    attrs.addAttribute("","",TasksXMLConstants.TASK_ITEM_COUNT_ATTR,XmlWriter.CDATA,String.valueOf(itemCount));

    hd.startElement("","",TasksXMLConstants.TASK_TAG,attrs);
    hd.endElement("","",TasksXMLConstants.TASK_TAG);
  }
}
