import { TabView, TabPanel } from 'primereact/tabview';

import AlmacenInfo from "./almacenInfo";
import AlmacenStock from "./almacenStock";


export default function AlmacenDetalle() {

    return (
        <div>
            <TabView>
                <TabPanel header="Información de almacén">
                    <AlmacenInfo />
                </TabPanel>
                <TabPanel header="Stock del almacén">
                    <AlmacenStock />
                </TabPanel>
            </TabView>
        </div>
    );
}