package delta.games.lotro.plugins.lotrocompanion.links;

import java.io.ByteArrayInputStream;

import org.apache.log4j.Logger;

import delta.games.lotro.common.colors.ColorDescription;
import delta.games.lotro.common.colors.ColorsManager;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.id.EntityId;
import delta.games.lotro.common.id.EntityType;
import delta.games.lotro.common.id.ItemInstanceId;
import delta.games.lotro.common.money.Money;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemFactory;
import delta.games.lotro.lore.items.ItemInstance;
import delta.games.lotro.lore.items.ItemsManager;
import delta.games.lotro.lore.items.essences.EssencesSet;
import delta.games.lotro.lore.items.legendary.LegaciesManager;
import delta.games.lotro.lore.items.legendary.LegendaryInstance;
import delta.games.lotro.lore.items.legendary.LegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.PassivesManager;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacy;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.imbued.ImbuedLegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegaciesManager;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegacyTier;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegendaryInstanceAttrs;
import delta.games.lotro.lore.items.legendary.non_imbued.TieredNonImbuedLegacyInstance;
import delta.games.lotro.lore.items.legendary.relics.Relic;
import delta.games.lotro.lore.items.legendary.relics.RelicsManager;
import delta.games.lotro.lore.items.legendary.relics.RelicsSet;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitle;
import delta.games.lotro.lore.items.legendary.titles.LegendaryTitlesManager;
import delta.games.lotro.plugins.lotrocompanion.BufferUtils;

/**
 * Parser for the main data as found in LotroCompanion plugin data.
 * @author DAM
 */
public class ChatItemLinksDecoder
{
  private static final Logger LOGGER=Logger.getLogger(ChatItemLinksDecoder.class);

  /**
   * Constructor.
   */
  public ChatItemLinksDecoder()
  {
    // Nothing
  }

  /**
   * Decode a chat item link buffer.
   * @param buffer Input buffer.
   * @return An item instance.
   * @throws LinkDecodingException
   */
  public ItemInstance<? extends Item> decodeBuffer(byte[] buffer) throws LinkDecodingException
  {
    ByteArrayInputStream bis=new ByteArrayInputStream(buffer);
    int lowInstanceId=BufferUtils.readUInt32(bis);
    int highInstanceId=BufferUtils.readUInt32(bis);
    ItemInstanceId instanceId=new ItemInstanceId(lowInstanceId,highInstanceId);
    LOGGER.debug("Instance ID: "+instanceId);
    int itemId=BufferUtils.readUInt32(bis);
    LOGGER.debug("Item ID: "+itemId);
    ItemsManager itemsMgr=ItemsManager.getInstance();
    Item item=itemsMgr.getItem(itemId);
    ItemInstance<? extends Item> instance=ItemFactory.buildInstance(item);
    instance.setInstanceId(instanceId);
    boolean itemIsLegendary=(instance instanceof LegendaryInstance);
    if (itemIsLegendary)
    {
      decodeLegendary(bis,instance);
    }
    else
    {
      decodeNonLegendary(bis,instance);
    }
    return instance;
  }

