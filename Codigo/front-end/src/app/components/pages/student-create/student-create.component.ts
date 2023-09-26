import {Component, ElementRef, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {StudentService} from '../../../services/student/student.service';
import {Student} from "../../../interfaces/Student";

@Component({
  selector: 'app-student-create',
  templateUrl: './student-create.component.html',
  styleUrls: ['./student-create.component.css']
})
export class StudentCreateComponent {

  student: Student = {
    guardians: [
      {
        fullName: '',
        cpf: '',
        rg: '',
        email: '',
        occupation: '',
        company: '',
        phoneNumber: '',
        streetAddress: '',
        addressNumber: '',
        neighborhood: '',
        cityOfResidence: '',
        zipCode: ''
      },
      {
        fullName: '',
        cpf: '',
        rg: '',
        email: '',
        occupation: '',
        company: '',
        phoneNumber: '',
        streetAddress: '',
        addressNumber: '',
        neighborhood: '',
        cityOfResidence: '',
        zipCode: ''
      }
    ],
    id: 0,
    fullName: '',
    registrationDate: '',
    grade: '',
    birthDate: '',
    hometown: '',
    homeState: '',
    nationality: '',
    religion: '',
    race: '',
    streetAddress: '',
    addressNumber: '',
    neighborhood: '',
    cityOfResidence: '',
    zipCode: ''
  };

  @ViewChild('showAddressCheckbox') showAddressCheckbox!: ElementRef<HTMLInputElement>;

  constructor(private router: Router, private toastr: ToastrService, private studentService: StudentService) {
  }

  createStudent(): void {
    console.log(this.student);
    // this.studentService.createStudent(this.student).subscribe();
  }

  cancel(): void {
    this.toastr.error('Ação cancelada');
    this.router.navigate(['/student-list']);
  }

}
