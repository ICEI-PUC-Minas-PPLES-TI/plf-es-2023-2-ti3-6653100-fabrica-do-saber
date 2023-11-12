export interface VacationTeam {
  id?: number;
  name: string;
  grade: string;
  classroom: string;
  numberStudents?: number;
  studentIds: number[];
  teacherId: number;
  startDate: string;
  endDate: string;
}
