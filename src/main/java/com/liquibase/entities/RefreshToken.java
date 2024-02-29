package com.liquibase.entities;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    private LocalDateTime expiryDate;
//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")

//    @Column(name = "email", unique=true)
    private String email;
}
