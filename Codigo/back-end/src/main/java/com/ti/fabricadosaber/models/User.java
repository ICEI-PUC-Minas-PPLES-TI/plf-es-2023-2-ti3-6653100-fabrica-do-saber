package com.ti.fabricadosaber.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ti.fabricadosaber.models.enums.ProfileEnum;
import jakarta.persistence.*;
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


    @Column(name = "password", length = 100, nullable = false)
    @NotBlank
    @Size(min = 6)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "create_date", length = 8, nullable = true)
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate createDate;

    @ElementCollection(fetch = FetchType.EAGER)// Ao buscar o usuário, os perfis sempre vai vir
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "user_profile")
    @Column(name = "profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>();


    public Set<ProfileEnum> getProfile() {
        return this.profiles.stream().map(x -> ProfileEnum.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum usuarioEnum) {

        this.profiles.add(usuarioEnum.getCode());
    }

}
