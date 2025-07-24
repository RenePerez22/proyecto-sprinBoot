package Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.PreguntaFrecuenta;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.PreguntaFrecuenteRepository;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.service.PreguntaFrecuenteService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PreguntaFrecuenteServiceImpl implements PreguntaFrecuenteService {

    private static final Logger logger = LoggerFactory.getLogger(PreguntaFrecuenteServiceImpl.class);

    private final PreguntaFrecuenteRepository preguntaRepository;

    public PreguntaFrecuenteServiceImpl(PreguntaFrecuenteRepository preguntaRepository) {
        this.preguntaRepository = preguntaRepository;
    }

    @Override
    public List<PreguntaFrecuenta> listarPreguntas() {
        logger.info("Listando todas las preguntas frecuentes");
        return preguntaRepository.findAll();
    }

    @Override
    public PreguntaFrecuenta obtenerPorId(Long id) {
        logger.info("Buscando pregunta frecuente por ID: {}", id);
        return preguntaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada con id: " + id));
    }

    @Override
    public PreguntaFrecuenta crear(PreguntaFrecuenta pregunta) {
        logger.info("Creando nueva pregunta frecuente: {}", pregunta.getPregunta());
        return preguntaRepository.save(pregunta);
    }

    @Override
    public PreguntaFrecuenta actualizar(Long id, PreguntaFrecuenta pregunta) {
        logger.info("Actualizando pregunta frecuente con ID: {}", id);
        PreguntaFrecuenta existente = obtenerPorId(id);
        existente.setPregunta(pregunta.getPregunta());
        existente.setRespuesta(pregunta.getRespuesta());
        existente.setContexto(pregunta.getContexto());
        return preguntaRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        logger.info("Eliminando pregunta frecuente con ID: {}", id);
        if (!preguntaRepository.existsById(id)) {
            logger.error("Pregunta no encontrada con id: {}", id);
            throw new EntityNotFoundException("Pregunta no encontrada con id: " + id);
        }
        preguntaRepository.deleteById(id);
    }

    @Override
    public List<PreguntaFrecuenta> buscarPorPalabraClave(String palabraClave) {
        logger.info("Buscando preguntas con palabra clave: '{}'", palabraClave);
        List<PreguntaFrecuenta> resultados = preguntaRepository.buscarPorTexto(palabraClave.toLowerCase());
        logger.info("Número de resultados encontrados: {}", resultados.size());
        return resultados;
    }
}
