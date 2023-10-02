import { Student } from './Student';
import { Teacher } from './Teacher';

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
