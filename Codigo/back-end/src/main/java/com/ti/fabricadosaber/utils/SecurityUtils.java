package com.ti.fabricadosaber.utils;

import com.ti.fabricadosaber.security.UserSpringSecurity;
import com.ti.fabricadosaber.models.enums.ProfileEnum;
import com.ti.fabricadosaber.services.exceptions.AuthorizationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtils {

    public static UserSpringSecurity authenticated() {
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean userIsAdmin(UserSpringSecurity userSpringSecurity) {
        return userSpringSecurity.hasRole(ProfileEnum.ADMIN);
    }


    public static UserSpringSecurity checkUser() {

        UserSpringSecurity userSpringSecurity = authenticated();


        if(Objects.isNull(userSpringSecurity)) {
            throw new AuthorizationException("Usuário não logado");
        }
        if(!userIsAdmin(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");


        return userSpringSecurity;
    }

}

/*o método authenticated é usado para obter o usuário autenticado, enquanto o método checkUser é usado para verificar se o usuário autenticado possui permissões específicas, como o perfil de administrador. Ambos são partes importantes de um sistema de segurança para controlar o acesso às funcionalidades com base nas permissões do usuário autenticado.*/
