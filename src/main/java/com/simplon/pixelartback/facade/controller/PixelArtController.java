package com.simplon.pixelartback.facade.controller;

import com.simplon.pixelartback.storage.dto.PixelArtDto;
import com.simplon.pixelartback.service.pixelart.PixelArtService;
import com.simplon.pixelartback.storage.dto.PixelArtSimpleDto;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
//@RequiredArgsConstructor //TODO: needed here?
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200")
public class PixelArtController {

    @Autowired
    private PixelArtService pixelArtService;

    /**
     * READ / GET all (catalog)    GET /api/pixelart
     * @return
     * @throws Exception
     */
    @GetMapping("/pixelart-catalog")
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //TODO: correct/needed here?
    public ResponseEntity<List<PixelArtDto>> getAllPixelArt() throws Exception {
        return ResponseEntity.ok(pixelArtService.getAllPixelArt());
    }

    /**
     * READ / GET all simplified pixelart (catalog)    GET /api/pixelart-simplelist
     * @return
     * @throws Exception
     */
    @GetMapping("/pixelart-simplelist")
    public ResponseEntity<List<PixelArtSimpleDto>> getAllSimplePixelArt() throws Exception {
        return ResponseEntity.ok(pixelArtService.getAllSimplePixelArt());
    }

    /**
     * READ / GET one pixelArt by Id    GET /api/pixelart/{id}
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/pixelart/{id}")
    public ResponseEntity<PixelArtDto> getPixelArtById(@PathVariable("id") Long id) throws Exception {
        return  ResponseEntity.ok(pixelArtService.getPixelArtById(id));
    }

    /**
     * READ / GET one simple pixelArt by Id    GET /api/pixelart-simple/{id}
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/pixelart-simple/{id}")
    public ResponseEntity<PixelArtSimpleDto> getSimplePixelArtById(@PathVariable("id") Long id) throws Exception {
        return  ResponseEntity.ok(pixelArtService.getSimplePixelArtById(id));
    }

    /**
     * READ / GET all pixelArt of one particular User    GET  /api/pixelart/currentuser
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/pixelart-by-user/{id}")
    public ResponseEntity<List<PixelArtDto>> getAllPixelArtByUser(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(pixelArtService.getAllPixelArtByUser(id));
    }

    /**
     * CREATE an own pixelArt    POST  /api/pixelart
     * @param pixelArtDto
     * @return
     * @throws Exception
     */
    @PostMapping("/pixelart-create")
    @PreAuthorize("hasAnyRole('USER')")
//    @PreAuthorize("isAuthenticated()") // TODO: Probably double definition with SecurityContext!!!
    public ResponseEntity<PixelArtDto> createPixelArt(@RequestBody PixelArtDto pixelArtDto) throws Exception {
        val createdPixelArtDto = pixelArtService.createPixelArt(pixelArtDto);
        val location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdPixelArtDto.getId()).toUri();
        return ResponseEntity.created(location).body(createdPixelArtDto);
    }

    /**
     * UPDATE one particular pixelArt   PUT  /api/pixelart/{id}
     * @param id
     * @param pixelArtSimpleDto
     * @return
     * @throws Exception
     */
    @PutMapping("/pixelart-edit/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<PixelArtSimpleDto> updatePixelArt(@PathVariable(name = "id") Long id, @RequestBody PixelArtSimpleDto pixelArtSimpleDto) throws Exception {
        if(id.longValue() != pixelArtSimpleDto.getId().longValue()) {
            throw new IllegalArgumentException("Id in URL " + id + " does not match the id of of current pixelArt : " + pixelArtSimpleDto.getId()
                    + "PixelArt id can not be updated.");
        }
        return ResponseEntity.ok(pixelArtService.updatePixelArt(pixelArtSimpleDto));
    }

    /**
     * DELETE one particular pixelArt    DELETE  /api/pixelart/{id_pixel_art}
     * @param id
     * @return
     * @throws Exception
     */
//
    @DeleteMapping("/pixelart-edit/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Void> deletePixelArt(@PathVariable(name = "id") Long id) throws Exception {
        pixelArtService.deletePixelArt(id);
        return ResponseEntity.ok().build();
    }
}
