package com.xpert.showcase.audit;

import com.xpert.annotation.AuditingEntity;
import com.xpert.audit.model.AbstractAuditing;
import com.xpert.showcase.model.Person;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Ayslan
 */
@AuditingEntity
@Entity
public class Auditing extends AbstractAuditing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   
    //set custom object "User"
    @ManyToOne
    private Person user;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "auditing")
    private List<Metadata> metadatas;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Person getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setUser(Object user) {
        this.user = (Person) user;
    }

    @Override
    public List getMetadatas() {
        return metadatas;
    }

    @Override
    public void setMetadatas(List metadatas) {
        this.metadatas = metadatas;
    }
}
