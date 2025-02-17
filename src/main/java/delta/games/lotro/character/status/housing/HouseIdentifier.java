package delta.games.lotro.character.status.housing;

import java.util.Objects;

/**
 * House identifier: server+address.
 * @author DAM
 */
public class HouseIdentifier
{
  private String _server;
  private HouseAddress _address; 

  /**
   * Constructor.
   * @param server Server name.
   * @param address Address.
   */
  public HouseIdentifier(String server, HouseAddress address)
  {
    _server=server;
    _address=address;
  }

  /**
   * Get the server name.
   * @return a server name.
   */
  public String getServer()
  {
    return _server;
  }

  /**
   * Get the house address.
   * @return the house address.
   */
  public HouseAddress getAddress()
  {
    return _address;
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(_address,_server);
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this==obj) return true;
    if (obj==null) return false;
    if (getClass()!=obj.getClass()) return false;
    HouseIdentifier other=(HouseIdentifier)obj;
    return Objects.equals(_address,other._address)&&Objects.equals(_server,other._server);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("House identifier: server=");
    sb.append(_server);
    sb.append(", address=");
    sb.append(_address);
    return sb.toString();
  }
}
