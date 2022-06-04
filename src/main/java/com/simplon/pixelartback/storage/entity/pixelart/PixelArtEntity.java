package com.simplon.pixelartback.storage.entity.pixelart;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplon.pixelartback.storage.entity.user.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pixel_art")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PixelArtEntity implements Serializable {

//    private static final long serialVersionUID = 6858481209380769717L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
//    @Column(name = "id_pixel_art")
    private Long id;
//    private Long idPixelArt;

//    @Column(name = "uuid", nullable = false, unique = true)
//    private UUID uuid;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

//    @Column(name = "image", nullable = false)
//    TODO: see format: Blob, BlobType, Image // private Base64 productImage;

//    Cascading expresses dependency between 2 entities: if User does not exist, PixelArt has no meaning on its own.
//    PixelArt depends on the existence of a User.
//    @ManyToOne(cascade= CascadeType.ALL)

//    https://www.baeldung.com/jpa-joincolumn-vs-mappedby :
//    In a One-to-Many/Many-to-One relationship, the owning side is usually defined on the ‘many' side of the relationship.
//    It's usually the side which owns the foreign key
//    The @JoinColumn annotation defines that actual physical mapping on the owning side:
//    The @JsonIgnoreProperties annotation is important to break the cycles that would print the created pixelart recursively
//    in the response of a GET all for PixelArt under "pixelArtEntityList [] " :
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = UserEntity.class)
    @JoinColumn(name = "id_user_fk", referencedColumnName = "id", nullable = false) // It is a fk!
    @JsonIgnoreProperties("pixelArtEntityList")
    private UserEntity userEntity;

    //    source: https://stackoverflow.com/questions/22688402/delete-not-working-with-jparepository
//    @PreRemove
    public void dismissParent() {
        this.userEntity.dismissChild(this); //SYNCHRONIZING THE OTHER SIDE OF RELATIONSHIP
        this.userEntity = null;
    }

//TODO: see theses 2 methods! (<<< User.java)
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
