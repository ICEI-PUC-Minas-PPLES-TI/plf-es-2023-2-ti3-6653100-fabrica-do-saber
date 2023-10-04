import {Team} from '../../interfaces/Team';

export class TeamImp implements Team {

  id?: number;
  name: string;
  grade: string;
  classroom: string;
  numberStudents?: number;
  studentIds: number[];
  teacherId: number;

  constructor() {
    this.id = 0;
    this.name = '';
    this.grade = '';
    this.classroom = '';
    this.numberStudents = 0;
    this.studentIds = [];
    this.teacherId = 0;
  }
}
