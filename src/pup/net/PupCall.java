package pup.net;

import arc.util.io.ReusableByteOutStream;
import arc.util.io.Writes;
import arc.util.pooling.Pools;
import java.io.DataOutputStream;
import mindustry.Vars;
import mindustry.gen.Player;
import mindustry.io.TypeIO;
import mindustry.net.Net.SendMode;
import mindustry.net.Packets.InvokePacket;
import mindustry.world.Tile;
import pup.world.blocks.PupUpgradeBlock;

public class PupCall {
  private static final ReusableByteOutStream OUT = new ReusableByteOutStream(8192);
  private static final Writes WRITE;

  public PupCall() {}

  public static void playerUpgradeSpawn(Tile tile, Player player) {
    if (Vars.net.server() || !Vars.net.active()) {
      PupUpgradeBlock.playerUpgradeSpawn(tile, player);
    }

    if (Vars.net.server()) {
      InvokePacket packet = Pools.obtain(InvokePacket.class, InvokePacket::new);
      packet.priority = 0;
      packet.type = 32;
      OUT.reset();
      TypeIO.writeTile(WRITE, tile);
      TypeIO.writeEntity(WRITE, player);
      packet.bytes = OUT.getBytes();
      packet.length = OUT.size();
      Vars.net.send(packet, SendMode.tcp);
    }
  }

  static {
    WRITE = new Writes(new DataOutputStream(OUT));
  }
}
