package pup.logic;

import static mindustry.Vars.control;
import static mindustry.Vars.net;

import arc.ApplicationListener;
import arc.Events;
import mindustry.content.Fx;
import mindustry.game.EventType.UnitControlEvent;
import mindustry.gen.BlockUnitc;
import pup.world.blocks.PupUpgradeBlock.UpgradeBuilding;

public class PupUpgradeHandler implements ApplicationListener {
  public void load() {
    Events.on(
        UnitControlEvent.class,
        event -> {
          if (event.unit instanceof BlockUnitc) {
            BlockUnitc block = (BlockUnitc) event.unit;
            if (block.tile() instanceof UpgradeBuilding) {
              UpgradeBuilding build = (UpgradeBuilding) block.tile();

              Fx.spawn.at(event.player);
              if (net.client()) {
                control.input.controlledType = null;
              }

              event.player.clearUnit();
              event.player.deathTimer = 61f;
              build.requestSpawn(event.player);
            }
          }
        });
  }
}
