package com.ti.fabricadosaber.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = User.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public static final String TABLE_NAME = "user";

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name", length = 45, nullable = false)
    @NotBlank
    @Size(min = 5, max = 45)
    private String fullName;

    @Column
    private String email;

    @Column(name = "password", length = 45, nullable = false, updatable = false)
    @NotBlank
    @Size(min = 6, max = 45)
//	A notacao @JsonProperty.Acess.WRITE_ONLY define que o password seja apenas inserido no banco de dados
//	Em momento algum ele sera retornado por meio de uma requisicao GET da API, por exemplo
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column
    private LocalDate createDate;

}
