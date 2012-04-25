package com.xpert.showcase.audit;

import com.xpert.audit.model.AbstractAuditing;
import com.xpert.showcase.model.Person;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Ayslan
 */
@Entity
public class Auditing extends AbstractAuditing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Person user;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auditing")
    private List<Metadata> metadatas;

    @Override
    public Long getId() {
        return id;
    }

    public Person getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(Person user) {
        this.user = user;
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
