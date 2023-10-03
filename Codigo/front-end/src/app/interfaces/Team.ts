import { Student } from './Student';

export interface Team {
    id: number;
    name: string;
    numberStudents: number;
    grade: string;
    classRoom: string;
    students: Student[];
    studentsId: number[];
    teacherId: number;
}
