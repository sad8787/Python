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
import es.uvigo.mei.pedidos.entidades.Cliente;
import es.uvigo.mei.pedidos.servicios.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ClienteController {
	@Autowired
	ClienteService clienteService;

	@GetMapping()
	public ResponseEntity<List<Cliente>> buscarTodos() {
		List<Cliente> resultado = new ArrayList<>();
		resultado = clienteService.buscarTodos();
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

	@GetMapping(params = "localidad")
	public ResponseEntity<List<Cliente>> buscarPorLocalidad(
			@RequestParam(required = true) String localidad) {
		List<Cliente> resultado = new ArrayList<>();
		resultado = clienteService.buscarPorLocalidad(localidad);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

	@GetMapping(params = "nombre")
	public ResponseEntity<List<Cliente>> buscarPorNombre(
			@RequestParam(required = true) String nombre) {
		List<Cliente> resultado = new ArrayList<>();
		resultado = clienteService.buscarPorNombre(nombre);
		return new ResponseEntity<>(resultado, HttpStatus.OK);
	}

	@GetMapping(path = "{dni}")
	public ResponseEntity<Cliente> buscarPorDNI(@PathVariable String dni) {
		Optional<Cliente> cliente = clienteService.buscarPorDNI(dni);

		if (cliente.isEmpty()) {
			throw new ResourceNotFoundException("Cliente no encontrado");
		} else {
			return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
		}

	}

	@DeleteMapping(path = "{dni}")
	public ResponseEntity<HttpStatus> eliminar(@PathVariable String dni) {
		Optional<Cliente> cliente = clienteService.buscarPorDNI(dni);

		if (cliente.isEmpty()) {
			throw new ResourceNotFoundException("Cliente no encontrado");
		} else {
			clienteService.eliminar(cliente.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping(path = "{dni}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cliente> modificar(@PathVariable String dni, @Valid @RequestBody Cliente cliente) {
		Optional<Cliente> clienteOptional = clienteService.buscarPorDNI(dni);

		if (clienteOptional.isEmpty()) {
			throw new ResourceNotFoundException("Cliente no encontrado");
		} else {
			Cliente nuevoCliente = clienteService.modificar(cliente);
			return new ResponseEntity<>(nuevoCliente, HttpStatus.OK);
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente) {
		String dni = cliente.getDNI();
		if ((dni != null) && !dni.isBlank()) {
			Optional<Cliente> clienteOptional = clienteService.buscarPorDNI(dni);

			if (clienteOptional.isPresent()) {
				// DNI ya existe
				throw new WrongParameterException("DNI indicado ya existe");
			} else {
				Cliente nuevoCliente = clienteService.crear(cliente);
				URI uri = crearURICliente(nuevoCliente);
				return ResponseEntity.created(uri).body(nuevoCliente);
			}
		}
		// No aporta DNI
		throw new WrongParameterException("Falta indicar DNI");
	}

	// Construye la URI del nuevo recurso creado con POST
	private URI crearURICliente(Cliente cliente) {
		return ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{dni}")
				.buildAndExpand(cliente.getDNI())
				.toUri();
	}

}
