package delta.games.lotro.character.storage;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.storage.bags.BagsManager;
import delta.games.lotro.character.storage.bags.io.BagsIo;
import delta.games.lotro.character.storage.vaults.Vault;
import delta.games.lotro.character.storage.vaults.io.VaultsIo;
import delta.games.lotro.character.storage.wallet.Wallet;
import delta.games.lotro.character.storage.wallet.io.xml.WalletsIO;

/**
 * Storage I/O methods.
 * @author DAM
 */
public class StoragesIO
{
  /**
   * Load character storage.
   * @param character Parent character.
   * @return the loaded storage.
   */
  public static CharacterStorage loadCharacterStorage(CharacterFile character)
  {
    // Vault
    Vault ownVault=VaultsIo.load(character);
    // Bags
    BagsManager bagsManager=BagsIo.load(character);
    // Wallet
    Wallet wallet=WalletsIO.loadCharacterWallet(character);
    CharacterStorage storage=new CharacterStorage(ownVault,bagsManager,wallet);
    return storage;
  }
}
