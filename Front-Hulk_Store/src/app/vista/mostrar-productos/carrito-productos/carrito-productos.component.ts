import { Component, ElementRef, ViewChild, inject } from '@angular/core';
import { AngularMaterialModule } from '../../../angular-material/angular-material.module';
import { Observable, Subject } from 'rxjs';
import { CurrencyPipe, NgFor } from '@angular/common';
import { ComunicacionComponentesService } from '../../../services/comunicacion-componentes.service';
import { RouterModule } from '@angular/router';

export interface Articulo {
  id: number;
  nombre: string;
  imagen: string;
  color: string;
  talla: string;
  stockLimitado: boolean;
  precio: number;
  stock: number;
  cantidad: number;
}
@Component({
  selector: 'app-carrito-productos',
  standalone: true,
  imports: [AngularMaterialModule, CurrencyPipe, NgFor, RouterModule],
  templateUrl: './carrito-productos.component.html',
  styleUrl: './carrito-productos.component.css'
})
export class CarritoProductosComponent {
  // Lista de artículos en el carrito (esto es solo un ejemplo, puedes cargarla desde tu backend)
  articulos: Articulo[] = [
    // {
    //   id: 1,
    //   nombre: 'Artículo 1',
    //   imagen: 'assets/chisel.png',
    //   color: 'Rojo',
    //   talla: 'M',
    //   stockLimitado: true,
    //   precioAnterior: 2500000,
    //   precioActual: 5000000,
    //   cantidad: 1
    // },
    // {
    //   id: 2,
    //   nombre: 'Artículo 2',
    //   imagen: 'assets/chisel.png',
    //   color: 'Rojo',
    //   talla: 'M',
    //   stockLimitado: true,
    //   precioAnterior: 2500000,
    //   precioActual: 5000000,
    //   cantidad: 1
    // },
    
    // Agrega más artículos aquí
  ];


  termsAccepted = false;
  @ViewChild('cartTable') cartTable!: ElementRef;
  @ViewChild('cartTableHeader') cartTableHeader!: ElementRef;
  @ViewChild('eliminarButton') eliminarButton!: ElementRef;
  servicioComunicacion = inject(ComunicacionComponentesService);
  private unsubscribe$ = new Subject<void>();
  constructor(
    private el: ElementRef,
    // private servicioComunicacion: ComunicacionComponentesService,
    // private authService: SocialAuthService
  ) { }

  isLoggedIn = false;
  dataCarro: any = [] ;
  ngOnInit(): void {
    this.articulos = this.servicioComunicacion.carroCompras;
    console.log(this.articulos);
    setTimeout(() => {
      this.subtotal = this.calcularSubtotal();
    });
  }
  ngAfterViewInit() {
    setTimeout(() => {
      if (this.articulos.length >= 2) {
        // Obtén la altura de la barra de navegación
        const navbarHeight = document.querySelector('.svg-background') as HTMLElement;
        navbarHeight?.setAttribute('style', `height: auto !important;`);
      }
      // Función para calcular y aplicar anchos de columnas
      this.calcularAnchoColumnas();
    }, 100);
  }

  ngOnDestroy() {
    this.dataCarro = [];
  }

  validarCarroCompras(): Observable<any>{
    return new Observable<any>(observer => {
      this.servicioComunicacion.carroComprasObservable().subscribe((data: any) => {
        if (data) {
          observer.next(data);
        }
      });
    });
  }

