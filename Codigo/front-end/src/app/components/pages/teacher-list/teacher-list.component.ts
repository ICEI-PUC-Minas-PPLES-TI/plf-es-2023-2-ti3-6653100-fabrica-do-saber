import {Component} from '@angular/core';
import {Teacher} from '../../../interfaces/Teacher';
import {TeacherService} from '../../../services/teacher/teacher.service';

@Component({
  selector: 'app-teacher-list',
  templateUrl: './teacher-list.component.html',
  styleUrls: ['./teacher-list.component.css'],
})

export class TeacherListComponent {

  originalTeachers: Teacher[] = [];
  teachers: Teacher[] = [];

  /*Table variables*/
  tableHeaders: String[] = ['Nome', 'CPF', 'E-mail', 'Telefone', 'Endereço', 'Gerenciar'];
  buttons = [
    {iconClass: 'fa fa-edit', title: 'Editar', route: '/teacher-edit', function: null},
    {iconClass: 'fa fa-upload', title: 'Imprimir', route: null, function: null},
    {iconClass: 'fa fa-trash', title: 'Excluir', route: null, function: this.deleteTeacher.bind(this)}
  ];
  filters = [
    {name: 'ordem alfabética', function: this.sortTeachersByName.bind(this)},
    {name: 'id', function: this.sortTeachersById.bind(this)}
  ];
  filterText!: string;

  constructor(private teacherService: TeacherService) {
  }

  ngOnInit(): void {
    this.getTeachers();
    this.filterText = this.filters[0].name;
  }

  getTeachers(): void {
    this.teacherService.getTeachers().subscribe((teachers: Teacher[]): void => {
      this.originalTeachers = teachers;
      this.teachers = [...this.originalTeachers];
      this.sortTeachersByName();
    });
  }

  deleteTeacher(id: number): void {
    this.teacherService.deleteTeacher(id).subscribe((): void => {
      this.getTeachers();
    });
  }

  filterTeacherList(event: Event): void {

    const searchInput: HTMLInputElement = event.target as HTMLInputElement;
    const inputValue: string = searchInput.value.toLowerCase();

    this.teachers = this.originalTeachers.filter((teacher: Teacher) => {
      const studentFullNameMatch: boolean = teacher.fullName.toLowerCase().includes(inputValue);
      return studentFullNameMatch;
    });
  }

  sortTeachersByName(): void {
    this.teachers = this.originalTeachers.sort(function (a: Teacher, b: Teacher): number {
      let nameA: string = a.fullName.toLowerCase();
      let nameB: string = b.fullName.toLowerCase();
      if (nameA < nameB)
        return -1;
      if (nameA > nameB)
        return 1;
      return 0;
    });
    this.updateBtnText(this.sortTeachersByName.name);
  }

  sortTeachersById(): void {
    this.teachers = this.originalTeachers.sort(function (a: Teacher, b: Teacher): number {
      let idA: number = a.id;
      let idB: number = b.id;
      if (idA < idB)
        return -1;
      if (idA > idB)
        return 1;
      return 0;
    });
    this.updateBtnText(this.sortTeachersById.name);
  }

  updateBtnText(funcName: string) {
    const filter = this.filters.find(filter => filter.function.name.includes(funcName));
    this.filterText = filter ? filter.name : '';
  }

}
