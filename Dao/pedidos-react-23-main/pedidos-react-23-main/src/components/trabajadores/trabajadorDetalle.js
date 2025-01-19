import React, { useState, useEffect } from 'react';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import { classNames } from 'primereact/utils';

import { useParams, useNavigate } from "react-router-dom";
import brigadaService from '../../services/brigadaService';
import contratoService from '../../services/contratoService';
import service from '../../services/trabajadorService';

export default function TrabajadorDetalle() {

    const params = useParams();
    const navigate = useNavigate();
    const esNuevo = !('idtarabajador' in params);

    const Vacia = {id:null, nombre: "", dni: "",cargo:"",
        brigada:{id:null,nombre:"",descripcion:""},
        contrato:{id:null,nombre:"",descripcion:""} };
    const [trabajador, setTrabajador] = useState(Vacia);
    const [submitted, setSubmitted] = useState(false);
    const [brigadas, setBrigadas] = useState([]);
    const [contratos,setContratos] = useState([]);


    useEffect(() => {
        if (!esNuevo) {
            service.buscarPorId(params.idtarabajador).then(res => setTrabajador(res.data));
        }
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
            setContratos([]); // Dejar la lista vacía en caso de error
            });
    }, []); // Carga después del primer renderizado


    function onInputnameChange(e, name) {
        const val = (e.target && e.target.value) || '';
        let _Trabajador = { ...trabajador };
        _Trabajador[`${name}`] = val;
        setTrabajador(_Trabajador);
    }
    function onInputDNIChange(e, dni) {
        const val = (e.target && e.target.value) || '';
        let _Trabajador = { ...trabajador };
        _Trabajador[`${dni}`] = val;
        setTrabajador(_Trabajador);
    }
    function onInputCargoChange(e, cargo) {
        const val = (e.target && e.target.value) || '';
        let _Trabajador = { ...trabajador };
        _Trabajador[`${cargo}`] = val;
        setTrabajador(_Trabajador);
    }
    function onInputNombreChange(e, nombre) {
        const val = (e.target && e.target.value) || '';
        let _Trabajador = { ...trabajador };
        _Trabajador[`${nombre}`] = val;
        setTrabajador(_Trabajador);
    }

    function onInputnivelEducativoChange(e, nivelEducativo) {
        const val = (e.target && e.target.value) || '';
        let _Trabajador = { ...trabajador };
        _Trabajador[`${nivelEducativo}`] = val;
        setTrabajador(_Trabajador);
    }

    

    function onBrigadaChange(e) {
        let _Trabajador = { ...trabajador };
        _Trabajador.brigada = e.value;
        setTrabajador(_Trabajador);
    }
    function onContratoChange(e) {
        let _Trabajador = { ...trabajador };
        _Trabajador.contrato = e.value;
        setTrabajador(_Trabajador);
    }



    function onCancelar(event) {
        navigate("/trabajadores");
    }

    function handleSubmit(event) {
        event.preventDefault();
        setSubmitted(true);
        if (esNuevo) {
            service.crear(trabajador);
        } else {            
           
            service.modificar(trabajador.id, trabajador);
        }
        navigate("/trabajadores");
    }
    return (
        <div>
            <div className="surface-card border-round shadow-2 p-4">
                {!esNuevo && <span className="text-900 text-2xl font-medium mb-4 block">Detalle del Trabajador</span>}
                {esNuevo && <span className="text-900 text-2xl font-medium mb-4 block">Nuevo Trabajador</span>}
                <div>
                    <span className="text-900 text-2xl font-medium mb-4 block">
                        JSON Data:
                    </span>
                        {/* Mostrar el JSON en un elemento <pre> para formatearlo */}
                    <pre style={{ backgroundColor: "#f4f4f4", padding: "10px", borderRadius: "5px" }}>
                        {JSON.stringify(params, null, 4)}
                    </pre>
                </div>
                <form onSubmit={handleSubmit} >
                    <div className="p-fluid">
                        <div className="p-field">
                            <label htmlFor="id" >ID</label>
                            <InputText id="id" value={trabajador.id} readOnly disabled/>
                        </div>

                        <div className="p-field">
                            <label htmlFor="name">Nombre</label>
                            <InputText id="name" value={trabajador.nombre} onChange={(e) => onInputnameChange(e, 'nombre')} required className={classNames({ 'p-invalid': submitted && !trabajador.nombre })} />
                            {submitted && !trabajador.nombre && <small className="p-error">Debe indicarse un nombre.</small>}
                        </div>
                        
                       
                        <div className="p-field">
                            <label htmlFor="DNI">DNI</label>
                            <InputText id="dni" value={trabajador.dni} onChange={(e) => onInputDNIChange(e, 'dni')} />
                        </div>
                        <div className="p-field">
                            <label htmlFor="nombre">Nombre</label>
                            <InputText id="nombre" value={trabajador.nombre} onChange={(e) => onInputNombreChange(e, 'nombre')} />
                        </div>
                        <div className="p-field">
                            <label htmlFor="nivelEducativo">nivel Educativo</label>
                            <InputText id="nivelEducativo" value={trabajador.nivelEducativo} onChange={(e) => onInputnivelEducativoChange(e, 'nivelEducativo')} />
                        </div>
                        <div className="p-field">
                            <label htmlFor="cargo">cargo</label>
                            <InputText id="cargo" value={trabajador.cargo} onChange={(e) => onInputCargoChange(e, 'cargo')} />
                        </div>
                        <div className="p-field">
                            <label htmlFor="brigada">Brigada</label>
                            <Dropdown value={trabajador.brigada} options={brigadas} onChange={onBrigadaChange} optionLabel="nombre"
                            filter showClear filterBy="nombre" placeholder="Seleccionar Brigada" />
                        </div>
                        <div className="p-field">
                            <label htmlFor="Contrato">Contrato</label>
                            <Dropdown value={trabajador.contrato} options={contratos} onChange={onContratoChange} optionLabel="nombre"
                            filter showClear filterBy="nombre" placeholder="Seleccionar contrato" />
                        </div>
                    </div>

                    <Divider />

                    <div className="p-p-3">
                        <Button label="Cancelar" icon="pi pi-times" className="p-button-outlined mr-2" onClick={onCancelar} />
                        <Button label="Guardar" icon="pi pi-check" type="submit" />
                    </div>
                </form>
            </div>
        </div>
    );
}
