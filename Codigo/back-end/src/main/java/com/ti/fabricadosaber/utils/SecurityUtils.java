package com.ti.fabricadosaber.utils;

import com.ti.fabricadosaber.security.UserSpringSecurity;
import com.ti.fabricadosaber.models.enums.ProfileEnum;
import com.ti.fabricadosaber.services.exceptions.AuthorizationException;

import java.util.Objects;

public class SecurityUtils {

    public static boolean userIsAdmin(UserSpringSecurity userSpringSecurity) {
        return userSpringSecurity.hasRole(ProfileEnum.ADMIN);
    }


    public static void checkUser(UserSpringSecurity userSpringSecurity) {
        if(Objects.isNull(userSpringSecurity)) {
            throw new AuthorizationException("Usuário não logado");
        }
        if(!userIsAdmin(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");
    }

}
