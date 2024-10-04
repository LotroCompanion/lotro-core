package delta.games.lotro.lore.pip.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.PipType;
import delta.games.lotro.lore.pip.PipDescription;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Parser for pips stored in XML.
 * @author DAM
 */
public class PipXMLParser
{
  private LotroEnum<PipType> _pipTypeEnum;
  private SingleLocaleLabelsManager _i18n;

  /**
   * Constructor.
   */
  public PipXMLParser()
  {
    _pipTypeEnum=LotroEnumsRegistry.getInstance().get(PipType.class);
    _i18n=I18nFacade.getLabelsMgr("pips");
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed pips.
   */
  public List<PipDescription> parseXML(File source)
  {
    List<PipDescription> ret=new ArrayList<PipDescription>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> pipTags=DOMParsingTools.getChildTagsByName(root,PipXMLConstants.PIP_TAG);
      for(Element pipTag : pipTags)
      {
        PipDescription pip=parsePip(pipTag);
        ret.add(pip);
      }
    }
    return ret;
  }

  private PipDescription parsePip(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int typeCode=DOMParsingTools.getIntAttribute(attrs,PipXMLConstants.PIP_TYPE_ATTR,0);
    PipType type=_pipTypeEnum.getEntry(typeCode);
    PipDescription pip=new PipDescription(type);
    // Name
    String name=_i18n.getLabel(String.valueOf(typeCode));
    pip.setName(name);
    // Min
    int min=DOMParsingTools.getIntAttribute(attrs,PipXMLConstants.PIP_MIN_ATTR,0);
    pip.setMin(min);
    // Max
    int max=DOMParsingTools.getIntAttribute(attrs,PipXMLConstants.PIP_MAX_ATTR,0);
    pip.setMax(max);
    // Home
    int home=DOMParsingTools.getIntAttribute(attrs,PipXMLConstants.PIP_HOME_ATTR,0);
    pip.setHome(home);
    // Min icon
    Integer minIconId=DOMParsingTools.getIntegerAttribute(attrs,PipXMLConstants.PIP_MIN_ICON_ID_ATTR,null);
    pip.setIconMin(minIconId);
    // Max icon
    Integer maxIconId=DOMParsingTools.getIntegerAttribute(attrs,PipXMLConstants.PIP_MAX_ICON_ID_ATTR,null);
    pip.setIconMax(maxIconId);
    // Min icon
    Integer homeIconId=DOMParsingTools.getIntegerAttribute(attrs,PipXMLConstants.PIP_HOME_ICON_ID_ATTR,null);
    pip.setIconHome(homeIconId);
    return pip;
  }
}
