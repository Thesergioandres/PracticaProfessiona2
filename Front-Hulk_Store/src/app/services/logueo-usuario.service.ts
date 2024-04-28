// Creamos el servicio para enviar peticiones al servidor
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Usuario } from '../models/Usuario';
import { SharedService } from '../shared-service';
@Injectable({
    providedIn: 'root'
})
export class UsuarioService { 
    
    private path = this.sharedService.APIUrl + 'usuario';

    constructor(private http: HttpClient, private sharedService: SharedService) {}

    public listarTodos(){
        return this.http.get<Usuario>(this.path+'/verUsuarios');
    }

    public listarPorId(id: number){
    return this.http.get<Usuario>(this.path+'/usuario/'+id);
    }

    public registrar(objetoUsuario: Usuario){
    return this.http.post<void>(this.path+'/addUsuario',objetoUsuario);
    }

    public actualizar(objetoModificarUsuario: Usuario){
    return this.http.put<void>(this.path+'/updateUsuario/'+ objetoModificarUsuario.id, objetoModificarUsuario);
    }

    public eliminar(id: number){
    return this.http.delete<void>(this.path+'/deleteUsuario/'+id);
    }
}
