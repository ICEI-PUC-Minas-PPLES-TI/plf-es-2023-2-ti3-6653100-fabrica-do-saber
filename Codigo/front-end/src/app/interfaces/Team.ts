export interface Team {
  id?: number;
  name: string;
  grade: string;
  classroom: string;
  numberStudents?: number;
  /*todo: renomear para studentsIds (front e back-end)*/
  studentIds: number[];
  teacherId: number;
}
