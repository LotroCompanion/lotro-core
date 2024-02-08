package delta.games.lotro.lore.buffs;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.requirements.UsageRequirement;

/**
 * Effect-based buff.
 * @author DAM
 */
public class EffectBuff implements Identifiable
{
  private String _key;
  private Effect _effect;
  private UsageRequirement _requirements;

  /**
   * Constructor.
   */
  public EffectBuff()
  {
    _key="";
    _effect=null;
    _requirements=new UsageRequirement();
  }

  /**
   * Get the identifier.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _effect!=null?_effect.getIdentifier():0;
  }

  /**
   * Get the key used to identify this buff.
   * @return a key (may be empty but never <code>null</code>).
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Set the key used to identify this buff.
   * @param key A key.
   */
  public void setKey(String key)
  {
    if (key==null) key="";
    _key=key;
  }

  /**
   * Get the associated effect.
   * @return an Effect or <code>null</code> if not set.
   */
  public Effect getEffect()
  {
    return _effect;
  }

  /**
   * Set the associated effect.
   * @param effect Effect to set.
   */
  public void setEffect(Effect effect)
  {
    _effect=effect;
  }

  /**
   * Get the usage requirements.
   * @return the usage requirements.
   */
  public UsageRequirement getUsageRequirements()
  {
    return _requirements;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Effect buff: effect=").append(_effect);
    sb.append(", requirements=").append(_requirements);
    return sb.toString();
  }
}
