package pup.content;

import mindustry.ai.types.BuilderAI;
import mindustry.annotations.Annotations.EntityDef;
import mindustry.content.Fx;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.UnitEntity;
import mindustry.gen.Unitc;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

public class PupUnitTypes implements ContentList {

  // air
  public static @EntityDef( value = {Unitc.class}, legacy = true) UnitType iota;

  @Override
  public void load() {
    iota =
        new UnitType("iota") {
          {
            constructor = UnitEntity::create;

            defaultController = BuilderAI::new;
            isCounted = false;

            flying = true;
            mineSpeed = 8f;
            mineTier = 2;
            buildSpeed = 1f;
            drag = 0.05f;
            speed = 3.55f;
            rotateSpeed = 19f;
            accel = 0.11f;
            itemCapacity = 70;
            health = 220f;
            engineOffset = 6f;
            hitSize = 11f;
            commandLimit = 5;

            weapons.add(
                new Weapon("small-mount-weapon") {
                  {
                    top = false;
                    reload = 15f;
                    x = 1f;
                    y = 2f;
                    shots = 2;
                    spacing = 2f;
                    inaccuracy = 3f;
                    shotDelay = 3f;
                    ejectEffect = Fx.casing1;

                    bullet =
                        new BasicBulletType(3.5f, 11) {
                          {
                            width = 6.5f;
                            height = 11f;
                            lifetime = 70f;
                            shootEffect = Fx.shootSmall;
                            smokeEffect = Fx.shootSmallSmoke;
                            buildingDamageMultiplier = 0.01f;
                            homingPower = 0.04f;
                          }
                        };
                  }
                });
          }
        };
  }
}
