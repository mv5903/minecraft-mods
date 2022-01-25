package net.kwikmatt.mattmod.misc;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;
import org.apache.logging.log4j.util.StackLocatorUtil;

public class TextileLogger {
    //private final boolean isDev = FabricLoader.getInstance().isDevelopmentEnvironment();

    private final MessageFactory messageFactory;
    private final Logger logger;

    private final String prefix;
    private final MutableText prefixText;

    /*  public TextileLogger(String name, String prefix) {
            this.messageFactory = ParameterizedMessageFactory.INSTANCE;
            this.logger = LogManager.getLogger(name, messageFactory);
            this.prefix = "[" + prefix + "]" + " ";
            this.prefixText = new LiteralText(this.prefix).styled(style -> style.withColor(0x5B23DA));
        }
    */
    public TextileLogger(String prefix) {
        this.messageFactory = ParameterizedMessageFactory.INSTANCE;
        this.logger = LogManager.getLogger(StackLocatorUtil.getCallerClass(2), messageFactory);
        this.prefix = "[" + prefix + "]" + " ";
        this.prefixText = new LiteralText(this.prefix).styled(style -> style.withColor(0x5B23DA));
    }

    public MutableText getPrefixText() {
        return prefixText.shallowCopy();
    }

    public void log(Level level, String msg, Object... data) {
        logger.log(level, prefix + msg, data);
    }

    public void trace(String msg, Object... data) {
        log(Level.TRACE, msg, data);
    }

    public void debug(String msg, Object... data) {
        log(Level.DEBUG, msg, data);
    }

    public void info(String msg, Object... data) {
        log(Level.INFO, msg, data);
    }

    public void warn(String msg, Object... data) {
        log(Level.WARN, msg, data);
    }

    public void error(String msg, Object... data) {
        log(Level.ERROR, msg, data);
    }

    public void fatal(String msg, Object... data) {
        log(Level.FATAL, msg, data);
    }

    boolean sendFeedback(Level level, ServerCommandSource source, String msg, Object... args) {
        if(source != null && source.getEntity() instanceof PlayerEntity) {
            LiteralText text = new LiteralText(messageFactory.newMessage(msg, args).getFormattedMessage());

            if(level.intLevel() == Level.TRACE.intLevel()) text.formatted(Formatting.GREEN);
            else if(level.intLevel() <= Level.WARN.intLevel()) text.formatted(Formatting.RED);
            else text.formatted(Formatting.WHITE);

            source.sendFeedback(prefixText.shallowCopy().append(text), false);

            return true;
        } else {
            log(level, msg, args);

            return false;
        }
    }

    public void sendHint(ServerCommandSource source, String msg, Object... args) {
        sendFeedback(Level.TRACE, source, msg, args);
    }

    public void sendInfo(ServerCommandSource source, String msg, Object... args) {
        sendFeedback(Level.INFO, source, msg, args);
    }

    public void sendError(ServerCommandSource source, String msg, Object... args) {
        sendFeedback(Level.ERROR, source, msg, args);
    }

    public void sendToPlayerAndLog(Level level, ServerCommandSource source, String msg, Object... args) {
        if(sendFeedback(level, source, msg, args))
            log(level, msg, args);
    }

    //send info and log
    public void sendInfoAL(ServerCommandSource source, String msg, Object... args) {
        sendToPlayerAndLog(Level.INFO, source, msg, args);
    }

    public void sendErrorAL(ServerCommandSource source, String msg, Object... args) {
        sendToPlayerAndLog(Level.ERROR, source, msg, args);
    }
}