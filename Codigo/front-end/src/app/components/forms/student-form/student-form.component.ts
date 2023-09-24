import {Component, Input} from '@angular/core';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {StudentService} from '../../../services/student.service';

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

  constructor() {
  }

  // todo: funcao de teste, deletar
  onDateChange(event: any) {
    const selectedDate = event.target.value;
    console.log('Data selecionada:', selectedDate);
  }
}