  private void decodeLegendary(ByteArrayInputStream bis, ItemInstance<? extends Item> instance) throws LinkDecodingException
  {
    LegendaryInstance legendary=(LegendaryInstance)instance;
    LegendaryInstanceAttrs attrs=legendary.getLegendaryAttributes();
    // Name
    int hasName=BufferUtils.readUInt8(bis);
    if (hasName==1)
    {
      String name=decodeName(bis);
      LOGGER.debug("Name: "+name);
      attrs.setLegendaryName(name);
    }
    else
    {
      int liNameId1=BufferUtils.readUInt32(bis);
      int liNameId2=BufferUtils.readUInt32(bis);
      LOGGER.debug("Name id1="+liNameId1+", id2="+liNameId2);
    }

    BufferUtils.skip(bis,6); // Usually 1 0 0 0 1 0
    // Title
    int titleId=BufferUtils.readUInt32(bis);
    LOGGER.debug("Title: "+titleId); // 0 if no title
    if (titleId!=0)
    {
      LegendaryTitlesManager legendaryTitlesMgr=LegendaryTitlesManager.getInstance();
      LegendaryTitle legendaryTitle=legendaryTitlesMgr.getLegendaryTitle(titleId);
      attrs.setTitle(legendaryTitle);
    }

    // Legacies
    NonImbuedLegaciesManager nonImbuedMgr=NonImbuedLegaciesManager.getInstance();
    BufferUtils.skip(bis,1); // Usually 0
    int nbLegacies=BufferUtils.readUInt8(bis);
    NonImbuedLegendaryInstanceAttrs nonImbuedAttrs=attrs.getNonImbuedAttrs();
    for(int i=0;i<nbLegacies;i++)
    {
      int legacyID=BufferUtils.readUInt32(bis);
      int rank=BufferUtils.readUInt32(bis);
      NonImbuedLegacyTier legacyTier=nonImbuedMgr.getLegacyTier(legacyID);
      TieredNonImbuedLegacyInstance legacyInstance=nonImbuedAttrs.getLegacy(i);
      legacyInstance.setLegacyTier(legacyTier);
      legacyInstance.setRank(rank);
    }

    // Relics
    /*int fRelics=*/BufferUtils.readUInt8(bis); // 0 if relics, 1 if no relic
    int nbRelics=BufferUtils.readUInt8(bis);
    if (nbRelics>0)
    {
      RelicsSet relics=attrs.getRelicsSet();
      RelicsManager relicsMgr=RelicsManager.getInstance();
      for(int i=0;i<nbRelics;i++)
      {
        int relicID=BufferUtils.readUInt32(bis);
        // 1: Setting, 2: Gem, 3: Rune ; Crafted=4
        int slot=BufferUtils.readUInt32(bis);
        LOGGER.debug("Relic: ID="+relicID+", slot="+slot);
        Relic relic=relicsMgr.getById(relicID);
        if (relic!=null)
        {
          if (slot==1) relics.setSetting(relic);
          else if (slot==2) relics.setGem(relic);
          else if (slot==3) relics.setRune(relic);
          else if (slot==4) relics.setCraftedRelic(relic);
        }
      }
    }

    // Passives
    PassivesManager passivesMgr=PassivesManager.getInstance();
    int nbPassives=BufferUtils.readUInt32(bis);
    for(int i=0;i<nbPassives;i++)
    {
      int passiveId=BufferUtils.readUInt32(bis);
      LOGGER.debug("Passive: "+passiveId);
      Effect passive=passivesMgr.getEffect(passiveId);
      attrs.addPassive(passive);
    }

    int test=BufferUtils.readUInt32(bis);
    boolean imbued=false;
    if (test==268460298) // ItemAdvancement_AdvanceableWidget_Data_Array
    {
      imbued=true;
    }
    else if (test==0)
    {
      imbued=false;
    }
    else
    {
      throw new LinkDecodingException("Unexpected test value: "+test);
    }
    // If non imbued
    if (!imbued)
    {
      // Points spent / left (only for non imbued ones)
      int nbPointsLeft=BufferUtils.readUInt32(bis);
      nonImbuedAttrs.setPointsLeft(nbPointsLeft);
      int nbPointsSpent=BufferUtils.readUInt32(bis);
      nonImbuedAttrs.setPointsSpent(nbPointsSpent);
      LOGGER.debug("Left: "+nbPointsLeft+" / Spent: "+nbPointsSpent);

      // Rank of the DPS legacy
      int dpsLegacyRank=BufferUtils.readUInt32(bis); // Got 0, 62, 83, 189, 192...

      // Default legacy
      DefaultNonImbuedLegacyInstance defaultLegacy=nonImbuedAttrs.getDefaultLegacy();
      int defaultLegacyRank=BufferUtils.readUInt32(bis);
      int defaultLegacyID=BufferUtils.readUInt32(bis);
      if (defaultLegacyID!=0)
      {
        DefaultNonImbuedLegacy legacy=nonImbuedMgr.getDefaultLegacy(defaultLegacyID);
        defaultLegacy.setLegacy(legacy);
        defaultLegacy.setRank(defaultLegacyRank);
      }
      else
      {
        defaultLegacy.setRank(dpsLegacyRank);
      }
    }
    else
    {
      ImbuedLegendaryInstanceAttrs imbuedAttrs=new ImbuedLegendaryInstanceAttrs();
      attrs.setImbuedAttrs(imbuedAttrs);

      int nbImbuedLegacies=BufferUtils.readUInt32(bis);
      LOGGER.debug("Found "+nbImbuedLegacies+" legacies");
      for(int i=0;i<nbImbuedLegacies;i++)
      {
        LOGGER.debug("Legacy #"+(i+1));
        int dataStructId=BufferUtils.readUInt32(bis);
        if (dataStructId!=268460297) // 268460297=ItemAdvancement_AdvanceableWidget_Data_Struct
        {
          throw new LinkDecodingException("Expected dataStructId=268460297, got: "+dataStructId);
        }
        /*int n3=*/BufferUtils.readUInt8(bis); // Always 0

        int legacyId=-1;
        int unlockedLevels=0;
        int legacyXp=0;
        int nbProps=BufferUtils.readUInt8(bis);
        for(int j=0;j<nbProps;j++)
        {
          int propId=BufferUtils.readUInt32(bis);
          int propId2=BufferUtils.readUInt32(bis);
          if (propId!=propId2)
          {
            throw new LinkDecodingException("Decoding error: propId="+propId+", propId2="+propId2);
          }
          if (propId==268460305)
          {
            // ItemAdvancement_WidgetDID
            legacyId=BufferUtils.readUInt32(bis);
          }
          else if (propId==268460306)
          {
            // ItemAdvancement_AdvanceableWidget_UnlockedLevels
            unlockedLevels=BufferUtils.readUInt32(bis);
            LOGGER.debug("Unlocked levels: "+unlockedLevels);
          }
          else if (propId==268442976)
          {
            // ItemAdvancement_EarnedXP
            legacyXp=BufferUtils.readUInt32(bis);
            LOGGER.debug("Legacy XP: "+legacyXp);
            BufferUtils.skip(bis,4);
          }
        }
        ImbuedLegacyInstance legacy=imbuedAttrs.getLegacy(i);
        loadImbuedLegacy(legacy,legacyId);
        if (legacy!=null)
        {
          legacy.setUnlockedLevels(unlockedLevels);
          legacy.setXp(legacyXp);
        }
      }
      /*
      3E  ...`...Rn......>
      0x000001b0  03 00 00 00 00 00 00 00 00 00 00 21 00 00 00 29  ...........!...)
      0x000001c0  3A 04 70 [EF 10 00 10 00 03 C5 12 00 10 C5 12 00  :.p.............
           */
    // 29 3A 04 70 is an effect that gives Tactical HPS
      BufferUtils.skip(bis,20);
    }
    int marker=BufferUtils.readUInt32(bis); // Expected 0x100010EF
    if (marker!=0x100010EF)
    {
      throw new LinkDecodingException("Bad marker: "+marker);
    }

    decodeShared(bis,instance);
  }