  calcularAnchoColumnas() {
    setTimeout(() => {
      // Si está en dispositivos grandes, no se calcula el ancho de las columnas
      // if (window.innerWidth > 912) {
      //   // Obtén todas las celdas de la fila de encabezado
      //   const headerCells = this.el.nativeElement.querySelectorAll('.cart-table-cell') as NodeListOf<HTMLElement>;
      //   const rowCells = this.el.nativeElement.querySelectorAll('.cart-table-row .cart-table-cell') as NodeListOf<HTMLElement>;
      //   const eliminarButtons = this.el.nativeElement.querySelectorAll('.eliminar-button') as NodeListOf<HTMLElement>;
      //   const totalElements = this.el.nativeElement.querySelectorAll('.total') as NodeListOf<HTMLElement>;
    
      //   // Calcula el ancho máximo de las celdas de encabezado
      //   headerCells.forEach((headerCell: HTMLElement, index: number) => {
      //     const headerWidth = headerCell.offsetWidth;
      //     rowCells[index]?.setAttribute('style', `width: ${headerWidth}px;`);
      //   });
    
      //   // Posiciona los botones de eliminar en la misma posición que los elementos de total
      //   eliminarButtons.forEach((eliminarButton: HTMLElement, index: number) => {
      //     const totalElement = totalElements[index];
      //     const totalElementRect = totalElement.getBoundingClientRect();
      //     eliminarButton.style.left = `${totalElementRect.left - 748}px`;
      //     eliminarButton.style.width = `0px`;
      //   });
      // }else{
      //   // Obtén todas las celdas de la fila de encabezado
      //   const headerCells = this.el.nativeElement.querySelectorAll('.cart-table-cell') as NodeListOf<HTMLElement>;
      //   const rowCells = this.el.nativeElement.querySelectorAll('.cart-table-row .cart-table-cell') as NodeListOf<HTMLElement>;
      //   const eliminarButtons = this.el.nativeElement.querySelectorAll('.eliminar-button') as NodeListOf<HTMLElement>;
      //   const totalElements = this.el.nativeElement.querySelectorAll('.total') as NodeListOf<HTMLElement>;
    
      //   // Calcula el ancho máximo de las celdas de encabezado
      //   headerCells.forEach((headerCell: HTMLElement, index: number) => {
      //     const headerWidth = headerCell.offsetWidth;
      //     rowCells[index]?.setAttribute('style', `width: ${headerWidth}px;`);
      //   });
    
      //   // Posiciona los botones de eliminar en la misma posición que los elementos de total
      //   eliminarButtons.forEach((eliminarButton: HTMLElement, index: number) => {
      //     const totalElement = totalElements[index];
      //     const totalElementRect = totalElement.getBoundingClientRect();
      //     eliminarButton.style.left = `${totalElementRect.left - 181}px`;
      //     eliminarButton.style.width = `0px`;
      //   });
      // }
      const headerCells = this.el.nativeElement.querySelectorAll('.cart-table-cell') as NodeListOf<HTMLElement>;
        const rowCells = this.el.nativeElement.querySelectorAll('.cart-table-row .cart-table-cell') as NodeListOf<HTMLElement>;
        const eliminarButtons = this.el.nativeElement.querySelectorAll('.eliminar-button') as NodeListOf<HTMLElement>;
        const totalElements = this.el.nativeElement.querySelectorAll('.total') as NodeListOf<HTMLElement>;
    
        // Calcula el ancho máximo de las celdas de encabezado
        headerCells.forEach((headerCell: HTMLElement, index: number) => {
          const headerWidth = headerCell.offsetWidth;
          rowCells[index]?.setAttribute('style', `width: ${headerWidth}px;`);
        });
    });
  }

  // Agrega un artículo al carrito
  agregarAlCarrito(item: Articulo) {
    // Implementa lógica para agregar el artículo al carrito
    this.articulos.push(item);
  }

  // Resta la cantidad de un artículo en el carrito
  restarCantidad(item: Articulo) {
    // Implementa lógica para restar la cantidad
    if (item.cantidad > 1) {
      item.cantidad--;
      this.subtotal = this.calcularSubtotal();
    }
  }

  // Suma la cantidad de un artículo en el carrito
  subtotal: number = 0;
  sumarCantidad(item: Articulo) {
    // Implementa lógica para sumar la cantidad
    item.cantidad++;
    this.subtotal = this.calcularSubtotal();
  }

  // Elimina un artículo del carrito
  eliminarItem(item: Articulo) {
    // Implementa lógica para eliminar el artículo del carrito
    const index = this.articulos.indexOf(item);
    if (index > -1) {
      this.articulos.splice(index, 1);
      this.subtotal = this.calcularSubtotal();
    }

    let navbarHeight = document.querySelector('.svg-background') as HTMLElement;
    if(this.articulos.length === 1 && window.innerWidth <= 912){
      navbarHeight?.setAttribute('style', `height: auto !important;`);
    }else if(this.articulos.length <=1 && window.innerWidth > 1400 && window.innerWidth <= 2000){
      navbarHeight?.setAttribute('style', `height: 100% !important;`);
    }else if(this.articulos.length >= 2 && window.innerWidth > 912 && window.innerWidth <= 1400){
      navbarHeight?.setAttribute('style', `height: auto !important;`);
    }
    // También eliminamos el artículo del servicio de comunicación de componentes
    // const index2 = this.servicioComunicacion.carroCompras.indexOf(item);
    // if (index2 > -1) {
    //   this.servicioComunicacion.carroCompras.splice(index2, 1);
    // }
    
    // this.servicioComunicacion.modificarCarroCompras(this.servicioComunicacion.carroCompras);
  }

  resetearCantidad(item: Articulo) {
    // Que la cantidad del item sea igual a 1
    item.cantidad = 1;
    this.subtotal = this.calcularSubtotal();
  }

  // Calcula el subtotal del carrito
  calcularSubtotal(): number {
    // Implementa lógica para calcular el subtotal
    let subtotal = 0;
    for (const item of this.articulos) {
      subtotal += item.precio * item.cantidad;
      this.subtotal = subtotal;
    }
    return subtotal;
  }
}
