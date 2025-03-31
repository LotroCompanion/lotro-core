package delta.games.lotro.lore.items.scaling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.reputation.FactionStatus;
import delta.games.lotro.character.status.reputation.ReputationStatus;
import delta.games.lotro.character.status.virtues.SingleVirtueStatus;
import delta.games.lotro.character.status.virtues.VirtuesStatus;
import delta.games.lotro.character.status.virtues.io.VirtuesStatusIO;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.reputation.Faction;
import delta.games.lotro.lore.reputation.FactionsRegistry;
import delta.games.lotro.utils.maths.Progression;

/**
 * Resolves item spellcraft values using context.
 * @author DAM
 */
public class SpellcraftResolver
{
  private static final Logger LOGGER=LoggerFactory.getLogger(SpellcraftResolver.class);

  private CharacterFile _toon;
  private CharacterData _data;

  /**
   * Constructor.
   * @param toon Character context.
   * @param data Character data context.
   */
  public SpellcraftResolver(CharacterFile toon, CharacterData data)
  {
    _toon=toon;
    _data=data;
  }

  /**
   * Get the effective spellcraft for an item, using the context for this resolver.
   * @param item Item to use.
   * @return A value or <code>null</code> if not found.
   */
  public Integer getEffectiveSpellcraft(Item item)
  {
    ItemSpellcraft spellcraftSpec=item.getSpellcraft();
    if (spellcraftSpec==null)
    {
      return null;
    }
    String propertyName=spellcraftSpec.getPropertyName();
    Integer spellcraft=resolveSpellcraftByProperty(propertyName);
    if (spellcraft==null)
    {
      return null;
    }
    Progression progression=spellcraftSpec.getProgression();
    if (progression!=null)
    {
      Float value=progression.getValue(spellcraft.intValue());
      spellcraft=(value!=null)?Integer.valueOf(value.intValue()):null;
    }
    LOGGER.debug("Resolved spellcraft {} to {}",spellcraftSpec,spellcraft);
    return spellcraft;
  }

  private Integer resolveSpellcraftByProperty(String propertyName)
  {
    Integer ret=null;
    if ("Advancement_Level".equals(propertyName))
    {
      ret=Integer.valueOf(getLevel());
    }
    else if ("Apparent_Level".equals(propertyName))
    {
      ret=Integer.valueOf(getLevel());
    }
    else if ("Glory_GloryRank".equals(propertyName))
    {
      ret=getGloryRank();
    }
    else if ("Reputation_Faction_Mirkwood_Offensive_CurrentTier".equals(propertyName))
    {
      ret=getReputationTier(propertyName);
    }
    else if ("Reputation_Faction_Wildermore_Basic_CurrentTier".equals(propertyName))
    {
      ret=getReputationTier(propertyName);
    }
    else if ("Trait_Virtue_Rank_Charity".equals(propertyName))
    {
      ret=getVirtueRank(propertyName);
    }
    else
    {
      LOGGER.warn("Unmanaged spellcraft property: {}",propertyName);
    }
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Resolved spellcraft property {} to {}",propertyName,ret);
    }
    return ret;
  }

  private int getLevel()
  {
    if (_data!=null)
    {
      return _data.getLevel();
    }
    if (_toon!=null)
    {
      return _toon.getSummary().getLevel();
    }
    return 1;
  }

  private Integer getGloryRank()
  {
    if (_toon==null)
    {
      return null;
    }
    Integer rankCode=_toon.getSummary().getRankCode();
    if (rankCode==null)
    {
      rankCode=Integer.valueOf(0); // Assume unranked
    }
    return rankCode;
  }

  private Integer getReputationTier(String propertyName)
  {
    if (_toon==null)
    {
      return null;
    }
    ReputationStatus status=_toon.getReputation();
    for(Faction faction : FactionsRegistry.getInstance().getAll())
    {
      String tierPropertyName=faction.getCurrentTierPropertyName();
      if (propertyName.equals(tierPropertyName))
      {
        FactionStatus factionStatus=status.getOrCreateFactionStat(faction);
        int tier=factionStatus.getFactionLevel().getTier();
        return Integer.valueOf(tier);
      }
    }
    return null;
  }

  private Integer getVirtueRank(String propertyName)
  {
    VirtueDescription virtue=getVirtueByProperty(propertyName);
    if (virtue==null)
    {
      return null;
    }
    if (_data!=null)
    {
      int rank=_data.getVirtues().getVirtueRank(virtue);
      return Integer.valueOf(rank);
    }
    if (_toon!=null)
    {
      VirtuesStatus status=VirtuesStatusIO.load(_toon);
      SingleVirtueStatus virtueStatus=status.getVirtueStatus(virtue);
      int rank=virtueStatus.getTier();
      return Integer.valueOf(rank);
    }
    return null;
  }

  private VirtueDescription getVirtueByProperty(String propertyName)
  {
    for(VirtueDescription virtue : VirtuesManager.getInstance().getAll())
    {
      String virtueTierPropertyName=virtue.getRankStatKey();
      if (propertyName.equals(virtueTierPropertyName))
      {
        return virtue;
      }
    }
    return null;
  }
}
