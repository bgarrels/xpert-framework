package it.pkg.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author
 */
@Entity
@SequenceGenerator(name = "Cidade", allocationSize = 1, sequenceName = "Cidade_id_seq")
public class Cidade implements Serializable {

    @Id
    @GeneratedValue(generator = "Cidade")
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Estado estado;
    @NotBlank
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return descricao;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cidade other = (Cidade) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
