package net.kwikmatt.mattmod.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.kwikmatt.mattmod.command.interfaces.Result;
import net.minecraft.network.MessageType;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class MattTimer extends Thread implements Result {

    // Command Registrations
    public static ArrayList<MattTimer> ACTIVE_TIMERS = new ArrayList<>();
    public static Scoreboard scoreboard = new Scoreboard();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(literal("timer")
            .then(literal("start")
                .then(argument("timerName", StringArgumentType.string())
                    .then(argument("hours", IntegerArgumentType.integer())
                        .then(argument("minutes", IntegerArgumentType.integer(0, 59))
                            .then(argument("seconds", IntegerArgumentType.integer(0, 59))
                                .executes(ctx -> {
                                    String timerName = StringArgumentType.getString(ctx, "timerName");
                                    int hours = IntegerArgumentType.getInteger(ctx, "hours");
                                    int minutes = IntegerArgumentType.getInteger(ctx, "minutes");
                                    int seconds = IntegerArgumentType.getInteger(ctx, "seconds");

                                    MattTimer timer = new MattTimer(timerName, ctx, hours, minutes, seconds);
                                    timer.start();
                                    ACTIVE_TIMERS.add(timer);
                                    ctx.getSource().sendFeedback(new LiteralText(CREATE_SUCCESS), true);
                                    return 1;
                                }))))))
            .then(literal("pause")
                .then(argument("timerName", StringArgumentType.string())
                    .executes(ctx -> {
                        MattTimer timer = getTimer(StringArgumentType.getString(ctx, "timerName"));
                        if (timer == null) {
                            ctx.getSource().sendFeedback(new LiteralText(PAUSE_FAILURE), true);
                        }
                        timer.stopTimer();
                        ctx.getSource().sendFeedback(new LiteralText(PAUSE_SUCCESS), true);
                        return 1;
                    })))
            .then(literal("getRemainingTime")
                .then(argument("timerName", StringArgumentType.string())
                    .executes(ctx -> {
                        MattTimer timer = getTimer(StringArgumentType.getString(ctx, "timerName"));
                        if (timer == null) {
                            ctx.getSource().sendFeedback(new LiteralText(Result.getSectionCode(false) + "Check your name. You provided a name that doesn't exist?"), true);
                        }

                        String timerName = timer.getTimerName();
                        int hours = timer.timeRemaining.getHour();
                        int minutes = timer.timeRemaining.getMinute();
                        int seconds = timer.timeRemaining.getSecond();

                        ctx.getSource().sendFeedback(new LiteralText(String.format("§a[§6%s§a]§b Time Remaining: %dh-%dm-%ds", timerName, hours, minutes, seconds)), true);
                        return 1;
                    })))
            .then(literal("resume")
                .then(argument("timerName", StringArgumentType.string())
                    .executes(ctx -> {
                        ctx.getSource().sendFeedback(new LiteralText("Before remove"), true);
                        ctx.getSource().sendFeedback(new LiteralText(ACTIVE_TIMERS.toString()), true);
                        MattTimer timer = getTimer(StringArgumentType.getString(ctx, "timerName"));
                        if (timer == null) {
                            ctx.getSource().sendFeedback(new LiteralText(RESUME_FAILURE), true);
                        }

                        assert timer != null;
                        LocalTime oldTime = timer.timeRemaining;
                        String oldTimerName = timer.timerName;


                        stopTimer(oldTimerName);

                        timer = new MattTimer(oldTimerName, ctx, oldTime.getHour(), oldTime.getMinute(), oldTime.getSecond());
                        timer.start();
                        ACTIVE_TIMERS.add(timer);

                        ctx.getSource().sendFeedback(new LiteralText("After remove"), true);
                        ctx.getSource().sendFeedback(new LiteralText(ACTIVE_TIMERS.toString()), true);

                        ctx.getSource().sendFeedback(new LiteralText(RESUME_SUCCESS), true);
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


    private static MattTimer getTimer(String timerName) {
        for (MattTimer timer: ACTIVE_TIMERS) {
            if (timer.getTimerName().equalsIgnoreCase(timerName)) {
                return timer;
            }
        }
        return null;
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
    private boolean isRunning;
    final private String timerName;
    private CommandContext<ServerCommandSource> context;
    private LocalTime timeRemaining;

    public MattTimer(String timerName, CommandContext<ServerCommandSource> context, int hours, int minutes, int seconds) {
        this.timerName = timerName;
        this.context = context;
        timeRemaining = LocalTime.of(hours, minutes, seconds);
        isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning && totalSecondsLeft() > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { e.printStackTrace(); }
            timeRemaining = timeRemaining.minusSeconds(1);
            checkTimeAnnouncement();
        }
        if (totalSecondsLeft() == 0) {
            sendMessage(context, "Timer Done!");
        }
    }

    private void checkTimeAnnouncement() {
        LocalTime[] announcementTimes = {
                LocalTime.of(12, 0, 0),
                LocalTime.of(6, 0, 0),
                LocalTime.of(2, 0, 0),
                LocalTime.of(1, 0, 0),
                LocalTime.of(0, 45, 0),
                LocalTime.of(0, 30, 0),
                LocalTime.of(0, 20, 0),
                LocalTime.of(0, 15, 0),
                LocalTime.of(0, 10, 0),
                LocalTime.of(0, 5, 0),
                LocalTime.of(0, 3, 0),
                LocalTime.of(0, 2, 0),
                LocalTime.of(0, 1, 0),
                LocalTime.of(0, 0, 30),
                LocalTime.of(0, 0, 15),
                LocalTime.of(0, 0, 10),
                LocalTime.of(0, 0, 5),
                LocalTime.of(0, 0, 4),
                LocalTime.of(0, 0, 3),
                LocalTime.of(0, 0, 2),
                LocalTime.of(0, 0, 1)
        };

        for (LocalTime announcementTime: announcementTimes) {
            if (announcementTime.compareTo(timeRemaining) == 0) {
                sendMessage(context, String.format("§a[§6%s§a]§b Time Remaining: %dh-%dm-%ds", timerName, timeRemaining.getHour(), timeRemaining.getMinute(), timeRemaining.getSecond()));
                break;
            }
        }
    }

    private long totalSecondsLeft() {
        return timeRemaining.getHour() * 3600 + timeRemaining.getMinute() * 60 + timeRemaining.getSecond();
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

    public String toString() {
        return String.format("Name: %s - Time Remaining: %s", timerName, timeRemaining.toString());
    }

}
