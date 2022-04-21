package net.kwikmatt.mattmod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.kwikmatt.mattmod.command.interfaces.BlockManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.server.command.CommandManager.literal;

public class OneBlock implements BlockManager {
    private static final String BLOCK_LOCATION = "100 100 100";
    private static BlockChecker activeChecker = null;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(literal("oneblock")
                .then(literal("start")
                        .executes(context -> {
                            activeChecker = new BlockChecker();
                            activeChecker.start();
                            return 1;
                        }))
                .then(literal("stop")
                        .executes(context -> {
                            setNewBlock(Blocks.BEDROCK);
                            activeChecker.shouldRun = false;
                            return 1;
                        }))
        );
    }

    public static void runCommand(String command) {
        MinecraftServer serverInstance = MinecraftClient.getInstance().getServer();
        if (serverInstance.getPlayerManager().getPlayerList().size() != 0) {
            // Run commands from first player in world
            ServerPlayerEntity firstPlayer = serverInstance.getPlayerManager().getPlayerList().get(0);
            serverInstance.getCommandManager().execute(firstPlayer.getCommandSource(), command);
        } else {
            throw new IllegalStateException("No players currently online!");
        }
    }

    public static void setNewBlock(Block block) {
        String str = block.toString();
        str = str.substring(str.indexOf("{") + 1, str.indexOf("}"));
        runCommand("setblock " + BLOCK_LOCATION + " " + str);
    }

    static class BlockChecker extends Thread {
        boolean shouldRun = false;
        final long OFFSET = 1000;

        public BlockChecker() {
            activeChecker = this;
        }

        public void run() {
            shouldRun = true;
            while (shouldRun) {
                try {
                    Thread.sleep(OFFSET);
                } catch (InterruptedException e) {}
                boolean canPlace = MinecraftClient.getInstance().getServer().getOverworld().isAir(new BlockPos(100, 100, 100));
                if (canPlace) {
                    Block blockToSet = LEVEL_ONE[(int) (Math.random() * LEVEL_ONE.length)];
                    setNewBlock(blockToSet);
                }
            }
        }
    }
}
