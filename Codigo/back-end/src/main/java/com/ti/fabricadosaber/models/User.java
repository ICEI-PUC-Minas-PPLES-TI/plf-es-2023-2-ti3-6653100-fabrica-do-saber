package com.ti.fabricadosaber.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@Column
    private String fullName;
	@Column
    private String email;
	@Column
    private String password;
	@Column
    private LocalDate createDate;
	
}
