import React, { useState, useEffect } from 'react';

import { InputText } from 'primereact/inputtext';
import { InputNumber } from 'primereact/inputnumber';
import { Dropdown } from 'primereact/dropdown';
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import { classNames } from 'primereact/utils';

import { useParams, useNavigate } from "react-router-dom";

import tiradaService from '../../services/tiradaService';
import brigadaService from '../../services/brigadaService';
import rotativaService from '../../services/rotativaService';


export default function TiradaDetalle() {

    const params = useParams();
    const navigate = useNavigate();
    const esNuevo = !('idtirada' in params);

    const tiradaVacio = { id: null, nombre: "", descripcion: "" };
    const [tirada, setTirada] = useState(tiradaVacio);
    
    const [submitted, setSubmitted] = useState(false);
    
    const [brigadas, setBrigadas] = useState([]);
    const [rotativas, setRotativas] = useState([]);
    

    useEffect(() => {
        if (!esNuevo) {
            tiradaService.buscarPorId(params.idtirada).then(res => setTirada(res.data));
            //tiradaService.buscarPorId(params.id).then(res => setAux(res.data));
        }
        
        rotativaService.buscarTodas().then(res => setRotativas(res.data)).catch(error => {
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
            
            setRotativas([]); // Dejar la lista vacía en caso de error
            }); 
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
    },[]); // Carga después del primer renderizado


    function onInputChange(e, name) {
        const val = (e.target && e.target.value) || '';
        let _tirada = { ...tirada };
        _tirada[`${name}`] = val;
        setTirada(_tirada);
    }

    function onBrigadaChange(e) {
        let _tirada = { ...tirada };
        _tirada.brigada = e.value;
        setTirada(_tirada);
    }
    function onRotativaChange(e) {
        let _tirada = { ...tirada };
        _tirada.rotativa = e.value;
        setTirada(_tirada);
    }



    function onCancelar(event) {
        navigate("/tiradas");
    }

    function handleSubmit(event) {
        event.preventDefault();
        setSubmitted(true);
        if (esNuevo) {
            tiradaService.crear(tirada);
        } else {            
           
            tiradaService.modificar(tirada.id, tirada);
        }
        navigate("/tiradas");
    }


    return (
        <div>
            <div className="surface-card border-round shadow-2 p-4">
                {!esNuevo && <span className="text-900 text-2xl font-medium mb-4 block">Upate</span>}
                {esNuevo && <span className="text-900 text-2xl font-medium mb-4 block">Nuevo artículo</span>}
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
                            <InputText id="id" value={tirada.id} readOnly disabled />
                        </div>

                        <div className="p-field">
                            <label htmlFor="nombre">Nombre</label>
                            <InputText id="nombre" value={tirada.nombre} onChange={(e) => onInputChange(e, 'nombre')   } required className={classNames({ 'p-invalid': submitted && !tirada.nombre })} />
                            {submitted && !tirada.nombre && <small className="p-error">Debe indicarse un nombre.</small>}
                        </div>

                        <div className="p-field">
                            <label htmlFor="descripcion">Descripción</label>
                            <InputText id="descripcion" value={tirada.descripcion} onChange={(e) => onInputChange(e, 'descripcion')} />
                        </div>

                       
                        <div className="p-field">
                            <label htmlFor="brigada">Brigada</label>
                            <Dropdown value={tirada.brigada} options={brigadas} onChange={onBrigadaChange} optionLabel="nombre"
                                filter showClear filterBy="nombre" placeholder="Seleccionar Brigada" />
                        </div>
                        <div className="p-field">
                            <label htmlFor="rotativa">Rotativa</label>
                            <Dropdown value={tirada.rotativa} options={rotativas} onChange={onRotativaChange} optionLabel="nombre"
                                filter showClear filterBy="nombre" placeholder="Seleccionar rotativa" />
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
