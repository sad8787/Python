import React, { useState, useEffect } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { ProgressSpinner } from 'primereact/progressspinner';
import { Dialog } from 'primereact/dialog';

import { useNavigate } from 'react-router-dom';

import Service from '../../services/contratoService';

export default function ContratoListado(props) {

    const [textoBusqueda, setTextoBusqueda] = useState("");
    const [contratos, setContrato] = useState(null);
    const [cargando, setCargando] = useState(true);
    const [contratoActual, setContratoActual] = useState(null);
    const [dialogoBorrado, setDialogoBorrado] = useState(false);

    let navigate = useNavigate();


    useEffect(() => {
        Service.buscarTodas().then(res => {
            setContrato(res.data);
            setCargando(false);
        });
    }, [dialogoBorrado]); // vincula la recarga a cambios en dialogoBorrado (para forzar la recarga despues de un borrado)


    function nuevaContrato() {
        navigate("nuevo"); // navega a URL para creacion de nuevo cliente
    }

    function editarContrato(contrato) {
        let a = typeof contrato.id;
        console.log(a);
        console.log("navega a "+contrato.id);
        console.log(contrato);
        navigate(contrato.id.toString()); // navega a URL de la Contrato
    }

    function confirmarBorradoContrato(contrato) {
        setContratoActual(contrato);
        setDialogoBorrado(true);
    }

    function borrarContrato() {
        Service.eliminar(contratoActual.id).catch((err) => { //Captura error en peticion HTTP
            alert("Error borrando entidad.\n"+err.message);
        });
        ocultarDialogoBorrado();
    }

    function ocultarDialogoBorrado() {
        setContratoActual(null);
        setDialogoBorrado(false);
    }

    function buscarPorDescripcion() {
        setCargando(true);
        Service.buscarPorDescripcion(textoBusqueda).then(res => {
            setContrato(res.data);
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
            setContrato([]); // Dejar la lista vacía en caso de error
            }); 
    }

    function buscarTodos() {
        setCargando(true);
        Service.buscarTodas().then(res => {
            setContrato(res.data);
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
            setContrato([]); // Dejar la lista vacía en caso de error
            }); 
    }

    function onBusquedaChange(e) {
        setTextoBusqueda(e.target.value);
    }

    function accionesContrato(rowData) {
        return (
            <React.Fragment>
                <Button icon="pi pi-pencil" className="p-button-rounded p-button-success mr-2" onClick={() => editarContrato(rowData)} tooltip="Ver/editar el Contrato" />
                <Button icon="pi pi-trash" className="p-button-rounded p-button-danger" onClick={() => confirmarBorradoContrato(rowData)} tooltip="Eliminar el Contrato" />
            </React.Fragment>
        );
    }


    const pieDialogoBorrado = (
        <React.Fragment>
            <Button label="No" icon="pi pi-times" className="p-button-text" onClick={ocultarDialogoBorrado}  />
            <Button label="Si" icon="pi pi-check" className="p-button-text" onClick={borrarContrato} />
        </React.Fragment>
    );
    return (
        <div>
            <div className="text-3xl text-800 font-bold mb-4">Listado de Contratos</div>

            <div className="grid">
                <InputText id="busqueda" className="col-6 mr-2" onChange={onBusquedaChange} />
                <Button label="Buscar por descripción" className="col-1 mr-2" onClick={buscarPorDescripcion} />
                <Button label="Buscar todas" className="col-1 mr-2" onClick={buscarTodos} />
            </div>

            <div className="flex justify-content-end">
                <Button label="Nuevo Contrato" icon="pi pi-plus" className="p-button-lg" onClick={nuevaContrato} tooltip="Crear un nueva Contrato" tooltipOptions={{position: 'bottom'}} />
            </div>

            {cargando && <div> <ProgressSpinner /> Cargando... </div>}

            <div className="surface-card p-4 border-round shadow-2">
                <DataTable value={contratos} responsiveLayout="scroll" stripedRows emptyMessage="No hay Contratos que mostrar">
                    <Column field="id" header="ID" />
                    <Column field="nombre" header="Nombre" sortable />
                    <Column field="descripcion" header="Descripcion"/>
                    <Column body={accionesContrato} />
                </DataTable>
            </div>

            <Dialog visible={dialogoBorrado} style={{ width: '450px' }} header="Confirm" modal
                footer={pieDialogoBorrado} onHide={ocultarDialogoBorrado}>
                <div className="flex align-items-center justify-content-center">
                    <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                    {contratoActual && <span>Confirmar el borrado de <b>{contratoActual.nombre}</b>?</span>}
                </div>
            </Dialog>

        </div>
    );
}