package delta.games.lotro.character.classes.initialGear.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.character.classes.initialGear.InitialGearDefinition;
import delta.games.lotro.character.classes.initialGear.InitialGearElement;
import delta.games.lotro.character.races.RaceDescription;
import delta.games.lotro.character.races.RacesManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Parser for initial gear data stored in XML.
 * @author DAM
 */
public class InitialGearXMLParser
{
  /**
   * Parse a initial gear XML file.
   * @param source Source file.
   * @return List of parsed initial gear definitions.
   */
  public static List<InitialGearDefinition> parseInitialGearFile(File source)
  {
    List<InitialGearDefinition> definitions=new ArrayList<InitialGearDefinition>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> classTags=DOMParsingTools.getChildTagsByName(root,InitialGearXMLConstants.CLASS_TAG);
      for(Element classTag : classTags)
      {
        InitialGearDefinition definition=parseInitialGearDefinition(classTag);
        definitions.add(definition);
      }
    }
    return definitions;
  }

  /**
   * Build an initial gear definition from an XML tag.
   * @param root Root XML tag.
   * @return An initial gear definition.
   */
  private static InitialGearDefinition parseInitialGearDefinition(Element root)
  {
    String key=DOMParsingTools.getStringAttribute(root.getAttributes(),InitialGearXMLConstants.CLASS_KEY_ATTR,"");
    ClassDescription characterClass=ClassesManager.getInstance().getCharacterClassByKey(key);
    InitialGearDefinition initialGear=new InitialGearDefinition(characterClass);
    List<Element> gearTags=DOMParsingTools.getChildTagsByName(root,InitialGearXMLConstants.ITEM_TAG);
    for(Element gearTag : gearTags)
    {
      NamedNodeMap gearAttrs=gearTag.getAttributes();
      // Item ID
      int itemId=DOMParsingTools.getIntAttribute(gearAttrs,InitialGearXMLConstants.ITEM_ID_ATTR,0);
      Item item=ItemsManager.getInstance().getItem(itemId);
      // Race
      RaceDescription requiredRace=null;
      String raceKey=DOMParsingTools.getStringAttribute(gearAttrs,InitialGearXMLConstants.ITEM_RACE_ATTR,null);
      if (raceKey!=null)
      {
        requiredRace=RacesManager.getInstance().getByKey(raceKey);
      }
      InitialGearElement element=new InitialGearElement(item,characterClass,requiredRace);
      initialGear.addGearElement(element);
    }
    return initialGear;
  }
}