  private void decodeNonLegendary(ByteArrayInputStream bis, ItemInstance<? extends Item> instance) throws LinkDecodingException
  {
    decodeShared(bis,instance);
  }

  private void decodeShared(ByteArrayInputStream bis, ItemInstance<? extends Item> itemInstance) throws LinkDecodingException
  {
    LegendaryInstanceAttrs attrs=null;
    if (itemInstance instanceof LegendaryInstance)
    {
      LegendaryInstance legendary=(LegendaryInstance)itemInstance;
      attrs=legendary.getLegendaryAttributes();
    }

    /*int padding=*/BufferUtils.readUInt8(bis);
    int nbSubs=BufferUtils.readUInt8(bis);
    LOGGER.debug("Nb subs: "+nbSubs);
    for(int i=0;i<nbSubs;i++)
    {
      int header=BufferUtils.readUInt32(bis);
      int header2=BufferUtils.readUInt32(bis);
      if (header!=header2)
      {
        throw new LinkDecodingException("Decoding error: header="+header+", header2="+header2);
      }
      if (header==0x100012C5)
      {
        int nbExtras=BufferUtils.readUInt32(bis);
        LOGGER.debug("Extras: "+nbExtras);
        for(int j=0;j<nbExtras;j++)
        {
          int subHeader=BufferUtils.readUInt32(bis);
          if(subHeader==0x34E)
          {
            // Container slot
            int bitsSet=BufferUtils.readUInt32(bis);
            LOGGER.debug("Container slots: "+bitsSet);
            // TODO Check if this gives the slot in case of an equipped item (e.g left/right ear)
          }
          else if(subHeader==0x10000E20)
          {
            // Crafted by
            BufferUtils.readUInt8(bis);
            String crafter=decodeName(bis);
            LOGGER.debug("Crafter: "+crafter);
            itemInstance.setCrafterName(crafter);
            BufferUtils.skip(bis,6);
          }
          else if (subHeader==0x10000884)
          {
            // Crafted Item Inscription
            BufferUtils.readUInt8(bis);
            String inscription=decodeName(bis);
            LOGGER.debug("Crafted item inscription: "+inscription);
            itemInstance.setBirthName(inscription);
            BufferUtils.skip(bis,6);
          }
          else if (subHeader==0x10000AC1) // 268438209 - Inventory_BoundToID
          {
            // Bound to
            int boundToLowId=BufferUtils.readUInt32(bis);
            int boundToHighId=BufferUtils.readUInt32(bis);
            EntityId id=new EntityId(EntityType.UNKNOWN,boundToLowId,boundToHighId);
            LOGGER.debug("Bound to: "+id);
            itemInstance.setBoundTo(id);
          }
          else if (subHeader==0x10000AC2)
          {
            // Binds on acquire
            int boA=BufferUtils.readUInt8(bis);
            LOGGER.debug("Bind on acquire: "+boA);
          }
          else if (subHeader==0x10000E7B)
          {
            // Quantity
            int quantity=BufferUtils.readUInt32(bis);
            LOGGER.debug("Quantity: "+quantity);
            // TODO Use
          }
          else if (subHeader==0x100031A4)
          {
            // Default legacy rank
            int defaultLegacyRank=BufferUtils.readUInt32(bis);
            LOGGER.debug("Default legacy rank: "+defaultLegacyRank);
            // TODO Duplicate?
          }
          else if (subHeader==0x10001D5F)
          {
            // LI level (for a non imbued item. Max 60 or 70)
            int liLevel=BufferUtils.readUInt32(bis);
            if (attrs!=null)
            {
              attrs.getNonImbuedAttrs().setLegendaryItemLevel(liLevel);
            }
            LOGGER.debug("LI level: "+liLevel);
          }
          else if (subHeader==0x100000C4)
          {
            // Usage min level
            int usageMinLevel=BufferUtils.readUInt32(bis);
            itemInstance.setMinLevel(Integer.valueOf(usageMinLevel));
            LOGGER.debug("Usage min level: "+usageMinLevel);
          }
          else if (subHeader==0x1000132C)
          {
            // Current item durability
            int durability=BufferUtils.readUInt32(bis);
            itemInstance.setDurability(Integer.valueOf(durability));
            LOGGER.debug("Durability: "+durability);
          }
          else if (subHeader==0x100060B5)
          {
            // ItemAdvancement_Imbued
            int iaImbued=BufferUtils.readUInt8(bis);
            LOGGER.debug("Imbued: "+iaImbued);
            // TODO Duplicate?
          }
          else if (subHeader==0x10001042)
          {
            // Combat_Damage (Max Damage)
            float damage=BufferUtils.readFloat(bis);
            LOGGER.debug("Max Damage: "+damage);
            // TODO Use
          }
          else if (subHeader==0x10000835)
          {
            // Item value
            int itemValue=BufferUtils.readUInt32(bis);
            Money money=parseItemValue(itemValue);
            itemInstance.setValue(money);
            LOGGER.debug("Item value: "+itemValue);
          }
          else if (subHeader==0x10000669)
          {
            // Item level
            int itemLevel=BufferUtils.readUInt32(bis);
            itemInstance.setItemLevel(Integer.valueOf(itemLevel));
            LOGGER.debug("Item level: "+itemLevel);
          }
          else if (subHeader==0x10004996)
          {
            // Upgrades (crystals)
            int itemUpgrades=BufferUtils.readUInt32(bis);
            if (attrs!=null)
            {
              attrs.getNonImbuedAttrs().setNbUpgrades(itemUpgrades);
            }
            LOGGER.debug("Item upgrades: "+itemUpgrades);
          }
          else if (subHeader==0x10005F0E) // 268459790 - Item_Socket_Gem_Array
          {
            EssencesSet essences=decodeEssences(bis);
            itemInstance.setEssences(essences);
          }
          else if (subHeader==0x10000ACD) // 268438221 - Item_ClothingColor
          {
            // Indigo: 0.15; Umber: 0.5/3F000000 ; Orange: 0.75/3F400000
            float dye=BufferUtils.readFloat(bis);
            LOGGER.debug("Dye: "+dye);
            ColorsManager colorsMgr=ColorsManager.getInstance();
            ColorDescription color=colorsMgr.getColor(dye);
            itemInstance.setColor(color);
          }
          else if (subHeader==0x10000570) // 268436848 - Item_Armor_Value
          {
            int armorValue=BufferUtils.readUInt32(bis);
            LOGGER.debug("Armour: "+armorValue);
            // TODO Use
          }
          else if (subHeader==0x100038A7) // 268449959 - Inventory_BindToAccount
          {
            int bindToAccount=BufferUtils.readUInt8(bis);
            LOGGER.debug("bindToAccount: "+bindToAccount);
          }
          else if (subHeader==0x10001426) // 268440614 - Quest_DataID
          {
            int questId=BufferUtils.readUInt32(bis);
            LOGGER.debug("questId: "+questId);
          }
          else if (subHeader==0x1000389D) // 268449949 - Item_IsPurchasedWebStoreItem
          {
            int purchasedFromStore=BufferUtils.readUInt8(bis);
            LOGGER.debug("purchasedFromStore: "+purchasedFromStore);
          }
          else if (subHeader==0x100026BC) // 268445372 - ItemAdvancement_SelectedEffect_Array
          {
            int nb=BufferUtils.readUInt32(bis);
            for(int i2=0;i2<nb;i2++)
            {
              int did=BufferUtils.readUInt32(bis);
              if (did==0x100026BD) // 268445373 - ItemAdvancement_SelectedEffect
              {
                /*int legacyId=*/BufferUtils.readUInt32(bis);
              }
              else
              {
                throw new LinkDecodingException("Unexpected DID value: "+did);
              }
            }
          }
          else
          {
            throw new LinkDecodingException("Unmanaged header: "+subHeader);
          }
        }
      }
      else if (header==0x10000421) // UI_Examination_Tooltip_DID
      {
        // Item ID, reloaded
        int itemId=BufferUtils.readUInt32(bis);
        LOGGER.debug("Item ID: "+itemId);
      }
      else if (header==0x10002897)
      {
        // Instance ID, reloaded
        int lowInstanceId=BufferUtils.readUInt32(bis);
        int highInstanceId=BufferUtils.readUInt32(bis);
        LOGGER.debug("Instance ID: low="+lowInstanceId+", high="+highInstanceId);
      }
    }
  }

