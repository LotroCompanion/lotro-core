package delta.games.lotro.lore.portraitFrames.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.lore.portraitFrames.PortraitFrameDescription;

/**
 * Parser for portrait frames stored in XML.
 * @author DAM
 */
public class PortraitFramesXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return the loaded portrait frames or <code>null</code>.
   */
  public List<PortraitFrameDescription> parseXML(File source)
  {
    List<PortraitFrameDescription> ret=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      ret=parsePortraitFrames(root);
    }
    return ret;
  }

  private static List<PortraitFrameDescription> parsePortraitFrames(Element mainTag)
  {
    List<PortraitFrameDescription> ret=new ArrayList<PortraitFrameDescription>();
    List<Element> elementTags=DOMParsingTools.getChildTagsByName(mainTag,PortraitFramesXMLConstants.PORTRAIT_FRAME_TAG,false);
    for(Element elementTag : elementTags)
    {
      PortraitFrameDescription portraitFrame=parsePortraitFrame(elementTag);
      if (portraitFrame!=null)
      {
        ret.add(portraitFrame);
      }
    }
    return ret;
  }

  /**
   * Read a portrait frame from a tag.
   * @param elementTag Tag to read.
   * @return the loaded portrait frame or <code>null</code>.
   */
  private static PortraitFrameDescription parsePortraitFrame(Element elementTag)
  {
    NamedNodeMap attrs=elementTag.getAttributes();
    PortraitFrameDescription ret=new PortraitFrameDescription();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,PortraitFramesXMLConstants.PORTRAIT_FRAME_ID_ATTR,0);
    ret.setIdentifier(id);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,PortraitFramesXMLConstants.PORTRAIT_FRAME_NAME_ATTR,"");
    ret.setName(name);
    // Code
    int code=DOMParsingTools.getIntAttribute(attrs,PortraitFramesXMLConstants.PORTRAIT_FRAME_CODE_ATTR,0);
    ret.setCode(code);
    // Icon name
    String iconName=DOMParsingTools.getStringAttribute(attrs,PortraitFramesXMLConstants.PORTRAIT_FRAME_ICON_ATTR,"");
    ret.setIconName(iconName);
    // Is for freeps
    boolean isForFreeps=DOMParsingTools.getBooleanAttribute(attrs,PortraitFramesXMLConstants.PORTRAIT_FRAME_IS_FOR_FREEPS_ATTR,true);
    ret.setIsForFreeps(isForFreeps);
    // Is for creeps
    boolean isForCreeps=DOMParsingTools.getBooleanAttribute(attrs,PortraitFramesXMLConstants.PORTRAIT_FRAME_IS_FOR_CREEPS_ATTR,false);
    ret.setIsForCreeps(isForCreeps);
    // Is for PVP characters
    boolean isForPvpCharacters=DOMParsingTools.getBooleanAttribute(attrs,PortraitFramesXMLConstants.PORTRAIT_FRAME_IS_FOR_PVP_CHARACTERS_ATTR,false);
    ret.setIsForPvpCharacters(isForPvpCharacters);
    return ret;
  }
}
