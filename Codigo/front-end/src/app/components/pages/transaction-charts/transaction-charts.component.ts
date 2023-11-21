import {Component} from '@angular/core';
import {TransactionService} from '../../../services/transaction/transaction.service';
import {Transaction} from '../../../interfaces/Transaction';

@Component({
  selector: 'app-transaction-charts',
  templateUrl: './transaction-charts.component.html',
  styleUrls: ['./transaction-charts.component.css']
})
export class TransactionChartsComponent {

  originalTransactions!: Transaction[];
  transactions!: Transaction[];
  financialFlowTypes: string[] = ['Entrada', 'Saída'];
  income!: number;
  outcome!: number;
  totalBalance !: number;

  constructor(private transactionService: TransactionService) {
  }

  ngOnInit(): void {
    this.getTransactions();
    this.calculateTotalBalance();
  }

  getTransactions(): void {
    this.transactionService.getTransactions().subscribe((transactions: Transaction[]): void => {
      this.originalTransactions = transactions;
      this.transactions = [...this.originalTransactions];
      this.income = this.getFinancialFlowTypeTotal(this.financialFlowTypes[0]);
      this.outcome = this.getFinancialFlowTypeTotal(this.financialFlowTypes[1]);
      console.log(this.transactions)
    });
  }

  calculateTotalBalance(): void {
    this.transactionService.getTotal().subscribe((total: number):void => {
      this.totalBalance = total;
    })
  }

  getFinancialFlowTypeTotal(financialFlowType: string): number {
    return this.originalTransactions
      .filter((transaction: Transaction): boolean => transaction.financialFlowType.toLowerCase() === financialFlowType.toLowerCase())
      .map((transaction: Transaction) => transaction.value)
      .reduce((total: number, value: number) => total + value, 0);
  }
}
