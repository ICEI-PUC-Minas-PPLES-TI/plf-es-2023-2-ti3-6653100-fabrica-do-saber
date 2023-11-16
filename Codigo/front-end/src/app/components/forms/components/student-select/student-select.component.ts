import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { Student } from 'src/app/interfaces/Student';
import { StudentService } from '../../../../services/student/student.service';
import { IDropdownSettings } from 'ng-multiselect-dropdown';

@Component({
  selector: 'app-student-select',
  templateUrl: './student-select.component.html',
  styleUrls: ['./student-select.component.css']
})
export class StudentSelectComponent implements OnInit {

  dropdownList: Student[] = [];
  selectedItems: Student[] = [];
  dropdownSettings: IDropdownSettings = {};

  @Input() selectedStudentIds: number[] = [];
  @Output() selectedStudentIdsChange: EventEmitter<number[]> = new EventEmitter<number[]>();
  @Output() selectedStudentsChange: EventEmitter<Student[]> = new EventEmitter<Student[]>();

  students: Student[] = [];

  constructor(private studentService: StudentService) {
    this.dropdownSettings = {
      idField: 'id',
      textField: 'fullName',
      allowSearchFilter: true,
      enableCheckAll: false,
    };
  }

  ngOnInit(): void {
    this.getStudents();
  }

  getStudents(): void {
    this.studentService.getStudents().subscribe((students: Student[]): void => {
      this.students = students;
      this.dropdownList = students;
    });
  }

  addStudents(): void {
    // Map selected students to just the IDs
    const selectedStudentIds = this.selectedItems.map(student => student.id);
  
    // Emit the IDs
    this.selectedStudentIdsChange.emit(selectedStudentIds);
  }
  
}
