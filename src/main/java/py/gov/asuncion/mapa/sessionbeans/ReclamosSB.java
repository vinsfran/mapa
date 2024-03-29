package py.gov.asuncion.mapa.sessionbeans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import py.gov.asuncion.mapa.entidades.Reclamos;
import py.gov.asuncion.mapa.entidades.TiposReclamos;

/**
 *
 * @author vinsfran
 */
@Stateless
@SuppressWarnings("unchecked")
public class ReclamosSB {

    @PersistenceContext(unitName = "reclamosmcaPU")
    private EntityManager em;

    public List<Reclamos> listarReclamos() {
        Query q = em.createNamedQuery("Reclamos.findAll");
        return q.getResultList();
    }

    public List<Reclamos> listarReclamos2(Integer codigo) {
        StringBuilder jpql = new StringBuilder();

        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodTipoReclamo.codTipoReclamo = :paramLoginUsuario");

        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramLoginUsuario", codigo);
        return q.getResultList();
    }

    public List<Reclamos> listarRangoDeFecha(Integer codTipoReclamo, Date fechaDesde, Date fechaHasta, List<Integer> estadosReclamo) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT e ");
        jpql.append("FROM Reclamos e ");
        jpql.append("WHERE e.fkCodTipoReclamo.codTipoReclamo = :paramCodTipo ");
        if (fechaDesde != null && fechaHasta != null) {
            jpql.append("AND e.fechaReclamo BETWEEN :paramFechaDesde AND :paramFechaHasta ");
        }
        if (!estadosReclamo.isEmpty()) {
            String sql = "AND e.fkCodEstadoReclamo.codEstadoReclamo IN (";
            for (int i = 0; i < estadosReclamo.size(); i++) {
                sql = sql + estadosReclamo.get(i);
                if ((i + 1) != estadosReclamo.size()) {
                    sql = sql + ",";
                }
            }
            sql = sql + ")";
            jpql.append(sql);
        }
        Query q = em.createQuery(jpql.toString());
        q.setParameter("paramCodTipo", codTipoReclamo);
        if (fechaDesde != null && fechaHasta != null) {
            q.setParameter("paramFechaDesde", fechaDesde);
            q.setParameter("paramFechaHasta", fechaHasta);
        }
        return q.getResultList();
    }

    public String formatearFecha(Date fecha) {
        if (fecha == null) {
            return "";
        } else {
            // formateo
            String patron = "dd/MM/yyyy";
            SimpleDateFormat formato = new SimpleDateFormat(patron);
            return formato.format(fecha).toString();
        }
    }

    @SuppressWarnings("unchecked")
    public List<TiposReclamos> listarTiposReclamos() {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT e ");
        jpql.append("FROM TiposReclamos e ");
        jpql.append("ORDER BY e.nombreTipoReclamo");
        Query q = em.createQuery(jpql.toString());
        return q.getResultList();
    }
}
