package delta.games.lotro.character.status.portraitFrames.io.xml;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.status.portraitFrames.PortraitFramesStatus;
import delta.games.lotro.lore.portraitFrames.PortraitFrameDescription;
import delta.games.lotro.lore.portraitFrames.PortraitFramesManager;

/**
 * Parser for the portrait frames status stored in XML.
 * @author DAM
 */
public class PortraitFramesStatusXMLParser
{
  private static final Logger LOGGER=LoggerFactory.getLogger(PortraitFramesStatusXMLParser.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed status or <code>null</code>.
   */
  public PortraitFramesStatus parseXML(File source)
  {
    PortraitFramesStatus status=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      status=parseStatus(root);
    }
    return status;
  }

  private PortraitFramesStatus parseStatus(Element root)
  {
    PortraitFramesStatus status=new PortraitFramesStatus();
    // Current
    Element currentTag=DOMParsingTools.getChildTagByName(root,PortraitFramesStatusXMLConstants.CURRENT_TAG,false);
    PortraitFrameDescription current=parseTag(currentTag);
    status.setCurrentPortraitFrame(current);
    // Unlocked
    List<Element> tags=DOMParsingTools.getChildTagsByName(root,PortraitFramesStatusXMLConstants.UNLOCKED_TAG,false);
    for(Element tag : tags)
    {
      PortraitFrameDescription unlocked=parseTag(tag);
      if (unlocked!=null)
      {
        status.addUnlockPortraitFrame(unlocked);
      }
    }
    return status;
  }

  private PortraitFrameDescription parseTag(Element tag)
  {
    if (tag==null)
    {
      return null;
    }
    NamedNodeMap attrs=tag.getAttributes();
    int id=DOMParsingTools.getIntAttribute(attrs,PortraitFramesStatusXMLConstants.PORTRAIT_FRAME_ID_ATTR,0);
    PortraitFrameDescription ret=PortraitFramesManager.getInstance().getPortraitFrameById(id);
    if (ret==null)
    {
      LOGGER.warn("Unknown portrait frame: {}",Integer.valueOf(id));
    }
    return ret;
  }
}
