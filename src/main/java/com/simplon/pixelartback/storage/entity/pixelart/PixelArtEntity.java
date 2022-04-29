package com.simplon.pixelartback.storage.entity.pixelart;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "pixel_art") //TODO needed? : schema = SchemaConstants.DATA_SCHEMA
@Getter
@Setter
@ToString
@NoArgsConstructor //TODO?
@AllArgsConstructor //TODO?
//@Builder //TODO?
public class PixelArtEntity {

//    TODO: see best way/form for unique id definition!

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
//    @Column(name = "id_pixel_art")
    private Long id;
//    private Long idPixelArt;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name", nullable = false) 
    private String name;

    @Column(name = "image", nullable = false)
//    TODO: see format: Blob, BlobType, Image
    private String image; //TODO: for initial testing, otherwise: private Base64 productImage;

//    TODO: adding this parameter once I have created the User class too!
//    @NotNull
//    @Column(name = "id_user", nullable = false) //TODO: it is a fk!
//    private UUID idUser;

//    @Column(name = "product_price", nullable = false)
//    private Integer productPrice;
}
