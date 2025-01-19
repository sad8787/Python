import React, { useState, useEffect } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { ProgressSpinner } from 'primereact/progressspinner';
import { Dialog } from 'primereact/dialog';
import { Dropdown } from 'primereact/dropdown';
import { useNavigate } from 'react-router-dom';
import contratoService from '../../services/contratoService';
import brigadaService from '../../services/brigadaService';
import trabajadorService from '../../services/trabajadorService';

export default function TrabajadorListado(props) {

    const [textoBusqueda, setTextoBusqueda] = useState("");
    const [trabajadors, setTrabajador] = useState(null);
    const [cargando, setCargando] = useState(true);
    const [trabajadorActual, setTrabajadorActual] = useState(null);
    const [dialogoBorrado, setDialogoBorrado] = useState(false);

    const [brigadas, setBrigadas] = useState([]);
    const [brigadasBusqueda, setBrigadasBusqueda] = useState(null);

    const [contratos, setContratos] = useState([]);
    const [contratosBusqueda, setContratosBusqueda] = useState(null);

    let navigate = useNavigate();


    useEffect(() => {
        trabajadorService.buscarTodas().then(res => {
            setTrabajador(res.data);
            setCargando(false);
        });
    }, [dialogoBorrado]); // vincula la recarga a cambios en dialogoBorrado (para forzar la recarga despues de un borrado)


    useEffect(() => {
        brigadaService.buscarTodas().then(res => setBrigadas(res.data)).catch(error => {
            // Manejo del error
            console.error("Error al buscar tiradas:", error);

            // Opcional: manejar diferentes tipos de errores
            if (error.response) {
                // Errores relacionados con la respuesta del servidor
                console.error(`Error ${error.response.status}: ${error.response.data.detail}`);
            } else if (error.request) {
                // El cliente hizo la solicitud pero no recibió respuesta
                console.error("No se recibió respuesta del servidor:", error.request);
            } else {
                // Algo salió mal al configurar la solicitud
                console.error("Error al configurar la solicitud:", error.message);
            }
            // Actualizar estado para reflejar el error en la UI
            setCargando(false);
            setBrigadas([]); // Dejar la lista vacía en caso de error
            }); 
        contratoService.buscarTodas().then(res => setContratos(res.data)).catch(error => {
            // Manejo del error
            console.error("Error al buscar tiradas:", error);

            // Opcional: manejar diferentes tipos de errores
            if (error.response) {
                // Errores relacionados con la respuesta del servidor
                console.error(`Error ${error.response.status}: ${error.response.data.detail}`);
            } else if (error.request) {
                // El cliente hizo la solicitud pero no recibió respuesta
                console.error("No se recibió respuesta del servidor:", error.request);
            } else {
                // Algo salió mal al configurar la solicitud
                console.error("Error al configurar la solicitud:", error.message);
            }
            // Actualizar estado para reflejar el error en la UI
            setContratos(false);
            setBrigadas([]); // Dejar la lista vacía en caso de error
            });
    }, []);  // al no estar vinculado a cambios, solo se ejecuta en el primer renderizado

    function nuevaTrabajador() {
        navigate("nuevo"); // navega a URL para creacion de nuevo cliente
    }

    function editarTrabajador(trabajador) {
        let a = typeof trabajador.id;
        console.log(a);
        console.log("navega a "+trabajador.id);
        console.log(trabajador);
        navigate(trabajador.id.toString()); // navega a URL de la Trabajador
    }

    function confirmarBorradoTrabajador(trabajador) {
        setTrabajadorActual(trabajador);
        setDialogoBorrado(true);
    }

    function borrarTrabajador() {
        trabajadorService.eliminar(trabajadorActual.id).catch((err) => { //Captura error en peticion HTTP
            alert("Error borrando entidad.\n"+err.message);
        });
        ocultarDialogoBorrado();
        buscarTodos();
    }

    function ocultarDialogoBorrado() {
        setTrabajadorActual(null);
        setDialogoBorrado(false);
    }

    function buscarPorNombre() {
        setCargando(true);
        trabajadorService.buscarPorNombre(textoBusqueda).then(res => {
            setTrabajador(res.data);
            setCargando(false);
        }).catch(error => {
            // Manejo del error
            console.error("Error al buscar tiradas:", error);

            // Opcional: manejar diferentes tipos de errores
            if (error.response) {
                // Errores relacionados con la respuesta del servidor
                console.error(`Error ${error.response.status}: ${error.response.data.detail}`);
            } else if (error.request) {
                // El cliente hizo la solicitud pero no recibió respuesta
                console.error("No se recibió respuesta del servidor:", error.request);
            } else {
                // Algo salió mal al configurar la solicitud
                console.error("Error al configurar la solicitud:", error.message);
            }
            // Actualizar estado para reflejar el error en la UI
            setCargando(false);
            setTrabajador([]); // Dejar la lista vacía en caso de error
            }); 
    }
    function buscarPorDNI() {
        setCargando(true);
        trabajadorService.buscarPorDNI(textoBusqueda).then(res => {
            setTrabajador(res.data);
            setCargando(false);
        }).catch(error => {
            // Manejo del error
            console.error("Error al buscar tiradas:", error);

            // Opcional: manejar diferentes tipos de errores
            if (error.response) {
                // Errores relacionados con la respuesta del servidor
                console.error(`Error ${error.response.status}: ${error.response.data.detail}`);
            } else if (error.request) {
                // El cliente hizo la solicitud pero no recibió respuesta
                console.error("No se recibió respuesta del servidor:", error.request);
            } else {
                // Algo salió mal al configurar la solicitud
                console.error("Error al configurar la solicitud:", error.message);
            }
            // Actualizar estado para reflejar el error en la UI
            setCargando(false);
            setTrabajador([]); // Dejar la lista vacía en caso de error
            }); 
    }

    function buscarTodos() {
        setCargando(true);
        trabajadorService.buscarTodas().then(res => {
            setTrabajador(res.data);
            setCargando(false);
        }).catch(error => {
            // Manejo del error
            console.error("Error al buscar tiradas:", error);

            // Opcional: manejar diferentes tipos de errores
            if (error.response) {
                // Errores relacionados con la respuesta del servidor
                console.error(`Error ${error.response.status}: ${error.response.data.detail}`);
            } else if (error.request) {
                // El cliente hizo la solicitud pero no recibió respuesta
                console.error("No se recibió respuesta del servidor:", error.request);
            } else {
                // Algo salió mal al configurar la solicitud
                console.error("Error al configurar la solicitud:", error.message);
            }
            // Actualizar estado para reflejar el error en la UI
            setCargando(false);
            setTrabajador([]); // Dejar la lista vacía en caso de error
            }); 
    }
    function buscarPorBrigada() {
        if (brigadasBusqueda != null) {
            setCargando(true);
            trabajadorService.buscarPorbrigadaId(brigadasBusqueda.id).then(res => {
                setTrabajador(res.data);
                setCargando(false);
            }).catch(error => {
                // Manejo del error
                console.error("Error al buscar tiradas:", error);
    
                // Opcional: manejar diferentes tipos de errores
                if (error.response) {
                    // Errores relacionados con la respuesta del servidor
                    console.error(`Error ${error.response.status}: ${error.response.data.detail}`);
                } else if (error.request) {
                    // El cliente hizo la solicitud pero no recibió respuesta
                    console.error("No se recibió respuesta del servidor:", error.request);
                } else {
                    // Algo salió mal al configurar la solicitud
                    console.error("Error al configurar la solicitud:", error.message);
                }
                // Actualizar estado para reflejar el error en la UI
                setCargando(false);
                setTrabajador([]); // Dejar la lista vacía en caso de error
                }); 
        }
    }
    


    function buscarPorContrato() {
        if (brigadasBusqueda != null) {
            setCargando(true);
            trabajadorService.buscarPorcontratoId(brigadasBusqueda.id).then(res => {
                setTrabajador(res.data);
                setCargando(false);
            }).catch(error => {
                // Manejo del error
                console.error("Error al buscar tiradas:", error);
    
                // Opcional: manejar diferentes tipos de errores
                if (error.response) {
                    // Errores relacionados con la respuesta del servidor
                    console.error(`Error ${error.response.status}: ${error.response.data.detail}`);
                } else if (error.request) {
                    // El cliente hizo la solicitud pero no recibió respuesta
                    console.error("No se recibió respuesta del servidor:", error.request);
                } else {
                    // Algo salió mal al configurar la solicitud
                    console.error("Error al configurar la solicitud:", error.message);
                }
                // Actualizar estado para reflejar el error en la UI
                setCargando(false);
                setTrabajador([]); // Dejar la lista vacía en caso de error
                }); 
        }
    }

    function onBusquedaChange(e) {
        setTextoBusqueda(e.target.value);
    }
    function onBrigadasBusquedaChange(e) {
        setBrigadasBusqueda(e.target.value);
    }
    function onContratosBusquedaChange(e) {
        setContratosBusqueda(e.target.value);
    }

    function accionesTrabajador(rowData) {
        return (
            <React.Fragment>
                <Button icon="pi pi-pencil" className="p-button-rounded p-button-success mr-2" onClick={() => editarTrabajador(rowData)} tooltip="Ver/editar la Trabajador" />
                <Button icon="pi pi-trash" className="p-button-rounded p-button-danger" onClick={() => confirmarBorradoTrabajador(rowData)} tooltip="Eliminar la Trabajador" />
            </React.Fragment>
        );
    }


    const pieDialogoBorrado = (
        <React.Fragment>
            <Button label="No" icon="pi pi-times" className="p-button-text" onClick={ocultarDialogoBorrado}  />
            <Button label="Si" icon="pi pi-check" className="p-button-text" onClick={borrarTrabajador} />
        </React.Fragment>
    );
    return (
        <div>
            
            <div className="text-3xl text-800 font-bold mb-4">Listado de Trabajadores</div>

            <div className="grid">
                <div className="col-8">
                    <div className="row">
                        <InputText id="busqueda" className="col-6 mr-2" onChange={onBusquedaChange} />    
                        
                        <Button label="Buscar DNI" className="col-1 mr-2" onClick={buscarPorDNI} />
                        <Button label="Buscar por nombre" className="col-1 mr-2" onClick={buscarPorNombre} />
                    </div>
                    <div className="row">
                        <Dropdown id="busquedabrigadas" value={brigadasBusqueda} options={brigadas} onChange={onBrigadasBusquedaChange} optionLabel="nombre"
                                            filter showClear filterBy="nombre" placeholder="Seleccionar" className="col-8 mr-2 mt-2" />
                        <Button label="Buscar Brigada" className="col-3 mt-2 mr-2" onClick={buscarPorBrigada} />
                    </div>
                    <div className="row">
                        <Dropdown id="busquedaContrato" value={contratosBusqueda} options={contratos} onChange={onContratosBusquedaChange} optionLabel="nombre"
                                            filter showClear filterBy="nombre" placeholder="Seleccionar" className="col-8 mr-2 mt-2" />
                        <Button label="Buscar Contrato" className="col-3 mt-2 mr-2" onClick={buscarPorContrato} />
                    </div>
                    <div className="row"> 

                    </div>
                    <div className="row">
                        
                    </div>

                </div> 
                <Button label="Buscar todas" className="col-1 mr-2" onClick={buscarTodos} />               
            </div>         
            

            <div className="flex justify-content-end">
                <Button label="Nuevo Trabajador" icon="pi pi-plus" className="p-button-lg" onClick={nuevaTrabajador} tooltip="Crear un nuevo Trabajador" tooltipOptions={{position: 'bottom'}} />
            </div>

            {cargando && <div> <ProgressSpinner /> Cargando... </div>}

            <div className="surface-card p-4 border-round shadow-2">
                <DataTable value={trabajadors} responsiveLayout="scroll" stripedRows emptyMessage="No hay Trabajadors que mostrar">
                    <Column field="id" header="ID" />
                    <Column field="dni" header="DNI" />
                    <Column field="nombre" header="Nombre" sortable />
                    <Column field="cargo" header="cargo"/>
                    <Column field="nivelEducativo" header="nivelEducativo"/>
                    <Column field="contrato.nombre" header="contrato"/>
                    
                    <Column body={accionesTrabajador} />
                </DataTable>
            </div>

            <Dialog visible={dialogoBorrado} style={{ width: '450px' }} header="Confirm" modal
                footer={pieDialogoBorrado} onHide={ocultarDialogoBorrado}>
                <div className="flex align-items-center justify-content-center">
                    <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                    {trabajadorActual && <span>Confirmar el borrado de <b>{trabajadorActual.nombre}</b>?</span>}
                </div>
            </Dialog>

        </div>
    );
}