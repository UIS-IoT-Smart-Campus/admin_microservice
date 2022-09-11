package com.iot.admin.admin.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Property {    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

    private String description;

    @ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Device deviceParent;

    @ManyToOne(optional = true)
    @JoinColumn()
    private Resource resourceParent;


}
