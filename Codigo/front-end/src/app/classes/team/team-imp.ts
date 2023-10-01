import {Team} from '../../interfaces/Team';
import {Student} from '../../interfaces/Student';
import {Teacher} from '../../interfaces/Teacher';
import {TeacherImp} from '../teacher/teacher-imp';

export class TeamImp implements Team {

  id: number;
  name: string;
  numberOfStudents: number;
  grade: string;
  classRoom: string;
  students: Student[];
  studentId: number[];
  teacher: Teacher;

  constructor() {
    this.id = 0;
    this.name = '';
    this.numberOfStudents = 0;
    this.grade = '';
    this.classRoom = '';
    this.students = [];
    this.studentId = [];
    this.teacher = new TeacherImp();
  }
}
