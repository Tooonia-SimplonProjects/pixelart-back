package com.simplon.pixelartback.facade.controller;

import com.simplon.pixelartback.service.user.UserService;
import com.simplon.pixelartback.storage.dao.UserDao;
import com.simplon.pixelartback.storage.dto.PixelArtDto;
import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
//@RequiredArgsConstructor //TODO: needed here?
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    //  READ / GET all users    GET /api/users
    @GetMapping("/users")
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //TODO: correct/needed here?
    public ResponseEntity<List<UserGetDto>> getAllPixelArt() throws Exception {
        return ResponseEntity.ok(userService.getAllUsers()); // Accorting to model "UserController / getAddresses"
//        TODO: other response status to set? @ResponsStatus, @ExceptionHandler, PixelArtApi to define with OpenAPI?
//        TODO: request.getHeader("Accept"), MediaType/valueOf("application/json"), response... <= needed here these elements like in UsersApi?
    }

//    READ / GET one user by Uuid    GET /api/user/{uuid}
    @GetMapping("/my-user/{uuid}")
    public ResponseEntity<UserGetDto> getUserByUuid(@PathVariable("uuid") UUID uuid) throws Exception {
        return ResponseEntity.ok(userService.getUserByUuid(uuid));
    }

    //    READ / GET one user by id    GET /api/user/{id}
    @GetMapping("/user/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //    READ / GET one "simplified" user by id    GET /api/alias/{id}
//    @GetMapping("/alias/{id}")
//    public ResponseEntity<UserGetDto> getSimplifiedUserById(@PathVariable("id") Long id) throws Exception {
//        return ResponseEntity.ok(userService.getUserById(id));
//    }

    //    CREATE one user     POST /api/user
    @PostMapping("/user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws Exception {
        val createdUserDto = userService.createUser(userDto);
        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUserDto.getUuid()).toUri();
        return ResponseEntity.created(location).body(createdUserDto);
    }

    // DELETE one user by id      DELETE  /api/user/{id}
    @DeleteMapping("/user/{id}")
//    TODO: will there be 2 @param here : userId and pixelArtDto !!! <= line 99 UsersController or just one like in line 83 of ProjectController?
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id) throws Exception {
        userService.deleteUser(id);
//        return ResponseEntity.noContent().build(); //line 85 ProjectController TODO!!
        return ResponseEntity.ok().build(); //line 101 UserController TODO!!
    }
}
