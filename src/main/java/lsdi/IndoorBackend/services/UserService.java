package lsdi.IndoorBackend.services;

import jakarta.persistence.EntityNotFoundException;
import lsdi.IndoorBackend.domain.model.User;
import lsdi.IndoorBackend.entities.UserEntity;
import lsdi.IndoorBackend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long save(User user) {
        UserEntity userEntity = new UserEntity(
                user.getName(),
                user.getEmail(),
                user.getMobileIdentifier()
        );

        UserEntity userSaved = userRepository.save(userEntity);

        return userSaved.getId();
    }

    public void updateUser(User user) {
        UserEntity userEntity = userRepository
                .findById(user.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found")
                );

        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setMobileIdentifier(user.getMobileIdentifier());

        userRepository.save(userEntity);
    }
}