  private Money parseItemValue(int itemValue)
  {
    int copper=itemValue%100;
    itemValue=itemValue/100;
    int silver=itemValue%1000;
    int gold=itemValue/1000;
    return new Money(gold,silver,copper);
  }

  private EssencesSet decodeEssences(ByteArrayInputStream bis) throws LinkDecodingException
  {
    ItemsManager itemMgr=ItemsManager.getInstance();
    int nbEssences=BufferUtils.readUInt32(bis);
    EssencesSet ret=new EssencesSet(nbEssences);
    LOGGER.debug("Nb essences: "+nbEssences);
    for(int i=0;i<nbEssences;i++)
    {
      // 0x10005F3D=268459837 - Item_Socket_Gem_Array_Entry
      int propId=BufferUtils.readUInt32(bis);
      if (propId!=268459837)
      {
        throw new LinkDecodingException("Expected property ID: "+268459837+", got: "+propId);
      }

      // Decode essence properties
      int essenceId=0;
      int essenceLevel=0;

      /*int n3=*/BufferUtils.readUInt8(bis); // Always 0
      int nbProps=BufferUtils.readUInt8(bis);
      for(int j=0;j<nbProps;j++)
      {
        int header=BufferUtils.readUInt32(bis);
        int header2=BufferUtils.readUInt32(bis);
        if (header!=header2)
        {
          throw new LinkDecodingException("Decoding error: header="+header+", header2="+header2);
        }
        // 0x10005F05=268459781 - Item_Socket_GemDID
        if (header==0x10005F05)
        {
          essenceId=BufferUtils.readUInt32(bis);
          LOGGER.debug("Essence #"+(i+1)+": "+essenceId);
        }
        // 0x10005F3E=268459838 - Item_Socket_GemLevel
        else if (header==0x10005F3E)
        {
          essenceLevel=BufferUtils.readUInt32(bis);
          LOGGER.debug("Essence level: "+essenceLevel);
        }
        else
        {
          throw new LinkDecodingException("Unmanaged property: "+propId);
        }
        if ((essenceId!=0) && (essenceLevel!=0))
        {
          Item essence=itemMgr.getItem(essenceId);
          ret.setEssence(i,essence);
        }
      }
    }
    return ret;
  }

  private void loadImbuedLegacy(ImbuedLegacyInstance ret, int legacyId)
  {
    LegaciesManager legaciesMgr=LegaciesManager.getInstance();
    ImbuedLegacy legacy=legaciesMgr.getLegacy(legacyId);
    if (legacy!=null)
    {
      ret.setLegacy(legacy);
    }
    else
    {
      LOGGER.warn("Legacy not found: "+legacyId);
    }
  }

  private String decodeName(ByteArrayInputStream bis)
  {
    return BufferUtils.readPrefixedUtf16String(bis);
  }
}
