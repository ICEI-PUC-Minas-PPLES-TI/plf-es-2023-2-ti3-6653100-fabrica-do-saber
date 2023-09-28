import { Student } from './Student';
import { Teacher } from './Teacher';

export interface Team {
    id: number;
    name: string;
    numberOfStudents: number;
    grade: string;
    classRoom: string;
    students: Student[];
    teacher: Teacher;
}