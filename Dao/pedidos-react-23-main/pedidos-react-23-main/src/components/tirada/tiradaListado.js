import React, { useState, useEffect } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { ProgressSpinner } from 'primereact/progressspinner';
import { Dialog } from 'primereact/dialog';
import { Dropdown } from 'primereact/dropdown';


import { useNavigate } from 'react-router-dom';

import tiradaService from '../../services/tiradaService';
import rotativaService from '../../services/rotativaService';
import brigadaService from '../../services/brigadaService';
export default function TiradaListado(props) {

    const [textoBusqueda, setTextoBusqueda] = useState("");
    
    const [rotativas, setRotativas] = useState([]);
    const [rotativasBusqueda, setRotativasBusqueda] = useState(null);

    const [brigadas, setBrigadas] = useState([]);
    const [brigadasBusqueda, setBrigadasBusqueda] = useState(null);

    const [tiradas, setTiradas] = useState(null);
    const [cargando, setCargando] = useState(true);
    const [tiradaActual, setTiradaActual] = useState(null);
    const [dialogoBorrado, setDialogoBorrado] = useState(false);

    const navigate = useNavigate();
    



    useEffect(() => {
        rotativaService.buscarTodas().then(res => setRotativas(res.data));
    }, []);  // al no estar vinculado a cambios, solo se ejecuta en el primer renderizado

    useEffect(() => {
        brigadaService.buscarTodas().then(res => setBrigadas(res.data));
    }, []);  // al no estar vinculado a cambios, solo se ejecuta en el primer renderizado


    useEffect(() => {
        tiradaService.buscarTodas().then(res => {
            setTiradas(res.data);
            setCargando(false);
        });
    }, [dialogoBorrado]); // vincula la recarga a cambios en dialogoBorrado (para forzar la recarga despues de un borrado)

    function nuevoTirada() {
        navigate("nuevo"); // navega a URL para creacion de nuevo articulo
    }

    function editarTirada(tirada) {
        navigate(tirada.id.toString()); // navega a URL del articulo
    }

    function confirmarBorradoTirada(tirada) {
        setTiradaActual(tirada);
        setDialogoBorrado(true);
    }

    function borrarTirada() {
        tiradaService.eliminar(tiradaActual.id).catch((err) => { //Captura error en peticion HTTP
            alert("Error borrando entidad.\n"+err.message);
        });
        ocultarDialogoBorrado();
    }

    function ocultarDialogoBorrado() {
        setTiradaActual(null);
        setDialogoBorrado(false);
    }

   
    function buscarPorRotativa() {
        if (rotativasBusqueda != null) {
            setCargando(true);   
            
            tiradaService.buscarPorRotativaId(rotativasBusqueda.id).then(res => {
                    setTiradas(res.data);
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
                setTiradas([]); // Dejar la lista vacía en caso de error
                }); 
                 
            
        }
    }
    function buscarPorBrigada() {
        if (brigadasBusqueda != null) {
            setCargando(true);
            tiradaService.buscarPorbrigadaId(brigadasBusqueda.id).then(res => {
                setTiradas(res.data);
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
            setTiradas([]); // Dejar la lista vacía en caso de error
            });           
            
        }
    }
    function buscarPorDescripcion() {
        setCargando(true);
        tiradaService.buscarPorDescripcion(textoBusqueda).then(res => {
            setTiradas(res.data);
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
            setTiradas([]); // Dejar la lista vacía en caso de error
            });
    }

    function buscarTodos() {
        setCargando(true);
        tiradaService.buscarTodas().then(res => {
            setTiradas(res.data);
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
            setTiradas([]); // Dejar la lista vacía en caso de error
            });



    }

    function onBusquedaChange(e) {
        setTextoBusqueda(e.target.value);
    }

    function onRotativasBusquedaChange(e) {
        setRotativasBusqueda(e.target.value);
    }
    function onBrigadasBusquedaChange(e) {
        setBrigadasBusqueda(e.target.value);
    }

    function accionesTirada(rowData) {
        return (
            <React.Fragment>
                <Button icon="pi pi-pencil" className="p-button-rounded p-button-success mr-2" onClick={() => editarTirada(rowData)} tooltip="Ver/editar"/>
                <Button icon="pi pi-trash" className="p-button-rounded p-button-danger" onClick={() => confirmarBorradoTirada(rowData)} tooltip="Eliminar" />
            </React.Fragment>
        );
    }
    
    const pieDialogoBorrado = (
        <React.Fragment>
            <Button label="No" icon="pi pi-times" className="p-button-text" onClick={ocultarDialogoBorrado} />
            <Button label="si" icon="pi pi-check" className="p-button-text" onClick={borrarTirada} />
        </React.Fragment>
    );



    return (
        <div>
            <div className="text-3xl text-800 font-bold mb-4">Listado de tiradas</div>            
            <div className="grid">
                <div className="col-8">
                    <div className="row">
                        <InputText id="busqueda" className="col-8 mr-2" onChange={onBusquedaChange} />
                        <Button label="Buscar Por Descripcion" className="col-3 mt-2 mr-2" onClick={buscarPorDescripcion} />
                    </div>
                    
                    <div className="row">
                        <Dropdown id="busquedarotativas" value={rotativasBusqueda} options={rotativas} onChange={onRotativasBusquedaChange} optionLabel="nombre"
                            filter showClear filterBy="nombre" placeholder="Seleccionar" className="col-8 mr-2 mt-2" />
                        <Button label="Buscar por Rotativa" className="col-3 mt-2 mr-2" onClick={buscarPorRotativa} />
                    </div>
                    <div className="row">
                        <Dropdown id="busquedabrigadas" value={brigadasBusqueda} options={brigadas} onChange={onBrigadasBusquedaChange} optionLabel="nombre"
                            filter showClear filterBy="nombre" placeholder="Seleccionar" className="col-8 mr-2 mt-2" />
                        <Button label="Buscar Brigada" className="col-3 mt-2 mr-2" onClick={buscarPorBrigada} />
                    </div>
                </div>
                <Button label="Buscar todos" className="col-2 my-2 mr-2" onClick={buscarTodos} />
            </div>

            <div className="flex justify-content-end">
                <Button label="Nuevo" icon="pi pi-plus" className="p-button-lg" onClick={nuevoTirada} tooltip="Crear un nuevo almacén" tooltipOptions={{ position: 'bottom' }} />
            </div>

            {cargando && <div> <ProgressSpinner /> Cargando... </div>}

            <div className="surface-card p-4 border-round shadow-2">
                <DataTable value={tiradas} responsiveLayout="scroll" stripedRows emptyMessage="No hay tiradas que mostrar">
                    <Column field="id" header="ID" />
                    <Column field="nombre" header="Nombre" sortable />
                    <Column field="descripcion" header="Descripción" />
                    <Column field="brigada.id" header="brigada_ID" sortable />
                    <Column field="brigada.nombre" header="brigada" sortable />
                    <Column field="rotativa.id" header="rotativa_ID" />                    
                    <Column field="rotativa.nombre" header="rotativa" />                    
                    <Column body={accionesTirada} />
                </DataTable>
            </div>

            <Dialog visible={dialogoBorrado} style={{ width: '450px' }} header="Confirm" modal
                footer={pieDialogoBorrado} onHide={ocultarDialogoBorrado}>
                <div className="flex align-items-center justify-content-center">
                    <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                    {tiradaActual && <span>Confirmar el borrado de <b>{tiradaActual.nombre}</b>?</span>}
                </div>
            </Dialog>

        </div>
    );
}