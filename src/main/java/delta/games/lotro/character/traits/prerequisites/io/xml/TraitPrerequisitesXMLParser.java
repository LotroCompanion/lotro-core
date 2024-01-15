package delta.games.lotro.character.traits.prerequisites.io.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.character.traits.prerequisites.AbstractTraitPrerequisite;
import delta.games.lotro.character.traits.prerequisites.CompoundTraitPrerequisite;
import delta.games.lotro.character.traits.prerequisites.SimpleTraitPrerequisite;
import delta.games.lotro.character.traits.prerequisites.TraitLogicOperator;
import delta.games.lotro.utils.Proxy;

/**
 * Parser for trait pre-requisites stored in XML.
 * @author DAM
 */
public class TraitPrerequisitesXMLParser
{
  private static final Logger LOGGER=Logger.getLogger(TraitPrerequisitesXMLParser.class);

  private List<Proxy<TraitDescription>> _toUpdate;

  /**
   * Constructor.
   */
  public TraitPrerequisitesXMLParser()
  {
    _toUpdate=new ArrayList<Proxy<TraitDescription>>();
  }

  /**
   * Build a trait pre-requisite from an XML tag.
   * @param root Root XML tag.
   * @return A trait pre-requisite.
   */
  public AbstractTraitPrerequisite parsePrerequisite(Element root)
  {
    String tagName=root.getTagName();
    AbstractTraitPrerequisite ret=null;
    if (TraitPrerequisitesXMLConstants.SIMPLE_PREREQUISITE_TAG.equals(tagName))
    {
      ret=parseSimplePrerequisite(root);
    }
    else if (TraitPrerequisitesXMLConstants.COMPOUND_PREREQUISITE_TAG.equals(tagName))
    {
      ret=parseCompoundPrerequisite(root);
    }
    return ret;
  }

  private SimpleTraitPrerequisite parseSimplePrerequisite(Element root)
  {
    SimpleTraitPrerequisite ret=new SimpleTraitPrerequisite();
    NamedNodeMap attrs=root.getAttributes();
    // ID
    int id=DOMParsingTools.getIntAttribute(attrs,TraitPrerequisitesXMLConstants.ID_ATTR,0);
    if (id>0)
    {
      Proxy<TraitDescription> proxy=ret.getTraitProxy();
      proxy.setId(id);
      _toUpdate.add(proxy);
    }
    return ret;
  }

  /**
   * Build a compound trait pre-requisite from an XML tag.
   * @param root Root XML tag.
   * @return A compound trait pre-requisite.
   */
  public CompoundTraitPrerequisite parseCompoundPrerequisite(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Operator
    String operatorStr=DOMParsingTools.getStringAttribute(attrs,TraitPrerequisitesXMLConstants.OPERATOR_ATTR,null);
    TraitLogicOperator operator=TraitLogicOperator.valueOf(operatorStr);
    CompoundTraitPrerequisite ret=new CompoundTraitPrerequisite(operator);
    // Child pre-requisites
    for(Element childTag : DOMParsingTools.getChildTags(root))
    {
      AbstractTraitPrerequisite childPrerequisite=parsePrerequisite(childTag);
      ret.addPrerequisite(childPrerequisite);
    }
    return ret;
  }

  /**
   * Resolve proxies.
   */
  public void resolveProxies()
  {
    TraitsManager traitsMgr=TraitsManager.getInstance();
    for(Proxy<TraitDescription> proxy : _toUpdate)
    {
      int id=proxy.getId();
      TraitDescription trait=traitsMgr.getTrait(id);
      if (trait!=null)
      {
        proxy.setName(trait.getName());
        proxy.setObject(trait);
      }
      else
      {
        LOGGER.warn("Could not resolve trait ID="+id);
      }
    }
  }
}
