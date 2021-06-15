package com.iot.admin.admin.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Resource {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,length = 10, nullable = false)
    private String tag;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;

    @ManyToOne(optional = true, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Device deviceId;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Property> properties;

}
