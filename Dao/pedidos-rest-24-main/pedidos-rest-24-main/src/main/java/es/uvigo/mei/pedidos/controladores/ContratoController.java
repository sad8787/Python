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
import es.uvigo.mei.pedidos.entidades.Contrato;
import es.uvigo.mei.pedidos.entidades.Trabajador;
import es.uvigo.mei.pedidos.servicios.ContratoService;
import es.uvigo.mei.pedidos.servicios.TrabajadorService;

@RestController
@RequestMapping(path = "/api/contratos", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ContratoController {
    @Autowired
	ContratoService contratoService;

	@Autowired
	TrabajadorService trabajadorService;

    @GetMapping()
	public ResponseEntity<List<Contrato>> buscarTodos() {
		List<Contrato> resultado = new ArrayList<>();
		resultado = contratoService.buscarTodos();
		return new ResponseEntity<>(resultado, HttpStatus.OK);

	}
    @GetMapping(path = "{id}")
	public ResponseEntity<Contrato> buscarPorId(@PathVariable Long id) {		 
		Optional<Contrato> resultado = contratoService.buscarPorid(id);
		if (resultado.isEmpty()) {
			throw new ResourceNotFoundException("Articulo no encontrado");
		} else {
			return new ResponseEntity<>(resultado.get(), HttpStatus.OK);
		}
	}
	

	



    @GetMapping(params = "descripcion")
	public ResponseEntity<List<Contrato>> buscarPorDescripcion(
			@RequestParam(required = true) String descripcion) {
		List<Contrato> resultado = new ArrayList<>();
		resultado = contratoService.buscarPorPatronDescripcion(descripcion);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}
   

    @DeleteMapping(path = "{id}")
	public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id) {
        
        List<Trabajador> trabajadors= trabajadorService.buscarPorContrto(id);
        if (trabajadors.isEmpty() ) {
            Optional<Contrato> contrato = contratoService.buscarPorid(id);
            
            if (contrato.isEmpty()) {
                throw new ResourceNotFoundException("no encontrado");
            } else {
                contratoService.eliminar(contrato.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        throw new ResourceNotFoundException("No es Posible Eliminarlo Ya que hay trabajadores contratatados bajo este contrato");
		
	}
    
   
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Contrato> modificar(@PathVariable Long id, @RequestBody Contrato contrato) {
		Optional<Contrato> contratoOptional = contratoService.buscarPorid(id);

		if (contratoOptional.isEmpty()) {
			throw new ResourceNotFoundException("Pedido no encontrado");
		} else {
			Contrato nuevoContrato = contratoService.modificar(contrato);
			return new ResponseEntity<>(nuevoContrato, HttpStatus.OK);
		}
	}

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Contrato> crear(@RequestBody Contrato contrato) {
		Contrato nuevoContrato = contratoService.crear(contrato);
		URI uri = crearURIContrato(nuevoContrato);
		return ResponseEntity.created(uri).body(nuevoContrato);
	}
    // Construye la URI del nuevo recurso creado con POST
	private URI crearURIContrato(Contrato contrato) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(contrato.getId())
				.toUri();
	}

}
