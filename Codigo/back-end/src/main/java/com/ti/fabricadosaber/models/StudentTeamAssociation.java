package com.ti.fabricadosaber.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "student_team_association")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class StudentTeamAssociation {


    @EmbeddedId
    private StudentTeamId id = new StudentTeamId();


    @ManyToOne
    @MapsId("studentId")
    private Student student;


    @ManyToOne
    @MapsId("teamId")
    private Team team;


    @Column(name = "start_date", length = 10, nullable = true, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @Column(name = "end_date", length = 10, nullable = true, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;


    @Column(name = "is_active")
    private Boolean isActive;



    public StudentTeamAssociation(Student student, Team team) {
        this.student = student;
        this.team = team;
    }



    @Embeddable
    public static class StudentTeamId implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long studentId;

        private Long teamId;

        public StudentTeamId() {}

        public StudentTeamId(Long studentId, Long teamId) {
            super();
            this.studentId = studentId;
            this.teamId = teamId;
        }

    }


}
