import { Component } from '@angular/core';
import { TransactionService } from '../../../services/transaction/transaction.service';
import { Transaction } from '../../../interfaces/Transaction';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TransactionCreateComponent } from '../transaction-create/transaction-create.component';
import { TransactionEditComponent } from '../transaction-edit/transaction-edit.component';

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.css']
})
export class TransactionListComponent {

  tableHeaders: String[] = ['Descrição', 'Data', 'Categoria', 'Valor', 'Gerenciar'];
  originalTransactions: Transaction[] = [
    {
      id: 2,
      description: 'teste',
      date: '30/11/2023',
      financialFlowType: 'income',
      category: 'contas',
      value: 100.39
    },
    {
      id: 3,
      description: 'teste2',
      date: '20/11/2023',
      financialFlowType: 'outcome',
      category: 'supeta',
      value: 300.99
    }
  ];
  transactions: Transaction[] = [
    {
      id: 2,
      description: 'teste',
      date: '30/11/2023',
      financialFlowType: 'income',
      category: 'contas',
      value: 100.39
    },
    {
      id: 3,
      description: 'teste2',
      date: '20/11/2023',
      financialFlowType: 'outcome',
      category: 'supeta',
      value: 300.99
    }
  ];
  financialFlowTypes: string[] = ['INCOME', 'OUTCOME'];
  income!: number;
  outcome!: number;
  totalBalance !: number;

  buttons = [
    {iconClass: 'fa fa-edit', title: 'Editar', route: null, function: this.updateTransaction.bind(this)},
    {iconClass: 'fa fa-upload', title: 'Imprimir', route: null, function: null},
    {iconClass: 'fa fa-trash', title: 'Excluir', route: null, function: this.deleteTransaction.bind(this)}
  ];
  filters = [
    {name: 'categoria', function: this.sortTransactionsByCategory.bind(this)},
    {name: 'data', function: this.sortTransactionsByDate.bind(this)}
  ];
  filterText!: string;

  constructor(private transactionService: TransactionService, private ngbModal: NgbModal) {
  }

  ngOnInit(): void {
    this.getTransactions();
    this.calculateTotalBalance();
    this.filterText = this.filters[0].name;
  }

  getTransactions(): void {
    this.transactionService.getTransactions().subscribe((transactions: Transaction[]): void => {
      this.originalTransactions = transactions;
      this.transactions = [...this.originalTransactions];
      this.sortTransactionsByDate();
    });
  }

  deleteTransaction(id: any): void {
    let op: boolean = confirm('Deseja deleter a transação?');
    if (op)
      this.transactionService.deleteTransaction(id).subscribe((): void => {
        this.getTransactions();
      });
  }

  calculateTotalBalance(): void {
    this.income = this.getFinancialFlowTypeTotal(this.financialFlowTypes[0]);
    this.outcome = this.getFinancialFlowTypeTotal(this.financialFlowTypes[1]);
    this.totalBalance = this.income - this.outcome;
  }

  createTransaction(): void {
    this.ngbModal.open(TransactionCreateComponent);
  }

  updateTransaction(id: number): void {
    const modalRef: NgbModalRef = this.ngbModal.open(TransactionEditComponent);
    modalRef.componentInstance.transactionId = id;
  }

  getFinancialFlowTypeTotal(financialFlowType: string): number {
    return this.transactions
      .filter((transaction: Transaction): boolean => transaction.financialFlowType.toLowerCase() === financialFlowType.toLowerCase())
      .map((transaction: Transaction) => transaction.value)
      .reduce((total: number, value: number) => total + value, 0);
  }

  filterTransactionList(event: Event): void {

    const searchInput: HTMLInputElement = event.target as HTMLInputElement;
    const inputValue: string = searchInput.value.toLowerCase();

    this.transactions = this.originalTransactions.filter((transaction: Transaction) => {

      const descriptionMatch: boolean = transaction.description.toLowerCase().includes(inputValue);
      const dateMatch: boolean = transaction.date.includes(inputValue);

      return descriptionMatch || dateMatch;
    });
  }

  sortTransactionsByDate(): void {
    this.transactions = this.originalTransactions.sort(function (a: Transaction, b: Transaction): number {
      let dateA: string = a.date;
      let dateB: string = b.date;
      if (dateA < dateB)
        return -1;
      if (dateA > dateB)
        return 1;
      return 0;
    });
    this.updateBtnText(this.sortTransactionsByDate.name);
  }

  sortTransactionsByCategory(): void {
    this.transactions = this.originalTransactions.sort(function (a: Transaction, b: Transaction): number {
      let idA: string = a.category;
      let idB: string = b.category;
      if (idA < idB)
        return -1;
      if (idA > idB)
        return 1;
      return 0;
    });
    this.updateBtnText(this.sortTransactionsByCategory.name);
  }

  updateBtnText(funcName: string): void {
    const filter = this.filters.find(filter => filter.function.name.includes(funcName));
    this.filterText = filter ? filter.name : '';
  }
}
