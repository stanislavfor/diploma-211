package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Primary;


@Setter
@Getter
@Primary
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "link")
    private String link;




}
