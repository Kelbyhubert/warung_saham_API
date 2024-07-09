package com.warungsaham.warungsahamappapi.user.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warungsaham.warungsahamappapi.premiumsub.model.PremiumSub;
import com.warungsaham.warungsahamappapi.role.model.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(
    exclude = {"password"}
)
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private int id;

    @Column(name = "userid")
    private String userId;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    @Column(name = "nama")
    private String name;

    @Column(name = "nomorPonsel")
    private String phoneNumber;

    private Date dob;

    private int status;

    private int active;

    @ManyToMany(fetch = FetchType.LAZY )
    @JoinTable(
        name = "tb_user_role",
        joinColumns = @JoinColumn(name = "userid"),
        inverseJoinColumns = @JoinColumn(name ="roleid")
    )
    private Set<Role> roles = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<PremiumSub> premiumSubList = new ArrayList<>();

}
