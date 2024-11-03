package delta.games.lotro.character.skills.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SingleTypeSkillEffectsManager;
import delta.games.lotro.character.skills.SkillEffectsManager;
import delta.games.lotro.character.skills.TravelSkill;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.enums.MountType;
import delta.games.lotro.common.enums.SkillCategory;
import delta.games.lotro.common.enums.SkillCharacteristicSubCategory;
import delta.games.lotro.common.enums.TravelLink;
import delta.games.lotro.common.geo.Position;
import delta.games.lotro.common.geo.io.xml.PositionXMLWriter;
import delta.games.lotro.lore.agents.io.xml.AgentsXMLIO;
import delta.games.lotro.lore.collections.mounts.MountDescription;
import delta.games.lotro.lore.collections.pets.CosmeticPetDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Writes skills to XML files.
 * @author DAM
 */
public class SkillDescriptionXMLWriter
{
  /**
   * Write some skills to a XML file.
   * @param toFile File to write to.
   * @param skills Data to save.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File toFile, final List<SkillDescription> skills)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        hd.startElement("","",SkillDescriptionXMLConstants.SKILLS_TAG,new AttributesImpl());
        for(SkillDescription skill : skills)
        {
          writeSkill(hd,skill);
        }
        hd.endElement("","",SkillDescriptionXMLConstants.SKILLS_TAG);
      }
    };
    boolean ret=helper.write(toFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private void writeSkill(TransformerHandler hd, SkillDescription skill) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=skill.getIdentifier();
    attrs.addAttribute("","",SkillDescriptionXMLConstants.SKILL_IDENTIFIER_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=skill.getName();
    attrs.addAttribute("","",SkillDescriptionXMLConstants.SKILL_NAME_ATTR,XmlWriter.CDATA,name);
    // Category
    SkillCategory category=skill.getCategory();
    if (category!=null)
    {
      attrs.addAttribute("","",SkillDescriptionXMLConstants.SKILL_CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(category.getCode()));
    }
    // Icon ID
    int iconId=skill.getIconId();
    attrs.addAttribute("","",SkillDescriptionXMLConstants.SKILL_ICON_ID_ATTR,XmlWriter.CDATA,String.valueOf(iconId));
    // Travel?
    if (skill instanceof TravelSkill)
    {
      TravelSkill travelSkill=(TravelSkill)skill;
      TravelLink type=travelSkill.getType();
      if (type!=null)
      {
        attrs.addAttribute("","",SkillDescriptionXMLConstants.SKILL_TRAVEL_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(type.getCode()));
      }
    }
    if (skill instanceof MountDescription)
    {
      MountDescription mount=(MountDescription)skill;
      writeMountAttrs(mount,attrs);
    }
    if (skill instanceof CosmeticPetDescription)
    {
      CosmeticPetDescription pet=(CosmeticPetDescription)skill;
      writePetAttrs(pet,attrs);
    }
    // Description
    String description=skill.getDescription();
    if (description.length()>0)
    {
      attrs.addAttribute("","",SkillDescriptionXMLConstants.SKILL_DESCRIPTION_ATTR,XmlWriter.CDATA,description);
    }
    String tagName=getTagName(skill);
    hd.startElement("","",tagName,attrs);
    // Position (for travel skills)
    if (skill instanceof TravelSkill)
    {
      TravelSkill travelSkill=(TravelSkill)skill;
      // Position
      Position position=travelSkill.getPosition();
      if (position!=null)
      {
        PositionXMLWriter.writePosition(hd,position);
      }
    }
    // Requirements
    writeSkillRequirements(hd,skill);
    // Effects
    SkillEffectsManager effectsMgr=skill.getEffects();
    if (effectsMgr!=null)
    {
      List<SingleTypeSkillEffectsManager> mgrs=effectsMgr.getAll();
      if (!mgrs.isEmpty())
      {
        SkillEffectsXmlIO.writeSkillEffects(hd,effectsMgr);
      }
    }
    hd.endElement("","",tagName);
  }

  private void writeSkillRequirements(TransformerHandler hd, SkillDescription skill) throws SAXException
  {
    Proxy<TraitDescription> requiredTrait=skill.getRequiredTrait();
    if (requiredTrait!=null)
    {
      writeTraitRequirement(hd,requiredTrait);
    }
    List<Effect> requiredEffects=skill.getRequiredEffects();
    if (requiredEffects!=null)
    {
      for(Effect requiredEffect : requiredEffects)
      {
        writeEffectRequirement(hd,requiredEffect);
      }
    }
  }

  private void writeTraitRequirement(TransformerHandler hd, Proxy<TraitDescription> trait) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=trait.getId();
    attrs.addAttribute("","",SkillDescriptionXMLConstants.REQUIRED_TRAIT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=trait.getName();
    attrs.addAttribute("","",SkillDescriptionXMLConstants.REQUIRED_TRAIT_NAME_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",SkillDescriptionXMLConstants.REQUIRED_TRAIT_TAG,attrs);
    hd.endElement("","",SkillDescriptionXMLConstants.REQUIRED_TRAIT_TAG);
  }

  private void writeEffectRequirement(TransformerHandler hd, Effect effect) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=effect.getIdentifier();
    attrs.addAttribute("","",SkillDescriptionXMLConstants.REQUIRED_EFFECT_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=effect.getName();
    if (name!=null)
    {
      attrs.addAttribute("","",SkillDescriptionXMLConstants.REQUIRED_EFFECT_NAME_ATTR,XmlWriter.CDATA,name);
    }
    hd.startElement("","",SkillDescriptionXMLConstants.REQUIRED_EFFECT_TAG,attrs);
    hd.endElement("","",SkillDescriptionXMLConstants.REQUIRED_EFFECT_TAG);
  }

  /**
   * Write mount attributes to the given storage.
   * @param mount Mount to use.
   * @param attrs Storage.
   */
  private void writeMountAttrs(MountDescription mount,AttributesImpl attrs)
  {
    // Initial name
    String initialName=mount.getInitialName();
    attrs.addAttribute("","",MountXMLConstants.MOUNT_INITIAL_NAME_ATTR,XmlWriter.CDATA,initialName);
    // Category
    SkillCharacteristicSubCategory category=mount.getMountCategory();
    if (category!=null)
    {
      int categoryCode=category.getCode();
      attrs.addAttribute("","",MountXMLConstants.MOUNT_CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(categoryCode));
    }
    // Mount type
    MountType mountType=mount.getMountType();
    if (mountType!=null)
    {
      int mountTypeCode=mountType.getCode();
      attrs.addAttribute("","",MountXMLConstants.MOUNT_MOUNT_TYPE_ATTR,XmlWriter.CDATA,String.valueOf(mountTypeCode));
    }
    // Source Description
    String sourceDescription=mount.getSourceDescription();
    if (sourceDescription.length()>0)
    {
      attrs.addAttribute("","",MountXMLConstants.MOUNT_SOURCE_DESCRIPTION_ATTR,XmlWriter.CDATA,String.valueOf(sourceDescription));
    }
    // Morale
    int morale=mount.getMorale();
    attrs.addAttribute("","",MountXMLConstants.MOUNT_MORALE_ATTR,XmlWriter.CDATA,String.valueOf(morale));
    // Speed
    float speed=mount.getSpeed();
    attrs.addAttribute("","",MountXMLConstants.MOUNT_SPEED_ATTR,XmlWriter.CDATA,String.valueOf(speed));
    // Tall
    boolean tall=mount.isTall();
    if (tall)
    {
      attrs.addAttribute("","",MountXMLConstants.MOUNT_TALL_ATTR,XmlWriter.CDATA,String.valueOf(tall));
    }
    // Peer Mount ID
    int peerMountId=mount.getPeerMountId();
    if (peerMountId!=0)
    {
      attrs.addAttribute("","",MountXMLConstants.MOUNT_PEER_ID_ATTR,XmlWriter.CDATA,String.valueOf(peerMountId));
    }
  }

