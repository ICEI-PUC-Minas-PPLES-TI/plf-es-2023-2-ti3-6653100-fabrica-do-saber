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
      imageUrl: '../../../assets/img/ger-alunos.png'
    },
    {
      title: 'Funcionários',
      imageUrl: '../../../assets/img/funcionarios.png'
    },
    {
      title: 'Financeiro',
      imageUrl: '../../../assets/img/financeiro.png'
    },
    {
      title: 'Turmas',
      imageUrl: '../../../assets/img/turmas.png'
    },
    {
      title: 'Relatórios',
      imageUrl: '../../../assets/img/relatorios.png'
    },
    {
      title: 'Card 6',
      imageUrl: ''
    }
  ];

  constructor() { }

  ngOnInit(): void {
  }

}
