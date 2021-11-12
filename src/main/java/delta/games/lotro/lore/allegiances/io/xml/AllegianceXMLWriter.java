package delta.games.lotro.lore.allegiances.io.xml;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.lore.allegiances.AllegianceDescription;
import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Writes LOTRO allegiances to XML files.
 * @author DAM
 */
public class AllegianceXMLWriter
{
  /**
   * Write a file with allegiances.
   * @param toFile Output file.
   * @param allegiances Allegiances to write.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeAllegiancesFile(File toFile, List<AllegianceDescription> allegiances)
  {
    AllegianceXMLWriter writer=new AllegianceXMLWriter();
    Collections.sort(allegiances,new IdentifiableComparator<AllegianceDescription>());
    boolean ok=writer.writeAllegiances(toFile,allegiances,EncodingNames.UTF_8);
    return ok;
  }

  /**
   * Write allegiances to a XML file.
   * @param outFile Output file.
   * @param allegiances Allegiances to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean writeAllegiances(File outFile, final List<AllegianceDescription> allegiances, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeAllegiances(hd,allegiances);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private void writeAllegiances(TransformerHandler hd, List<AllegianceDescription> allegiances) throws Exception
  {
    hd.startElement("","",AllegianceXMLConstants.ALLEGIANCES_TAG,new AttributesImpl());
    for(AllegianceDescription allegiance : allegiances)
    {
      writeAllegiance(hd,allegiance);
    }
    hd.endElement("","",AllegianceXMLConstants.ALLEGIANCES_TAG);
  }

  private void writeAllegiance(TransformerHandler hd, AllegianceDescription allegiance) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // In-game identifier
    int id=allegiance.getIdentifier();
    if (id!=0)
    {
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    }
    // Name
    String name=allegiance.getName();
    if (name.length()>0)
    {
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_NAME_ATTR,XmlWriter.CDATA,name);
    }
    // Icon identifier
    int iconId=allegiance.getIconId();
    if (iconId!=0)
    {
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_ICON_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    }
    // Group
    String group=allegiance.getGroup();
    if (group.length()>0)
    {
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_GROUP_ATTR,XmlWriter.CDATA,group);
    }
    // Min level
    Integer minLevel=allegiance.getMinLevel();
    if (minLevel!=null)
    {
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_MIN_LEVEL_ATTR,XmlWriter.CDATA,minLevel.toString());
    }
    // Travel skill
    SkillDescription travelSkill=allegiance.getTravelSkill();
    if (travelSkill!=null)
    {
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_TRAVEL_SKILL_ID_ATTR,XmlWriter.CDATA,String.valueOf(travelSkill.getIdentifier()));
    }
    // Description
    String description=allegiance.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",AllegianceXMLConstants.ALLEGIANCE_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    hd.startElement("","",AllegianceXMLConstants.ALLEGIANCE_TAG,attrs);
    // Deeds
    for(DeedDescription deed : allegiance.getDeeds())
    {
      AttributesImpl deedAttrs=new AttributesImpl();
      // ID
      int deedId=deed.getIdentifier();
      deedAttrs.addAttribute("","",AllegianceXMLConstants.DEED_ID_ATTR,XmlWriter.CDATA,String.valueOf(deedId));
      // Name
      String deedName=deed.getName();
      deedAttrs.addAttribute("","",AllegianceXMLConstants.DEED_NAME_ATTR,XmlWriter.CDATA,deedName);
      hd.startElement("","",AllegianceXMLConstants.DEED_TAG,deedAttrs);
      hd.endElement("","",AllegianceXMLConstants.DEED_TAG);
    }
    hd.endElement("","",AllegianceXMLConstants.ALLEGIANCE_TAG);
  }
}
