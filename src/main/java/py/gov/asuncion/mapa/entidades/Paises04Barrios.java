/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.gov.asuncion.mapa.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vinsfran
 */
@Entity
@Table(name = "paises_04_barrios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paises04Barrios.findAll", query = "SELECT p FROM Paises04Barrios p"),
    @NamedQuery(name = "Paises04Barrios.findByCodBarrio", query = "SELECT p FROM Paises04Barrios p WHERE p.codBarrio = :codBarrio"),
    @NamedQuery(name = "Paises04Barrios.findByBarrioNombre", query = "SELECT p FROM Paises04Barrios p WHERE p.barrioNombre = :barrioNombre")})
public class Paises04Barrios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_barrio")
    private Integer codBarrio;
    @Size(max = 2147483647)
    @Column(name = "barrio_nombre")
    private String barrioNombre;
    @OneToMany(mappedBy = "fkCodBarrio")
    private List<Paises05Direcciones> paises05DireccionesList;
    @JoinColumn(name = "fk_cod_ciudad", referencedColumnName = "cod_cuidad")
    @ManyToOne
    private Paises03Ciudades fkCodCiudad;

    public Paises04Barrios() {
    }

    public Paises04Barrios(Integer codBarrio) {
        this.codBarrio = codBarrio;
    }

    public Integer getCodBarrio() {
        return codBarrio;
    }

    public void setCodBarrio(Integer codBarrio) {
        this.codBarrio = codBarrio;
    }

    public String getBarrioNombre() {
        return barrioNombre;
    }

    public void setBarrioNombre(String barrioNombre) {
        this.barrioNombre = barrioNombre;
    }

    @XmlTransient
    public List<Paises05Direcciones> getPaises05DireccionesList() {
        return paises05DireccionesList;
    }

    public void setPaises05DireccionesList(List<Paises05Direcciones> paises05DireccionesList) {
        this.paises05DireccionesList = paises05DireccionesList;
    }

    public Paises03Ciudades getFkCodCiudad() {
        return fkCodCiudad;
    }

    public void setFkCodCiudad(Paises03Ciudades fkCodCiudad) {
        this.fkCodCiudad = fkCodCiudad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codBarrio != null ? codBarrio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paises04Barrios)) {
            return false;
        }
        Paises04Barrios other = (Paises04Barrios) object;
        if ((this.codBarrio == null && other.codBarrio != null) || (this.codBarrio != null && !this.codBarrio.equals(other.codBarrio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.gov.asuncion.mapa.entidades.Paises04Barrios[ codBarrio=" + codBarrio + " ]";
    }
    
}
