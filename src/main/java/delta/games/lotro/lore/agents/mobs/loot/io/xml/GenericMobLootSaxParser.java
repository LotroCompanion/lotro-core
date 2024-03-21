package delta.games.lotro.lore.agents.mobs.loot.io.xml;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import delta.common.utils.NumericTools;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.MobType;
import delta.games.lotro.common.enums.Species;
import delta.games.lotro.common.enums.SubSpecies;
import delta.games.lotro.common.treasure.LootsManager;
import delta.games.lotro.common.treasure.TreasureList;
import delta.games.lotro.common.treasure.TrophyList;
import delta.games.lotro.lore.agents.mobs.loot.GenericMobLootEntry;
import delta.games.lotro.lore.agents.mobs.loot.GenericMobLootSpec;
import delta.games.lotro.lore.agents.mobs.loot.SpeciesLoot;
import delta.games.lotro.lore.agents.mobs.loot.SpeciesLootsManager;
import delta.games.lotro.lore.agents.mobs.loot.SubSpeciesLoot;

/**
 * SAX parser for generic mob loot.
 * @author DAM
 */
public final class GenericMobLootSaxParser extends DefaultHandler
{
  private static final Logger LOGGER=Logger.getLogger(GenericMobLootSaxParser.class);

  /**
   * "Unexpected tag" error message.
   */
  private static final String UNEXPECTED_TAG_ERROR_MESSAGE="Unexpected tag: ";

  private Deque<DefaultHandler> _handlers;
  private SpeciesLootsManager _data;

  private GenericMobLootSaxParser()
  {
    _handlers=new ArrayDeque<DefaultHandler>();
    _handlers.push(new RootParser());
  }

  /**
   * Parse the XML file.
   * @param source Source file.
   * @return loaded data.
   */
  public static SpeciesLootsManager parseFile(File source)
  {
    try
    {
      GenericMobLootSaxParser handler=new GenericMobLootSaxParser();

      // Use the default (non-validating) parser
      SAXParserFactory factory=SAXParserFactory.newInstance();
      factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      SAXParser saxParser=factory.newSAXParser();
      saxParser.parse(source,handler);
      saxParser.reset();
      return handler._data;
    }
    catch (Exception e)
    {
      LOGGER.error("Error when loading species loot file "+source,e);
    }
    return null;
  }

  @Override
  public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException
  {
    _handlers.peek().startElement(uri,localName,qualifiedName,attributes);
  }

  /**
   * Identify end of element.
   */

  @Override
  public void endElement(String uri, String localName, String qualifiedName) throws SAXException
  {
    _handlers.peek().endElement(uri,localName,qualifiedName);
  }

  /**
   * Initial parser.
   * @author DAM
   */
  public final class RootParser extends DefaultHandler
  {
    @Override
    public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException
    {
      if (GenericMobLootXMLConstants.GENERIC_MOB_LOOT_TAG.equals(qualifiedName))
      {
        _data=new SpeciesLootsManager();
      }
      else if (GenericMobLootXMLConstants.SPECIES_TAG.equals(qualifiedName))
      {
        DefaultHandler next=new SpeciesLootParser();
        _handlers.push(next);
        next.startElement(uri,localName,qualifiedName,attributes);
      }
      else
      {
        throw new SAXException(UNEXPECTED_TAG_ERROR_MESSAGE+qualifiedName);
      }
    }

    @Override
    public void endElement(String uri, String localName, String qualifiedName)
    {
      _handlers.pop();
    }
  }

  /**
   * SAX parser for a species loot.
   * @author DAM
   */
  public final class SpeciesLootParser extends DefaultHandler
  {
    private SpeciesLoot _speciesLoot;

    @Override
    public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException
    {
      if (GenericMobLootXMLConstants.SPECIES_TAG.equals(qualifiedName))
      {
        // Category
        String speciesCodeStr=attributes.getValue(GenericMobLootXMLConstants.SPECIES_CODE_ATTR);
        LotroEnum<Species> speciesMgr=LotroEnumsRegistry.getInstance().get(Species.class);
        Species species=speciesMgr.getEntry(NumericTools.parseInt(speciesCodeStr,0));
        _speciesLoot=new SpeciesLoot(species);
      }
      else if (GenericMobLootXMLConstants.SUBSPECIES_TAG.equals(qualifiedName))
      {
        SubSpeciesLootParser parser=new SubSpeciesLootParser(_speciesLoot);
        _handlers.push(parser);
        parser.startElement(uri,localName,qualifiedName,attributes);
      }
      else
      {
        throw new SAXException(UNEXPECTED_TAG_ERROR_MESSAGE+qualifiedName);
      }
    }

    @Override
    public void endElement(String uri, String localName, String qualifiedName)
    {
      if (GenericMobLootXMLConstants.SPECIES_TAG.equals(qualifiedName))
      {
        GenericMobLootSaxParser.this._data.addSpeciesLoot(_speciesLoot);
        _speciesLoot=null;
      }
      _handlers.pop();
    }
  }

