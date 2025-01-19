package es.uvigo.mei.pedidos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.uvigo.mei.pedidos.daos.AlmacenDAO;
import es.uvigo.mei.pedidos.daos.ArticuloAlmacenDAO;
import es.uvigo.mei.pedidos.daos.ArticuloDAO;
import es.uvigo.mei.pedidos.daos.BrigadaDAO;
import es.uvigo.mei.pedidos.daos.ClienteDAO;
import es.uvigo.mei.pedidos.daos.ContratoDAO;
import es.uvigo.mei.pedidos.daos.FamiliaDAO;
import es.uvigo.mei.pedidos.daos.PedidoDAO;
import es.uvigo.mei.pedidos.daos.RotativaDAO;
import es.uvigo.mei.pedidos.daos.TiradaDAO;
import es.uvigo.mei.pedidos.daos.TrabajadorDAO;
import es.uvigo.mei.pedidos.entidades.Almacen;
import es.uvigo.mei.pedidos.entidades.Articulo;
import es.uvigo.mei.pedidos.entidades.ArticuloAlmacen;
import es.uvigo.mei.pedidos.entidades.Brigada;
import es.uvigo.mei.pedidos.entidades.Cliente;
import es.uvigo.mei.pedidos.entidades.Contrato;
import es.uvigo.mei.pedidos.entidades.Direccion;
import es.uvigo.mei.pedidos.entidades.Familia;
import es.uvigo.mei.pedidos.entidades.LineaPedido;
import es.uvigo.mei.pedidos.entidades.Pedido;
import es.uvigo.mei.pedidos.entidades.Rotativa;
import es.uvigo.mei.pedidos.entidades.Tirada;
import es.uvigo.mei.pedidos.entidades.Trabajador;

@SpringBootApplication
public class PedidosSpringApplication implements CommandLineRunner {
	@Autowired
	FamiliaDAO familiaDAO;

	@Autowired
	ArticuloDAO articuloDAO;

	@Autowired
	ClienteDAO clienteDAO;

	@Autowired
	PedidoDAO pedidoDAO;

	@Autowired
	AlmacenDAO almacenDAO;

	@Autowired
	ArticuloAlmacenDAO articuloAlmacenDAO;


	@Autowired
	ContratoDAO contratoDAO;
	@Autowired
	TrabajadorDAO trabajadorDAO;
	@Autowired
	BrigadaDAO brigadaDAO;
	@Autowired
	RotativaDAO rotativaDAO;
	@Autowired
	TiradaDAO tiradaDAO;

