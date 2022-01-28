package ru.itmo.wp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Pavel Lymar
 */
public class PostForm {
    @NotNull
    @NotEmpty
    @Size(max = 60)
    private String title;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String text;

    private String tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
