package ru.itmo.wp.lesson8.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Pavel Lymar
 */
public class ToggleDisabledForm {
    @Pattern(regexp = "Enabled|Disabled")
    private String currentValue;

    private long id;

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
