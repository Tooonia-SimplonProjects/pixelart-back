package com.simplon.pixelartback.facade.controller;

import com.simplon.pixelartback.service.user.UserService;
import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
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
    public ResponseEntity<List<UserGetDto>> getAllUsers() throws Exception {
        return ResponseEntity.ok(userService.getAllUsers()); // Accorting to model "UserController / getAddresses"
//        TODO: other response status to set? @ResponsStatus, @ExceptionHandler, PixelArtApi to define with OpenAPI?
//        TODO: request.getHeader("Accept"), MediaType/valueOf("application/json"), response... <= needed here these elements like in UsersApi?
    }

//    READ / GET one user by Uuid, with detailed User information including email    GET /api/user/{uuid}
    @GetMapping("/my-user/{uuid}")
    public ResponseEntity<UserDto> getUserByUuid(@PathVariable("uuid") UUID uuid) throws Exception {
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
    @PostMapping("/signup")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) throws Exception {
        val createdUserDto = userService.createUser(userDto);
//      Works with this one too:
//        val location = UriComponentsBuilder.newInstance().path("/user/{id}")
//                .buildAndExpand(createdUserDto.getUuid()).toUri();
        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUserDto.getId()).toUri();
//        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(createdUserDto.getUuid()).toUri();
        return ResponseEntity.created(location).body(createdUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@RequestBody UserDto userDto) throws Exception {
        userService.loginUser(userDto);
        return ResponseEntity.ok().build();
    }

    // DELETE one user by id      DELETE  /api/user/{id}
//    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/user/{id}")
    @RolesAllowed("USER")
//    TODO: will there be 2 @param here : userId and pixelArtDto !!! <= line 99 UsersController or just one like in line 83 of ProjectController?
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
