package pup.world.blocks;

import static mindustry.Vars.net;
import static mindustry.Vars.state;

import arc.math.Mathf;
import arc.struct.EnumSet;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.UnitTypes;
import mindustry.entities.TargetPriority;
import mindustry.gen.BlockUnitc;
import mindustry.gen.Building;
import mindustry.gen.Nulls;
import mindustry.gen.Player;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.Tile;
import mindustry.world.blocks.ControlBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.BlockGroup;
import pup.content.PupUnitTypes;
import pup.net.PupCall;

public class PupUpgradeBlock extends StorageBlock {

  public UnitType unitType = PupUnitTypes.iota;

  public PupUpgradeBlock(String name) {
    super(name);

    solid = true;
    priority = TargetPriority.turret;
    flags = EnumSet.of(BlockFlag.unitModifier);
    unitCapModifier = 10;
    loopSound = Sounds.hum;
    loopSoundVolume = 1f;
    group = BlockGroup.none;
  }

  public static void playerUpgradeSpawn(Tile tile, Player player) {
    if (player == null || tile == null || !(tile.build instanceof UpgradeBuilding)) return;

    PupUpgradeBlock block = (PupUpgradeBlock) tile.block();
    Fx.spawn.at(tile.build);

    player.set(tile.build);

    if (!net.client()) {
      Unit unit = block.unitType.create(tile.team());
      unit.set(tile.build);
      unit.rotation(90f);
      unit.impulse(0f, 3f);
      unit.controller(player);
      unit.spawnedByCore(true);
      unit.add();
    }

    if (state.isCampaign() && player == Vars.player) {
      block.unitType.unlock();
    }
  }

  public ItemStack[] researchRequirements = ItemStack.empty;

  @Override
  public ItemStack[] researchRequirements() {
    if (researchRequirements.length != 0) {
      return researchRequirements;
    }

    return super.researchRequirements();
  }

  public class UpgradeBuilding extends Building implements ControlBlock {
    // note that this unit is never actually used for control; the possession handler makes the player respawn when this unit is controlled
    public BlockUnitc unit = Nulls.blockUnit;

    public void requestSpawn(Player player) {
      PupCall.playerUpgradeSpawn(tile, player);
    }

    @Override
    public void created() {
      unit = (BlockUnitc) UnitTypes.block.create(team);
      unit.tile(this);
    }

    @Override
    public Unit unit() {
      return (Unit) unit;
    }

    @Override
    public void drawLight() {
      Drawf.light(team, x, y, 30f + 20f * size, Pal.accent, 0.65f + Mathf.absin(20f, 0.1f));
    }
  }
}
