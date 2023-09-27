import {Component, ElementRef, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {StudentService} from '../../../services/student/student.service';
import {StudentImp} from '../../../classes/Student/student-imp';
import {Student} from '../../../interfaces/Student';

@Component({
  selector: 'app-student-create',
  templateUrl: './student-create.component.html',
  styleUrls: ['./student-create.component.css']
})
export class StudentCreateComponent {

  student: Student = new StudentImp();

  @ViewChild('showAddressCheckbox') showAddressCheckbox!: ElementRef<HTMLInputElement>;

  constructor(private router: Router, private toastr: ToastrService, private studentService: StudentService) {
  }

  createStudent(): void {
    this.studentService.createStudent(this.student).subscribe();
  }

  cancel(): void {
    this.toastr.error('Ação cancelada');
    this.router.navigate(['/student-list']);
  }

}
