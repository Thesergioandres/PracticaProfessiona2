import { Component, inject } from '@angular/core';
import { ProductoService } from '../../../services/producto.service';
import { ComunicacionComponentesService } from '../../../services/comunicacion-componentes.service';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [],
  templateUrl: './productos.component.html',
  styleUrl: './productos.component.css'
})
export class ProductosComponent {
 servicioProducto = inject(ProductoService);
 servicioComunicacionComponentes = inject(ComunicacionComponentesService);
 listadoProductos: any[] = [];
  constructor() { }

  async ngOnInit(): Promise<void> {
    await this.listarProductos();
  }

  async listarProductos(): Promise<void> {
    const productos = await this.servicioProducto.listarTodos().toPromise() as any;
    this.listadoProductos = productos;
    console.log(this.listadoProductos);
  }

  agregarAlCarrito(producto: any, url:string): void {
    // Agregamos al producto la url de la imagen
    producto.imagen = url;
    producto.stock = producto.cantidad;
    producto.cantidad = 1;
    this.servicioComunicacionComponentes.actualizarCarroCompras(producto);
  }
}
