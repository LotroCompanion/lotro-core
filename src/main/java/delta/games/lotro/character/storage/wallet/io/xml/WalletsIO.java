package delta.games.lotro.character.storage.wallet.io.xml;

import java.io.File;

import delta.games.lotro.account.Account;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.storage.wallet.Wallet;

/**
 * Wallet I/O methods.
 * @author DAM
 */
public class WalletsIO
{
  /**
   * Load wallet for a character.
   * @param character Parent character.
   * @return A wallet.
   */
  public static Wallet loadCharacterWallet(CharacterFile character)
  {
    Wallet wallet=new Wallet();
    File walletFile=getWalletFile(character);
    if (walletFile.canRead())
    {
      WalletXMLParser.parseWalletXML(walletFile,wallet);
    }
    return wallet;
  }

  /**
   * Write wallet.
   * @param character Character to use.
   * @param wallet Wallet to write.
   * @return <code>true</code> if successfull, <code>false</code> otherwise.
   */
  public static boolean writeWallet(CharacterFile character, Wallet wallet)
  {
    File walletFile=getWalletFile(character);
    WalletXMLWriter writer=new WalletXMLWriter();
    boolean ok=writer.writeWallet(walletFile,wallet);
    return ok;
  }

  /**
   * Load shared wallet for an account.
   * @param account Parent account.
   * @param server Targeted server.
   * @return A wallet.
   */
  public static Wallet loadAccountSharedWallet(Account account, String server)
  {
    Wallet wallet=new Wallet();
    File sharedWalletFile=getSharedWalletFile(account,server);
    if (sharedWalletFile.canRead())
    {
      WalletXMLParser.parseWalletXML(sharedWalletFile,wallet);
    }
    return wallet;
  }

  /**
   * Write shared wallet.
   * @param account Account to use.
   * @param serverName Server name.
   * @param sharedWallet Wallet to write.
   * @return <code>true</code> if successfull, <code>false</code> otherwise.
   */
  public static boolean writeSharedWallet(Account account, String serverName, Wallet sharedWallet)
  {
    File sharedWalletFile=getSharedWalletFile(account,serverName);
    WalletXMLWriter writer=new WalletXMLWriter();
    boolean ok=writer.writeWallet(sharedWalletFile,sharedWallet);
    return ok;
  }


  private static File getWalletFile(CharacterFile character)
  {
    File rootDir=character.getRootDir();
    return new File(rootDir,"wallet.xml");
  }

  private static File getSharedWalletFile(Account account, String server)
  {
    File rootDir=account.getRootDir();
    File serverDir=new File(rootDir,server);
    return new File(serverDir,"sharedWallet.xml");
  }
}
