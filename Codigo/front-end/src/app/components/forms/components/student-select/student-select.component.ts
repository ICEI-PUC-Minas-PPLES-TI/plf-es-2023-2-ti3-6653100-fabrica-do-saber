import {Component, EventEmitter, Input, Output} from '@angular/core';
import {SelectValue} from '../../../../interfaces/SelectValue';
import { Student } from 'src/app/interfaces/Student';

@Component({
  selector: 'app-student-select',
  templateUrl: './student-select.component.html',
  styleUrls: ['./student-select.component.css']
})
export class StudentSelectComponent {

  // @Input() student !: string;
  // @Output() studentChange: EventEmitter<string> = new EventEmitter<string>();

  @Input() selectedStudentIds: number[] = [];
  @Output() selectedStudentIdsChange: EventEmitter<number[]> = new EventEmitter<number[]>();
  

  selectedStudents: number[] = [];

  students: Student[] = [
    {
      id: 1,
      fullName: "John Smith",
      registrationDate: "2023-09-29",
      birthDate: "2000-05-15",
      hometown: "Springfield",
      homeState: "IL",
      nationality: "American",
      religion: "Christian",
      race: "Caucasian",
      streetAddress: "123 Main Street",
      addressNumber: "Apt 4B",
      addressComplement: "",
      neighborhood: "Downtown",
      cityOfResidence: "Springfield",
      zipCode: "12345",
      parents: [
        {
          fullName: "Mary Smith",
          cpf: "123-456-789-01",
          rg: "A12345",
          email: "mary@example.com",
          occupation: "Teacher",
          company: "Springfield High School",
          phoneNumber: "555-123-4567",
          streetAddress: "456 Elm Street",
          addressNumber: "Unit 2C",
          neighborhood: "Downtown",
          addressComplement: "",
          cityOfResidence: "Springfield",
          zipCode: "12345",
          homeState: "IL",
          birthDate: "1975-03-20",
          relationship: "Mother",
        },
        {
          fullName: "Robert Smith",
          cpf: "987-654-321-09",
          rg: "B54321",
          email: "robert@example.com",
          occupation: "Engineer",
          company: "TechCorp",
          phoneNumber: "555-987-6543",
          streetAddress: "789 Oak Avenue",
          addressNumber: "",
          neighborhood: "Suburbia",
          addressComplement: "House 3",
          cityOfResidence: "Springfield",
          zipCode: "12346",
          homeState: "IL",
          birthDate: "1972-11-10",
          relationship: "Father",
        },
      ],
    },

    {
      id: 2,
      fullName: "Alice Johnson",
      registrationDate: "2023-09-29",
      birthDate: "2002-08-22",
      hometown: "New York City",
      homeState: "NY",
      nationality: "American",
      religion: "Jewish",
      race: "African American",
      streetAddress: "456 Park Avenue",
      addressNumber: "",
      addressComplement: "Apt 5D",
      neighborhood: "Upper East Side",
      cityOfResidence: "New York City",
      zipCode: "10001",
      parents: [
        {
          fullName: "David Johnson",
          cpf: "234-567-890-12",
          rg: "C67890",
          email: "david@example.com",
          occupation: "Lawyer",
          company: "Johnson & Associates",
          phoneNumber: "555-234-5678",
          streetAddress: "789 Lexington Avenue",
          addressNumber: "",
          neighborhood: "Upper East Side",
          addressComplement: "Suite 101",
          cityOfResidence: "New York City",
          zipCode: "10002",
          homeState: "NY",
          birthDate: "1978-04-05",
          relationship: "Father",
        },
        {
          fullName: "Sarah Johnson",
          cpf: "876-543-210-98",
          rg: "D98765",
          email: "sarah@example.com",
          occupation: "Doctor",
          company: "NYC Hospital",
          phoneNumber: "555-876-5432",
          streetAddress: "101 Central Park West",
          addressNumber: "Apt 10E",
          neighborhood: "Upper West Side",
          addressComplement: "",
          cityOfResidence: "New York City",
          zipCode: "10003",
          homeState: "NY",
          birthDate: "1980-12-15",
          relationship: "Mother",
        },
      ],
    }
  ];


  // onStudentSelectChange(event: any): void {
  //   this.student = event.target.value;
  //   this.studentChange.emit(this.student);
  // }

  onStudentChange(selectedStudentIds: number[]): void {
    // Handle the selected student IDs here
    // You can access the selectedStudentIds array
    console.log(selectedStudentIds);
  }
  
}