import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Student} from 'src/app/interfaces/Student';
import {StudentService} from '../../../../services/student/student.service';
import {IDropdownSettings} from 'ng-multiselect-dropdown';
import {TeamService} from '../../../../services/team/team.service';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {VacationTeamService} from '../../../../services/vacation-team/vacation-team.service';

@Component({
  selector: 'app-student-select',
  templateUrl: './student-select.component.html',
  styleUrls: ['./student-select.component.css']
})
export class StudentSelectComponent implements OnInit {

  dropdownList: Student[] = [];
  selectedItems: Student[] = [];
  dropdownSettings: IDropdownSettings = {};
  teamId!: number;
  teamType!: string;

  @Input() selectedStudentIds: number[] = [];
  @Output() selectedStudentIdsChange: EventEmitter<number[]> = new EventEmitter<number[]>();
  @Output() selectedStudentsChange: EventEmitter<Student[]> = new EventEmitter<Student[]>();

  students: Student[] = [];

  constructor(private studentService: StudentService, private teamService: TeamService, private vacationTeamService: VacationTeamService, private route: ActivatedRoute) {
    this.dropdownSettings = {
      idField: 'id',
      textField: 'fullName',
      allowSearchFilter: true,
      enableCheckAll: false,
    };
  }

  ngOnInit(): void {
    this.getAllStudents();
    this.route.paramMap.subscribe((params: ParamMap): void => {
      this.teamId = parseInt(<string>params.get('id'));
      this.route.url.subscribe((url) => {
        this.teamType = url[0].path;
        this.getStudents(this.teamId, this.teamType);
      });
    });
  }

  getAllStudents(): void {
    this.studentService.getStudents().subscribe((students: Student[]): void => {
      this.students = students;
      this.dropdownList = students;
    });
  }

  getStudents(id: number, teamType: string): void {
    if (teamType.includes('vacation'))
      this.vacationTeamService.getStudents(id).subscribe((students: Student[]): void => {
        this.selectedItems = students;
      });
    else
      this.teamService.getStudents(id).subscribe((students: Student[]): void => {
        this.selectedItems = students;
      });
  }

  updateStudents(): void {
    const selectedStudentIds = this.selectedItems.map(student => student.id);
    this.selectedStudentIdsChange.emit(selectedStudentIds);
  }
}
