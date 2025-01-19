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
import es.uvigo.mei.pedidos.entidades.Brigada;
import es.uvigo.mei.pedidos.entidades.Trabajador;
import es.uvigo.mei.pedidos.servicios.BrigadaService;
import es.uvigo.mei.pedidos.servicios.TrabajadorService;

@RestController
@RequestMapping(path = "/api/brigadas", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")

public class BrigadaController {
   
	@Autowired
	BrigadaService brigadaService;
    @Autowired
    TrabajadorService trabajadorService;

    @GetMapping()
	public ResponseEntity<List<Brigada>> buscarTodos() {
		List<Brigada> resultado = new ArrayList<>();
		resultado = brigadaService.buscarTodos();
		return new ResponseEntity<>(resultado, HttpStatus.OK);

	}
    @GetMapping(path = "{id}")
	public ResponseEntity<Brigada> buscarPorId(@PathVariable Long id) {		 
		Optional<Brigada> resultado = brigadaService.buscarPorId(id);
		if (resultado.isEmpty()) {
			throw new ResourceNotFoundException("Articulo no encontrado");
		} else {
			return new ResponseEntity<>(resultado.get(), HttpStatus.OK);
		}
	}

    @GetMapping(params = "descripcion")
	public ResponseEntity<List<Brigada>> buscarPorDescripcion(
			@RequestParam(required = true) String descripcion) {
		List<Brigada> resultado = new ArrayList<>();
		resultado = brigadaService.buscarPorPatronDescripcion( descripcion);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}
	@GetMapping(params = "nombre")
	public ResponseEntity<List<Brigada>> buscarPorNombre(
			@RequestParam(required = true) String nombre) {
		List<Brigada> resultado = new ArrayList<>();
		resultado = brigadaService.buscarPorNombre(nombre);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

    @DeleteMapping(path = "{id}")
	public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id) {
        
        List<Trabajador> trabajadors= trabajadorService.buscarPorBrigada(id);
        if (trabajadors.isEmpty() ) {
            Optional<Brigada> result = brigadaService.buscarPorId(id);
            
            if (result.isEmpty()) {
                throw new ResourceNotFoundException("no encontrado");
            } else {
                brigadaService.eliminar(result.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        throw new ResourceNotFoundException("No es Posible Eliminarlo Ya que hay trabajadores contratatados bajo este Brigada");
		
	}
    
   
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Brigada> modificar(@PathVariable Long id, @RequestBody Brigada brigada) {
		Optional<Brigada> optional = brigadaService.buscarPorId(id);

		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Pedido no encontrado");
		} else {
			Brigada nuevo = brigadaService.modificar(brigada);
			return new ResponseEntity<>(nuevo, HttpStatus.OK);
		}
	}

	/*
	@GetMapping(path = "{id}/trabajadores")
	public ResponseEntity<List<Trabajador>> getTrabajadoresPorBrigadaId(@PathVariable Long id) {		 
		Optional<Brigada> resultado = brigadaService.buscarPorId(id);
		if (resultado.isEmpty()) {
			throw new ResourceNotFoundException("Articulo no encontrado");
		} else {
			return new ResponseEntity<>(resultado.get().getTrabajadores(), HttpStatus.OK);
		}
	}
	
	// PUT {idBrigadas}/trabajador/{dni}
	// add trabajador
	@PutMapping(path = "{idBrigadas}/trabajador/{dni}")
	public ResponseEntity<Brigada> addTrabajador(@PathVariable Long idBrigadas,
	 @PathVariable String dni){

		Optional<Brigada> optional = brigadaService.buscarPorId(idBrigadas);
		List<Trabajador> optionalTrabajador = trabajadorService.buscarPorDNI(dni);
		if (optional.isEmpty() || optionalTrabajador.isEmpty()) {
			throw new ResourceNotFoundException("No encontrado");
		} else {
			Brigada brigada = optional.get();
			Trabajador trabajador=optionalTrabajador.get(0);
			List<Trabajador> trabajadors =brigada.getTrabajadores();
			if(!trabajadors.contains(trabajador)){
				trabajador.setBrigada(brigada);
				Trabajador nuevo= trabajadorService.modificar(trabajador);
				optional = brigadaService.buscarPorId(idBrigadas);
				if (optional.isEmpty() ) {
					throw new ResourceNotFoundException("No encontrado");
				}else{ 
					return new ResponseEntity<>(optional.get(), HttpStatus.OK);
			}
			}else{
				throw new ResourceNotFoundException("El trbajador ya pertenece a esta Brigada");
			}
		}
		
	}

	// DELETE {idBrigadas}/trabajador/{idTrabajador}
	// Eliminar los datos de Stock de un Artículo en el Almacén indicado
	@DeleteMapping(path = "{idbrigadas}/trabajador/{idtrabajador}")
	public ResponseEntity<Brigada> deleteTrabajadorFromBrigada(@PathVariable Long idBrigadas,
	 @PathVariable Long idtrabajador){

		Optional<Brigada> optional = brigadaService.buscarPorId(idBrigadas);
		Optional<Trabajador> optionalTrabajador = trabajadorService.buscarPorId(idtrabajador);
		if (optional.isEmpty() || optionalTrabajador.isEmpty()) {
			throw new ResourceNotFoundException("No encontrado");
		} else {
			Brigada brigada = optional.get();
			List<Trabajador> listTrabajador=brigada.getTrabajadores();
			Trabajador trabajador=optionalTrabajador.get();
			System.err.println("1");
			System.err.println(brigada);
			System.err.println(trabajador);
			System.err.println("-----");

			if(listTrabajador.contains(trabajador)){
				
				listTrabajador.remove(trabajador);

				System.err.println(listTrabajador);
				System.err.println(trabajador);
				trabajador.setBrigada(null);
				System.err.println(trabajador);
				trabajadorService.modificar(trabajador);
				brigada.setTrabajadores(listTrabajador);
				brigadaService.modificar(brigada);
				
				return new ResponseEntity<>(optional.get(), HttpStatus.OK);
			}else{
				throw new ResourceNotFoundException("No encontrado");
			}
								
			
		}
			
		
	}
 */





    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Brigada> crear(@RequestBody Brigada brigada) {
		Brigada nuewBrigada = brigadaService.crear(brigada);
		URI uri = crearURIContrato(nuewBrigada);
		return ResponseEntity.created(uri).body(nuewBrigada);
	}
    // Construye la URI del nuevo recurso creado con POST
	private URI crearURIContrato(Brigada brigada) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(brigada.getId())
				.toUri();
	}

}
