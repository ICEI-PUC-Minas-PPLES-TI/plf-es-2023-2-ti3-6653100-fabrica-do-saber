package com.ti.fabricadosaber.models;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ti.fabricadosaber.enums.Grade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@DiscriminatorValue("vacation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VacationTeam extends Team{

    @Column(name = "start_date", length = 10, nullable = true, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @Column(name = "end_date", length = 10, nullable = true, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;


}
