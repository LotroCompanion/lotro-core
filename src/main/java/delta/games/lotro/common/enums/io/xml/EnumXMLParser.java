package delta.games.lotro.common.enums.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumEntry;
import delta.games.lotro.utils.PerfUtils;
import delta.games.lotro.utils.i18n.I18nFacade;

/**
 * Parser for enums stored in XML.
 * @param <T> Type of entries.
 * @author DAM
 */
public class EnumXMLParser<T extends LotroEnumEntry>
{
  /**
   * Parse the contents of a LOTRO enum.
   * @param source Source file.
   * @param implClass Implementation class for enum entries.
   * @return the loaded enum or <code>null</code> if not found.
   */
  public LotroEnum<T> parseXML(File source, Class<T> implClass)
  {
    Element root=DOMParsingTools.parse(source);
    if (root==null)
    {
      return null;
    }

    SingleLocaleLabelsManager i18n=I18nFacade.getLabelsMgr("enum-"+implClass.getSimpleName());
    long now=System.currentTimeMillis();
    NamedNodeMap rootAttrs=root.getAttributes();
    int id=DOMParsingTools.getIntAttribute(rootAttrs,EnumXMLConstants.ENUM_ID_ATTR,0);
    // Name
    String enumName=DOMParsingTools.getStringAttribute(rootAttrs,EnumXMLConstants.ENTRY_NAME_ATTR,"");
    LotroEnum<T> ret=new LotroEnum<T>(id,enumName,implClass);
    List<Element> entryTags=DOMParsingTools.getChildTagsByName(root,EnumXMLConstants.ENTRY_TAG);
    for(Element entryTag : entryTags)
    {
      NamedNodeMap attrs=entryTag.getAttributes();
      // Code
      int code=DOMParsingTools.getIntAttribute(attrs,EnumXMLConstants.ENTRY_CODE_ATTR,0);
      // Key
      String key=DOMParsingTools.getStringAttribute(attrs,EnumXMLConstants.ENTRY_KEY_ATTR,null);
      // Name
      String i18nKey=DOMParsingTools.getStringAttribute(attrs,EnumXMLConstants.ENTRY_NAME_ATTR,"");
      String name=i18nKey;
      if (i18n!=null)
      {
        name=i18n.getLabel(i18nKey);
        if (name==null)
        {
          name=i18nKey;
        }
      }
      T entry=ret.buildEntryInstance(code,key,name);
      ret.registerEntry(entry);
    }
    long now2=System.currentTimeMillis();
    long duration=now2-now;
    PerfUtils.showLoadedLog("enum "+ret.getName(),duration);
    return ret;
  }
}
