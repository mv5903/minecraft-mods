package net.kwikmatt.mattmod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.kwikmatt.mattmod.command.interfaces.Result;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;
import java.util.UUID;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class MattTimer extends Thread implements Result {

    // Command Registrations
    public static ArrayList<MattTimer> ACTIVE_TIMERS = new ArrayList<>();

    public static void registerMattCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("timer")
            .then(literal("start")
                .then(argument("timerName", StringArgumentType.string())
                    .then(argument("seconds", LongArgumentType.longArg())
                        .executes(ctx -> {
                            String timerName = StringArgumentType.getString(ctx, "timerName");
                            long timerLength = LongArgumentType.getLong(ctx, "seconds");

                            ctx.getSource().sendFeedback(new LiteralText(CREATE_SUCCESS), true);

                            MattTimer timer = new MattTimer(timerLength, timerName, ctx);
                            timer.start();
                            ACTIVE_TIMERS.add(timer);
                            return 1;
                        }))))
            .then(literal("pause")
                .then(argument("timerName", StringArgumentType.string())
                    .executes(ctx -> {
                        ctx.getSource().sendFeedback(new LiteralText("Command Not Implemented Yet"), true);
                        return 1;
                    })))
            .then(literal("resume")
                .then(argument("timerName", StringArgumentType.string())
                    .executes(ctx -> {
                        ctx.getSource().sendFeedback(new LiteralText("Command Not Implemented Yet"), true);
                        return 1;
                    })))
            .then(literal("remove")
                .then(argument("timerName", StringArgumentType.string())
                    .executes(ctx -> {
                        String timerToStop = StringArgumentType.getString(ctx, "timerName");
                        String result = stopTimer(timerToStop) ? REMOVE_SUCCESS : REMOVE_FAILURE;

                        ctx.getSource().sendFeedback(new LiteralText(result), true);
                        return result.equals(REMOVE_SUCCESS) ? 0 : 1;
                    }))));
    }

    private static boolean stopTimer(String timerName) {
        boolean foundTimer = false;
        int timerToRemove = 0;
        for (int i = 0; i < ACTIVE_TIMERS.size(); i++) {
            if (ACTIVE_TIMERS.get(i).getTimerName().equals(timerName)) {
                timerToRemove = i;
                foundTimer = true;
                break;
            }
        }
        if (!foundTimer) {
            return false;
        }
        ACTIVE_TIMERS.get(timerToRemove).stopTimer();
        ACTIVE_TIMERS.remove(timerToRemove);
        return true;
    }

    // Timer Creations
    private long totalSeconds;
    private boolean isRunning;
    private String timerName;
    private CommandContext<ServerCommandSource> context;

    public MattTimer(long totalSeconds, String timerName, CommandContext<ServerCommandSource> context) {
        this.context = context;
        this.totalSeconds = totalSeconds;
        this.timerName = timerName;
        isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning && totalSeconds >= 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { e.printStackTrace(); }
            sendMessage(context, timerName + " Time Remaining: " + totalSeconds);
            totalSeconds--;
        }
        if (totalSeconds == -1) {
            sendMessage(context, "Timer Done!");
        }
    }

    public void stopTimer() {
        isRunning = false;
    }

    public String getTimerName() {
        return timerName;
    }

    public static int sendMessage(CommandContext<ServerCommandSource> context, String message) {
        MinecraftServer server = context.getSource().getWorld().getServer();
        server.getPlayerManager().broadcast(new LiteralText(message), MessageType.SYSTEM, UUID.randomUUID());
        return Command.SINGLE_SUCCESS;
    }

}
