package es.uvigo.mei.pedidos.controladores;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import es.uvigo.mei.pedidos.controladores.excepciones.ResourceNotFoundException;
import es.uvigo.mei.pedidos.entidades.Rotativa;
import es.uvigo.mei.pedidos.servicios.RotativaService;


@RestController
@RequestMapping(path = "/api/rotativas", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class RotativaController {
    @Autowired
	RotativaService rotativaService;



    @GetMapping()
	public ResponseEntity<List<Rotativa>> buscarTodos() {
		List<Rotativa> resultado = new ArrayList<>();
		resultado = rotativaService.buscarTodos();
		return new ResponseEntity<>(resultado, HttpStatus.OK);

	}
    @GetMapping(path = "{id}")
	public ResponseEntity<Rotativa> buscarPorId(@PathVariable Long id) {		 
		Optional<Rotativa> resultado = rotativaService.buscarPorId(id);
		if (resultado.isEmpty()) {
			throw new ResourceNotFoundException("no encontrado");
		} else {
			return new ResponseEntity<>(resultado.get(), HttpStatus.OK);
		}
	}

	@GetMapping(path = "id")
	public ResponseEntity<Rotativa> buscarporid(@PathVariable Long id) {		 
		Optional<Rotativa> resultado = rotativaService.buscarPorId(id);
		if (resultado.isEmpty()) {
			throw new ResourceNotFoundException("no encontrado");
		} else {
			return new ResponseEntity<>(resultado.get(), HttpStatus.OK);
		}
	}


    @GetMapping(params = "descripcion")
	public ResponseEntity<List<Rotativa>> buscarPorDescripcion(
			@RequestParam(required = true) String descripcion) {
		List<Rotativa> resultado = new ArrayList<>();
		resultado = rotativaService.buscarPorDescripcion(descripcion);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}
	@GetMapping(params = "nombre")
	public ResponseEntity<List<Rotativa>> buscarPorNombre(
			@RequestParam(required = true) String nombre) {
		List<Rotativa> resultado = new ArrayList<>();
		resultado = rotativaService.buscarPorNombre(nombre);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}
   

    @DeleteMapping(path = "{id}")
	public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id) {
        
        Optional<Rotativa> rotativas= rotativaService.buscarPorId(id);
        if (!rotativas.isEmpty() ) {
            rotativaService.eliminar(rotativas.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);           
        }
        throw new ResourceNotFoundException("No es Posible Eliminarlo ");
		
	}
	@DeleteMapping(path = "id")
	public ResponseEntity<HttpStatus> eliminar2(@PathVariable Long id) {
        
        Optional<Rotativa> rotativas= rotativaService.buscarPorId(id);
        if (!rotativas.isEmpty() ) {
            rotativaService.eliminar(rotativas.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);           
        }
        throw new ResourceNotFoundException("No es Posible Eliminarlo ");
		
	}
    
   
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rotativa> modificar(@PathVariable Long id, @RequestBody Rotativa rotativa) {
		Optional<Rotativa> optional = rotativaService.buscarPorId(id);
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("rotativa no encontrado");
		} else {
			Rotativa nuevo = rotativaService.modificar(rotativa);
			return new ResponseEntity<>(nuevo, HttpStatus.OK);
		}
	}
	@PutMapping(path = "id", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rotativa> modificarbyid(@PathVariable Long id, @RequestBody Rotativa rotativa) {
		Optional<Rotativa> optional = rotativaService.buscarPorId(id);
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("rotativa no encontrado");
		} else {
			Rotativa nuevo = rotativaService.modificar(rotativa);
			return new ResponseEntity<>(nuevo, HttpStatus.OK);
		}
	}


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rotativa> crear(@RequestBody Rotativa rotativa) {
		Rotativa newRotativa = rotativaService.crear(rotativa);
		URI uri = crearURIRotativa(newRotativa);
		return ResponseEntity.created(uri).body(newRotativa);
	}
   
    // Construye la URI del nuevo recurso creado con POST
	private URI crearURIRotativa(Rotativa rotativa) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(rotativa.getId())
				.toUri();
	}

}
