package com.simplon.pixelartback.facade.controller;

import com.simplon.pixelartback.facade.security.AuthenticationUtil;
import com.simplon.pixelartback.service.user.UserService;
import com.simplon.pixelartback.storage.dto.UserDto;
import com.simplon.pixelartback.storage.dto.UserGetDto;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
//    @Lazy Might be needed here
    private UserService userService;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    /**
     * READ / GET all users    GET /api/users
     * @return
     */
    @GetMapping("/users")
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<List<UserGetDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

//    /**
//     * READ / GET one user by Uuid, with detailed User information including email (protected access)
//     * Not used on Front-end for the moment
//     * @param uuid
//     * @return
//     */
//    @GetMapping("/my-profile/{uuid}")
////    @PreAuthorize("hasAnyRole('USER')")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<UserDto> getUserByUuid(@PathVariable("uuid") UUID uuid) {
//        if (authenticationUtil.authenticatedUserHasAccessToUser(uuid)) {
//            return ResponseEntity.ok(userService.getUserByUuid(uuid));
//        }
//        LOGGER.info("Not authorized to access that User information");
//        return null;
//    }

    /**
     * READ / GET one user with public information by id    GET /api/user/{id}
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable("id") Long id) {
        //        TODO: see if needed to compare id of parameter and entity!!!
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * READ / GET one user with public information by id    GET /api/me
     * @param email
     * @return
     */
    @GetMapping("/me")
    public ResponseEntity<UserGetDto> getUserByEmail(@RequestParam String email) {
//    public ResponseEntity<UserGetDto> getUserByEmail(@RequestBody String email) {
//        System.out.println(this.userService.findUserByEmail(email));
        return ResponseEntity.ok(userService.findUserByEmail(email));

    }

