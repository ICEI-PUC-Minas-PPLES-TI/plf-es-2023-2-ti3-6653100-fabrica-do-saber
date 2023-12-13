import { Component, OnInit } from '@angular/core';

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

  printTransactionList():void {

    let newWindow: Window = <Window>window.open(`/transaction-list`, '_blank');

    newWindow.onload = function (): void {
      setTimeout((): void => {
        newWindow.print();
        newWindow.close();
      }, 200);
    };
  }

  printTeamList():void {

    let newWindow: Window = <Window>window.open(`/team-list`, '_blank');

    newWindow.onload = function (): void {
      setTimeout((): void => {
        newWindow.print();
        newWindow.close();
      }, 200);
    };
  }

  printVacationList():void {

    let newWindow: Window = <Window>window.open(`/vacation-team-list`, '_blank');

    newWindow.onload = function (): void {
      setTimeout((): void => {
        newWindow.print();
        newWindow.close();
      }, 200);
    };
  }
  

  cards = [
    {
      title: 'Alunos',
      name: 'alunos',
      imageUrl: '../../../assets/img/relatorios.png',
      route: '/student-list',
      click: this.printStudentList.bind(this)
    },
    {
      title: 'Funcionários',
      name: 'funcionarios',
      imageUrl: '../../../assets/img/relatorios.png',
      route: '/teacher-list',
      click: this.printTeacherList.bind(this)
    },
    {
      title: 'Financeiro',
      name: 'financeiro',
      imageUrl: '../../../assets/img/relatorios.png',
      route: '/transaction-list',
      click: this.printTransactionList.bind(this)
    },
    {
      title: 'Turmas',
      name: 'turmas',
      imageUrl: '../../../assets/img/relatorios.png',
      route: '/team-list',
      click: this.printTeamList.bind(this)
    },
    {
      title: 'Creche de Férias',
      name: 'creche',
      imageUrl: '../../../assets/img/relatorios.png',
      route: '/vacation-team-list',
      click: this.printVacationList.bind(this)
    }
  ];

  constructor() { }

  ngOnInit(): void {
  }
}
