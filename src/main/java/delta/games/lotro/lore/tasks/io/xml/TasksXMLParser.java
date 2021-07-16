package delta.games.lotro.lore.tasks.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.quests.QuestDescription;
import delta.games.lotro.lore.quests.QuestsManager;
import delta.games.lotro.lore.tasks.Task;

/**
 * Parser for tasks stored in XML.
 * @author DAM
 */
public class TasksXMLParser
{
  /**
   * Parse tasks from an XML file.
   * @param source Source file.
   * @return List of parsed tasks.
   */
  public static List<Task> parseTasksFile(File source)
  {
    List<Task> tasks=new ArrayList<Task>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> taskTags=DOMParsingTools.getChildTagsByName(root,TasksXMLConstants.TASK_TAG);
      for(Element taskTag : taskTags)
      {
        Task task=parseTask(taskTag);
        if (task!=null)
        {
          tasks.add(task);
        }
      }
    }
    return tasks;
  }

  /**
   * Build a task from an XML tag.
   * @param root Root XML tag.
   * @return A task.
   */
  private static Task parseTask(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,TasksXMLConstants.TASK_IDENTIFIER_ATTR,0);
    QuestDescription quest=QuestsManager.getInstance().getQuest(id);
    if (quest==null)
    {
      return null;
    }
    Task ret=new Task(quest);
    // Item ID
    int itemID=DOMParsingTools.getIntAttribute(attrs,TasksXMLConstants.TASK_ITEM_ID_ATTR,0);
    Item item=ItemsManager.getInstance().getItem(itemID);
    if (item==null)
    {
      return null;
    }
    // Item count
    int itemCount=DOMParsingTools.getIntAttribute(attrs,TasksXMLConstants.TASK_ITEM_COUNT_ATTR,0);
    ret.setRequiredItems(item,itemCount);
    return ret;
  }
}
