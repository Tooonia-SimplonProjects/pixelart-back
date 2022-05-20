package com.simplon.pixelartback.storage.entity.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplon.pixelartback.storage.entity.language.LanguageEntity;
import com.simplon.pixelartback.storage.entity.pixelart.PixelArtEntity;
import com.simplon.pixelartback.storage.entity.role.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @Column(name = "alias", length = 30, nullable = false, unique = true)
    private String alias;

    @Column(name = "user_email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "user_password", length = 50, nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEntity role;

//    @Column(name = "language", nullable = false)
//    private LanguageEntity language;

//    FONTOS: valoszinuleg a "FetchType.EAGER" segitett!!!
//    https://www.baeldung.com/jpa-joincolumn-vs-mappedby :
//    TODO: see this again: "Defining here @OneToMany, we are making it bidirectional"
//    @JoinColumn(name = "id") <<< when having "mappedBy" defined here, we can't add that @ : that would put this "id" as an extra column to PixelArt
//
////    This "@JsonIgnoreProperties" annotation is important to break the cycles that would print in the created pixelart
////    in the response recursively under "pixelArtEntityList" with a GET all for PixelArt:
//    Finally: removed "cascade = CascadeType.ALL" and added that code from:
//    source: https://stackoverflow.com/questions/22688402/delete-not-working-with-jparepository
    @OneToMany(mappedBy="userEntity", orphanRemoval = true, fetch = FetchType.EAGER, targetEntity = PixelArtEntity.class)
    @JsonIgnoreProperties("userEntity")
    private List<PixelArtEntity> pixelArtEntityList;

    public UserEntity() {
        super();
    }

    //    source: https://stackoverflow.com/questions/22688402/delete-not-working-with-jparepository

    public void dismissChild(PixelArtEntity pixelArtEntity) {
        this.pixelArtEntityList.remove(pixelArtEntity);
    }
//    @PreRemove
    public void dismissChildren() {
        this.pixelArtEntityList.forEach(pixelArtEntity -> pixelArtEntity.dismissParent()); // SYNCHRONIZING THE OTHER SIDE OF RELATIONSHIP
        this.pixelArtEntityList.clear();
    }

}
