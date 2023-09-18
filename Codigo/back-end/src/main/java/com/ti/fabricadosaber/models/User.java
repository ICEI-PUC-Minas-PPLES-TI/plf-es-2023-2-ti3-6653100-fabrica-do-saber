package com.ti.fabricadosaber.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "id", unique = true )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", length = 45, nullable = false)
    @NotBlank
    @Size(min = 5, max = 45)
    private String fullName;

    @Column(name = "email", length = 45, nullable = false, unique = true)
    @Email(message = "E-mail inválido")
    private String email;


    @Column(name = "password", length = 45, nullable = false, updatable = false)
    @NotBlank
    @Size(min = 6, max = 45)
//	A notacao @JsonProperty.Acess.WRITE_ONLY define que o password seja apenas inserido no banco de dados
//	Em momento algum ele sera retornado por meio de uma requisicao GET da API, por exemplo
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "create_date", length = 8, nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate createDate;

}
