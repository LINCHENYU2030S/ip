package Alex.commands;

import Alex.AlexException;
import Alex.ui.Ui;
import Alex.tasks.TaskList;

import java.time.format.DateTimeParseException;

/**
 * A class that extends from Commadn class. The instance of this class represents a command that is trying to
 * view the existing the class in the TaskList. It includes listing all the task(s) in the TaskList, listing all the
 * task(s) in a specific date.
 */
public class ViewCommand extends Command {
    private String command;
    private View viewType;

    /**
     * The constructor of ViewCommand class when the user input string and viewType is given.
     *
     * @param command The user input string.
     * @param viewType The type of view command which consist of "LISTALL", "LISTONEDAY".
     */
    public ViewCommand(String command, View viewType) {
        this.command = command;
        this.viewType = viewType;
    }

    /**
     * Override the method from Command class.
     * The execute method of ViewCommand class is to actually print all the task(s) in the TaskList or
     * the task(s) of a specific date to the standard output.
     */
    @Override
    public String execute() {
        String output = null;
        switch(this.viewType) {
        case LISTALL:
            output = TaskList.getAllContent();
            return output;

        case LISTONEDAY:
            try {
                output = TaskList.getTaskForDate(command);
                return output;
            } catch (DateTimeParseException e) {
                output = Ui.getAlertForDate();
                return output;
            } finally {
                break;
            }

        case FIND:
            try {
                if (command.length() == 4 || !command.substring(4, 5).equals(" ")) {
                    throw new AlexException("");
                }
                assert command.substring(4, 5).equals(" ") : "There should be space after find keyword";
                String toMatch = command.substring(5).stripTrailing();
                output = TaskList.getTaskForMatchWord(toMatch);
                return output;
            } catch (AlexException e) {
                output = Ui.getAlertForFind();
                return output;
            } finally {
                break;
            }
        }
        return output;

    }

}
