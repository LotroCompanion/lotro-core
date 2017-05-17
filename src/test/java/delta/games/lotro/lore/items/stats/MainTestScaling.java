package delta.games.lotro.lore.items.stats;

import delta.games.lotro.lore.items.Armour;
import delta.games.lotro.lore.items.Item;
import delta.games.lotro.lore.items.ItemPropertyNames;

/**
 * Test for item scaling facilities.
 * @author DAM
 */
public class MainTestScaling
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    // Scaling and formulas
    {
      Armour armour=new Armour();
      armour.setProperty(ItemPropertyNames.SCALING,ScalingRulesNames.SCALABLE_REWARDS);
      armour.setProperty(ItemPropertyNames.FORMULAS,"ARMOUR(ArmHHY);MIGHT:2.0;VITALITY:1.6;FATE:1.4");
      Scaling.scaleToRequiredLevel(armour,105);
      System.out.println(armour.dump());
      Scaling.scaleToRequiredLevel(armour,100);
      System.out.println(armour.dump());
      /*
      <item key="1879279194" name="Lesser Valourous Helm of Fate" level="217" slot="HEAD" category="ARMOUR" unique="false" sturdiness="TOUGH" quality="UNCOMMON" description="" armour="1059" armourType="HEAVY">
      <property key="backgroundIconId" value="1090519042"/>
      <property key="formulas" value=/>
      <property key="iconId" value="1091764723"/>
      <property key="scaling" value="ScalableRewards"/>
      <stats>
      <stat name="MIGHT" value="19600"/>
      <stat name="VITALITY" value="15600"/>
      <stat name="FATE" value="13700"/>
      </stats>
      </item>
      */
    }
    // Formulas only
    {
      Item jewel=new Item();
      jewel.setProperty(ItemPropertyNames.FORMULAS,"MORALE:1.4;VITALITY:2.0;INCOMING_HEALING:1.6;PHYSICAL_MITIGATION:1.0;TACTICAL_MITIGATION:1.0");
      Scaling.scaleToItemLevel(jewel,84);
      System.out.println(jewel.dump());
      /*
      <item key="1879230164" name="Exquisite Great River Necklace of Morale" level="84" slot="NECK" category="ITEM" binding="BIND_ON_EQUIP" unique="false" durability="80" sturdiness="NORMAL" quality="INCOMPARABLE" minLevel="75" description="">
      <property key="backgroundIconId" value="1090519044"/>
      <property key="formulas" value="MORALE:1.4;VITALITY:2.0;INCOMING_HEALING:1.6;PHYSICAL_MITIGATION:1.0;TACTICAL_MITIGATION:1.0"/>
      <property key="iconId" value="1091699345"/>
      <stats>
      <stat name="MORALE" value="23500"/>
      <stat name="VITALITY" value="6300"/>
      <stat name="INCOMING_HEALING" value="107519"/>
      <stat name="PHYSICAL_MITIGATION" value="75700"/>
      <stat name="TACTICAL_MITIGATION" value="75700"/>
      </stats>
      </item>
      */
    }
  }
}
