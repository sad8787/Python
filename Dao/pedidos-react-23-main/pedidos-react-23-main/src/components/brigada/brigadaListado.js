import React, { useState, useEffect } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { ProgressSpinner } from 'primereact/progressspinner';
import { Dialog } from 'primereact/dialog';

import { useNavigate } from 'react-router-dom';

import brigadaService from '../../services/brigadaService';

export default function BrigadaListado(props) {

    const [textoBusqueda, setTextoBusqueda] = useState("");
    const [brigadas, setBrigada] = useState(null);
    const [cargando, setCargando] = useState(true);
    const [brigadaActual, setBrigadaActual] = useState(null);
    const [dialogoBorrado, setDialogoBorrado] = useState(false);

    let navigate = useNavigate();


    useEffect(() => {
        brigadaService.buscarTodas().then(res => {
            setBrigada(res.data);
            setCargando(false);
        });
    }, [dialogoBorrado]); // vincula la recarga a cambios en dialogoBorrado (para forzar la recarga despues de un borrado)


    function nuevaBrigada() {
        navigate("nuevo"); // navega a URL para creacion de nuevo cliente
    }

    function editarBrigada(brigada) {
        let a = typeof brigada.id;
        console.log(a);
        console.log("navega a "+brigada.id);
        console.log(brigada);
        navigate(brigada.id.toString()); // navega a URL de la brigada
    }

    function confirmarBorradoBrigada(brigada) {
        setBrigadaActual(brigada);
        setDialogoBorrado(true);
    }

    function borrarBrigada() {
        brigadaService.eliminar(brigadaActual.id).catch((err) => { //Captura error en peticion HTTP
            alert("Error borrando entidad.\n"+err.message);
        });
        ocultarDialogoBorrado();
    }

    function ocultarDialogoBorrado() {
        setBrigadaActual(null);
        setDialogoBorrado(false);
    }

    function buscarPorDescripcion() {
        setCargando(true);
        brigadaService.buscarPorDescripcion(textoBusqueda).then(res => {
            setBrigada(res.data);
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
            setBrigada([]); // Dejar la lista vacía en caso de error
            }); 
    }

    function buscarTodos() {
        setCargando(true);
        brigadaService.buscarTodas().then(res => {
            setBrigada(res.data);
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
            setBrigada([]); // Dejar la lista vacía en caso de error
            }); 
    }

    function buscarPorNombre() {
        setCargando(true);
        brigadaService.buscarPorNombre(textoBusqueda).then(res => {
            setBrigada(res.data);
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
            setBrigada([]); // Dejar la lista vacía en caso de error
            }); 
    }


    function onBusquedaChange(e) {
        setTextoBusqueda(e.target.value);
    }

    function accionesBrigada(rowData) {
        return (
            <React.Fragment>
                <Button icon="pi pi-pencil" className="p-button-rounded p-button-success mr-2" onClick={() => editarBrigada(rowData)} tooltip="Ver/editar la brigada" />
                <Button icon="pi pi-trash" className="p-button-rounded p-button-danger" onClick={() => confirmarBorradoBrigada(rowData)} tooltip="Eliminar la brigada" />
            </React.Fragment>
        );
    }


    const pieDialogoBorrado = (
        <React.Fragment>
            <Button label="No" icon="pi pi-times" className="p-button-text" onClick={ocultarDialogoBorrado}  />
            <Button label="Si" icon="pi pi-check" className="p-button-text" onClick={borrarBrigada} />
        </React.Fragment>
    );
    return (
        <div>
            <div className="text-3xl text-800 font-bold mb-4">Listado de brigadas</div>

            <div className="grid">
                <InputText id="busqueda" className="col-6 mr-2" onChange={onBusquedaChange} />
                <Button label="Buscar por descripción" className="col-1 mr-2" onClick={buscarPorDescripcion} />
                <Button label="Buscar por nombre" className="col-1 mr-2" onClick={buscarPorNombre} />
                <Button label="Buscar todas" className="col-1 mr-2" onClick={buscarTodos} />
            </div>

            <div className="flex justify-content-end">
                <Button label="Nueva brigada" icon="pi pi-plus" className="p-button-lg" onClick={nuevaBrigada} tooltip="Crear un nueva brigada" tooltipOptions={{position: 'bottom'}} />
            </div>

            {cargando && <div> <ProgressSpinner /> Cargando... </div>}

            <div className="surface-card p-4 border-round shadow-2">
                <DataTable value={brigadas} responsiveLayout="scroll" stripedRows emptyMessage="No hay brigadas que mostrar">
                    <Column field="id" header="ID" />
                    <Column field="nombre" header="Nombre" sortable />
                    <Column field="descripcion" header="Descripcion"/>
                    <Column body={accionesBrigada} />
                </DataTable>
            </div>

            <Dialog visible={dialogoBorrado} style={{ width: '450px' }} header="Confirm" modal
                footer={pieDialogoBorrado} onHide={ocultarDialogoBorrado}>
                <div className="flex align-items-center justify-content-center">
                    <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                    {brigadaActual && <span>Confirmar el borrado de <b>{brigadaActual.nombre}</b>?</span>}
                </div>
            </Dialog>

        </div>
    );
}