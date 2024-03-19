package delta.games.lotro.character.stats.buffs;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.buffs.io.xml.BuffsXMLConstants;
import delta.games.lotro.character.stats.buffs.io.xml.BuffsXMLParser;
import delta.games.lotro.character.stats.buffs.io.xml.RawBuffStorage;
import delta.games.lotro.character.status.traitTree.BuffsManagerToTraitTreeStatus;
import delta.games.lotro.character.status.traitTree.TraitTreeStatus;
import delta.games.lotro.character.status.traits.TraitsStatus;
import delta.games.lotro.character.status.traits.racial.BuffsManagerToRacialTraitsStatus;
import delta.games.lotro.character.status.traits.shared.TraitSlotsStatus;

/**
 * I/O methods for buffs.
 * @author DAM
 */
public class BuffsIO
{
  private static final Logger LOGGER=Logger.getLogger(BuffsIO.class);

  /**
   * Load buffs (and possibly traits) from XML.
   * @param root 
   * @param c
   */
  public static void loadBuffs(Element root, CharacterData c)
  {
    // Load raw buffs
    Element buffsTag=DOMParsingTools.getChildTagByName(root,BuffsXMLConstants.BUFFS_TAG);
    RawBuffStorage buffs=BuffsXMLParser.parseBuffs(buffsTag);
    TraitsStatus traitsStatus=c.getTraits();
    // Initialize the trait tree
    TraitTreeStatus traitTreeStatus=traitsStatus.getTraitTreeStatus();
    if (traitTreeStatus==null)
    {
      TraitTreeStatus traitTree=BuffsManagerToTraitTreeStatus.initFromBuffs(c.getCharacterClass(),buffs);
      traitsStatus.setTraitTreeStatus(traitTree);
    }
    // Initialize the racial traits status
    TraitSlotsStatus racialTraits=traitsStatus.getRacialTraitsStatus();
    if (racialTraits==null)
    {
      racialTraits=BuffsManagerToRacialTraitsStatus.initFromBuffs(c.getRace(),buffs);
      traitsStatus.setRacialTraitsStatus(racialTraits);
    }
    // Initialize other buffs
    initBuffs(buffs,c.getBuffs());
  }

  private static void initBuffs(RawBuffStorage rawBuffs, BuffsManager buffs)
  {
    BuffRegistry registry=BuffRegistry.getInstance();
    int nbBuffs=rawBuffs.getSize();
    for(int i=0;i<nbBuffs;i++)
    {
      String buffID=rawBuffs.getBuffID(i);
      Integer tier=rawBuffs.getTier(i);
      BuffInstance buffInstance=registry.newBuffInstance(buffID);
      if (buffInstance!=null)
      {
        buffInstance.setTier(tier);
        buffs.addBuff(buffInstance);
      }
      else
      {
        LOGGER.warn("Buff not found: "+buffID);
      }
    }
  }
}
