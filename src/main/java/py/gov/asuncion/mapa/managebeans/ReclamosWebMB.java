package py.gov.asuncion.mapa.managebeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.chart.PieChartModel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
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
    private Integer tipoMapa;
    private String resultadoConsulta;
    private MapModel emptyModel;

    private String resultadoMapa;

    private Integer totalGeneral;
    private Integer totalPendiente;
    private Integer totalAtendido;
    private Integer totalFinalizado;

    private PieChartModel pieModel2;

    private List<Integer> estadoReclamo;

    @EJB
    private ReclamosSB reclamosSB;

    public ReclamosWebMB() {

    }

    @PostConstruct
    public void init() {
        reclamo = new Reclamos();
        reclamo.setFkCodTipoReclamo(new TiposReclamos());
        resultadoConsulta = "";
        estadoReclamo = new ArrayList<>();
        tipoMapa = 0;
        totalGeneral = 0;
        totalPendiente = 0;
        totalAtendido = 0;
        totalFinalizado = 0;
    }

    public String consultarPorRangoDeFecha() {

        listaRec = reclamosSB.listarRangoDeFecha(reclamo.getFkCodTipoReclamo().getCodTipoReclamo(), fechaDesde, fechaHasta, estadoReclamo);
        totalGeneral = listaRec.size();
        if (tipoMapa == 0) {
            for (int i = 0; i < listaRec.size(); i++) {
                switch (listaRec.get(i).getFkCodEstadoReclamo().getCodEstadoReclamo()) {
                    case 3:
                        totalFinalizado = totalFinalizado + 1;
                        break;
                    case 2:
                        totalAtendido = totalAtendido + 1;
                        break;
                    default:
                        totalPendiente = totalPendiente + 1;
                        break;
                }
            }
        } else if (tipoMapa == 1) {
//            this.emptyModel = null;
            emptyModel = new DefaultMapModel();
            emptyModel.addOverlay(null);
//            Marker marker = new Marker(new LatLng(-25.2929451, -57.6072704), "jkhfdkjf");
//            emptyModel.addOverlay(marker);
            for (int i = 0; i < listaRec.size(); i++) {
                Marker marca = new Marker(new LatLng(listaRec.get(i).getLatitud(), listaRec.get(i).getLongitud()));
                marca.setTitle(listaRec.get(i).getDireccionReclamo());
                switch (listaRec.get(i).getFkCodEstadoReclamo().getCodEstadoReclamo()) {
                    case 3:
                        marca.setIcon("http://maps.google.com/mapfiles/ms/micons/green-dot.png");
                        totalFinalizado = totalFinalizado + 1;
                        break;
                    case 2:
                        marca.setIcon("http://maps.google.com/mapfiles/ms/micons/yellow-dot.png");
                        totalAtendido = totalAtendido + 1;
                        break;
                    default:
                        totalPendiente = totalPendiente + 1;
                        break;
                }
                emptyModel.addOverlay(marca);
            }
            return "map";
        } else {
            for (int i = 0; i < listaRec.size(); i++) {
                switch (listaRec.get(i).getFkCodEstadoReclamo().getCodEstadoReclamo()) {
                    case 3:
                        totalFinalizado = totalFinalizado + 1;
                        break;
                    case 2:
                        totalAtendido = totalAtendido + 1;
                        break;
                    default:
                        totalPendiente = totalPendiente + 1;
                        break;
                }
            }
            pieModel2 = new PieChartModel();
            pieModel2.set("Finalizados", totalFinalizado);
            pieModel2.set("Atendidos", totalAtendido);
            pieModel2.set("Pendientes", totalPendiente);

            pieModel2.setTitle(reclamo.getFkCodTipoReclamo().getNombreTipoReclamo());
            pieModel2.setShowDataLabels(true);
            pieModel2.setLegendPosition("e");
            pieModel2.setFill(true);
            pieModel2.setDiameter(500);

            return "graficos";
        }
        return "index";
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

    /**
     * @return the resultadoConsulta
     */
    public String getResultadoConsulta() {
        resultadoConsulta = "";
        if (listaRec != null && listaRec.size() > 0) {
            resultadoConsulta = "Total de reclamos: " + listaRec.size();
        }
        return resultadoConsulta;
    }

    /**
     * @param resultadoConsulta the resultadoConsulta to set
     */
    public void setResultadoConsulta(String resultadoConsulta) {
        this.resultadoConsulta = resultadoConsulta;
    }

    /**
     * @return the estadoReclamo
     */
    public List<Integer> getEstadoReclamo() {
        return estadoReclamo;
    }

    /**
     * @param estadoReclamo the estadoReclamo to set
     */
    public void setEstadoReclamo(List<Integer> estadoReclamo) {
        this.estadoReclamo = estadoReclamo;
    }

    /**
     * @return the tipoMapa
     */
    public Integer getTipoMapa() {
        return tipoMapa;
    }

    /**
     * @param tipoMapa the tipoMapa to set
     */
    public void setTipoMapa(Integer tipoMapa) {
        this.tipoMapa = tipoMapa;
    }

    /**
     * @return the emptyModel
     */
    public MapModel getEmptyModel() {
        return emptyModel;
    }

    /**
     * @param emptyModel the emptyModel to set
     */
    public void setEmptyModel(MapModel emptyModel) {
        this.emptyModel = emptyModel;
    }

    /**
     * @return the totalGeneral
     */
    public Integer getTotalGeneral() {
        return totalGeneral;
    }

    /**
     * @param totalGeneral the totalGeneral to set
     */
    public void setTotalGeneral(Integer totalGeneral) {
        this.totalGeneral = totalGeneral;
    }

    /**
     * @return the totalPendiente
     */
    public Integer getTotalPendiente() {
        return totalPendiente;
    }

    /**
     * @param totalPendiente the totalPendiente to set
     */
    public void setTotalPendiente(Integer totalPendiente) {
        this.totalPendiente = totalPendiente;
    }

    /**
     * @return the totalAtendido
     */
    public Integer getTotalAtendido() {
        return totalAtendido;
    }

    /**
     * @param totalAtendido the totalAtendido to set
     */
    public void setTotalAtendido(Integer totalAtendido) {
        this.totalAtendido = totalAtendido;
    }

    /**
     * @return the totalFinalizado
     */
    public Integer getTotalFinalizado() {
        return totalFinalizado;
    }

    /**
     * @param totalFinalizado the totalFinalizado to set
     */
    public void setTotalFinalizado(Integer totalFinalizado) {
        this.totalFinalizado = totalFinalizado;
    }

    /**
     * @return the resultadoMapa
     */
    public String getResultadoMapa() {
        return resultadoMapa;
    }

    /**
     * @param resultadoMapa the resultadoMapa to set
     */
    public void setResultadoMapa(String resultadoMapa) {
        this.resultadoMapa = resultadoMapa;
    }

    /**
     * @return the pieModel2
     */
    public PieChartModel getPieModel2() {
        return pieModel2;
    }

    /**
     * @param pieModel2 the pieModel2 to set
     */
    public void setPieModel2(PieChartModel pieModel2) {
        this.pieModel2 = pieModel2;
    }
}
