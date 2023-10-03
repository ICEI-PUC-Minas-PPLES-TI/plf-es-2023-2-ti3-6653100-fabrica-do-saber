package com.ti.fabricadosaber.services;


import com.ti.fabricadosaber.models.User;
import com.ti.fabricadosaber.models.enums.ProfileEnum;
import com.ti.fabricadosaber.repositories.UserRepository;
import com.ti.fabricadosaber.services.exceptions.DataBindingViolationException;
import com.ti.fabricadosaber.services.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public User findById(Long id) {
        // lidando com null usando Optional
        Optional<User> user = this.userRepository.findById(id);

        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! id: " + id + ", Tipo: " + User.class.getName()
        ));
    }


    @Transactional
    public User create(User obj) {
        obj.setId(null);
        // Encriptando a senha:
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(ProfileEnum.ADMIN.getCode()).collect(Collectors.toSet()));
        obj.setCreateDate(LocalDate.now());
        obj = this.userRepository.save(obj);
        return obj;
    }

    // Atualizando somente a senha
    @Transactional
    public User update(User obj) {
        User existingUser = findById(obj.getId());
        String[] ignoreProperties = new String[] {"id", "profiles","createDate","password"};

        // Copia as propriedades não nulas do updatedStudent para o existingUser
        BeanUtils.copyProperties(obj, existingUser, ignoreProperties);
        obj.setProfiles(Stream.of(ProfileEnum.ADMIN.getCode()).collect(Collectors.toSet()));
        existingUser.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        existingUser.setCreateDate(LocalDate.now());
        return this.userRepository.save(existingUser);
    }


    public void delete(Long id) {
        User user = findById(id);
        // caso a entidade esteja relacionada a outra:
        try{
            this.userRepository.delete(user);
        } catch (Exception error) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas");
        }
    }

}
