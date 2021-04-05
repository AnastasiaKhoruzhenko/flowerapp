package com.hse.flowerapp.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @ManyToMany(mappedBy = "categoryList", fetch = FetchType.LAZY)
    List<Item> itemList;

//    // все цветы
//    ALL_FLOWERS,
//    // классика
//    CLASSIC,
//    // авторские
//    AUTHORS,
//    // в коробке
//    IN_BOX,
//    // сухоцветы
//    POTPOURRI,
//    // в шляпной коробке
//    IN_HAT_BOX,
//    // корзина
//    IN_BASKET


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
