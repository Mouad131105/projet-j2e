package fr.uge.jee.ugeoverflow.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
@NoArgsConstructor
@Entity
@Table(name = "Notes")
public class Note {

    @Id
    @NotNull
    private String receiverUsername;

    @NotNull
    @ManyToOne
    private User author;

    @NotNull
    @Positive
    private long score = 0;
}