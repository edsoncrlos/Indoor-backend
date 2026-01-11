package lsdi.IndoorBackend.controllers;

import lsdi.IndoorBackend.common.ApiPaths;
import lsdi.IndoorBackend.domain.model.User;
import lsdi.IndoorBackend.dtos.UserDTO;
import lsdi.IndoorBackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.USER)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Long> addUser(@RequestBody UserDTO userDTO) {
        User user = User.Mapper.fromDTO(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO) {
        User user = User.Mapper.fromDTO(userDTO);
        userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("updated");
    }
}
