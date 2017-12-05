package delta.games.lotro.lore.items.stats;

import delta.games.lotro.lore.items.ArmourType;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.ItemQuality;

/**
 * Computes armour of scalable items.
 * @author DAM
 */
public class ScaledArmourComputer
{
  /**
   * Get armour value.
   * @param itemLevel Item level.
   * @param type Armour type.
   * @param slot Targeted slot.
   * @param quality Item quality.
   * @param sliceCount Slice count.
   * @return An armour value.
   */
  public double getArmour(int itemLevel, ArmourType type, EquipmentLocation slot, ItemQuality quality, double sliceCount)
  {
    if (type==ArmourType.HEAVY) return getHeavyArmour(itemLevel,slot,quality,sliceCount);
    if (type==ArmourType.LIGHT) return getLightArmour(itemLevel,slot,quality,sliceCount);
    if (type==ArmourType.MEDIUM) return getMediumArmour(itemLevel,slot,quality,sliceCount);
    return 0.0;
  }

  private double getHeavyArmour(int itemLevel, EquipmentLocation slot, ItemQuality quality, double sliceCount)
  {
    double ret=0.0;
    if (slot==EquipmentLocation.HEAD)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(5.643*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((5.7*itemLevel-5)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.5*itemLevel+213.4)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=10) ret=Math.floor(5.076*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=11) && (itemLevel<=79)) ret=Math.floor(5.076*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((5.13*itemLevel-4.524)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.05*itemLevel+192.036)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(5.346*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((5.415*itemLevel-4.775)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.275*itemLevel+202.705)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=10) ret=Math.floor(3.5*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=11) && (itemLevel<=79)) ret=Math.floor((5.076*itemLevel-15)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((5.13*itemLevel-19.914)*sliceCount+0.500000000001);
        else if (itemLevel>=186) ret=Math.floor((4.05*itemLevel+179.886)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.SHOULDER)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        // Same as head
        if (itemLevel<=79) ret=Math.floor(5.643*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((5.7*itemLevel-5)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.5*itemLevel+213.4)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(5.1316*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((5.13*itemLevel+0.26)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.08*itemLevel+200.7)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        // Same as head
        if (itemLevel<=79) ret=Math.floor(5.346*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((5.415*itemLevel-4.775)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.275*itemLevel+202.705)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=79) ret=Math.floor((5.1*itemLevel-0.8)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((5.122*itemLevel-3)*sliceCount+0.500000000001);
        else if (itemLevel>=186) ret=Math.floor((4.04638*itemLevel+192.8)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.CHEST)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(18.81*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((19*itemLevel-15)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((15*itemLevel+713)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(16.929*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((17.119*itemLevel-13.99)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((13.515*itemLevel+641.938)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(17.82*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((18.012*itemLevel-14.72)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((14.22*itemLevel+675.424)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=10) ret=Math.floor(12*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=11) && (itemLevel<=79)) ret=Math.floor((16.9328*itemLevel-51)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((17.119*itemLevel-65.347)*sliceCount+0.500000000001);
        else if (itemLevel>=186) ret=Math.floor((13.515*itemLevel+601.393)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.HAND)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(9.405*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((9.5*itemLevel-8)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((7.5*itemLevel+356)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(8.4645*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((8.569*itemLevel-7.265)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((6.765*itemLevel+321.063)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(8.91*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((9.006*itemLevel-7.632)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((7.11*itemLevel+337.44)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        /*
        if (SliceILvl<=10) ret=Math.floor(6*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceILvl<=79)) ret=Math.floor((8.45*SliceILvl-25)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceILvl<=185)) ret=Math.floor((8.569*SliceILvl-32.972)*SliceCount+0.500000000001);
        else if (SliceILvl>=186) ret=Math.floor((6.765*SliceILvl+300.768)*SliceCount+0.500000000001);
        */
        if (itemLevel<=79) ret=Math.floor(8.4645*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((8.569*itemLevel-7.265)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((6.765*itemLevel+321.063)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.LEGS)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(15.675*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((15.8454*itemLevel-12.9)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((12.509526316*itemLevel+594.229010488)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(14.1*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((14.25*itemLevel-11.725)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((11.25*itemLevel+534.275)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(14.85*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((15.01*itemLevel-12.35)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((11.85*itemLevel+562.77)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        /*
        if (SliceILvl<=10) ret=Math.floor(10*SliceILvl*SliceCount+0.50000000000);
        else if ((SliceILvl>=11) && (SliceILvl<=79)) ret=Math.floor((14.096*SliceILvl-42.1)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceILvl<=185)) ret=Math.floor((14.25*SliceILvl-54.475)*SliceCount+0.500000000001);
        else if (SliceILvl>=186) ret=Math.floor((11.25*SliceILvl+500.525)*SliceCount+0.500000000001);
        */
        if (itemLevel<=79) ret=Math.floor(14.1*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((14.25*itemLevel-11.725)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((11.25*itemLevel+534.275)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.FEET)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(7.524*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((7.6*itemLevel-6.5)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((6*itemLevel+284.7)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=10) ret=Math.floor(6.768*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=11) && (itemLevel<=79)) ret=Math.floor(6.768*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((6.84*itemLevel-5.874)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((5.4*itemLevel+256.206)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(7.128*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((7.22*itemLevel-6.2)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((5.7*itemLevel+270.44)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        /*
        if (SliceILvl<=10) ret=Math.floor((4.75*SliceILvl+0.25)*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceILvl<=79)) ret=Math.floor((6.7675*SliceILvl-20.1)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceILvl<=185)) ret=Math.floor((6.84*SliceILvl-26.394)*SliceCount+0.500000000001);
        else if (SliceILvl>=186) ret=Math.floor((5.4*SliceILvl+240.006)*SliceCount+0.500000000001);
        */
        if (itemLevel<=10) ret=Math.floor(6.768*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=11) && (itemLevel<=79)) ret=Math.floor(6.768*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((6.84*itemLevel-5.874)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((5.4*itemLevel+256.206)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.OFF_HAND) // Shield
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(35.625*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((34.7*itemLevel+76.5)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((14.9*itemLevel+3680.1)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(27*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((26.53*itemLevel+37.4)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((11.37*itemLevel+2796.52)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor((33.75*itemLevel+0.25)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((32.9*itemLevel+46.6)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((14.1*itemLevel+3468.2)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=19) ret=Math.floor(23*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=20) && (itemLevel<=79)) ret=Math.floor((27*itemLevel-81)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((26.53*itemLevel-42.19)*sliceCount+0.500000000001);
        else if (itemLevel>=186) ret=Math.floor((11.37*itemLevel+2762.41)*sliceCount+0.500000000001);
      }
    }
    return ret;
  }

  private double getLightArmour(int itemLevel, EquipmentLocation slot, ItemQuality quality, double sliceCount)
  {
    double ret=0.0;
    if (slot==EquipmentLocation.HEAD)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(3.078*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((3*itemLevel+5.5)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((2.1*itemLevel+169.3)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(2.769*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((2.7*itemLevel+5.116)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((1.89*itemLevel+152.536)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(2.916*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((2.85*itemLevel+5.4)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((1.995*itemLevel+161.01)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=79) ret=Math.floor((2.769*itemLevel-8.307)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((2.7*itemLevel-2.984)*sliceCount+0.500000000001);
        else if (itemLevel>=186) ret=Math.floor((1.89*itemLevel+146.866)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.SHOULDER)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(3.078*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((3*itemLevel+5.5)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((2.1*itemLevel+169.3)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(2.8*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((2.7*itemLevel+5.116)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((1.89*itemLevel+152.536)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(2.916*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((2.85*itemLevel+5.4)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((1.995*itemLevel+161.01)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=79) ret=Math.floor((2.8*itemLevel-8.4)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((2.7*itemLevel-2.984)*sliceCount+0.500000000001);
        else if (itemLevel>=186) ret=Math.floor((1.89*itemLevel+146.866)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.BACK) // Cloak
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(5.64*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((7*itemLevel-58)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((5*itemLevel+306)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor((5.64*itemLevel-0.15)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((6.3*itemLevel-52.7)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.5*itemLevel+274.9)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(5.93*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((6.629*itemLevel-55.452)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.735*itemLevel+289.256)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        /*
        if (SliceILvl<=28) ret=Math.floor((4.9*SliceILvl-0.25)*SliceCount+0.500000000001);
        else if ((SliceILvl>=29) && (SliceILvl<=79)) ret=Math.floor((5.69*SliceILvl-20)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceILvl<=185)) ret=Math.floor((6.3*SliceILvl-71.6)*SliceCount+0.500000000001);
        else if (SliceILvl>=186) ret=Math.floor((4.5*SliceILvl+261.4)*SliceCount+0.500000000001);
        */
        if (itemLevel<=79) ret=Math.floor((5.64*itemLevel-0.15)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((6.3*itemLevel-52.7)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.5*itemLevel+274.9)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.CHEST)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(10.26*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((10*itemLevel+21)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((7*itemLevel+567)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(9.234*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((9.01*itemLevel+18.438)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((6.307*itemLevel+510.384)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(9.72*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((9.48*itemLevel+19.4)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((6.636*itemLevel+537.008)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=79) ret=Math.floor((9.234*itemLevel-27.702)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((9.01*itemLevel-8.592)*sliceCount+0.500000000001);
        else if (itemLevel>=186) ret=Math.floor((6.307*itemLevel+491.463)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.HAND)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(5.13*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((5*itemLevel+10)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((3.5*itemLevel+283)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(4.617*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((4.51*itemLevel+8.944)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((3.157*itemLevel+255.19)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(4.86*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((4.74*itemLevel+9.4)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((3.318*itemLevel+268.204)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=79) ret=Math.floor((4.617*itemLevel-13.851)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((4.51*itemLevel-4.586)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((3.157*itemLevel+245.719)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.LEGS)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(8.55*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((8.339*itemLevel+17.1)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((5.837*itemLevel+472.464)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(7.691*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((7.5*itemLevel+15.256)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((5.25*itemLevel+424.756)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(8.1*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((7.9*itemLevel+16.07)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((5.53*itemLevel+447.41)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=79) ret=Math.floor(7.31*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((7.5*itemLevel-7.244)*sliceCount+0.500000000001);
        else if (itemLevel>=186) ret=Math.floor((5.25*itemLevel+409.006)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.FEET)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(4.104*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((4*itemLevel+8)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((2.8*itemLevel+226.4)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(3.691636364*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((3.6*itemLevel+7.011)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((2.52*itemLevel+203.571)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(3.888*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((3.8*itemLevel+7.4)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((2.66*itemLevel+214.88)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=79) ret=Math.floor((3.691636364*itemLevel-11.074909092)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((3.6*itemLevel-3.789)*sliceCount+0.500000000001);
        else if (itemLevel>=186) ret=Math.floor((2.52*itemLevel+196.011)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.OFF_HAND) // Shield
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(11.027388889*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((10.9*itemLevel+14)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.7*itemLevel+1142.4)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=29) ret=Math.floor(9.94*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=30) && (itemLevel<=79)) ret=Math.floor((10.45*itemLevel-20)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((10.29*itemLevel-6.4)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.41*itemLevel+1075.52)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=23) ret=Math.floor(9.94*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=24) && (itemLevel<=63)) ret=Math.floor((10.447*itemLevel+0.8)*sliceCount+0.500000000001);
        else if ((itemLevel>=64) && (itemLevel<=79)) ret=Math.floor((10.447*itemLevel+1.1)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((10.29*itemLevel+14.2)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.41*itemLevel+1084.36)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=79) ret=Math.floor((8.18*itemLevel+1)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((8*itemLevel+18)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((3.428571429*itemLevel+872.857142777)*sliceCount+0.500000000001);
      }
    }
    return ret;
  }

  private double getMediumArmour(int itemLevel, EquipmentLocation slot, ItemQuality quality, double sliceCount)
  {
    double ret=0.0;
    if (slot==EquipmentLocation.HEAD)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(4.3605*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((4.5*itemLevel-12)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((3.3*itemLevel+206.4)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(3.922*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((4.05*itemLevel-10.468)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((2.97*itemLevel+186.092)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(4.131*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((4.275*itemLevel-11.05)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((3.135*itemLevel+196.43)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=10) ret=Math.floor((2.9*itemLevel-1)*sliceCount+0.500000000001);
        else if ((itemLevel>=11) && (itemLevel<=79)) ret=Math.floor((3.937*itemLevel-12.5)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((4.05*itemLevel-22.618)*sliceCount+0.500000000001);
        else if (itemLevel>=186) ret=Math.floor((2.97*itemLevel+177.182)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.SHOULDER)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(4.3605*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((4.5*itemLevel-12)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((3.3*itemLevel+206.4)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(3.922*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((4.05*itemLevel-10.468)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((2.97*itemLevel+186.092)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(4.131*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((4.275*itemLevel-11.05)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((3.135*itemLevel+196.43)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=79) ret=Math.floor(3.922*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((4.05*itemLevel-10.468)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((2.97*itemLevel+186.092)*sliceCount+0.500000000001);
        /*
        if (SliceILvl<=10) ret=Math.floor((2.9*SliceILvl-1)*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceILvl<=79)) ret=Math.floor((3.922*SliceILvl-11.7)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceILvl<=185)) ret=Math.floor((4.05*SliceILvl-22.618)*SliceCount+0.500000000001);
        else if (SliceILvl>=186) ret=Math.floor((2.97*SliceILvl+177.182)*SliceCount+0.500000000001);
        */
      }
    }
    else if (slot==EquipmentLocation.CHEST)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(14.535*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((15*itemLevel-37)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((11*itemLevel+691)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(13.0815*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((13.51485*itemLevel-33.816)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((9.91089*itemLevel+622.10472)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(13.77*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((14.22*itemLevel-35.58)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((10.428*itemLevel+654.564)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        /*
        if (SliceILvl<=10) ret=Math.floor((8.9*SliceILvl+2.4)*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceILvl<=79)) ret=Math.floor((13.084*SliceILvl-39.3)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceILvl<=185)) ret=Math.floor((13.51485*SliceILvl-74.36055)*SliceCount+0.500000000001);
        else if (SliceILvl>=186) ret=Math.floor((9.91089*SliceILvl+592.37205)*SliceCount+0.500000000001);
        */
        if (itemLevel<=79) ret=Math.floor(13.0815*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((13.51485*itemLevel-33.816)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((9.91089*itemLevel+622.10472)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.HAND)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(7.2675*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((7.5*itemLevel-19)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((5.5*itemLevel+345)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(6.54075*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((6.765*itemLevel-17.174)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.961*itemLevel+311.154)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(6.885*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((7.11*itemLevel-18.05)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((5.214*itemLevel+327.022)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=10) ret=Math.floor(4.625*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=11) && (itemLevel<=79)) ret=Math.floor((6.545*itemLevel-19.5)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((6.765*itemLevel-37.469)*sliceCount+0.500000000001);
        else if (itemLevel>=186) ret=Math.floor((4.961*itemLevel+296.271)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.LEGS)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(12.1125*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((12.51*itemLevel-31.35)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((9.174*itemLevel+575.802)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=10) ret=Math.floor(10.895*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=11) && (itemLevel<=79)) ret=Math.floor(10.895*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((11.25*itemLevel-28.22)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((8.25*itemLevel+517.78)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(11.475*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((11.85*itemLevel-29.725)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((8.69*itemLevel+545.395)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        /*
        if (SliceILvl<=10) ret=Math.floor((7.65*SliceILvl-0.4)*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceILvl<=79)) ret=Math.floor((10.9*SliceILvl-32.6)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceILvl<=185)) ret=Math.floor((11.25*SliceILvl-61.97)*SliceCount+0.500000000001);
        else if (SliceILvl>=186) ret=Math.floor((8.25*SliceILvl+493.03)*SliceCount+0.500000000001);
        */
        if (itemLevel<=10) ret=Math.floor(10.895*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=11) && (itemLevel<=79)) ret=Math.floor(10.895*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((11.25*itemLevel-28.22)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((8.25*itemLevel+517.78)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.FEET)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(5.814*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((6*itemLevel-15)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.4*itemLevel+276.2)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=79) ret=Math.floor(5.229818182*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((5.4*itemLevel-13.784)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((3.96*itemLevel+248.296)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=79) ret=Math.floor(5.508*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((5.7*itemLevel-14.55)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((4.18*itemLevel+262.09)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        /*
        if (SliceILvl<=10) ret=Math.floor((3.65*SliceILvl+0.85)*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceILvl<=79)) ret=Math.floor((5.235*SliceILvl-15.5)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceILvl<=185)) ret=Math.floor((5.4*SliceILvl-29.984)*SliceCount+0.500000000001);
        else if (SliceILvl>=186) ret=Math.floor((3.96*SliceILvl+236.416)*SliceCount+0.500000000001);
        */
        if (itemLevel<=79) ret=Math.floor(5.229818182*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((5.4*itemLevel-13.784)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((3.96*itemLevel+248.296)*sliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.OFF_HAND) // Shield
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (itemLevel<=79) ret=Math.floor(16.049722222*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((15.9*itemLevel+15)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((6.8*itemLevel+1671.2)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (itemLevel<=13) ret=Math.floor(9.9*itemLevel*sliceCount+0.500000000001);
        else if ((itemLevel>=14) && (itemLevel<=79)) ret=Math.floor((14.46*itemLevel+1)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((14.245*itemLevel+19.8)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((6.105*itemLevel+1501.28)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (itemLevel<=49) ret=Math.floor((15.2*itemLevel+1.5)*sliceCount+0.500000000001);
        else if ((itemLevel>=50) && (itemLevel<=59)) ret=Math.floor((15.4*itemLevel-9)*sliceCount+0.500000000001);
        else if ((itemLevel>=60) && (itemLevel<=79)) ret=Math.floor((15.205*itemLevel+2.355)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=182)) ret=Math.floor((15.05*itemLevel+21)*sliceCount+0.500000000001);
        else if (itemLevel>=183) ret=Math.floor((6.45*itemLevel+1586.2)*sliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (itemLevel<=15) ret=Math.floor((8.9*itemLevel+0.9)*sliceCount+0.500000000001);
        else if ((itemLevel>=16) && (itemLevel<=30)) ret=Math.floor((13.05*itemLevel-0.25)*sliceCount+0.500000000001);
        else if ((itemLevel>=31) && (itemLevel<=79)) ret=Math.floor((14.485*itemLevel-44)*sliceCount+0.500000000001);
        else if ((itemLevel>=80) && (itemLevel<=185)) ret=Math.floor((14.245*itemLevel-22.935)*sliceCount+0.500000000001);
        else if (itemLevel>=186) ret=Math.floor((6.105*itemLevel+1482.965)*sliceCount+0.500000000001);
      }
    }
    return ret;
  }
}
