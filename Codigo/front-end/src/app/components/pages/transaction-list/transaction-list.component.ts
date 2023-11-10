import {Component} from '@angular/core';
import {TransactionService} from '../../../services/transaction/transaction.service';
import {Transaction} from '../../../interfaces/Transaction';

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.css']
})
export class TransactionListComponent {

  tableHeaders: String[] = ['Descrição', 'Data', 'Categoria', 'Valor', 'Gerenciar'];
  transactions: Transaction[] = [
    {
      id: 2,
      description: 'teste',
      date: '10/11/2023',
      financialFlowType: 'income',
      category: 'contas',
      value: 100.39
    },
    {
      id: 3,
      description: 'teste2',
      date: '10/11/2023',
      financialFlowType: 'outcome',
      category: 'contas',
      value: 300.99
    }
  ];
  financialFlowTypes: string[] = ['INCOME', 'OUTCOME'];
  income!: number;
  outcome!: number;
  totalBalance !: number;


  buttons = [
    {iconClass: 'fa fa-edit', title: 'Editar', route: '/transaction-edit', function: null},
    {iconClass: 'fa fa-upload', title: 'Imprimir', route: null, function: null},
    {iconClass: 'fa fa-trash', title: 'Excluir', route: null, function: this.deleteTransaction.bind(this)}
  ];

  constructor(private transactionService: TransactionService) {
  }

  ngOnInit(): void {
    this.getTransactions();
    this.calculateTotalBalance();
  }

  getTransactions(): void {
    this.transactionService.getTransactions().subscribe((transactions: Transaction[]): void => {
      this.transactions = transactions;
    });
  }

  deleteTransaction(id: any): void {
    let op: boolean = confirm('Deseja deletar a transação?');
    if (op)
      this.transactionService.deleteTransaction(id).subscribe((): void => {
        this.getTransactions();
      });
  }

  calculateTotalBalance(): void {
    this.income = this.getFinancialFlowTypeTotal(this.financialFlowTypes[0]);
    this.outcome = this.getFinancialFlowTypeTotal(this.financialFlowTypes[1]);
    this.totalBalance = this.income - this.outcome;
    console.log(this.totalBalance);
  }

  getFinancialFlowTypeTotal(financialFlowType: string): number {
    return this.transactions
      .filter((transaction: Transaction): boolean => transaction.financialFlowType.toLowerCase() === financialFlowType.toLowerCase())
      .map((transaction: Transaction) => transaction.value)
      .reduce((total: number, value: number) => total + value, 0);
  }
}