  /**
   * Write pet attributes to the given storage.
   * @param pet Pet to use.
   * @param attrs Storage.
   */
  private void writePetAttrs(CosmeticPetDescription pet,AttributesImpl attrs)
  {
    // Initial name
    String initialName=pet.getInitialName();
    attrs.addAttribute("","",CosmeticPetXMLConstants.PET_INITIAL_NAME_ATTR,XmlWriter.CDATA,initialName);
    // Source Description
    String sourceDescription=pet.getSourceDescription();
    if (sourceDescription.length()>0)
    {
      attrs.addAttribute("","",CosmeticPetXMLConstants.PET_SOURCE_DESCRIPTION_ATTR,XmlWriter.CDATA,String.valueOf(sourceDescription));
    }
    // Entity Classification
    AgentsXMLIO.writeEntityClassification(attrs,pet.getClassification());
  }

  private String getTagName(SkillDescription skill)
  {
    if (skill instanceof MountDescription)
    {
      return MountXMLConstants.MOUNT_TAG;
    }
    if (skill instanceof CosmeticPetDescription)
    {
      return CosmeticPetXMLConstants.PET_TAG;
    }
    if (skill instanceof TravelSkill)
    {
      return SkillDescriptionXMLConstants.TRAVEL_SKILL_TAG;
    }
    return SkillDescriptionXMLConstants.SKILL_TAG;
  }
}
