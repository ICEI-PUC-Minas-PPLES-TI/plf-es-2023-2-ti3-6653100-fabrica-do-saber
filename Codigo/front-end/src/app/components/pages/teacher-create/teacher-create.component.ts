import {Component, ElementRef, ViewChild} from '@angular/core';
// import {TeacherService} from '../../../services/teacher/teacher.service';
import {Teacher} from '../../../interfaces/Teacher';
import {TeacherImp} from '../../../classes/teacher/teacher-imp';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {TeacherService} from '../../../services/teacher/teacher.service';

@Component({
  selector: 'app-teacher-create',
  templateUrl: './teacher-create.component.html',
  styleUrls: ['./teacher-create.component.css']
})
export class TeacherCreateComponent {

  teacher: Teacher = new TeacherImp();

  @ViewChild('showAddressCheckbox') showAddressCheckbox!: ElementRef<HTMLInputElement>;

  constructor(private router: Router, private toastr: ToastrService, private teacherService: TeacherService) {
  }

  createTeacher(): void {
    console.log(this.teacher);
    // this.teacherService.createTeacher(this.teacher).subscribe();
  }

  cancel(): void {
    this.toastr.error('Ação cancelada');
    this.router.navigate(['/teacher-list']);
  }

}
