import { VacationTeam } from '../../interfaces/Vacation-team';

export class VacationTeamImp implements VacationTeam{

  id?: number;
  name: string;
  grade: string;
  classroom: string;
  numberStudents?: number;
  studentIds: number[];
  teacherId: number;
  startDate: string;
  endDate: string;

  constructor() {
    this.id = 0;
    this.name = '';
    this.grade = '';
    this.classroom = '';
    this.numberStudents = 0;
    this.studentIds = [];
    this.teacherId = 0;
    this.startDate = '';
    this.endDate = '';
  }
}
