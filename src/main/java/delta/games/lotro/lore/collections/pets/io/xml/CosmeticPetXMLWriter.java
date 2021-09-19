package delta.games.lotro.lore.collections.pets.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.lore.agents.EntityClassification;
import delta.games.lotro.lore.agents.io.xml.AgentsXMLIO;
import delta.games.lotro.lore.collections.pets.CosmeticPetDescription;

/**
 * Writes pets to XML files.
 * @author DAM
 */
public class CosmeticPetXMLWriter
{
  /**
   * Write some pets to a XML file.
   * @param toFile File to write to.
   * @param pets Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean write(File toFile, final List<CosmeticPetDescription> pets)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",CosmeticPetXMLConstants.PETS_TAG,new AttributesImpl());
        for(CosmeticPetDescription pet : pets)
        {
          writePet(hd,pet);
        }
        hd.endElement("","",CosmeticPetXMLConstants.PETS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writePet(TransformerHandler hd, CosmeticPetDescription pet) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=pet.getIdentifier();
    attrs.addAttribute("","",CosmeticPetXMLConstants.PET_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=pet.getName();
    attrs.addAttribute("","",CosmeticPetXMLConstants.PET_NAME_ATTR,XmlWriter.CDATA,name);
    // Initial name
    String initialName=pet.getInitialName();
    attrs.addAttribute("","",CosmeticPetXMLConstants.PET_INITIAL_NAME_ATTR,XmlWriter.CDATA,initialName);
    // Description
    String description=pet.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",CosmeticPetXMLConstants.PET_DESCRIPTION_ATTR,XmlWriter.CDATA,String.valueOf(description));
    }
    // Source Description
    String sourceDescription=pet.getSourceDescription();
    if (sourceDescription.length()>0)
    {
      attrs.addAttribute("","",CosmeticPetXMLConstants.PET_SOURCE_DESCRIPTION_ATTR,XmlWriter.CDATA,String.valueOf(sourceDescription));
    }
    // Icon ID
    int iconId=pet.getIconId();
    attrs.addAttribute("","",CosmeticPetXMLConstants.PET_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    EntityClassification classification=pet.getClassification();
    // Entity Classification
    AgentsXMLIO.writeEntityClassification(attrs,classification);
    hd.startElement("","",CosmeticPetXMLConstants.PET_TAG,attrs);
    hd.endElement("","",CosmeticPetXMLConstants.PET_TAG);
  }
}
