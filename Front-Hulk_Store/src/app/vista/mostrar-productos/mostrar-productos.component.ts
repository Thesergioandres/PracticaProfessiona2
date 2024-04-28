import { Component, inject } from '@angular/core';
import { AngularMaterialModule } from '../../angular-material/angular-material.module';
import { Router, RouterLink, RouterModule, provideRouter } from '@angular/router';
import { ComunicacionComponentesService } from '../../services/comunicacion-componentes.service';

@Component({
  selector: 'app-mostrar-productos',
  standalone: true,
  imports: [AngularMaterialModule, RouterModule],
  templateUrl: './mostrar-productos.component.html',
  styleUrl: './mostrar-productos.component.css'
})
export class MostrarProductosComponent {

  router: Router = inject(Router);
  servicioComunicacion = inject(ComunicacionComponentesService);
  cerrarSesion(): void {
    sessionStorage.clear();
    this.router.navigate(['/']);
  }
}
