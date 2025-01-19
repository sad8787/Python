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
import es.uvigo.mei.pedidos.entidades.Tirada;
import es.uvigo.mei.pedidos.servicios.TiradaServise;


@RestController
@RequestMapping(path = "/api/tiradas", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class TiradaController {
    @Autowired
	TiradaServise tiradaService;

    @GetMapping()
	public ResponseEntity<List<Tirada>> buscarTodos() {
		List<Tirada> resultado = new ArrayList<>();
		resultado = tiradaService.buscarTodos();
		return new ResponseEntity<>(resultado, HttpStatus.OK);

	}
    @GetMapping(path = "{id}")
	public ResponseEntity<Tirada> buscarPorId(@PathVariable Long id) {		 
		Optional<Tirada> resultado = tiradaService.buscarPorId(id);
		if (resultado.isEmpty()) {
			throw new ResourceNotFoundException("Articulo no encontrado");
		} else {
			return new ResponseEntity<>(resultado.get(), HttpStatus.OK);
		}
	}

    @GetMapping(params = "nombre")
	public ResponseEntity<List<Tirada>> buscarPorNombre(
			@RequestParam(required = true) String nombre) {
		List<Tirada> resultado = new ArrayList<>();
		resultado = tiradaService.buscarPorNombre(nombre);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}
   //////////////////////////////////////////////
    // GET {brigadaid}brigada
	// Tiradas de una brigada
	@GetMapping(path = "brigadaid")
    public ResponseEntity<List<Tirada>> buscarPorBrigadaId(long brigadaid){
        List<Tirada> resultado = new ArrayList<>();
        resultado = tiradaService.buscarPorBrigadaId(brigadaid);
		if(resultado.isEmpty()){resultado = new ArrayList<>();}
        return new ResponseEntity<>(resultado, HttpStatus.OK);

    }

    // GET {rotativaid}rotativa
	// Tiradas de una Rotativa
	@GetMapping(path = "rotativaid")
    public ResponseEntity<List<Tirada>> buscarPorRotativaId(long rotativaid){
        List<Tirada> resultado = new ArrayList<>();
        resultado = tiradaService.buscarPorRotativaId(rotativaid);
        return new ResponseEntity<>(resultado, HttpStatus.OK);

    }

     // GET {Descripcion} string
	// Tiradas de una Rotativa
	@GetMapping(path = "descripcion")
    public ResponseEntity<List<Tirada>> buscarPorDescripcion(String descripcion){
        List<Tirada> resultado = new ArrayList<>();
        resultado = tiradaService.buscarPorDescripcion(descripcion);
        return new ResponseEntity<>(resultado, HttpStatus.OK);

    }
   
    

	@GetMapping(path = "brigada/{id}")
	public ResponseEntity<List<Tirada>> getBrigadaPorTiradaId(@PathVariable Long id) {		 
		List<Tirada> resultado = tiradaService.buscarPorBrigadaId(id);
		if (resultado.isEmpty()) {
			throw new ResourceNotFoundException("Articulo no encontrado");
		} else {
			return new ResponseEntity<>(resultado, HttpStatus.OK);
		}
	}
	@GetMapping(path = "rotativa/{id}")
	public ResponseEntity<List<Tirada>> getRotativaPorTiradaId(@PathVariable Long id) {		 
		List<Tirada> resultado = tiradaService.buscarPorRotativaId(id);
		if (resultado.isEmpty()) {
			throw new ResourceNotFoundException("Articulo no encontrado");
		} else {
			return new ResponseEntity<>(resultado, HttpStatus.OK);
		}
	}





   

    @DeleteMapping(path = "{id}")
	public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id) {
        
        Optional<Tirada> tiradas= tiradaService.buscarPorId(id);
        if (!tiradas.isEmpty() ) {
            tiradaService.eliminar(tiradas.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);           
        }
        throw new ResourceNotFoundException("No es Posible Eliminarlo ");
		
	}
    
   
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Tirada> modificar(@PathVariable Long id, @RequestBody Tirada tirada) {
		Optional<Tirada> optional = tiradaService.buscarPorId(id);
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException(" no encontrado");
		} else {
			Tirada nuevo = tiradaService.modificar(tirada);
			return new ResponseEntity<>(nuevo, HttpStatus.OK);
		}
	}
    
  




   @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Tirada> crear(@RequestBody Tirada tirada) {
		Tirada newTirada = tiradaService.crear(tirada);
		URI uri = crearURI(newTirada);
		return ResponseEntity.created(uri).body(newTirada);
	}
    // Construye la URI del nuevo recurso creado con POST
	private URI crearURI(Tirada tirada) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(tirada.getId())
				.toUri();
	}
	





}
