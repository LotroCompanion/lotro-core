package delta.games.lotro.common.stats;

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
   * @param SliceILvl Item level.
   * @param type Armour type.
   * @param slot Targeted slot.
   * @param quality Item quality.
   * @param SliceCount Slice count.
   * @return An armour value.
   */
  public double getArmour(int SliceILvl, ArmourType type, EquipmentLocation slot, ItemQuality quality, double SliceCount)
  {
    if (type==ArmourType.HEAVY) return getHeavyArmour(SliceILvl,slot,quality,SliceCount);
    if (type==ArmourType.LIGHT) return getLightArmour(SliceILvl,slot,quality,SliceCount);
    if (type==ArmourType.MEDIUM) return getMediumArmour(SliceILvl,slot,quality,SliceCount);
    return 0.0;
  }

  private double getHeavyArmour(int SliceILvl, EquipmentLocation slot, ItemQuality quality, double SliceCount)
  {
    double ret=0.0;
    if (slot==EquipmentLocation.HEAD)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(5.643*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((5.7*SliceILvl-5)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.5*SliceILvl+213.4)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=10) ret=Math.floor(5.076*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor(5.076*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((5.13*SliceILvl-4.524)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.05*SliceILvl+192.036)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(5.346*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((5.415*SliceILvl-4.775)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.275*SliceILvl+202.705)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=10) ret=Math.floor(3.5*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor((5.076*SliceILvl-15)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((5.13*SliceILvl-19.914)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((4.05*SliceILvl+179.886)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.SHOULDER)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        // Same as head
        if (SliceILvl<=79) ret=Math.floor(5.643*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((5.7*SliceILvl-5)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.5*SliceILvl+213.4)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(5.1316*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((5.13*SliceILvl+0.26)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.08*SliceILvl+200.7)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        // Same as head
        if (SliceILvl<=79) ret=Math.floor(5.346*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((5.415*SliceILvl-4.775)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.275*SliceILvl+202.705)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=79) ret=Math.floor((5.1*SliceILvl-0.8)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((5.122*SliceILvl-3)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((4.04638*SliceILvl+192.8)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.CHEST)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(18.81*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((19*SliceILvl-15)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((15*SliceILvl+713)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(16.929*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((17.119*SliceILvl-13.99)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((13.515*SliceILvl+641.938)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(17.82*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((18.012*SliceILvl-14.72)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((14.22*SliceILvl+675.424)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=10) ret=Math.floor(12*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor((16.9328*SliceILvl-51)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((17.119*SliceILvl-65.347)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((13.515*SliceILvl+601.393)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.HAND)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(9.405*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((9.5*SliceILvl-8)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((7.5*SliceILvl+356)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(8.4645*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((8.569*SliceILvl-7.265)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((6.765*SliceILvl+321.063)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(8.91*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((9.006*SliceILvl-7.632)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((7.11*SliceILvl+337.44)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=10) ret=Math.floor(6*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor((8.45*SliceILvl-25)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((8.569*SliceILvl-32.972)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((6.765*SliceILvl+300.768)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.LEGS)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(15.675*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((15.8454*SliceILvl-12.9)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((12.509526316*SliceILvl+594.229010488)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(14.1*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((14.25*SliceILvl-11.725)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((11.25*SliceILvl+534.275)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(14.85*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((15.01*SliceILvl-12.35)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((11.85*SliceILvl+562.77)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=10) ret=Math.floor(10*SliceILvl*SliceCount+0.50000000000);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor((14.096*SliceILvl-42.1)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((14.25*SliceILvl-54.475)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((11.25*SliceILvl+500.525)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.FEET)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(7.524*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((7.6*SliceILvl-6.5)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((6*SliceILvl+284.7)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=10) ret=Math.floor(6.768*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor(6.768*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((6.84*SliceILvl-5.874)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((5.4*SliceILvl+256.206)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(7.128*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((7.22*SliceILvl-6.2)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((5.7*SliceILvl+270.44)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=10) ret=Math.floor((4.75*SliceILvl+0.25)*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor((6.7675*SliceILvl-20.1)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((6.84*SliceILvl-26.394)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((5.4*SliceILvl+240.006)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.OFF_HAND) // Shield
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(35.625*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((34.7*SliceILvl+76.5)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((14.9*SliceILvl+3680.1)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(27*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((26.53*SliceILvl+37.4)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((11.37*SliceILvl+2796.52)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor((33.75*SliceILvl+0.25)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((32.9*SliceILvl+46.6)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((14.1*SliceILvl+3468.2)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=19) ret=Math.floor(23*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=20) && (SliceCount<=79)) ret=Math.floor((27*SliceILvl-81)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((26.53*SliceILvl-42.19)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((11.37*SliceILvl+2762.41)*SliceCount+0.500000000001);
      }
    }
    return ret;
  }

  private double getLightArmour(int SliceILvl, EquipmentLocation slot, ItemQuality quality, double SliceCount)
  {
    double ret=0.0;
    if (slot==EquipmentLocation.HEAD)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(3.078*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((3*SliceILvl+5.5)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((2.1*SliceILvl+169.3)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(2.769*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((2.7*SliceILvl+5.116)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((1.89*SliceILvl+152.536)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(2.916*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((2.85*SliceILvl+5.4)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((1.995*SliceILvl+161.01)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=79) ret=Math.floor((2.769*SliceILvl-8.307)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((2.7*SliceILvl-2.984)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((1.89*SliceILvl+146.866)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.SHOULDER)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(3.078*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((3*SliceILvl+5.5)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((2.1*SliceILvl+169.3)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(2.8*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((2.7*SliceILvl+5.116)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((1.89*SliceILvl+152.536)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(2.916*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((2.85*SliceILvl+5.4)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((1.995*SliceILvl+161.01)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=79) ret=Math.floor((2.8*SliceILvl-8.4)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((2.7*SliceILvl-2.984)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((1.89*SliceILvl+146.866)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.BACK) // Cloak
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(5.64*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((7*SliceILvl-58)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((5*SliceILvl+306)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor((5.64*SliceILvl-0.15)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((6.3*SliceILvl-52.7)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.5*SliceILvl+274.9)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(5.93*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((6.629*SliceILvl-55.452)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.735*SliceILvl+289.256)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=28) ret=Math.floor((4.9*SliceILvl-0.25)*SliceCount+0.500000000001);
        else if ((SliceILvl>=29) && (SliceCount<=79)) ret=Math.floor((5.69*SliceILvl-20)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((6.3*SliceILvl-71.6)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((4.5*SliceILvl+261.4)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.CHEST)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(10.26*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((10*SliceILvl+21)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((7*SliceILvl+567)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(9.234*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((9.01*SliceILvl+18.438)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((6.307*SliceILvl+510.384)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(9.72*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((9.48*SliceILvl+19.4)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((6.636*SliceILvl+537.008)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=79) ret=Math.floor((9.234*SliceILvl-27.702)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((9.01*SliceILvl-8.592)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((6.307*SliceILvl+491.463)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.HAND)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(5.13*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((5*SliceILvl+10)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((3.5*SliceILvl+283)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(4.617*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((4.51*SliceILvl+8.944)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((3.157*SliceILvl+255.19)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(4.86*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((4.74*SliceILvl+9.4)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((3.318*SliceILvl+268.204)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=79) ret=Math.floor((4.617*SliceILvl-13.851)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((4.51*SliceILvl-4.586)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((3.157*SliceILvl+245.719)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.LEGS)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(8.55*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((8.339*SliceILvl+17.1)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((5.837*SliceILvl+472.464)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(7.691*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((7.5*SliceILvl+15.256)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((5.25*SliceILvl+424.756)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(8.1*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((7.9*SliceILvl+16.07)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((5.53*SliceILvl+447.41)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=79) ret=Math.floor(7.31*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((7.5*SliceILvl-7.244)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((5.25*SliceILvl+409.006)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.FEET)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(4.104*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((4*SliceILvl+8)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((2.8*SliceILvl+226.4)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(3.691636364*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((3.6*SliceILvl+7.011)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((2.52*SliceILvl+203.571)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(3.888*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((3.8*SliceILvl+7.4)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((2.66*SliceILvl+214.88)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=79) ret=Math.floor((3.691636364*SliceILvl-11.074909092)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((3.6*SliceILvl-3.789)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((2.52*SliceILvl+196.011)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.OFF_HAND) // Shield
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(11.027388889*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((10.9*SliceILvl+14)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.7*SliceILvl+1142.4)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=29) ret=Math.floor(9.94*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=30) && (SliceCount<=79)) ret=Math.floor((10.45*SliceILvl-20)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((10.29*SliceILvl-6.4)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.41*SliceILvl+1075.52)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=23) ret=Math.floor(9.94*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=24) && (SliceCount<=63)) ret=Math.floor((10.447*SliceILvl+0.8)*SliceCount+0.500000000001);
        else if ((SliceILvl>=64) && (SliceCount<=79)) ret=Math.floor((10.447*SliceILvl+1.1)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((10.29*SliceILvl+14.2)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.41*SliceILvl+1084.36)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=79) ret=Math.floor((8.18*SliceILvl+1)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((8*SliceILvl+18)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((3.428571429*SliceILvl+872.857142777)*SliceCount+0.500000000001);
      }
    }
    return ret;
  }

  private double getMediumArmour(int SliceILvl, EquipmentLocation slot, ItemQuality quality, double SliceCount)
  {
    double ret=0.0;
    if (slot==EquipmentLocation.HEAD)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(4.3605*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((4.5*SliceILvl-12)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((3.3*SliceILvl+206.4)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(3.922*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((4.05*SliceILvl-10.468)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((2.97*SliceILvl+186.092)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(4.131*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((4.275*SliceILvl-11.05)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((3.135*SliceILvl+196.43)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=10) ret=Math.floor((2.9*SliceILvl-1)*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor((3.937*SliceILvl-12.5)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((4.05*SliceILvl-22.618)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((2.97*SliceILvl+177.182)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.SHOULDER)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(4.3605*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((4.5*SliceILvl-12)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((3.3*SliceILvl+206.4)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(3.922*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((4.05*SliceILvl-10.468)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((2.97*SliceILvl+186.092)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(4.131*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((4.275*SliceILvl-11.05)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((3.135*SliceILvl+196.43)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=10) ret=Math.floor((2.9*SliceILvl-1)*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor((3.922*SliceILvl-11.7)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((4.05*SliceILvl-22.618)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((2.97*SliceILvl+177.182)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.CHEST)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(14.535*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((15*SliceILvl-37)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((11*SliceILvl+691)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(13.0815*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((13.51485*SliceILvl-33.816)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((9.91089*SliceILvl+622.10472)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(13.77*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((14.22*SliceILvl-35.58)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((10.428*SliceILvl+654.564)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=10) ret=Math.floor((8.9*SliceILvl+2.4)*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor((13.084*SliceILvl-39.3)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((13.51485*SliceILvl-74.36055)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((9.91089*SliceILvl+592.37205)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.HAND)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(7.2675*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((7.5*SliceILvl-19)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((5.5*SliceILvl+345)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(6.54075*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((6.765*SliceILvl-17.174)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.961*SliceILvl+311.154)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(6.885*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((7.11*SliceILvl-18.05)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((5.214*SliceILvl+327.022)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=10) ret=Math.floor(4.625*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor((6.545*SliceILvl-19.5)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((6.765*SliceILvl-37.469)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((4.961*SliceILvl+296.271)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.LEGS)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(12.1125*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((12.51*SliceILvl-31.35)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((9.174*SliceILvl+575.802)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=10) ret=Math.floor(10.895*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor(10.895*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((11.25*SliceILvl-28.22)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((8.25*SliceILvl+517.78)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(11.475*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((11.85*SliceILvl-29.725)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((8.69*SliceILvl+545.395)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=10) ret=Math.floor((7.65*SliceILvl-0.4)*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor((10.9*SliceILvl-32.6)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((11.25*SliceILvl-61.97)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((8.25*SliceILvl+493.03)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.FEET)
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(5.814*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((6*SliceILvl-15)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.4*SliceILvl+276.2)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=79) ret=Math.floor(5.229818182*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((5.4*SliceILvl-13.784)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((3.96*SliceILvl+248.296)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=79) ret=Math.floor(5.508*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((5.7*SliceILvl-14.55)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((4.18*SliceILvl+262.09)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=10) ret=Math.floor((3.65*SliceILvl+0.85)*SliceCount+0.500000000001);
        else if ((SliceILvl>=11) && (SliceCount<=79)) ret=Math.floor((5.235*SliceILvl-15.5)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((5.4*SliceILvl-29.984)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((3.96*SliceILvl+236.416)*SliceCount+0.500000000001);
      }
    }
    else if (slot==EquipmentLocation.OFF_HAND) // Shield
    {
      if (quality==ItemQuality.LEGENDARY) // Gold
      {
        if (SliceILvl<=79) ret=Math.floor(16.049722222*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((15.9*SliceILvl+15)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((6.8*SliceILvl+1671.2)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.RARE) // Purple
      {
        if (SliceILvl<=13) ret=Math.floor(9.9*SliceILvl*SliceCount+0.500000000001);
        else if ((SliceILvl>=14) && (SliceCount<=79)) ret=Math.floor((14.46*SliceILvl+1)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((14.245*SliceILvl+19.8)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((6.105*SliceILvl+1501.28)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.INCOMPARABLE) // Teal
      {
        if (SliceILvl<=49) ret=Math.floor((15.2*SliceILvl+1.5)*SliceCount+0.500000000001);
        else if ((SliceILvl>=50) && (SliceCount<=59)) ret=Math.floor((15.4*SliceILvl-9)*SliceCount+0.500000000001);
        else if ((SliceILvl>=60) && (SliceCount<=79)) ret=Math.floor((15.205*SliceILvl+2.355)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=182)) ret=Math.floor((15.05*SliceILvl+21)*SliceCount+0.500000000001);
        else if (SliceCount>=183) ret=Math.floor((6.45*SliceILvl+1586.2)*SliceCount+0.500000000001);
      }
      else if (quality==ItemQuality.UNCOMMON) // Yellow
      {
        if (SliceILvl<=15) ret=Math.floor((8.9*SliceILvl+0.9)*SliceCount+0.500000000001);
        else if ((SliceILvl>=16) && (SliceCount<=30)) ret=Math.floor((13.05*SliceILvl-0.25)*SliceCount+0.500000000001);
        else if ((SliceILvl>=31) && (SliceCount<=79)) ret=Math.floor((14.485*SliceILvl-44)*SliceCount+0.500000000001);
        else if ((SliceILvl>=80) && (SliceCount<=185)) ret=Math.floor((14.245*SliceILvl-22.935)*SliceCount+0.500000000001);
        else if (SliceCount>=186) ret=Math.floor((6.105*SliceILvl+1482.965)*SliceCount+0.500000000001);
      }
    }
    return ret;
  }
}
