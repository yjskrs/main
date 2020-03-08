package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.module.*;
import seedu.address.model.module.Memo;
import seedu.address.model.module.Module;
import seedu.address.model.tags.Tags;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Title title;
    private ModuleCode moduleCode;
    private Credits credits;
    private Memo memo;
    private Set<Tags> tags;

    public PersonBuilder() {
        title = new Title(DEFAULT_NAME);
        moduleCode = new ModuleCode(DEFAULT_PHONE);
        credits = new Credits(DEFAULT_EMAIL);
        memo = new Memo(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder( Module moduleToCopy ) {
        title = moduleToCopy.getTitle();
        moduleCode = moduleToCopy.getModuleCode();
        credits = moduleToCopy.getCredits();
        memo = moduleToCopy.getMemo();
        tags = new HashSet<>( moduleToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.title = new Title(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.memo = new Memo(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.moduleCode = new ModuleCode(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.credits = new Credits(email);
        return this;
    }

    public Module build() {
        return new Module( title, moduleCode, credits, memo, tags );
    }

}
