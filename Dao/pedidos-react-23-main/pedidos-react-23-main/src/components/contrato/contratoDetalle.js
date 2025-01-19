import React, { useState, useEffect } from 'react';

import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import { classNames } from 'primereact/utils';

import { useParams, useNavigate } from "react-router-dom";

import service from '../../services/contratoService';

export default function ContrtoDetalle() {

    const params = useParams();
    const navigate = useNavigate();
    const esNuevo = !('idcontrato' in params);

    const Vacia = { id:null,nombre: "", descripcion: "" };
    const [contrato, setContrto] = useState(Vacia);
    const [submitted, setSubmitted] = useState(false);


    useEffect(() => {
        if (!esNuevo) {
            service.buscarPorId(params.idcontrato).then(res => setContrto(res.data));
            //tiradaService.buscarPorId(params.id).then(res => setAux(res.data));
        }        
        
    },[]); // Carga después del primer renderizado

    function onInputnameChange(e, name) {
        const val = (e.target && e.target.value) || '';
        let _contrto = { ...contrato };
        _contrto[`${name}`] = val;
        setContrto(_contrto);
    }
    function onInputdescripcionChange(e, descripcion) {
        const val = (e.target && e.target.value) || '';
        let _contrto = { ...contrato };
        _contrto[`${descripcion}`] = val;
        setContrto(_contrto);
    }


    function onCancelar(event) {
        navigate("/contratos");
    }

    function handleSubmit(event) {
        event.preventDefault();
        setSubmitted(true);
        if (esNuevo) {
            service.crear(contrato);
        } else {            
           
           service.modificar(contrato.id, contrato);
        }
        navigate("/contratos");
    }

    return (
        <div>
            <div className="surface-card border-round shadow-2 p-4">
                {!esNuevo && <span className="text-900 text-2xl font-medium mb-4 block">Detalle de la Contrto</span>}
                {esNuevo && <span className="text-900 text-2xl font-medium mb-4 block">Nueva Contrto</span>}
                
                <form onSubmit={handleSubmit} >
                    <div className="p-fluid">
                        <div className="p-field">
                            <label htmlFor="id" >ID</label>
                            <InputText id="id" value={contrato.id} readOnly disabled/>
                        </div>

                        <div className="p-field">
                            <label htmlFor="name">Nombre</label>
                            <InputText id="name" value={contrato.nombre} onChange={(e) => onInputnameChange(e, 'nombre')} required className={classNames({ 'p-invalid': submitted && !contrato.nombre })} />
                            {submitted && !contrato.nombre && <small className="p-error">Debe indicarse un nombre.</small>}
                        </div>
                        <div className="p-field">
                            <label htmlFor="descripcion">Descripcion</label>
                            <InputText id="descripcion" value={contrato.descripcion} onChange={(e) => onInputdescripcionChange(e, 'descripcion')} />
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
