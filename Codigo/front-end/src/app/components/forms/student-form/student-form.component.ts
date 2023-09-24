import {Component, Input} from '@angular/core';
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {StudentService} from "../../../services/student.service";

@Component({
  selector: 'app-student-form',
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent {

  @Input() title!: string;
  @Input() showCheckbox!: boolean;
  @Input() studentFullName!: string;
  @Input() studentGrade!: string;
  @Input() studentBirthDate!: string;
  @Input() studentHometown!: string;
  @Input() studentHomeState!: string;
  @Input() studentNationality!: string;
  @Input() studentReligion!: string;
  @Input() studentRace!: string;
  @Input() studentStreetAddress!: string;
  @Input() studentAddressNumber!: string;
  @Input() studentNeighborhood!: string;
  @Input() studentCityOfResidence!: string;
  @Input() studentZipCode!: string;
  @Input() fatherFullName!: string;
  @Input() fatherCpf!: string;
  @Input() fatherRg!: string;
  @Input() fatherEmail!: string;
  @Input() fatherOccupation!: string;
  @Input() fatherCompany!: string;
  @Input() fatherPhoneNumber!: string;
  @Input() fatherStreetAddress!: string;
  @Input() fatherAddressNumber!: string;
  @Input() fatherNeighborhood!: string;
  @Input() fatherCityOfResidence!: string;
  @Input() fatherZipCode!: string;
  @Input() motherFullName!: string;
  @Input() motherCpf!: string;
  @Input() motherRg!: string;
  @Input() motherEmail!: string;
  @Input() motherOccupation!: string;
  @Input() motherCompany!: string;
  @Input() motherPhoneNumber!: string;
  @Input() motherStreetAddress!: string;
  @Input() motherAddressNumber!: string;
  @Input() motherNeighborhood!: string;
  @Input() motherCityOfResidence!: string;
  @Input() motherZipCode!: string;

  constructor(private router: Router, private toastr: ToastrService, private studentService: StudentService) {
  }

  createStudent(): void {
    // const student = {
    //   guardians: [
    //     {
    //       fullName: this.fatherFullName,
    //       cpf: this.fatherCpf,
    //       rg: this.fatherRg,
    //       email: this.fatherEmail,
    //       occupation: this.fatherOccupation,
    //       company: this.fatherCompany,
    //       phoneNumber: this.fatherPhoneNumber,
    //       streetAddress: this.fatherStreetAddress,
    //       addressNumber: this.fatherAddressNumber,
    //       neighborhood: this.fatherNeighborhood,
    //       cityOfResidence: this.fatherCityOfResidence,
    //       zipCode: this.fatherZipCode
    //     },
    //     {
    //       fullName: this.motherFullName,
    //       cpf: this.motherCpf,
    //       rg: this.motherRg,
    //       email: this.motherEmail,
    //       occupation: this.motherOccupation,
    //       company: this.motherCompany,
    //       phoneNumber: this.motherPhoneNumber,
    //       streetAddress: this.motherStreetAddress,
    //       addressNumber: this.motherAddressNumber,
    //       neighborhood: this.motherNeighborhood,
    //       cityOfResidence: this.motherCityOfResidence,
    //       zipCode: this.motherZipCode
    //     }
    //   ],
    //   fullName: this.studentFullName,
    //   registrationDate: '', // Preencha com o valor desejado
    //   grade: this.studentGrade,
    //   birthDate: this.studentBirthDate,
    //   hometown: this.studentHometown,
    //   homeState: this.studentHomeState,
    //   nationality: this.studentNationality,
    //   religion: this.studentReligion,
    //   race: this.studentRace,
    //   streetAddress: this.studentStreetAddress,
    //   addressNumber: this.studentAddressNumber,
    //   neighborhood: this.studentNeighborhood,
    //   cityOfResidence: this.studentCityOfResidence,
    //   zipCode: this.studentZipCode
    // };

    const student = {
      guardians: [
        {
          fullName: "Nome Completo do Pai",
          cpf: "123.456.789-00",
          rg: "12.345.678",
          email: "pai@email.com",
          occupation: "Ocupação do Pai",
          company: "Empresa do Pai",
          phoneNumber: "11 98765-4321",
          streetAddress: "Endereço do Pai",
          addressNumber: "123",
          neighborhood: "Bairro do Pai",
          cityOfResidence: "Cidade de Residência do Pai",
          zipCode: "12345-678"
        },
        {
          fullName: "Nome Completo da Mãe",
          cpf: "987.654.321-00",
          rg: "11.222.333",
          email: "mae@email.com",
          occupation: "Ocupação da Mãe",
          company: "Empresa da Mãe",
          phoneNumber: "21 55555-1234",
          streetAddress: "Endereço da Mãe",
          addressNumber: "456",
          neighborhood: "Bairro da Mãe",
          cityOfResidence: "Cidade de Residência da Mãe",
          zipCode: "54321-876"
        }
      ],
      fullName: "Nome do Estudante",
      registrationDate: "01/01/2023",
      grade: "5º ano",
      birthDate: "15/06/2010",
      hometown: "Cidade Natal",
      homeState: "Estado Natal",
      nationality: "Brasileiro",
      religion: "Religião do Estudante",
      race: "Raça do Estudante",
      streetAddress: "Endereço do Estudante",
      addressNumber: "123",
      neighborhood: "Bairro do Estudante",
      cityOfResidence: "Cidade de Residência",
      zipCode: "12345-678"
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

  // todo: funcao de teste, deletar
  onDateChange(event: any) {
    const selectedDate = event.target.value;
    console.log('Data selecionada:', selectedDate);
  }
}
