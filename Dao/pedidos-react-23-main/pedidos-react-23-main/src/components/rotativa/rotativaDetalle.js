import React, { useState, useEffect } from 'react';

import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import { classNames } from 'primereact/utils';

import { useParams, useNavigate } from "react-router-dom";

import service from '../../services/rotativaService';

export default function RotativaDetalle() {

    const params = useParams();
    const navigate = useNavigate();
    const esNuevo = !('idrotativa' in params);

    const Vacia = { nombre: "", descripcion: "" };
    const [rotativa, setRotativa] = useState(Vacia);
    const [submitted, setSubmitted] = useState(false);


    useEffect(() => {
        if (!esNuevo) {
            service.buscarPorId(params.idrotativa).then(res => setRotativa(res.data));
        }
    }, []); // Carga después del primer renderizado


    function onInputChange(e, name) {
        const val = (e.target && e.target.value) || '';
        let _Rotativa = { ...rotativa };
        _Rotativa[`${name}`] = val;
        setRotativa(_Rotativa);
    }

    function onCancelar(event) {
        navigate("/rotativas");
    }

    function handleSubmit(event) {
        event.preventDefault();
        setSubmitted(true);
        if (esNuevo) {
            service.crear(rotativa);
        } else {
            service.modificar(rotativa.id, rotativa);
        }
        navigate("/rotativas");
    }

    return (
        <div>
            <div className="surface-card border-round shadow-2 p-4">
                {!esNuevo && <span className="text-900 text-2xl font-medium mb-4 block">Detalle de la Rotativa</span>}
                {esNuevo && <span className="text-900 text-2xl font-medium mb-4 block">Nueva Rotativa</span>}

                

                <form onSubmit={handleSubmit} >
                    <div className="p-fluid">
                        <div className="p-field">
                            <label htmlFor="id" >ID</label>
                            <InputText id="id" value={rotativa.id} readOnly disabled/>
                        </div>

                        <div className="p-field">
                            <label htmlFor="name">Nombre</label>
                            <InputText id="name" value={rotativa.nombre} onChange={(e) => onInputChange(e, 'nombre')} required className={classNames({ 'p-invalid': submitted && !rotativa.nombre })} />
                            {submitted && !rotativa.nombre && <small className="p-error">Debe indicarse un nombre.</small>}
                        </div>
                        <div className="p-field">
                            <label htmlFor="descripcion">Descripcion</label>
                            <InputText id="descripcion" value={rotativa.descripcion} onChange={(e) => onInputChange(e, 'descripcion')} />
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