//    /**
//     * GET the user profile of the connected user by email (protected access)   GET  /api/my-profile
//     * @return
//     */
//    @GetMapping("/user-profile")
//    @PreAuthorize("isAuthenticated()")
////    public ResponseEntity<UserDto> getUserByEmail(@RequestBody String email) throws Exception {
////    public ResponseEntity<UserDto> getConnectedUserProfile() throws Exception {
//    public ResponseEntity<UserDto> getConnectedUserProfile(@PathVariable("id") Long id) throws Exception {
////    public ResponseEntity<UserDto> findUserByLogin(String email, boolean withPassword) throws Exception {
////        if (authenticationUtil.isAuthenticated()) {//TODO: elvileg ez nem kell!
////            return ResponseEntity.ok(userService.findByEmail(email));
////        }
//        //return ResponseEntity.ok(userService.getMe()); //TODO: csak ez a sor volt itt az elobb!!!
////        LOGGER.info("You have to be logged in to create pixelart");
////        return null;
//
//        //2.proba
////        if (userService.getMe().getEmail().equals(email)) {
////            return ResponseEntity.ok(userService.getMe());
////        } else {
////            throw new IllegalArgumentException("Email does not belong to connected user"); //TODO: correct?
////        }
//        if (authenticationUtil.authenticatedUserHasAccessToUserById(id)) {
////          //3.proba
////          return userService.getConnectedUserPrivateProfileByEmail(email);
//            return ResponseEntity.ok(userService.getConnectedUserPrivateProfileById(id));
//
////        if (authenticationUtil.authenticatedUserHasAccessToUserByEmail(email)) {
//////            return userService.getConnectedUserPrivateProfileByEmail(email);
////            return ResponseEntity.ok(userService.getConnectedUserPrivateProfileByEmail(email));
//        } else {
//            LOGGER.info("Not authorized to access that User information");
//        }
//        return null;
//    }

    /**
     * GET the user profile of the connected user (protected access)   GET  /api/my-profile
     * @return
     */
    @GetMapping("/my-profile/{id}")
    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<UserDto> getUserByEmail(@RequestBody String email) throws Exception {
//    public ResponseEntity<UserDto> getConnectedUserProfile() throws Exception {
    public ResponseEntity<UserGetDto> getConnectedUserProfile(@PathVariable("id") Long id) throws Exception {
//    public ResponseEntity<UserDto> findUserByLogin(String email, boolean withPassword) throws Exception {
//        if (authenticationUtil.isAuthenticated()) {//TODO: elvileg ez nem kell!
//            return ResponseEntity.ok(userService.findByEmail(email));
//        }
        //return ResponseEntity.ok(userService.getMe()); //TODO: csak ez a sor volt itt az elobb!!!
//        LOGGER.info("You have to be logged in to create pixelart");
//        return null;

        //2.proba
//        if (userService.getMe().getEmail().equals(email)) {
//            return ResponseEntity.ok(userService.getMe());
//        } else {
//            throw new IllegalArgumentException("Email does not belong to connected user"); //TODO: correct?
//        }
        if (authenticationUtil.authenticatedUserHasAccessToUserById(id)) {
//          //3.proba
//          return userService.getConnectedUserPrivateProfileByEmail(email);
            return ResponseEntity.ok(userService.getConnectedUserPrivateProfileById(id));

//        if (authenticationUtil.authenticatedUserHasAccessToUserByEmail(email)) {
////            return userService.getConnectedUserPrivateProfileByEmail(email);
//            return ResponseEntity.ok(userService.getConnectedUserPrivateProfileByEmail(email));
        } else {
            LOGGER.info("Not authorized to access that User information");
        }
        return null;
    }

//    GET user by email
//    @GetMapping("/user")

    /**
     * GET connected user private information for my-profile info
     * @return
     */
    @GetMapping("/user/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserGetDto> getMe() {
        return ResponseEntity.ok(userService.getMe());
    }



    //TODO: kell ez method? Egyelore nem mukodik!!!
//    @GetMapping("/user/me")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<UserDto> getMe() {
//         if (authenticationUtil.isAuthenticated()) {
//             AuthenticatedUser authenticatedUser = contextHelperUtil.getAuthenticatedUser();
////             if (authenticatedUser != null) {
//             return ResponseEntity.ok(userService.getUserByUuid(authenticatedUser.getUuid()));
//         }
//        return null;
//    }

    /**
     * CREATE one user     POST /api/user
     * @param userDto
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        val createdUserDto = userService.createUser(userDto);
        // Works with this syntax too:
        // val location = UriComponentsBuilder.newInstance().path("/user/{id}")
        //    .buildAndExpand(createdUserDto.getUuid()).toUri();
        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUserDto.getId()).toUri();
        // val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        //    .buildAndExpand(createdUserDto.getUuid()).toUri();
        return ResponseEntity.created(location).body(createdUserDto);
    }

    /**
     *  DELETE one user by id      DELETE  /api/my-profile/{id}
     * //@param uuid
     * @param id
     * @return
     */
//    @DeleteMapping("/my-profile/{uuid}")
    @DeleteMapping("/my-profile/{id}")
//    @PreAuthorize("hasAnyRole('USER')")
    @PreAuthorize("isAuthenticated()")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<Void> deleteUser(String email) {
//        if (authenticationUtil.authenticatedUserHasAccessToUser(uuid)) {
//            userService.deleteUser(uuid);
//            return ResponseEntity.ok().build();
//        }
//        LOGGER.info("Not authorized to delete that User");
//        return null;
//    }
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        if (authenticationUtil.authenticatedUserHasAccessToUserById(id)) {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }
        LOGGER.info("Not authorized to delete that User");
        return null;
    }

//    public ResponseEntity<Void> deleteUser(@PathVariable(name = "uuid") UUID uuid) {
//        if (authenticationUtil.authenticatedUserHasAccessToUser(uuid)) {
//            userService.deleteUser(uuid);
//            return ResponseEntity.ok().build();
//        }
//        LOGGER.info("Not authorized to delete that User");
//        return null;
//    }

//    @PostMapping("/login")
//    public ResponseEntity<Void> loginUser(@RequestBody UserDto userDto) throws Exception {
//        userService.loginUser(userDto);
//        return ResponseEntity.ok().build();
//    }
}
