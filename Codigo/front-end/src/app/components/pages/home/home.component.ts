import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  cards = [
    {
      title: 'Alunos',
      imageUrl: '../../../assets/img/ger-alunos.png',
      route: '/student-list'
      // route: 'student-list'
    },
    {
      title: 'Funcionários',
      imageUrl: '../../../assets/img/funcionarios.png',
      route: ''
    },
    {
      title: 'Financeiro',
      imageUrl: '../../../assets/img/financeiro.png',
      route: ''
    },
    {
      title: 'Turmas',
      imageUrl: '../../../assets/img/turmas.png',
      route: ''
    },
    {
      title: 'Relatórios',
      imageUrl: '../../../assets/img/relatorios.png',
      route: ''
    },
    {
      title: 'Card 6',
      imageUrl: '',
      route: ''
    }
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
