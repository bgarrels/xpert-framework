package it.pkg.model.audit;

import com.xpert.audit.model.AbstractAuditing;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author
 */
@Entity
public class Auditing extends AbstractAuditing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auditing")
    private List<Metadata> metadatas;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public List getMetadatas() {
        return metadatas;
    }

    @Override
    public void setMetadatas(List metadatas) {
        this.metadatas = metadatas;
    }

    @Override
    public String getUserName() {
        return "TODO - User";
    }
}
