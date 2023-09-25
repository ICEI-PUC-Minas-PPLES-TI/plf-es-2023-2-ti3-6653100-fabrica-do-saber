import {Component, Input} from '@angular/core';
import {Student} from "../../../interfaces/Student";

@Component({
  selector: 'app-student-form',
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent {

  @Input() student!: Student;
  @Input() title!: string;
  @Input() showCheckbox!: boolean;

  constructor() {
  }

}
