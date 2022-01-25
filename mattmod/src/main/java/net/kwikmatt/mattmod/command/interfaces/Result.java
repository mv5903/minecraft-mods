package net.kwikmatt.mattmod.command.interfaces;

public interface Result {
    // Success Messages
    String CREATE_SUCCESS = getSectionCode(true) + "Timer Created Successfully";
    String PAUSE_SUCCESS = getSectionCode(true) + "Timer Paused Successfully";
    String RESUME_SUCCESS = getSectionCode(true) + "Timer Resumed Successfully";
    String REMOVE_SUCCESS = getSectionCode(true) + "Timer Removed Successfully";

    // Failure Message
    String CREATE_FAILURE = getSectionCode(false) + "There was an issue creating that timer. Check arguments and try again.";
    String PAUSE_FAILURE = getSectionCode(false) + "There was an issue pausing that timer. Check arguments and try again.";
    String RESUME_FAILURE = getSectionCode(false) + "There was an issue resuming that timer. Check arguments and try again.";
    String REMOVE_FAILURE = getSectionCode(false) + "There was an issue removing that timer. Check arguments and try again.";

    // Color of message, where green is success and red is failure
    static String getSectionCode(boolean isSuccess) {
        return isSuccess ? "§a" : "§c";
    }
}
