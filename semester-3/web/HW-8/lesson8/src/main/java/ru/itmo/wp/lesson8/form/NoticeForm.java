package ru.itmo.wp.lesson8.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Pavel Lymar
 */
public class NoticeForm {
    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String noticeContent;

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
}
