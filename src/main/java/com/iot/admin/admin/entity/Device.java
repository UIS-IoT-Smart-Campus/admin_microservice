package com.iot.admin.admin.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
/*import javax.persistence.EnumType;
import javax.persistence.Enumerated;*/
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,length = 10, nullable = false)
    private String tag;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false,columnDefinition = "tinyint(1) default 0") 
    private Boolean enabled=false;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    /*
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
    */

    @ManyToOne(optional = true, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Device deviceParent;

    @OneToMany(mappedBy = "deviceParent", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Property> properties;

    @OneToMany(mappedBy = "deviceParent", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Resource> resources;


    @Column(nullable = false,columnDefinition = "tinyint(1) default 0") 
    private Boolean is_gateway=false;

    @Column(length = 20)
    private String ipv4_address;

    @ManyToMany
    private Set<Topic> topics;


    

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

    public Device(Long id) {
        this.id  = id;
    }
}
