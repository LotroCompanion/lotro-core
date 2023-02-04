package delta.games.lotro.lore.instances.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.instances.InstanceCategory;
import delta.games.lotro.lore.instances.InstancesTree;
import delta.games.lotro.lore.instances.PrivateEncounter;
import delta.games.lotro.lore.instances.PrivateEncountersManager;
import delta.games.lotro.lore.instances.SkirmishPrivateEncounter;

/**
 * Parser for the instances tree stored in XML.
 * @author DAM
 */
public class InstancesTreeXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public InstancesTree parseXML(File source)
  {
    InstancesTree ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parseInstancesTree(root);
    }
    return ret;
  }

  /**
   * Build an instances tree from an XML tag.
   * @param rootTag Root tag.
   * @return An instances tree.
   */
  private InstancesTree parseInstancesTree(Element rootTag)
  {
    InstancesTree mgr=new InstancesTree();
    List<Element> categoryTags=DOMParsingTools.getChildTagsByName(rootTag,InstancesTreeXMLConstants.CATEGORY_TAG);
    for(Element categoryTag : categoryTags)
    {
      InstanceCategory category=parseCategory(categoryTag);
      mgr.addCategory(category);
    }
    return mgr;
  }

  private InstanceCategory parseCategory(Element categoryTag)
  {
    NamedNodeMap attrs=categoryTag.getAttributes();
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,InstancesTreeXMLConstants.CATEGORY_NAME_ATTR,"");
    InstanceCategory ret=new InstanceCategory(name);
    // Instances
    PrivateEncountersManager peMgr=PrivateEncountersManager.getInstance();
    List<Element> instanceTags=DOMParsingTools.getChildTagsByName(categoryTag,InstancesTreeXMLConstants.INSTANCE_TAG,false);
    for(Element instanceTag : instanceTags)
    {
      int instanceId=DOMParsingTools.getIntAttribute(instanceTag.getAttributes(),InstancesTreeXMLConstants.INSTANCE_ID_ATTR,0);
      PrivateEncounter pe=peMgr.getPrivateEncounterById(instanceId);
      if (pe instanceof SkirmishPrivateEncounter)
      {
        SkirmishPrivateEncounter skirmishPe=(SkirmishPrivateEncounter)pe;
        ret.addPrivateEncounter(skirmishPe);
      }
    }
    return ret;
  }
}