	public static void main(String[] args) {
		SpringApplication.run(PedidosSpringApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//crearEntidades();
		crearEntidades2();
		//consultarEntidades();
	}

	// Mantienen referencias al Almacen "principal" y al Cliente "Pepe", creados en "crearEntidades()", para ser usadas en "consultarEntidades()"
	private Almacen almacenPrincipal;  
	private Cliente clientePepe;

	private void crearEntidades() {
		Familia f1 = new Familia("tubos", "tubos de todas clases");
		Familia f2 = new Familia("tuercas", "tuercas de todas clases");
		f1 = familiaDAO.save(f1);
		f2 = familiaDAO.save(f2);

		Articulo a1 = new Articulo("tubo acero", "tubo de acero", f1, 10.0);
		Articulo a2 = new Articulo("tubo plastico", "tubo de plastico", f1, 5.0);
		Articulo a3 = new Articulo("tuerca acero", "tuerca de acero 10/18", f2, 10.0);
		Articulo a4 = new Articulo("tuerca plástico", "tuerca de plástico", f2, 5.0);
		a1 = articuloDAO.save(a1);
		a2 = articuloDAO.save(a2);
		a3 = articuloDAO.save(a3);
		a4 = articuloDAO.save(a4);

		Direccion d1 = new Direccion("calle 1", "Ourense", "11111", "Ourense", "988111111");
		Direccion d2 = new Direccion("calle 2", "Santiago", "22222", "A Coruña", "981222222");

		Cliente c1 = new Cliente("11111111A", "Pepe Cliente1 Cliente1", d1);
		Cliente c2 = new Cliente("22222222A", "Ana Cliente2 Cliente2", d2);
		c1 = clienteDAO.save(c1);
		c2 = clienteDAO.save(c2);
		clientePepe = c1; // Guarda referencia

		Direccion d3 = new Direccion("calle 3", "Santiago", "33333", "A Coruña", "981333333");
		Almacen a = new Almacen("principal", "almacen principal", d3);
		a = almacenDAO.save(a);
		almacenPrincipal = a; // Guarda referencia

		ArticuloAlmacen aa1 = new ArticuloAlmacen(a1, a, 10);
		ArticuloAlmacen aa2 = new ArticuloAlmacen(a2, a, 15);
		ArticuloAlmacen aa3 = new ArticuloAlmacen(a3, a, 20);
		ArticuloAlmacen aa4 = new ArticuloAlmacen(a4, a, 25);
		aa1 = articuloAlmacenDAO.save(aa1);
		aa2 = articuloAlmacenDAO.save(aa2);
		aa3 = articuloAlmacenDAO.save(aa3);
		aa4 = articuloAlmacenDAO.save(aa4);

		Pedido p1 = new Pedido(Calendar.getInstance().getTime(), c1);
		p1.anadirLineaPedido(new LineaPedido(p1, 2, a1, a1.getPrecioUnitario()));
		p1.anadirLineaPedido(new LineaPedido(p1, 5, a2, a2.getPrecioUnitario()));
		p1.anadirLineaPedido(new LineaPedido(p1, 1, a3, a3.getPrecioUnitario()));
		p1 = pedidoDAO.save(p1);

		Pedido p2 = new Pedido(Calendar.getInstance().getTime(), c1);
		p2.anadirLineaPedido(new LineaPedido(p2, 5, a4, a4.getPrecioUnitario()));
		p2.anadirLineaPedido(new LineaPedido(p2, 2, a1, a1.getPrecioUnitario()));
		p2 = pedidoDAO.save(p2);

	}

	private void consultarEntidades() {
		System.out.println("-----------");
		List<Articulo> articulos = articuloDAO.findAll();
		System.out.println("Todos los Articulos:");
		for (Articulo a : articulos) {
			System.out.println("\t" + a);
		}
		System.out.println("-----------");

		System.out.println("Todos los tubos:");
		List<Familia> familiasTubos = familiaDAO.findByPatronDescripcion("tubo");
		for (Familia f : familiasTubos) {
			List<Articulo> tubos = articuloDAO.findByFamiliaId(f.getId());
			for (Articulo a : tubos) {
				System.out.println("\t" + a);
			}
		}
		System.out.println("-----------");

		System.out.println("Stock en almacen principal "+almacenPrincipal.getNombre());	
		List<ArticuloAlmacen> stocks = articuloAlmacenDAO.findByArticuloId(almacenPrincipal.getId());
		for (ArticuloAlmacen stock : stocks) {
			System.out.println("\t"+stock.getArticulo()+" [Unidades="+stock.getStock()+"]");
		}
		System.out.println("-----------");

		System.out.println("Pedidos de "+clientePepe.getNombre());	
		List<Pedido> pedidos = pedidoDAO.findByClienteDNI(clientePepe.getDNI());
		for (Pedido p : pedidos) {
			System.out.println("\t"+p);
			Pedido pConLineas = pedidoDAO.findPedidoConLineas(p.getId());
			for (LineaPedido lp : pConLineas.getLineas()) {
				System.out.println("\t\t"+lp);
			}
			System.out.println("\t---");
		}
		System.out.println("-----------");
	}

	private void crearEntidades2(){
		Date date = new Date(); // Fecha actual
        System.out.println("Fecha original: " + date);
        // Crear una instancia de Calendar y ajustar la fecha
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Establecer la fecha base
        calendar.add(Calendar.YEAR, 1); // Sumar 1 año
        Date date2 = calendar.getTime(); // Obtener la nueva fecha

		Contrato c1= new Contrato("Impresor A","Impresor jefe de brigada",date,date2);
		Contrato c2= new Contrato("Impresor B","Impresor segundo jefe de brigada",date,date2);
		Contrato c3= new Contrato("Impresor c","Impresor de brigada",date,date2);	
		Contrato c4= new Contrato("Impresor j","Impresor de brigada",date,date2);
		contratoDAO.save(c1);
		contratoDAO.save(c2);
		contratoDAO.save(c3);
		contratoDAO.save(c4);
		
		Brigada b1= new Brigada("Brigada 1", "Turno de dia");
		Brigada b2= new Brigada("Brigada 2", "Turno de Noche");
		brigadaDAO.save(b1);
		brigadaDAO.save(b2);
		
		Trabajador t1= new Trabajador("80063826111","Juan","jefe","Superior",c1,b1);
		Trabajador t2= new Trabajador("80063426112","Ramon","jefe","Superior",c1,b2);
		Trabajador t3= new Trabajador("80063426113","Rafael","obrero","Superior",c2,b1);
		Trabajador t4= new Trabajador("80063426114","Luis","obrero","Superior",c2,b2);
		Trabajador t5= new Trabajador("80063426115","Javier","obrero","Superior",c3,b1);
		Trabajador t6= new Trabajador("80063426116","Godofredo","obrero","Superior",c3,b1);
		Trabajador t7= new Trabajador("80063426122","Alejandro","obrero","Superior",c3,b1);
		Trabajador t8= new Trabajador("80063426134","Fidel","obrero","Superior",c3,b2);
		Trabajador t9= new Trabajador("80063426156","Manuel","obrero","Superior",c3,b2);
		Trabajador t10= new Trabajador("800634261189","Lisandre","obrero","Superior",c3,b2);
		trabajadorDAO.save(t1);
		trabajadorDAO.save(t2);
		trabajadorDAO.save(t3);
		trabajadorDAO.save(t4);
		trabajadorDAO.save(t5);
		trabajadorDAO.save(t6);
		trabajadorDAO.save(t7);
		trabajadorDAO.save(t8);
		trabajadorDAO.save(t9);
		trabajadorDAO.save(t10);
		
		

		Rotativa r1= new Rotativa("Rotatiba 1","Blanco y negro");
		Rotativa r2= new Rotativa("Rotatiba 2","A color");
		Rotativa r3= new Rotativa("Rotatiba 3","blanco y negro del fondo");
		rotativaDAO.save(r1);
		rotativaDAO.save(r2);
		rotativaDAO.save(r3);
		
		Tirada tirada1 = new Tirada("Principito", "El Principito pagina 1 a la 50",b1,r1);
		Tirada tirada2 = new Tirada("Principito", "El Principito pagina 51 a la 100",b2,r3);
		Tirada tirada3 = new Tirada("Principito", "El Principito pagina 101 a la 150",b1,r1);
		Tirada tirada4 = new Tirada("Atlas", "Atlas pagina 1 a la 50",b2,r2);
		Tirada tirada5 = new Tirada("Libretas", "libreta a cuadros",b2,r1);
		Tirada tirada6 = new Tirada("Livertador", "periodico de 8 paginas Libertador",b1,r3);
		tiradaDAO.save(tirada1);
		tiradaDAO.save(tirada2);
		tiradaDAO.save(tirada3);
		tiradaDAO.save(tirada4);
		tiradaDAO.save(tirada5);
		tiradaDAO.save(tirada6);



	}
}
