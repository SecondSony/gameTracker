package ru.commonuser.gameTracker.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "urls")
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "url")
    private String url;
}
