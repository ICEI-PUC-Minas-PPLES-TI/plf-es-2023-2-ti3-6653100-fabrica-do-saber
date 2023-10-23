import { Component } from '@angular/core';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent {

  printStudentList():void {

    let newWindow: Window = <Window>window.open(`/student-list`, '_blank');

    newWindow.onload = function (): void {
      setTimeout((): void => {
        newWindow.print();
        newWindow.close();
      }, 200);
    };
  }

  printTeacherList():void {

    let newWindow: Window = <Window>window.open(`/teacher-list`, '_blank');

    newWindow.onload = function (): void {
      setTimeout((): void => {
        newWindow.print();
        newWindow.close();
      }, 200);
    };
  }
}
