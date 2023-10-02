import {Team} from '../../interfaces/Team';
import {Student} from '../../interfaces/Student';
import {Teacher} from '../../interfaces/Teacher';
import {TeacherImp} from '../teacher/teacher-imp';

export class TeamImp implements Team {

  id: number;
  name: string;
  numberStudents: number;
  grade: string;
  classRoom: string;
  students: Student[];
  studentsId: number[];
  teacherId: number;

  constructor() {
    this.id = 0;
    this.name = '';
    this.numberStudents = 0;
    this.grade = '';
    this.classRoom = '';
    this.students = [];
    this.studentsId = [];
    this.teacherId = 0;
  }
}
