package ru.commonuser.gameTracker.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "urls")
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "url_pattern")
    private String urlPattern;

    @Column(name = "css_pattern")
    private String cssPattern;

    @Column(name = "is_auth")
    private Boolean isAuth;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;
}