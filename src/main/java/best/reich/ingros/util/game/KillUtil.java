package best.reich.ingros.util.game;

import best.reich.ingros.IngrosWare;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.concurrent.ConcurrentHashMap;

public class KillUtil {
        public static KillUtil INSTANCE;
        private ConcurrentHashMap<Entity, Integer> targets = new ConcurrentHashMap();

        public KillUtil() {
            IngrosWare.INSTANCE.bus.registerListener(this);
            INSTANCE = this;
        }

        public void addTarget(Entity player) {
            this.targets.put(player, 20);
        }

        public ConcurrentHashMap<Entity, Integer> getTargets() {
            return this.targets;
        }
}
