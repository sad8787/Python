package es.uvigo.mei.pedidos.controladores.hateoas;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.uvigo.mei.pedidos.entidades.Articulo;
import es.uvigo.mei.pedidos.servicios.ArticuloService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/v2/articulos", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticuloControllerV2 {
	@Autowired
	ArticuloService articuloService;

	@GetMapping()
	public ResponseEntity<List<EntityModel<Articulo>>> buscarTodos(
			@RequestParam(required = false) Long familiaId,
			@RequestParam(required = false) String descripcion) {
		try {
			List<Articulo> resultado = new ArrayList<>();

			if (familiaId != null) {
				resultado = articuloService.buscarPorFamilia(familiaId);
			} else if (descripcion != null) {
				resultado = articuloService.buscarPorDescripcion(descripcion);
			} else {
				resultado = articuloService.buscarTodos();
			}

			if (resultado.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			List<EntityModel<Articulo>> resultadoDTO = new ArrayList<>();
			resultado.forEach(a -> resultadoDTO.add(crearDTOArticulo(a)));

			return new ResponseEntity<>(resultadoDTO, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<EntityModel<Articulo>> buscarPorId(@PathVariable Long id) {
		Optional<Articulo> articulo = articuloService.buscarPorId(id);

		if (articulo.isPresent()) {
			EntityModel<Articulo> dto = crearDTOArticulo(articulo.get());
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "{id}")
	public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id) {
		try {
			Optional<Articulo> articulo = articuloService.buscarPorId(id);
			if (articulo.isPresent()) {
				articuloService.eliminar(articulo.get());
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EntityModel<Articulo>> modificar(@PathVariable Long id,
			@Valid @RequestBody Articulo articulo) {
		Optional<Articulo> articuloOptional = articuloService.buscarPorId(id);

		if (articuloOptional.isPresent()) {
			Articulo nuevoArticulo = articuloService.modificar(articulo);
			EntityModel<Articulo> dto = crearDTOArticulo(nuevoArticulo);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EntityModel<Articulo>> crear(@Valid @RequestBody Articulo articulo) {
		try {
			Articulo nuevoArticulo = articuloService.crear(articulo);
			EntityModel<Articulo> dto = crearDTOArticulo(nuevoArticulo);
			URI uri = crearURIArticulo(nuevoArticulo);

			return ResponseEntity.created(uri).body(dto);
		} catch (

		Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Crear los DTO con enlaces HATEOAS
	private EntityModel<Articulo> crearDTOArticulo(Articulo articulo) {
		Long id = articulo.getId();
		EntityModel<Articulo> dto = EntityModel.of(articulo);
		Link linkSelf = linkTo(methodOn(ArticuloControllerV2.class).buscarPorId(id)).withSelfRel();
		dto.add(linkSelf);

		if (articulo.getFamilia() != null) {
			Long idFamilia = articulo.getFamilia().getId();
			Link linkFamilia = linkTo(methodOn(FamiliaControllerV2.class).buscarPorId(idFamilia)).withRel("familia");
			dto.add(linkFamilia);
		}
		
		return dto;
	}

	// Construye la URI del nuevo recurso creado con POST
	private URI crearURIArticulo(Articulo articulo) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(articulo.getId()).toUri();
	}

}
