package pup.content;

import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import pup.world.blocks.PupUpgradeBlock;

public class PupBlocks implements ContentList {
  public static Block upgradeIota;

  @Override
  public void load() {
    upgradeIota = new PupUpgradeBlock("upgrade-iota") {
      {
        requirements(Category.effect, ItemStack
            .with(Items.copper, 8000, Items.lead, 8000, Items.silicon, 5000, Items.thorium, 4000));
        unitType = PupUnitTypes.iota;
        health = 6000;
        size = 5;
        researchCostMultiplier = 0.06f;
        buildCostMultiplier = 0.06f;
        researchRequirements = ItemStack
            .with(Items.copper, 50000, Items.lead, 25000, Items.thorium, 4000);
      }
    };
  }
}
