package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.*;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindByNumberCommand extends Command {

    public static final String COMMAND_WORD = "find num";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all persons whose number matches the keyed in digits "
            + "and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " 87349023";

    private final String keyDigits;

    public FindByNumberCommand(String keyDigits) {
        this.keyDigits = keyDigits;
    }

    /**
     * Returns copy of keywords in this command.
     */
//    public Set<String> getKeywords() {
//        return new HashSet<>(keywords);
//    }

    @Override
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithNumberContainingAnyKeyword(keyDigits);
        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }

    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithNumberContainingAnyKeyword(String keyDigits) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            if (person.getPhone().equals(keyDigits)) {
                matchedPersons.add(person);
            }
        }
        return matchedPersons;
    }

}
