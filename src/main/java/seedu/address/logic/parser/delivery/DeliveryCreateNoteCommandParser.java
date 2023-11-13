package seedu.address.logic.parser.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.logic.commands.delivery.DeliveryCreateNoteCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Note;

/**
 * Parses input arguments and creates a new DeliveryCreateNoteCommand object
 */
public class DeliveryCreateNoteCommandParser implements Parser<DeliveryCreateNoteCommand> {

    private static final Logger logger = Logger.getLogger(DeliveryCreateNoteCommandParser.class.getName());

    /**
     * Parses the given {@code String} of arguments in the context of the DeliveryCreateNoteCommand
     * and returns an DeliveryCreateNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeliveryCreateNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NOTE)
            || argMultimap.getPreamble().isEmpty()) {
            logger.warning("DeliveryCreateNoteCommand: missing either note or Delivery ID");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeliveryCreateNoteCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NOTE);

        int id;

        try {
            id = ParserUtil.parseId(argMultimap.getPreamble());
        } catch (ParseException pe) {
            logger.warning("DeliveryCreateNoteCommand: delivery ID must be an integer more than 0.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeliveryCreateNoteCommand.MESSAGE_USAGE), pe);
        }

        assert id > 0 : "Delivery ID must be an integer more than 0.";

        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());

        return new DeliveryCreateNoteCommand(id, note);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
