package com.gabrieljuliao.tacocloud.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdAt;
    @NotNull
    @Size(min = 5, message = "Name Must be at Least 5 Characters Long")
    private String name;
    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min = 1, message = "You Must Choose at Least 1 Ingredient")
    private List<Ingredient> ingredients;

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Taco taco = (Taco) o;

        return Objects.equals(id, taco.id);
    }

    @Override
    public int hashCode() {
        return 944046367;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "createdAt = " + createdAt + ", " +
                "name = " + name + ")";
    }
}
