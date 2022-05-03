package com.simplon.pixelartback.storage.entity.pixelart;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "pixel_art") //TODO needed? : schema = SchemaConstants.DATA_SCHEMA
@Getter
@Setter
@ToString
//@NoArgsConstruct
// or //TODO?
//@Builder //TODO?
public class PixelArtEntity {

//    TODO: see best way/form for unique id definition!
    private static final long serialVersionUID = 6858481209380769717L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
//    @Column(name = "id_pixel_art")
    private Long id;
//    private Long idPixelArt;

//    @Column(name = "uuid", nullable = false, unique = true)
//    private UUID uuid;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

//    @Column(name = "image", nullable = false)
//    @Column(name = "image", nullable = false)
//    TODO: see format: Blob, BlobType, Image
//    private String image; //TODO: for initial testing, otherwise: private Base64 productImage;

//    TODO: adding this parameter once I have created the User class too!
//    @NotNull
//    @Column(name = "id_user", nullable = false) //TODO: it is a fk!
//    private UUID idUser;

//    @Column(name = "product_price", nullable = false)
//    private Integer productPrice;


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PixelArtEntity)) {
            return false;
        }
        return super.equals(obj);
    }
}
