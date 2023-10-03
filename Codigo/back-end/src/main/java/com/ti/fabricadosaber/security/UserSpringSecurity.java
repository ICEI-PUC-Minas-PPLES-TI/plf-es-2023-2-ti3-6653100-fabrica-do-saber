package com.ti.fabricadosaber.security;

import com.ti.fabricadosaber.models.enums.ProfileEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class UserSpringSecurity implements UserDetails {

   private Long id;
   private String fullName;
   private String email;
   private String password;
   private LocalDate creationDate;
   private Collection<? extends GrantedAuthority> authorities;


    public UserSpringSecurity(Long id, String fullName, String email, String password, LocalDate creationDate, Set<ProfileEnum> profileEnums) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.creationDate = creationDate;
        this.authorities =
                profileEnums.stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toList());
    }


    @Override
    public boolean isAccountNonExpired() {
        return true; // Garantindo não expiração de conta do usuário
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(ProfileEnum profileEnum) {
        return getAuthorities().contains(new SimpleGrantedAuthority(profileEnum.getDescription()));
    }
}
