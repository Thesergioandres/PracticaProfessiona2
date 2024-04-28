// Creamos el servicio para enviar peticiones al servidor
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SharedService } from '../shared-service';
import { Producto } from '../models/Producto';
@Injectable({
    providedIn: 'root'
})
export class ProductoService { 
    
    private path = this.sharedService.APIUrl + 'producto';

    constructor(private http: HttpClient, private sharedService: SharedService) {}

    public listarTodos(){
        return this.http.get<Producto>(this.path+'/verProductos');
    }

    public listarPorId(id: number){
    return this.http.get<Producto>(this.path+'/verProducto/'+id);
    }

    public registrar(objetoProducto: Producto){
    return this.http.post<void>(this.path+'/addProducto',objetoProducto);
    }

    public actualizar(objetoModificarProducto: Producto){
    return this.http.put<void>(this.path+'/updateProducto/'+ objetoModificarProducto.id, objetoModificarProducto);
    }

    public eliminar(id: number){
    return this.http.delete<void>(this.path+'/deleteProducto/'+id);
    }
}
