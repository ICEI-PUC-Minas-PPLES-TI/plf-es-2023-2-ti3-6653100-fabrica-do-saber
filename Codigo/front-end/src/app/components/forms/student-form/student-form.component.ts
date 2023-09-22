import {Component, ElementRef, Input, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-student-form',
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent {

  @Input() title!: string;
  @Input() showCheckbox!: boolean;

  // Student info
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
  constructor(private router: Router, private toastr: ToastrService) {
  }

  states: String[] = [
    'Acre', 'Alagoas', 'Amapá', 'Amazonas', 'Bahia', 'Ceará', 'Distrito Federal',
    'Espírito Santo', 'Goiás', 'Maranhão', 'Mato Grosso', 'Mato Grosso do Sul',
    'Minas Gerais', 'Pará', 'Paraíba', 'Paraná', 'Pernambuco', 'Piauí',
    'Rio de Janeiro', 'Rio Grande do Norte', 'Rio Grande do Sul', 'Rondônia',
    'Roraima', 'Santa Catarina', 'São Paulo', 'Sergipe', 'Tocantins'
  ];

  races = ['Amarelo', 'Branco', 'Indígena', 'Pardo', 'Preto', 'Outra', 'Prefiro não declarar'];

  religions = ['Candomblé', 'Catolicismo', 'Espiritismo', 'Protestantismo', 'Umbanda', 'Outra', 'Não possui', 'Prefiro não declarar'];

  // Cancel Button
  cancel() {
    this.toastr.error('Ação Cancelada');
    this.router.navigate(['/']);
  }


}
