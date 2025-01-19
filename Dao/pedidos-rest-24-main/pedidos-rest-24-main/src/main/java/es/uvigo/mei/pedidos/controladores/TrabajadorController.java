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
import es.uvigo.mei.pedidos.controladores.excepciones.WrongParameterException;
import es.uvigo.mei.pedidos.entidades.Trabajador;
import es.uvigo.mei.pedidos.servicios.TrabajadorService;


@RestController
@RequestMapping(path = "/api/trabajadores", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class TrabajadorController {
	@Autowired
	TrabajadorService trabajadorService;

    @GetMapping()
	public ResponseEntity<List<Trabajador>> buscarTodos() {
		List<Trabajador> resultado = new ArrayList<>();
		resultado = trabajadorService.buscarTodos();
		return new ResponseEntity<>(resultado, HttpStatus.OK);

	}
    @GetMapping(path = "{id}")
	public ResponseEntity<Trabajador> buscarPorId(@PathVariable Long id) {		 
		Optional<Trabajador> resultado = trabajadorService.buscarPorId(id);
		if (resultado.isEmpty()) {
			throw new ResourceNotFoundException("Articulo no encontrado");
		} else {
			return new ResponseEntity<>(resultado.get(), HttpStatus.OK);
		}
	}

	@GetMapping(path = "/contrato/{id}")
	public ResponseEntity<List<Trabajador>> buscarContratoPorId(@PathVariable Long id) {		 
		List<Trabajador> resultado = trabajadorService.buscarPorBrigadaId(id);
		if (resultado.isEmpty()) {
			throw new ResourceNotFoundException("Articulo no encontrado");
		} else {
			return new ResponseEntity<>(resultado, HttpStatus.OK);
		}
	}
	@GetMapping(path = "/brigada/{id}")
	public ResponseEntity<List<Trabajador>> buscarBrigadaPorId(@PathVariable Long id) {		 
		List<Trabajador> resultado = trabajadorService.buscarPorBrigadaId(id);
		if (resultado.isEmpty()) {
			throw new ResourceNotFoundException("Articulo no encontrado");
		} else {
			return new ResponseEntity<>(resultado, HttpStatus.OK);
		}
	}

	@GetMapping(path = "id")
	public ResponseEntity<Trabajador> buscarPorIdid(@PathVariable Long id) {		 
		Optional<Trabajador> resultado = trabajadorService.buscarPorId(id);
		if (resultado.isEmpty()) {
			throw new ResourceNotFoundException("Articulo no encontrado");
		} else {
			return new ResponseEntity<>(resultado.get(), HttpStatus.OK);
		}
	}


    @GetMapping(params = "nombre")
	public ResponseEntity<List<Trabajador>> buscarPorNombre(
			@RequestParam(required = true) String nombre) {
		List<Trabajador> resultado = new ArrayList<>();
		resultado = trabajadorService.buscarPorNombre(nombre);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}
    @GetMapping(params = "dni")
	public ResponseEntity<List<Trabajador>> buscarPorDNI(
			@RequestParam(required = true) String dni) {
		List<Trabajador> resultado = new ArrayList<>();
		resultado = trabajadorService.buscarPorDNI(dni);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}


	

	@DeleteMapping(path = "{dni}")
	public ResponseEntity<HttpStatus> eliminar(@PathVariable String dni) {
        List<Trabajador> trabajadores = trabajadorService.buscarPorDNI(dni);
        if (trabajadores.isEmpty()) {
            throw new ResourceNotFoundException("no encontrado");
        } else {
            trabajadorService.eliminar(trabajadores.get(0));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }      
		
	}
    @DeleteMapping(path = "dni")
	public ResponseEntity<HttpStatus> eliminardni(@PathVariable String dni) {
        List<Trabajador> trabajadores = trabajadorService.buscarPorDNI(dni);
        if (trabajadores.isEmpty()) {
            throw new ResourceNotFoundException("no encontrado");
        } else {
            trabajadorService.eliminar(trabajadores.get(0));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }      
		
	}
    
   
    @PutMapping(path = "dni", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Trabajador> modificardni(@PathVariable String dni, @RequestBody Trabajador trabajador) {
		List<Trabajador> trabajadorOptional = trabajadorService.buscarPorDNI(dni);

		if (trabajadorOptional.isEmpty()) {
			throw new ResourceNotFoundException(" no encontrado");
		} else {
			Trabajador nuevoTrabajador = trabajadorService.modificar(trabajador);
			return new ResponseEntity<>(nuevoTrabajador, HttpStatus.OK);
		}
	}
	@PutMapping(path = "{dni}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Trabajador> modificar(@PathVariable String dni, @RequestBody Trabajador trabajador) {
		List<Trabajador> trabajadorOptional = trabajadorService.buscarPorDNI(dni);

		if (trabajadorOptional.isEmpty()) {
			throw new ResourceNotFoundException(" no encontrado");
		} else {
			Trabajador nuevoTrabajador = trabajadorService.modificar(trabajador);
			return new ResponseEntity<>(nuevoTrabajador, HttpStatus.OK);
		}
	}



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Trabajador> crear(@RequestBody Trabajador trabajador) {
        String dni = trabajador.getDNI();
		if ((dni == null) || dni.isBlank()){throw new WrongParameterException("Falta indicar DNI");}
		Trabajador nuevoTrabajador = trabajadorService.crear(trabajador);
		URI uri = crearURI(nuevoTrabajador);
		return ResponseEntity.created(uri).body(nuevoTrabajador);
	}
    // Construye la URI del nuevo recurso creado con POST
	private URI crearURI(Trabajador trabajador) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{dni}").buildAndExpand(trabajador.getDNI())
				.toUri();
	}

}
