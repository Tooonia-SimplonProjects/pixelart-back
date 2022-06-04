package com.simplon.pixelartback.facade.controller;

import com.simplon.pixelartback.facade.security.AuthenticationUtil;
import com.simplon.pixelartback.facade.security.AuthenticationUtilImpl;
import com.simplon.pixelartback.service.user.UserService;
import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
//@RequiredArgsConstructor //TODO: needed here?
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    //  READ / GET all users    GET /api/users
    @GetMapping("/users")
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //TODO: correct/needed here?
//    TODO: method only for Postman testing
    public ResponseEntity<List<UserGetDto>> getAllUsers() throws Exception {
        return ResponseEntity.ok(userService.getAllUsers()); // Accorting to model "UserController / getAddresses"
//        TODO: other response status to set? @ResponsStatus, @ExceptionHandler, PixelArtApi to define with OpenAPI?
//        TODO: request.getHeader("Accept"), MediaType/valueOf("application/json"), response... <= needed here these elements like in UsersApi?
    }

//    READ / GET one user by Uuid, with detailed User information including email (protected access)   GET /api/user/{uuid}
    @GetMapping("/my-profile/{uuid}")
    @PreAuthorize("hasAnyRole('USER')")
//    @PreAuthorize("isAuthenticated()") //TODO: probably en double!
    public ResponseEntity<UserDto> getUserByUuid(@PathVariable("uuid") UUID uuid) throws Exception {
        if (authenticationUtil.authenticatedUserHasAccessToUser(uuid)) {
            return ResponseEntity.ok(userService.getUserByUuid(uuid));
        }
        LOGGER.info("Not authorized to access that User information");
        return null;
    }

    //    READ / GET one user by id    GET /api/user/{id}
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) throws Exception {
//    public ResponseEntity<UserGetDto> getUserById(@PathVariable("id") Long id) throws Exception {
        //        TODO: see if needed to compare id of parameter and entity!!!
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //    GET the user profile of the connected user by email (getMe)   GET  /api/user/me
    @GetMapping("/user/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getMe() throws Exception {
        return ResponseEntity.ok(userService.getMe());
    }


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

    // DELETE one user by id      DELETE  /api/my-profile/{id}
//    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/my-profile/{uuid}")
    @PreAuthorize("hasAnyRole('USER')")
//    @PreAuthorize("isAuthenticated()")
//    TODO: will there be 2 @param here : userId and pixelArtDto !!! <= line 99 UsersController or just one like in line 83 of ProjectController?
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "uuid") UUID uuid) {
        userService.deleteUser(uuid);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/login")
//    public ResponseEntity<Void> loginUser(@RequestBody UserDto userDto) throws Exception {
//        userService.loginUser(userDto);
//        return ResponseEntity.ok().build();
//    }
}
