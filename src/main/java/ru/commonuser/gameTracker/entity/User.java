package ru.commonuser.gameTracker.entity;

import lombok.Data;
import ru.commonuser.gameTracker.enums.UserRole;
import ru.commonuser.gameTracker.enums.UserStatus;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_create")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_block")
    private Date dateBlock;

    @Column(name = "date_last_online")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastOnline;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;
}