  /**
   * SAX parser for a subspecies loot.
   * @author DAM
   */
  public final class SubSpeciesLootParser extends DefaultHandler
  {
    private SpeciesLoot _parent;
    private SubSpeciesLoot _subSpeciesLoot;

    private SubSpeciesLootParser(SpeciesLoot parent)
    {
      _parent=parent;
    }

    @Override
    public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException
    {
      if (GenericMobLootXMLConstants.SUBSPECIES_TAG.equals(qualifiedName))
      {
        String subSpeciesCodeStr=attributes.getValue(GenericMobLootXMLConstants.SUBSPECIES_CODE_ATTR);
        LotroEnum<SubSpecies> subSpeciesMgr=LotroEnumsRegistry.getInstance().get(SubSpecies.class);
        SubSpecies subSpecies=subSpeciesMgr.getEntry(NumericTools.parseInt(subSpeciesCodeStr,0));
        _subSpeciesLoot=new SubSpeciesLoot(_parent.getSpecies(),subSpecies);
      }
      else if (GenericMobLootXMLConstants.MOB_TYPE_TAG.equals(qualifiedName))
      {
        MobTypeLootParser parser=new MobTypeLootParser(_subSpeciesLoot);
        _handlers.push(parser);
        parser.startElement(uri,localName,qualifiedName,attributes);
      }
      else
      {
        throw new SAXException(UNEXPECTED_TAG_ERROR_MESSAGE+qualifiedName);
      }
    }

    @Override
    public void endElement(String uri, String localName, String qualifiedName)
    {
      if (GenericMobLootXMLConstants.SUBSPECIES_TAG.equals(qualifiedName))
      {
        _parent.addSubSpeciesLoot(_subSpeciesLoot);
        _subSpeciesLoot=null;
        _handlers.pop();
      }
    }
  }

  /**
   * SAX parser for a mob type loot.
   * @author DAM
   */
  public final class MobTypeLootParser extends DefaultHandler
  {
    private SubSpeciesLoot _parent;
    private GenericMobLootSpec _mobTypeLoot;

    private MobTypeLootParser(SubSpeciesLoot parent)
    {
      _parent=parent;
    }

    @Override
    public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException
    {
      if (GenericMobLootXMLConstants.MOB_TYPE_TAG.equals(qualifiedName))
      {
        String mobTypeCodeStr=attributes.getValue(GenericMobLootXMLConstants.MOB_TYPE_CODE_ATTR);
        LotroEnum<MobType> mobTypeMgr=LotroEnumsRegistry.getInstance().get(MobType.class);
        MobType mobType=mobTypeMgr.getEntry(NumericTools.parseInt(mobTypeCodeStr,0));
        _mobTypeLoot=new GenericMobLootSpec(_parent.getSpecies(),_parent.getSubSpecies(),mobType);
      }
      else if (GenericMobLootXMLConstants.LOOT_ENTRY_TAG.equals(qualifiedName))
      {
        GenericMobLootEntry entry=buildEntry(attributes);
        _mobTypeLoot.addLevelEntry(entry);
      }
      else
      {
        throw new SAXException(UNEXPECTED_TAG_ERROR_MESSAGE+qualifiedName);
      }
    }

    private GenericMobLootEntry buildEntry(Attributes attributes)
    {
      String levelStr=attributes.getValue(GenericMobLootXMLConstants.LEVEL_ATTR);
      int level=NumericTools.parseInt(levelStr,1);
      GenericMobLootEntry entry=new GenericMobLootEntry(level);
      LootsManager lootsMgr=LootsManager.getInstance();
      // Treasure list
      String treasureListStr=attributes.getValue(GenericMobLootXMLConstants.TREASURE_LIST_ATTR);
      int treasureListId=NumericTools.parseInt(treasureListStr,0);
      if (treasureListId!=0)
      {
        TreasureList treasureList=(TreasureList)lootsMgr.getTables().getItem(treasureListId);
        entry.setTreasureList(treasureList);
      }
      // Trophy list
      String trophyListStr=attributes.getValue(GenericMobLootXMLConstants.TROPHY_LIST_ATTR);
      int trophyListId=NumericTools.parseInt(trophyListStr,0);
      if (trophyListId!=0)
      {
        TrophyList trophyList=(TrophyList)lootsMgr.getTables().getItem(trophyListId);
        entry.setTrophyList(trophyList);
      }
      return entry;
    }

    @Override
    public void endElement(String uri, String localName, String qualifiedName)
    {
      if (GenericMobLootXMLConstants.MOB_TYPE_TAG.equals(qualifiedName))
      {
        _parent.addMobTypeLoot(_mobTypeLoot);
        _mobTypeLoot=null;
        _handlers.pop();
      }
      else if (GenericMobLootXMLConstants.LOOT_ENTRY_TAG.equals(qualifiedName))
      {
        // Nothing!
      }
    }
  }
}
