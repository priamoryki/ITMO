package ru.itmo.wp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Pavel Lymar
 */
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 32)
    private String name;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
