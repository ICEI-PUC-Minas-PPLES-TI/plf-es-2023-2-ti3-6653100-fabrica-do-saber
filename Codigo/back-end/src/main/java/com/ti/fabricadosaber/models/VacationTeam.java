package com.ti.fabricadosaber.models;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("vacation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VacationTeam extends Team{

    @Column(name = "start_date", length = 10, nullable = true, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate startDate;

    @Column(name = "end_date", length = 10, nullable = true, updatable = true)
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate endDate;


}
