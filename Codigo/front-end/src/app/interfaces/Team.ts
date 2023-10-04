export interface Team {
  id?: number;
  name: string;
  grade: string;
  classroom: string;
  numberStudents?: number;
  studentIds: number[];
  teacherId: number;
}
