package com.empresa.catalogo.service;

import com.empresa.catalogo.dto.ProductoRequestDTO;
import com.empresa.catalogo.dto.ProductoResponseDTO;
import com.empresa.catalogo.entity.Producto;
import com.empresa.catalogo.exception.RecursoNoEncontradoException;
import com.empresa.catalogo.factory.ProductoFactory;
import com.empresa.catalogo.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de ProductoService con logging SLF4J.
 *
 * Niveles de log usados:
 * - DEBUG: operaciones de consulta (solo visible con nivel DEBUG activo)
 * - INFO:  operaciones exitosas (crear, actualizar, eliminar)
 * - WARN:  situaciones inusuales (recurso no encontrado)
 * - ERROR: excepciones inesperadas
 *
 * Se usan placeholders {} en lugar de concatenación de strings
 * para evitar construcción del mensaje cuando el nivel está desactivado.
 *
 * @author Andres Felipe Jimenez Ramirez
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    private static final Logger log =
            LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository repo;
    private final ProductoFactory factory;

    public ProductoServiceImpl(ProductoRepository repo, ProductoFactory factory) {
        this.repo = repo;
        this.factory = factory;
    }

    @Override
    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        log.info("Creando producto: nombre={}, categoria={}",
                dto.getNombre(), dto.getCategoria());

        Producto p = factory.toEntity(dto);
        ProductoResponseDTO resp = factory.toResponseDTO(repo.save(p));

        log.info("Producto creado exitosamente con id={}", resp.getId());
        return resp;
    }

    @Override
    public ProductoResponseDTO buscarPorId(Long id) {
        log.debug("Buscando producto con id={}", id);

        Producto p = repo.findById(id).orElseThrow(() -> {
            log.warn("Producto con id={} no encontrado", id);
            return new RecursoNoEncontradoException("Producto", id);
        });

        log.debug("Producto con id={} encontrado: nombre={}", id, p.getNombre());
        return factory.toResponseDTO(p);
    }

    @Override
    public List<ProductoResponseDTO> listarActivos() {
        log.debug("Listando todos los productos activos");

        List<ProductoResponseDTO> lista = repo.findByActivoTrue().stream()
                .map(factory::toResponseDTO)
                .toList();

        log.info("Se retornaron {} productos activos", lista.size());
        return lista;
    }

    @Override
    @Transactional
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {
        log.info("Actualizando producto con id={}", id);

        Producto existente = repo.findById(id).orElseThrow(() -> {
            log.warn("Producto con id={} no encontrado para actualizar", id);
            return new RecursoNoEncontradoException("Producto", id);
        });

        existente.setNombre(dto.getNombre());
        existente.setPrecio(dto.getPrecio());
        existente.setCategoria(dto.getCategoria());

        ProductoResponseDTO resp = factory.toResponseDTO(repo.save(existente));
        log.info("Producto con id={} actualizado correctamente", id);
        return resp;
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando producto con id={}", id);
        buscarPorId(id); // verifica existencia — genera WARN si no existe
        repo.deleteById(id);
        log.info("Producto con id={} eliminado correctamente", id);
    }
}
