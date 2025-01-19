import React, { useState, useEffect } from 'react';

import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import { classNames } from 'primereact/utils';

import { useParams, useNavigate } from "react-router-dom";

import service from '../../services/brigadaService';

export default function BrigadaDetalle() {

    const params = useParams();
    const navigate = useNavigate();
    const esNuevo = !('idbrigada' in params);

    const Vacia = { nombre: "", descripcion: "" };
    const [brigada, setBrigada] = useState(Vacia);
   
   
    const [submitted, setSubmitted] = useState(false);
    


    useEffect(() => {
        if (!esNuevo) {
            service.buscarPorId(params.idbrigada).then(res => setBrigada(res.data));
        }
    }, []); // Carga después del primer renderizado


    useEffect(() => {
        if (!esNuevo) {
            service.buscarPorId(params.idbrigada).then(res => setBrigada(res.data));
            //tiradaService.buscarPorId(params.id).then(res => setAux(res.data));
        }        
        
    },[]); // Carga después del primer renderizado

    function onInputnameChange(e, name) {
        const val = (e.target && e.target.value) || '';
        let _brigada = { ...brigada };
        _brigada[`${name}`] = val;
        setBrigada(_brigada);
    }
    function onInputdescripcionChange(e, descripcion) {
        const val = (e.target && e.target.value) || '';
        let _brigada = { ...brigada };
        _brigada[`${descripcion}`] = val;
        setBrigada(_brigada);
    }


    function onCancelar(event) {
        navigate("/brigadas");
    }

    function handleSubmit(event) {
        event.preventDefault();
        setSubmitted(true);
        if (esNuevo) {
            service.crear(brigada);
        } else {            
           
           service.modificar(brigada.id, brigada);
        }
        navigate("/brigadas");
    }


     return (
            <div>
                <div>
                    <span className="text-900 text-2xl font-medium mb-4 block">
                        JSON Data:
                    </span>
                        {/* Mostrar el JSON en un elemento <pre> para formatearlo */}
                    <pre style={{ backgroundColor: "#f4f4f4", padding: "10px", borderRadius: "5px" }}>
                        {JSON.stringify(params, null, 4)}
                    </pre>
                </div>
                <div className="surface-card border-round shadow-2 p-4">
                    {!esNuevo && <span className="text-900 text-2xl font-medium mb-4 block">Detalle de la Contrto</span>}
                    {esNuevo && <span className="text-900 text-2xl font-medium mb-4 block">Nueva Contrto</span>}
                    
                    <form onSubmit={handleSubmit} >
                        <div className="p-fluid">
                            <div className="p-field">
                                <label htmlFor="id" >ID</label>
                                <InputText id="id" value={brigada.id} readOnly disabled/>
                            </div>
    
                            <div className="p-field">
                                <label htmlFor="name">Nombre</label>
                                <InputText id="name" value={brigada.nombre} onChange={(e) => onInputnameChange(e, 'nombre')} required className={classNames({ 'p-invalid': submitted && !brigada.nombre })} />
                                {submitted && !brigada.nombre && <small className="p-error">Debe indicarse un nombre.</small>}
                            </div>
                            <div className="p-field">
                                <label htmlFor="descripcion">Descripcion</label>
                                <InputText id="descripcion" value={brigada.descripcion} onChange={(e) => onInputdescripcionChange(e, 'descripcion')} />
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
