package delta.games.lotro.lore.items.details.io.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import delta.common.utils.NumericTools;
import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.details.GrantType;
import delta.games.lotro.lore.items.details.GrantedElement;

/**
 * SAX parser for item details.
 * @author DAM
 */
public class ItemDetailsSaxParser
{
  /**
   * Handle an element start. 
   * @param item Parent item.
   * @param qualifiedName Tag name.
   * @param attributes Attributes.
   * @return <code>true</code> if tag was used, <code>false</code> otherwise.
   * @throws SAXException If an error occurs.
   */
  public boolean startElement(Item item, String qualifiedName, Attributes attributes) throws SAXException
  {
    if (ItemDetailsXMLConstants.GRANTS_TAG.equals(qualifiedName))
    {
      // Type
      String typeStr=attributes.getValue(ItemDetailsXMLConstants.GRANTS_TYPE_ATTR);
      GrantType type=GrantType.valueOf(typeStr);
      // ID
      String idStr=attributes.getValue(ItemDetailsXMLConstants.GRANTS_ID_ATTR);
      int id=NumericTools.parseInt(idStr,-1);
      GrantedElement<?> grantedElement=buildGrantedElement(id,type);
      if (grantedElement!=null)
      {
        Item.addDetail(item,grantedElement);
        return true;
      }
    }
    return false;
  }

  private GrantedElement<?> buildGrantedElement(int id, GrantType type)
  {
    switch(type)
    {
      case SHORT_MOUNT:
      case TALL_MOUNT:
      case SKILL:
      {
        SkillDescription skill=SkillsManager.getInstance().getSkill(id);
        if (skill!=null)
        {
          return new GrantedElement<SkillDescription>(type,skill);
        }
      }
      break;
      case TRAIT:
      {
        TraitDescription trait=TraitsManager.getInstance().getTrait(id);
        if (trait!=null)
        {
          return new GrantedElement<TraitDescription>(type,trait);
        }
      }
      break;
      default:
    }
    return null;
  }
}
