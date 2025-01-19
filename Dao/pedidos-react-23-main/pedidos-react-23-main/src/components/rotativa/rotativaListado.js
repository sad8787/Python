
import React, { useState, useEffect } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { ProgressSpinner } from 'primereact/progressspinner';
import { Dialog } from 'primereact/dialog';

import { useNavigate } from 'react-router-dom';

import rotativaService from '../../services/rotativaService';

export default function RotativaListado(props) {

    const [textoBusqueda, setTextoBusqueda] = useState("");
    const [rotativas, setRotativa] = useState(null);
    const [cargando, setCargando] = useState(true);
    const [rotativaActual, setRotativaActual] = useState(null);
    const [dialogoBorrado, setDialogoBorrado] = useState(false);

    let navigate = useNavigate();


    useEffect(() => {
        rotativaService.buscarTodas().then(res => {
            setRotativa(res.data);
            setCargando(false);
        });
    }, [dialogoBorrado]); // vincula la recarga a cambios en dialogoBorrado (para forzar la recarga despues de un borrado)


    function nuevaRotativa() {
        navigate("nuevo"); // navega a URL para creacion de nuevo cliente
    }

    




    function editarRotativa(rotativa) {
        let a = typeof rotativa.id;
        console.log(a);
        console.log("navega a "+rotativa.id);
        console.log(rotativa);
        navigate(rotativa.id.toString()); // navega a URL de la Rotativa
    }

    function confirmarBorradoRotativa(rotativa) {
        setRotativaActual(rotativa);
        setDialogoBorrado(true);
    }

    function borrarRotativa() {
        rotativaService.eliminar(rotativaActual.id).catch((err) => { //Captura error en peticion HTTP
            alert("Error borrando entidad.\n"+err.message);
        });
        ocultarDialogoBorrado();
    }

    function ocultarDialogoBorrado() {
        setRotativaActual(null);
        setDialogoBorrado(false);
    }

    function buscarPorDescripcion() {
        setCargando(true);
        rotativaService.buscarPorDescripcion(textoBusqueda).then(res => {
            setRotativa(res.data);
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
            setRotativa([]); // Dejar la lista vacía en caso de error
            }); 
    }

    function buscarTodos() {
        setCargando(true);
        rotativaService.buscarTodas().then(res => {
            setRotativa(res.data);
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
            setRotativa([]); // Dejar la lista vacía en caso de error
            }); 
    }

    function onBusquedaChange(e) {
        setTextoBusqueda(e.target.value);
    }

    function accionesRotativa(rowData) {
        return (
            <React.Fragment>
                
                <Button icon="pi pi-pencil" className="p-button-rounded p-button-success mr-2" onClick={() => editarRotativa(rowData)} tooltip="Ver/editar la Rotativa" />
                <Button icon="pi pi-trash" className="p-button-rounded p-button-danger" onClick={() => confirmarBorradoRotativa(rowData)} tooltip="Eliminar la Rotativa" />
            </React.Fragment>
        );
    }


    const pieDialogoBorrado = (
        <React.Fragment>
            <Button label="No" icon="pi pi-times" className="p-button-text" onClick={ocultarDialogoBorrado}  />
            <Button label="Si" icon="pi pi-check" className="p-button-text" onClick={borrarRotativa} />
        </React.Fragment>
    );
    return (
        <div>
            <div className="text-3xl text-800 font-bold mb-4">Listado de Rotativas</div>

            <div className="grid">
                <InputText id="busqueda" className="col-6 mr-2" onChange={onBusquedaChange} />
                <Button label="Buscar por descripción" className="col-1 mr-2" onClick={buscarPorDescripcion} />
                <Button label="Buscar todas" className="col-1 mr-2" onClick={buscarTodos} />
            </div>

            <div className="flex justify-content-end">
                <Button label="Nueva Rotativa" icon="pi pi-plus" className="p-button-lg" onClick={nuevaRotativa} tooltip="Crear una nueva Rotativa" tooltipOptions={{position: 'bottom'}} />
            </div>

            {cargando && <div> <ProgressSpinner /> Cargando... </div>}

            <div className="surface-card p-4 border-round shadow-2">
                <DataTable value={rotativas} responsiveLayout="scroll" stripedRows emptyMessage="No hay Rotativas que mostrar">
                    <Column field="id" header="ID" />
                    <Column field="nombre" header="Nombre" sortable />
                    <Column field="descripcion" header="Descripcion"/>
                    <Column body={accionesRotativa} />
                </DataTable>
            </div>

            <Dialog visible={dialogoBorrado} style={{ width: '450px' }} header="Confirm" modal
                footer={pieDialogoBorrado} onHide={ocultarDialogoBorrado}>
                <div className="flex align-items-center justify-content-center">
                    <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                    {rotativaActual && <span>Confirmar el borrado de <b>{rotativaActual.nombre}</b>?</span>}
                </div>
            </Dialog>

        </div>
    );
}