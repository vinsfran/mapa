package py.gov.asuncion.mapa.managebeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import py.gov.asuncion.mapa.entidades.Reclamos;
import py.gov.asuncion.mapa.entidades.TiposReclamos;
import py.gov.asuncion.mapa.sessionbeans.ReclamosSB;

/**
 *
 * @author vinsfran
 */
@ManagedBean(name = "reclamosWebMB")
@RequestScoped
public class ReclamosWebMB implements Serializable {

    private Reclamos reclamo;
    private List<Reclamos> listaRec;
    private Date fechaDesde;
    private Date fechaHasta;
    private List<TiposReclamos> tiposDeReclamos;

    @EJB
    private ReclamosSB reclamosSB;

    public ReclamosWebMB() {

    }

    @PostConstruct
    public void init() {
        reclamo = new Reclamos();
        reclamo.setFkCodTipoReclamo(new TiposReclamos());
    }

    public String consultarPorRangoDeFecha() {
        listaRec = reclamosSB.listarRangoDeFecha(reclamo.getFkCodTipoReclamo().getCodTipoReclamo(), fechaDesde, fechaHasta);
        return "";
    }

    /**
     * @return the reclamo
     */
    public Reclamos getReclamo() {
        return reclamo;
    }

    /**
     * @param reclamo the reclamo to set
     */
    public void setReclamo(Reclamos reclamo) {
        this.reclamo = reclamo;
    }

    /**
     * @return the listaRec
     */
    public List<Reclamos> getListaRec() {
//        listaRec = getReclamosSB().listarReclamos2(23);
        return listaRec;
    }

    /**
     * @param listaRec the listaRec to set
     */
    public void setListaRec(List<Reclamos> listaRec) {
        this.listaRec = listaRec;
    }

    /**
     * @return the fechaHasta
     */
    public Date getFechaHasta() {
        return fechaHasta;
    }

    /**
     * @param fechaHasta the fechaHasta to set
     */
    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    /**
     * @return the reclamosSB
     */
    public ReclamosSB getReclamosSB() {
        return reclamosSB;
    }

    /**
     * @param reclamosSB the reclamosSB to set
     */
    public void setReclamosSB(ReclamosSB reclamosSB) {
        this.reclamosSB = reclamosSB;
    }

    /**
     * @return the fechaDesde
     */
    public Date getFechaDesde() {
        return fechaDesde;
    }

    /**
     * @param fechaDesde the fechaDesde to set
     */
    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    /**
     * @return the tiposDeReclamos
     */
    public List<TiposReclamos> getTiposDeReclamos() {
        tiposDeReclamos = reclamosSB.listarTiposReclamos();
        return tiposDeReclamos;
    }

    /**
     * @param tiposDeReclamos the tiposDeReclamos to set
     */
    public void setTiposDeReclamos(List<TiposReclamos> tiposDeReclamos) {
        this.tiposDeReclamos = tiposDeReclamos;
    }
}
