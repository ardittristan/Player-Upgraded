package pup;

import mindustry.mod.*;
import pup.content.PupBlocks;
import pup.logic.PupUpgradeHandler;
import pup.content.PupTechTree;
import pup.content.PupUnitTypes;

public class PlayerUpgraded extends Mod {

  public PlayerUpgraded() {}

  @Override
  public void loadContent() {
    new PupUnitTypes().load();
    new PupBlocks().load();
    new PupTechTree().load();
    new PupUpgradeHandler().load();
  }
}
