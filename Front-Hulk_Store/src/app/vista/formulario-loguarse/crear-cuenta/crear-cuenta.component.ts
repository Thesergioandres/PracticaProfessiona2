import { Component, inject } from '@angular/core';
import { AngularMaterialModule } from '../../../angular-material/angular-material.module';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../../services/logueo-usuario.service';
import { Usuario } from '../../../models/Usuario';
// Swal
import Swal from 'sweetalert2';
import * as CryptoJS from 'crypto-js';

@Component({
  selector: 'app-crear-cuenta',
  standalone: true,
  imports: [AngularMaterialModule, RouterLink, CommonModule],
  templateUrl: './crear-cuenta.component.html',
  styleUrl: './crear-cuenta.component.css'
})
export class CrearCuentaComponent {
  public formSesion!: FormGroup;

  servicioUsuario = inject(UsuarioService);
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    // private loginService: LoginService
  ) { 
    this.formSesion = this.formBuilder.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      email: ['', Validators.required, this.validarCorreo],
      contrasena: ['', Validators.required]
    });
  }

  async crearCuenta(): Promise<void> {
    if(this.formSesion.valid){
      const usuariosAll = await this.servicioUsuario.listarTodos().toPromise() as any;
      console.log(usuariosAll);
      const usuarioEncontrado = usuariosAll.find((usuario: Usuario) => usuario.correo === this.formSesion.value.email);
      if(usuarioEncontrado){
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'El correo ya se encuentra registrado',
        });
        return;
      }
      const usuario: Usuario = new Usuario();
      usuario.nombre = this.formSesion.value.nombre + ' ' + this.formSesion.value.apellido;
      usuario.correo = this.formSesion.value.email;
      const contrasena = this.formSesion.get('contrasena')?.value;
      const encryptedPassoword = CryptoJS.AES.encrypt(contrasena.trim(), 'secret key 123').toString();
      usuario.contrasena = encryptedPassoword;
      this.servicioUsuario.registrar(usuario).subscribe(
        (response) => {
          console.log(response);
          Swal.fire({
            icon: 'success',
            title: 'Usuario creado',
            text: 'Se ha creado el usuario correctamente',
            showConfirmButton: false,
            timer: 1500
          });
        },
        (error) => {
          console.log(error);
        }
      );
    }else{
      this.formSesion.markAllAsTouched();
    }
  }

  validarCorreo(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
    return new Promise<ValidationErrors | null>((resolve, reject) => {
      const correo = control.value;
      // Validamos que tenga un @ y un .com
      if (correo.includes('@')) {
        resolve(null);
      }
      resolve({ noEsCorreo: true });
    });
  }
}
