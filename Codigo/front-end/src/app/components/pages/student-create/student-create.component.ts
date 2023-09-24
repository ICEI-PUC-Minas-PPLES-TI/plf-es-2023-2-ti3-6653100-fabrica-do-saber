import {Component, ElementRef, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {StudentService} from '../../../services/student.service';

@Component({
  selector: 'app-student-create',
  templateUrl: './student-create.component.html',
  styleUrls: ['./student-create.component.css']
})
export class StudentCreateComponent {

  @ViewChild('showAddressCheckbox') showAddressCheckbox!: ElementRef<HTMLInputElement>;
  constructor(private router: Router, private toastr: ToastrService, private studentService: StudentService) {
  }

  createStudent(): void {

    const student = {
      guardians: [
        {
          fullName: 'Nome Completo do Pai',
          cpf: '123.456.789-00',
          rg: '12.345.678',
          email: 'pai@email.com',
          occupation: 'Ocupação do Pai',
          company: 'Empresa do Pai',
          phoneNumber: '11 98765-4321',
          streetAddress: 'Endereço do Pai',
          addressNumber: '123',
          neighborhood: 'Bairro do Pai',
          cityOfResidence: 'Cidade de Residência do Pai',
          zipCode: '12345-678'
        },
        {
          fullName: 'Nome Completo da Mãe',
          cpf: '987.654.321-00',
          rg: '11.222.333',
          email: 'mae@email.com',
          occupation: 'Ocupação da Mãe',
          company: 'Empresa da Mãe',
          phoneNumber: '21 55555-1234',
          streetAddress: 'Endereço da Mãe',
          addressNumber: '456',
          neighborhood: 'Bairro da Mãe',
          cityOfResidence: 'Cidade de Residência da Mãe',
          zipCode: '54321-876'
        }
      ],
      fullName: 'Nome do Estudante',
      registrationDate: '01/01/2023',
      grade: '5º ano',
      birthDate: '15/06/2010',
      hometown: 'Cidade Natal',
      homeState: 'Estado Natal',
      nationality: 'Brasileiro',
      religion: 'Religião do Estudante',
      race: 'Raça do Estudante',
      streetAddress: 'Endereço do Estudante',
      addressNumber: '123',
      neighborhood: 'Bairro do Estudante',
      cityOfResidence: 'Cidade de Residência',
      zipCode: '12345-678'
    };

    this.studentService.createStudent(student).subscribe(
      response => {
        console.log('Estudante criado com sucesso!', response);
      },
      error => {
        console.error('Erro ao criar o estudante', error);
      });
  }

  cancel() {
    this.toastr.error('Ação Cancelada');
    this.router.navigate(['/student-list']);
  }
}
