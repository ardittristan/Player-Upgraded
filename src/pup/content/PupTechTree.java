package pup.content;

import arc.struct.Seq;
import mindustry.content.Blocks;
import mindustry.content.TechTree;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.ContentList;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives.Objective;
import mindustry.game.Objectives.Produce;
import mindustry.type.ItemStack;

public class PupTechTree implements ContentList {
  private static TechNode context = null;

  @Override
  public void load() {

    attachNode(Blocks.coreShard, () -> node(PupBlocks.upgradeIota));

  }

  private static void attachNode(UnlockableContent parent, Runnable children){
    context = TechTree.all.find(t -> t.content == parent);
    children.run();
  }

  private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives, Runnable children){
    TechNode node = new TechNode(context, content, requirements);
    if(objectives != null) node.objectives = objectives;
    TechNode prev = context;
    context = node;
    children.run();
    context = prev;
  }

  private static void node(UnlockableContent content, ItemStack[] requirements, Runnable children){
    node(content, requirements, null, children);
  }

  private static void node(UnlockableContent content, Seq<Objective> objectives, Runnable children){
    node(content, content.researchRequirements(), objectives, children);
  }

  private static void node(UnlockableContent content, Runnable children){
    node(content, content.researchRequirements(), children);
  }

  private static void node(UnlockableContent block){
    node(block, () -> {});
  }

  private static void nodeProduce(UnlockableContent content, Seq<Objective> objectives, Runnable children){
    node(content, content.researchRequirements(), objectives.and(new Produce(content)), children);
  }

  private static void nodeProduce(UnlockableContent content, Runnable children){
    nodeProduce(content, new Seq<>(), children);
  }
}
