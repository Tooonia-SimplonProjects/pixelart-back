package com.simplon.pixelartback.facade.controller;

import com.simplon.pixelartback.storage.dto.PixelArtDto;
import com.simplon.pixelartback.service.pixelart.PixelArtService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
//@RequiredArgsConstructor //TODO: needed here?
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PixelArtController {

    @Autowired
//    In UserController it is with @Autowired, but alert message: "Field injection is not recommended ???
    private PixelArtService pixelArtService;

//    public PixelArtController(PixelArtService pixelArtService) {
//        super();
//        this.pixelArtService = pixelArtService;
//    }

//  READ / GET all (catalog)    GET /api/pixelart
    @GetMapping("/pixelart")
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //TODO: correct/needed here?
    public ResponseEntity<List<PixelArtDto>> getAllPixelArt() throws Exception {
        return ResponseEntity.ok(pixelArtService.getAllPixelArt()); // As in "UserController / getAddresses"
//        TODO: other response status to set? @ResponsStatus, @ExceptionHandler, PixelArtApi to define with OpenAPI?
//        TODO: request.getHeader("Accept"), MediaType/valueOf("application/json"), response... <= needed here these elements like in UsersApi?
    }

//  READ / GET one pixelArt by Id    GET /api/pixelart/{id}
    @GetMapping("/pixelart/{id}")
    public ResponseEntity<PixelArtDto> getPixelArtById(@PathVariable("id") Long id) throws Exception {
        return  ResponseEntity.ok(pixelArtService.getPixelArtById(id));
    }

//  READ / GET all pixelArt of one particular User    GET  /api/pixelart/currentuser
//    //    List<PixelArtDto> getPixelArtByUser(UserDto userDto); //TODO: correct? Complete when User is created!

//  CREATE an own pixelArt    POST  /api/pixelart
    @PostMapping("/pixelart")
//    TODO: very likely there will be 2 @param here : userId and pixelArtDto !!! <= line 91 UsersApi.java!
    public ResponseEntity<PixelArtDto> createPixelArt(@RequestBody PixelArtDto pixelArtDto) throws Exception {
        val createdPixelArtDto = pixelArtService.createPixelArt(pixelArtDto);
        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdPixelArtDto.getId()).toUri();
        return ResponseEntity.created(location).body(createdPixelArtDto); // As from line 50 in ProjectController!
//        TODO: correct, or would be better "ResponseEntity.ok(...)" style like in line 94 of UserController?
    }

//    UPDATE one particular pixelArt   PUT  /api/pixelart/{id}
    @PutMapping("/pixelart/{id}")
//    TODO: very likely there will be 2 @param here : userId and pixelArtDto !!! <= line 118 UsersApi.class!
    public ResponseEntity<PixelArtDto> updatePixelArt(@PathVariable(name = "id") Long id, @RequestBody PixelArtDto pixelArtDto) throws Exception {
        return ResponseEntity.ok(pixelArtService.updatePixelArt(pixelArtDto));
//    TODO: why different at "return ResponseEntity.noContent().build(); (line 78) or "new ResponseEntity<>(HttpStatus.NO_CONTENT); (line 161) ???
    }

//    DELETE one particular pixelArt    DELETE  /api/pixelart/{id_pixel_art}
    @DeleteMapping("/pixelart/{id}")
//    TODO: will there be 2 @param here : userId and pixelArtDto !!! <= line 99 UsersController or just one like in line 83 of ProjectController?
    public ResponseEntity<Void> deletePixelArt(@PathVariable(name = "id") Long id) throws Exception {
        pixelArtService.deletePixelArt(id);
//        return ResponseEntity.noContent().build(); //line 85 ProjectController TODO!!
        return ResponseEntity.ok().build(); //line 101 UserController TODO!!
    }
}
