import { Routes } from '@angular/router';
import { LoginComponent } from './vista/formulario-loguarse/login/login.component';
import { CrearCuentaComponent } from './vista/formulario-loguarse/crear-cuenta/crear-cuenta.component';
import { MostrarProductosComponent } from './vista/mostrar-productos/mostrar-productos.component';
import { AuthenticationGuard } from './services/AuthenticationGuard.service';
import { ProductosComponent } from './vista/mostrar-productos/productos/productos.component';
import { CarritoProductosComponent } from './vista/mostrar-productos/carrito-productos/carrito-productos.component';

export const routes: Routes = [
    {
        path: '',
        component: LoginComponent
    },
    {
        path: 'crearCuenta',
        component: CrearCuentaComponent
    },
    {
        path: 'Productos',
        component: MostrarProductosComponent,
        canActivateChild: [AuthenticationGuard],
        children: [
            {
                path: '',
                component: ProductosComponent
            },
            {
                path: 'Carrito',
                component: CarritoProductosComponent
            }
        ]
    }
];
