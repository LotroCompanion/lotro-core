package delta.games.lotro.character.status.effects;

import java.util.Date;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.id.InternalGameId;

/**
 * Effect instance.
 * @author DAM
 */
public class EffectInstance implements Identifiable
{
  private Effect _effect;
  private Float _spellcraft;
  private Long _castTime;
  private InternalGameId _casterID;

  /**
   * Constructor.
   */
  public EffectInstance()
  {
    _effect=null;
    _spellcraft=null;
    _castTime=null;
    _casterID=null;
  }

  /**
   * Constructor.
   * @param effect Managed effect.
   */
  public EffectInstance(Effect effect)
  {
    _effect=effect;
    _spellcraft=null;
    _castTime=null;
    _casterID=null;
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public EffectInstance(EffectInstance source)
  {
    copyFrom(source);
  }

  /**
   * Copy data from the given source.
   * @param source Source to copy.
   */
  public void copyFrom(EffectInstance source)
  {
    _effect=source._effect;
    _spellcraft=source._spellcraft;
    _castTime=source._castTime;
    _casterID=source._casterID;
  }

  @Override
  public int getIdentifier()
  {
    return _effect.getIdentifier();
  }

  /**
   * Get the managed effect.
   * @return the managed effect.
   */
  public Effect getEffect()
  {
    return _effect;
  }

  /**
   * Set the effect.
   * @param effect Effect to set.
   */
  public void setEffect(Effect effect)
  {
    _effect=effect;
  }

  /**
   * Get the effect spellcraft.
   * @return A spellcraft value or <code>null</code>.
   */
  public Float getSpellcraft()
  {
    return _spellcraft;
  }

  /**
   * Set the spellcraft value.
   * @param spellcraft the spellcraft to set.
   */
  public void setSpellcraft(Float spellcraft)
  {
    _spellcraft=spellcraft;
  }

  /**
   * Get the cast time.
   * @return the cast time (ms since Epoch) or <code>null</code>.
   */
  public Long getCastTime()
  {
    return _castTime;
  }

  /**
   * Set the cast time.
   * @param castTime the cast time to set (ms since Epoch) or <code>null</code>.
   */
  public void setCastTime(Long castTime)
  {
    _castTime=castTime;
  }

  /**
   * Get the ID of the caster.
   * @return A caster ID or <code>null</code>.
   */
  public InternalGameId getCasterID()
  {
    return _casterID;
  }

  /**
   * Set the caster ID.
   * @param casterID the ID to set (may be <code>null</code>).
   */
  public void setCasterID(InternalGameId casterID)
  {
    _casterID=casterID;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Effect instance: ");
    if (_effect!=null)
    {
      sb.append(_effect);
    }
    if (_spellcraft!=null)
    {
      sb.append(" spellcraft: ").append(_spellcraft);
    }
    if (_castTime!=null)
    {
      sb.append(" cast time: ").append(_castTime).append(" (").append(new Date(_castTime.longValue())).append(')');
    }
    if (_casterID!=null)
    {
      sb.append(" caster: ").append(_casterID.asDisplayableString());
    }
    return sb.toString();
  }
}
