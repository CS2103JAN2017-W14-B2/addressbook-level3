package seedu.addressbook.data;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.person.UniquePersonList.*;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;
import java.util.*;

/**
 * Represents the entire address book. Contains the data of the address book.
 *
 * Guarantees:
 *  - Every tag found in every person will also be found in the tag list.
 *  - The tags in each person point to tag objects in the master list. (== equality)
 */
public class AddressBook {

    private final UniquePersonList allPersons;
    private final UniqueTagList allTags; // can contain tags not attached to any person

    public static AddressBook empty() {
        return new AddressBook();
    }

    /**
     * Creates an empty address book.
     */
    public AddressBook() {
        allPersons = new UniquePersonList();
        allTags = new UniqueTagList();
    }

    /**
     * Constructs an address book with the given data.
     * Also updates the tag list with any missing tags found in any person.
     *
     * @param persons external changes to this will not affect this address book
     * @param tags external changes to this will not affect this address book
     */
    public AddressBook(UniquePersonList persons, UniqueTagList tags) {
        this.allPersons = new UniquePersonList(persons);
        this.allTags = new UniqueTagList(tags);
        for (Person p : allPersons) {
            syncTagsWithMasterList(p);
        }
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #allTags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Person person) {
        final UniqueTagList personTags = person.getTags();
        allTags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : allTags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of person tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : personTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        person.setTags(new UniqueTagList(commonTagReferences));
    }

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #allTags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #allTags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person toAdd) throws DuplicatePersonException {
        syncTagsWithMasterList(toAdd);
        allPersons.add(toAdd);
    }

    /**
     * Checks if an equivalent person exists in the address book.
     */
    public boolean containsPerson(ReadOnlyPerson key) {
        return allPersons.contains(key);
    }

    /**
     * Removes the equivalent person from the address book.
     *
     * @throws PersonNotFoundException if no such Person could be found.
     */
    public void removePerson(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        allPersons.remove(toRemove);
    }

    /**
     * Clears all persons and tags from the address book.
     */
    public void clear() {
        allPersons.clear();
        allTags.clear();
    }

    /**
     * Defensively copied UniquePersonList of all persons in the address book at the time of the call.
     */
    public UniquePersonList getAllPersons() {
        return new UniquePersonList(allPersons);
    }

    /**
     * Defensively copied UniqueTagList of all tags in the address book at the time of the call.
     */
    public UniqueTagList getAllTags() {
        return new UniqueTagList(allTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.allPersons.equals(((AddressBook) other).allPersons)
                && this.allTags.equals(((AddressBook) other).allTags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(allPersons, allTags);
    }

    /**
     * Edits name of the person of the given index in address book
     * @throws IllegalValueException 
     */
	public void editPersonName(int targetIndex, String newName) throws PersonNotFoundException, IllegalValueException {
		allPersons.editName(targetIndex, newName);
		
	}
}
