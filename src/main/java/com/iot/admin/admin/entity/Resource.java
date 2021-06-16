package com.iot.admin.admin.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

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

    @ManyToOne
    @JoinColumn(nullable = true)
    private Device deviceParent;

    @OneToMany(mappedBy = "resourceParent", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Property> properties;

    /*
    * This method is executed before create
    */
    @PrePersist
    protected void onCreate(){
        tag = tag.toUpperCase();
    }

    /*
    * This method is executed before update
    */
    @PreUpdate
    protected void onUpdate(){
        tag = tag.toUpperCase();
    }

    public Resource(Long id) {
        this.id  = id;
    }

}
