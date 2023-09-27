package com.ti.fabricadosaber.services;


import com.ti.fabricadosaber.models.User;
import com.ti.fabricadosaber.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User findById(Long id) {
        // lidando com null usando Optional
        Optional<User> user = this.userRepository.findById(id);

        return user.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado! id: " + id + ", Tipo: " + User.class.getName()
        ));
    }


    @Transactional
    public User create(User obj) {
        obj.setId(null); //prevenindo de pessoas mal intencionadas
        obj = this.userRepository.save(obj);
        return obj;
    }

    // Atualizando somente a senha
    @Transactional
    public User update(User obj) {
        User existingUser = findById(obj.getId());

        // Copia as propriedades não nulas do updatedStudent para o existingUser
        BeanUtils.copyProperties(obj, existingUser, "id");

        return this.userRepository.save(existingUser);
    }


    public void delete(Long id) {
        User user = findById(id);
        // caso a entidade esteja relacionada a outra:
        try{
            this.userRepository.delete(user);
        } catch (Exception error) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas");
        }
    }

}
